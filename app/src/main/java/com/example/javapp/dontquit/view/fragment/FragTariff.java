package com.example.javapp.dontquit.view.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javapp.dontquit.R;
import com.example.javapp.dontquit.adapter.SpinnerFrag2Adapter;
import com.example.javapp.dontquit.contract.TariffContract;
import com.example.javapp.dontquit.domain.TariffCNLists;
import com.example.javapp.dontquit.domain.TariffRequestBody;
import com.example.javapp.dontquit.domain.TariffEUnUSALists;
import com.example.javapp.dontquit.listener.OnSingleClickListener;
import com.example.javapp.dontquit.other.SpinnerItemRow;
import com.example.javapp.dontquit.presenter.TariffPresenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FragTariff extends Fragment implements TariffContract.View
{
    private Context mContext;
    private View mView;

    //변수
    private ArrayList<SpinnerItemRow> spinnerItemRowList;
    private String hscode="";

    private LinearLayout linearLayoutTableRoot;
    private TextView tvHscode;
    private TextView tvProductName;
    private Spinner spinner;
    private TableLayout tableLayout;

    private SpinnerFrag2Adapter spAdapter;

    //폰트
    private Typeface fontKotraBold;
    private Typeface fontKotraGothic;

    //EU
    //USA : hscode,품명,기본세율,FTA,hs6
    //CN : hs6,hscode,품명,MFN(2순위),잠정세율(2순위),APTA(1순위),FTA세율
    final String[] usaCol = new String[]{"HS 코드","품명","기본세율","FTA"};
    final String[] cnCol = new String[]{"HS 코드","품명","MFN\n(2순위)","잠정세율\n(2순위)","APTA\n(1순위)","FTA"};
    final int USAColSIZE = usaCol.length;
    final int CNColSIZE = cnCol.length;
    //MVP
    private TariffPresenter tariffPresenter;

    //Constructor
    public FragTariff(String hscode) {
        this.hscode = hscode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mContext = view.getContext();
        mView = view;

        linearLayoutTableRoot = (LinearLayout)view.findViewById(R.id.layout_frag2_table_root);
        tvProductName = (TextView)view.findViewById(R.id.tv_frag2_productname);
        tvHscode = (TextView) view.findViewById(R.id.tv_frag2_hscode);
        tvHscode.setText(hscode);
        spinner = (Spinner) view.findViewById(R.id.spinner_frag2);

        // 폰트 초기화
        fontKotraBold = ResourcesCompat.getFont(mContext,R.font.font_kotra_bold);
        fontKotraGothic = ResourcesCompat.getFont(mContext,R.font.font_kotra_gothic);

        initList();

        tariffPresenter = new TariffPresenter(this);

        spAdapter = new SpinnerFrag2Adapter(mContext,spinnerItemRowList);
        spinner.setAdapter(spAdapter);

        spinnerListener();
    }


    private void spinnerListener()
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                SpinnerItemRow clickedItem = (SpinnerItemRow) adapterView.getItemAtPosition(position);
                String clickedCountryCode = clickedItem.getCountryCode();
                Toast.makeText(mContext,clickedCountryCode,Toast.LENGTH_SHORT).show();

                //init tableLayout
                tableLayout = (TableLayout) mView.findViewById(R.id.tablelayout_frag2);
                tableLayout.setStretchAllColumns(true);

                // 서버에 요청
                tariffPresenter.requestTariffFromServer(new TariffRequestBody(hscode, clickedCountryCode));
                tvProductName.setText("품명 클릭");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void showResultEU(TariffEUnUSALists tariffEUnUSALists)
    {
        //Layout에 있는 모든 View들이 사라지게 된다.
        if (tableLayout != null )tableLayout.removeAllViews();

        createTableRowCol(usaCol);


        //=========================Values=========================//
        for (int row = 0; row < tariffEUnUSALists.getHscode().size(); row++) //size
        {
            TableRow tableRow = new TableRow(mContext);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            // hscode
            TextView tvValue1= createTextView();
            tvValue1.setTypeface(fontKotraGothic);
            tvValue1.setText(tariffEUnUSALists.getHscode().get(row));
            tableRow.addView(tvValue1);

            // 품명
            TextView tvValue2= createTextView();
            tvValue2.setTypeface(fontKotraGothic);
            String name = tariffEUnUSALists.getProductname().get(row);
            if (name.length() >= 10) name = name.substring(0,10)+ "..";
            tvValue2.setText(name);
            tableRow.addView(tvValue2);
            final String n = tariffEUnUSALists.getProductname().get(row);
            tvValue2.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    tvProductName.setText(n);
                }
            });

            // 기본세율
            TextView tvValue3= createTextView();
            tvValue3.setTypeface(fontKotraGothic);
            tvValue3.setText(tariffEUnUSALists.getDefaultRates().get(row));
            tableRow.addView(tvValue3);

            // FTA
            TextView tvValue4= createTextView();
            tvValue4.setTypeface(fontKotraGothic);
            tvValue4.setText(tariffEUnUSALists.getFta().get(row));
            tableRow.addView(tvValue4);

            tableLayout.addView(tableRow);
        }
    }

    @Override
    public void showResultUSA(TariffEUnUSALists tariffEUnUSALists)
    {
        //Layout에 있는 모든 View들이 사라지게 된다.
        if (tableLayout != null )tableLayout.removeAllViews();

        createTableRowCol(usaCol);


        //=========================Values=========================//
        for (int row = 0; row < tariffEUnUSALists.getHscode().size(); row++) //size
        {
            TableRow tableRow = new TableRow(mContext);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            // hscode
            TextView tvValue1= createTextView();
            tvValue1.setTypeface(fontKotraGothic);
            tvValue1.setText(tariffEUnUSALists.getHscode().get(row));
            tableRow.addView(tvValue1);

            // 품명
            TextView tvValue2= createTextView();
            tvValue2.setTypeface(fontKotraGothic);
            String name = tariffEUnUSALists.getProductname().get(row);
            if (name.length() >= 10) name = name.substring(0,10)+ "..";
            tvValue2.setText(name);
            tableRow.addView(tvValue2);
            final String n = tariffEUnUSALists.getProductname().get(row);
            tvValue2.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    tvProductName.setText(n);
                }
            });

            // 기본세율
            TextView tvValue3= createTextView();
            tvValue3.setTypeface(fontKotraGothic);
            tvValue3.setText(tariffEUnUSALists.getDefaultRates().get(row));
            tableRow.addView(tvValue3);

            // FTA
            TextView tvValue4= createTextView();
            tvValue4.setTypeface(fontKotraGothic);
            tvValue4.setText(tariffEUnUSALists.getFta().get(row));
            tableRow.addView(tvValue4);

            tableLayout.addView(tableRow);
        }
    }

    //CN : hscode,품명,MFN(2순위),잠정세율(2순위),APTA(1순위),FTA세율
    @Override
    public void showResultCN(TariffCNLists tariffCNLists)
    {
        //Layout에 있는 모든 View들이 사라지게 된다.
        if (tableLayout != null )tableLayout.removeAllViews();

        createTableRowCol(cnCol);


        //=========================Values=========================//
        for (int row = 0; row < tariffCNLists.getHscode().size(); row++) //size
        {
            TableRow tableRow = new TableRow(mContext);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            // hscode
            TextView tvValue1= createTextView();
            tvValue1.setTypeface(fontKotraGothic);
            tvValue1.setText(tariffCNLists.getHscode().get(row));
            tableRow.addView(tvValue1);

            // 품명
            TextView tvValue2= createTextView();
            tvValue2.setTypeface(fontKotraGothic);
            String name = tariffCNLists.getProductname().get(row);
            if (name.length() >= 10) name = name.substring(0,10)+ "..";
            tvValue2.setText(name);
            tableRow.addView(tvValue2);
            final String n = tariffCNLists.getProductname().get(row);
            tvValue2.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    tvProductName.setText(n);
                }
            });

            // MFN
            TextView tvValue3= createTextView();
            tvValue3.setTypeface(fontKotraGothic);
            tvValue3.setText(tariffCNLists.getMfn().get(row));
            tableRow.addView(tvValue3);

            // 잠정세율
            TextView tvValue4= createTextView();
            tvValue4.setTypeface(fontKotraGothic);
            tvValue4.setText(tariffCNLists.getTempTaxRates().get(row));
            tableRow.addView(tvValue4);

            // APTA
            TextView tvValue5= createTextView();
            tvValue5.setTypeface(fontKotraGothic);
            tvValue5.setText(tariffCNLists.getApta().get(row));
            tableRow.addView(tvValue5);

            // FTA
            TextView tvValue6= createTextView();
            tvValue6.setTypeface(fontKotraGothic);
            tvValue6.setText(tariffCNLists.getFta().get(row));
            tableRow.addView(tvValue6);

            tableLayout.addView(tableRow);
        }

    }

    // 테이블의 속성 row 생성
    private void createTableRowCol(String[] columns)
    {
        TableRow tableRowCol = new TableRow(mContext);
        tableRowCol.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        //=========================속성값=========================//
        for(String column : columns) //
        {
            TextView textViewColumn = createTextView();
            textViewColumn.setText(column);
            textViewColumn.setTypeface(fontKotraBold);
            tableRowCol.addView(textViewColumn);
        }
        tableLayout.addView(tableRowCol);
    }

    private TextView createTextView()
    {
        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(16);
        textView.setTextColor(ContextCompat.getColor(mContext,R.color.black));
        textView.setPadding(0,5,0,5);
        return textView;
    }

    private void initList()
    {
        spinnerItemRowList = new ArrayList<>();
        spinnerItemRowList.add(new SpinnerItemRow("EU", R.drawable.ic_eu));
        spinnerItemRowList.add(new SpinnerItemRow("USA", R.drawable.ic_usa));
        spinnerItemRowList.add(new SpinnerItemRow("CN", R.drawable.ic_china));
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onResponseFailure(String message) {
        tvProductName.setText(message);
    }
}