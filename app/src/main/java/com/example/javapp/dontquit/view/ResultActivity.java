package com.example.javapp.dontquit.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javapp.dontquit.R;
import com.example.javapp.dontquit.contract.ProductListContract;
import com.example.javapp.dontquit.domain.Categories;
import com.example.javapp.dontquit.domain.Category;
import com.example.javapp.dontquit.domain.Hscodes;
import com.example.javapp.dontquit.domain.Product;
import com.example.javapp.dontquit.domain.ProductName;
import com.example.javapp.dontquit.listener.OnSingleClickListener;
import com.example.javapp.dontquit.network.ApiClient;
import com.example.javapp.dontquit.network.Apiinterface;
import com.example.javapp.dontquit.other.Loading;
import com.example.javapp.dontquit.presenter.ProductListPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;

public class ResultActivity extends AppCompatActivity implements ProductListContract.View
{
    // 변수
    private Context mContext;
    private Intent getIntent;
    private String query="";
    private Hscodes hscodes;
    private Set<String> hs6set;                     // 6단위 그룹화

    private List<Product> products;                 // 서버에서 받아온 추천 코드들
    private Categories categories;

    private Map<String,LinearLayout> hs6layoutMap;  // 동적 레이아웃
    private Map<String,TextView> hs6contentMap;     // 동적

    // 폰트
    private Typeface fontKotraBold;
    private Typeface fontKotraGothic;

    // MVP
    private ProductListPresenter presenter;

    // View
    private LinearLayout mainLinearLayout;
    private TextView tvResultQuery;
    private TextView tvResultError;

    // 쓰레드 처리
    //화면 처리용 핸들러
    public DisplayHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        init();
    }

    private void init()
    {
        handler = new DisplayHandler();
        mContext = this;
        getIntent = getIntent();
        query = getIntent.getStringExtra("query");

        hs6layoutMap = new HashMap<>();
        hs6contentMap= new HashMap<>();
        hscodes = new Hscodes();

        //프레젠터
        presenter = new ProductListPresenter(this);
        presenter.requestProductDataFromServer(new ProductName(query));

        // element 초기화
        mainLinearLayout = (LinearLayout) findViewById(R.id.layout_main);
        tvResultQuery = (TextView)findViewById(R.id.tv_result_query);
        tvResultQuery.setText(query);
        tvResultError = (TextView)findViewById(R.id.tv_result_error);

        // 폰트 초기화
        fontKotraBold = ResourcesCompat.getFont(this,R.font.font_kotra_bold);
        fontKotraGothic = ResourcesCompat.getFont(this,R.font.font_kotra_gothic);
    }


    // 서버에서 값을 받아온다.
    @Override
    public void showResult(List<Product> products)
    {
        Log.v("받았는지 확인", products.get(0).getHs6());
        this.products =products;
        hs6set = new LinkedHashSet<>();
        for(Product product:products) hs6set.add(product.getHs6());

        createHscode6Units();
        // Hscodes 카테고리 TextView 생성
        createHscode10UnitsStartThread();
    }

    private void createHscode6Units()
    {
        // linearLayout params 정의
        LinearLayout.LayoutParams hs6LayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        hs6LayoutParams.setMargins(0,10,0,10);
        // linearLayout params 정의
        LinearLayout.LayoutParams hs10LayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        for(String hs6 : hs6set)
        {
            // 루트
            LinearLayout rootLayout = new LinearLayout(this);
            rootLayout.setLayoutParams(hs6LayoutParams);
            rootLayout.setOrientation(LinearLayout.VERTICAL);

            // hs6 와 설명
            LinearLayout hs6innerLayout = new LinearLayout(this);
            hs6innerLayout.setOrientation(LinearLayout.VERTICAL);
            hs6innerLayout.setLayoutParams(hs6LayoutParams);

            // 추천된 10단위와 설명
            LinearLayout hs10innerLayout = new LinearLayout(this);
            hs10innerLayout.setOrientation(LinearLayout.HORIZONTAL);
            hs10innerLayout.setLayoutParams(hs10LayoutParams);

            // view params 정의
            LinearLayout.LayoutParams viewparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1);
            viewparams.setMargins(0,15,0,15);
            //  mainLinearLayout
            View view = new View(mContext);
            view.setBackgroundColor(ContextCompat.getColor(mContext,R.color.purple_200));
            view.setLayoutParams(viewparams);

            TextView tvHs6 = new TextView(this);
            tvHs6.setLayoutParams(hs6LayoutParams);
            tvHs6.setText(hs6);
            tvHs6.setTextColor(ContextCompat.getColor(this,R.color.black));
            tvHs6.setTextSize(20);
            tvHs6.setTypeface(fontKotraBold);

            TextView tvHs6Content = new TextView(this);
            tvHs6Content.setLayoutParams(hs6LayoutParams);
            tvHs6Content.setText("...");
            tvHs6Content.setTextColor(ContextCompat.getColor(this,R.color.black));
            tvHs6Content.setTextSize(16);
            tvHs6Content.setTypeface(fontKotraGothic);

            // hs6을 키로 저장
            hs6contentMap.put(hs6,tvHs6Content); // 동적으로 6단위 해설 set
            hs6layoutMap.put(hs6,rootLayout);    // 동적으로 10단위 set

            // 추가하는 부분
            hs6innerLayout.addView(view);
            hs6innerLayout.addView(tvHs6);
            hs6innerLayout.addView(tvHs6Content);
            rootLayout.addView(hs6innerLayout);
            mainLinearLayout.addView(rootLayout);
        }
    }

    // 카테고리 TextView 생성
    private void createHscode10UnitsStartThread()
    {
        for(Product product : this.products) hscodes.addHscodes(product.getHscode());

        Log.v("hscodes",hscodes.getHscodes().get(0));


        Runnable runnable =new Runnable() {
            @Override
            public void run()
            {
                // 카테고리를 동기로 얻어온다.
                Call<Categories> call = ApiClient.getInstance().create(Apiinterface.class).getCategories(hscodes);
                try {
                    categories = call.execute().body();

                    // UI 동적으로 수정
                    handler.sendEmptyMessage(0);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    // 6단위 content, 10단위 hscode 생성
    class DisplayHandler extends Handler
    {
        //카테고리 데이터로 해야될텐데

        @Override
        public void handleMessage(@NonNull Message msg)
        {
            super.handleMessage(msg);

            // inlayout params 정의
            LinearLayout.LayoutParams inlayoutparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            // iv params 정의
            LinearLayout.LayoutParams ivparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);


            // tv params 정의
            LinearLayout.LayoutParams tvparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tvparams.setMargins(10,10,10,10);

            Map<String,Boolean>ischeck = new HashMap<>();
            for(int i =0; i < categories.getHs6().size(); i++)
            {
                // 6단위 설명
                String hs6=categories.getHs6().get(i);
                if(ischeck.get(hs6) ==null || !ischeck.get(hs6) ) // 안했다면
                {
                    hs6contentMap.get(hs6).setText(categories.getHs6content().get(i));

                // 10단위 생성
                    for (String hscode : categories.getHscodes())
                    {
                        if (hs6.equals(hscode.substring(0, 6)))
                        {
                            int idx = categories.getHscodes().indexOf(hscode);
                            ischeck.put(hs6, true);

                            LinearLayout inlayout = new LinearLayout(mContext);
                            inlayout.setOrientation(LinearLayout.HORIZONTAL);
                            inlayout.setLayoutParams(inlayoutparams);

                            ImageView ivSearch = new ImageView(mContext);
                            ivSearch.setLayoutParams(ivparams);
                            ivSearch.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_search));
                            ivSearch.setForegroundGravity(Gravity.CENTER);

                            TextView tvHs10 = new TextView(mContext);
                            tvHs10.setLayoutParams(tvparams);
                            tvHs10.setText(hscode);
                            tvHs10.setTextColor(ContextCompat.getColor(mContext,R.color.black));
                            tvHs10.setTextSize(16);
                            tvHs10.setTypeface(fontKotraGothic);

                            TextView tvHscontent = new TextView(mContext);
                            tvHscontent.setLayoutParams(tvparams);
                            tvHscontent.setText(categories.getUnitNames().get(idx));
                            tvHscontent.setTextColor(ContextCompat.getColor(mContext,R.color.black));
                            tvHscontent.setTextSize(16);
                            tvHscontent.setTypeface(fontKotraGothic);

                            // 추가
                            inlayout.addView(ivSearch); inlayout.addView(tvHs10); inlayout.addView(tvHscontent);
                            LinearLayout rootlayout = hs6layoutMap.get(hs6);
                            rootlayout.addView(inlayout);


                            //버튼
                            inlayout.setOnClickListener(new OnSingleClickListener() {
                                @SuppressLint("ResourceType")
                                @Override
                                public void onSingleClick(View v) {
                                    Toast.makeText(mContext,tvHs10.getText(),Toast.LENGTH_SHORT).show();
                                    Intent sendIntent = new Intent(getApplicationContext(),TabActivity.class);
                                    sendIntent.putExtra("data",new Category(
                                            categories.getHscodes().get(idx),categories.getHs6content().get(idx),categories.getUnitNames().get(idx),categories.getDivinityNames().get(idx)));
                                    startActivity(sendIntent);
                                }
                            });
                        }
                    }

                }
            }

        }
    }


    @Override
    public void showProgress() {
        Loading.showLoading(this,true);
    }

    @Override
    public void hideProgress() {
        Loading.showLoading(this,false);

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,"검색결과 없음",Toast.LENGTH_LONG);

        Runnable runnable =new Runnable() {
            @Override
            public void run()
            {
                try {
                    Call<String> callWords = ApiClient.getInstance().create(Apiinterface.class).getWordsHypernyms(new ProductName(query));

                    String resultMsg = callWords.execute().body();
                    // runOnUiThread를 추가하고 그 안에 UI작업을 한다.

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvResultError.setText("검색결과 없음\n\n\n"+resultMsg);
                        }
                    });

                } catch (IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvResultError.setText("검색결과 없음");
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();

    }

    @Override
    public void onResponseFailure(Throwable throwable) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
