# HS 품목 분류 결정 및 추천 안드로이드 어플리케이션

## 팀명 : Don't Quit

#### 빅리더 AI 아카데미 에서 진행한 *코트라(대한무역투자진흥공사)* 협업 프로젝트
[BIG 빅리더](https://bigleader.net/) <br>
[팀 영상](https://www.youtube.com/watch?v=yN5rmja-LNo&t=1s)

## 1. **프로젝트 동기**
    * HSCODE 란
      * 국제 통일 분류 체계
      * 최대 10자리로 이루어져있다.
      * 관세율과 밀접한 관계가 있다.
      * 수출입 통관 서류에서 매우 중요한 역할을 한다.
    * 문제 현황
      * 현재 KOTRA의 HSCODE 검색 사이트에서 실거래 품명으로 검색시 원하는 HSCODE 결과를 얻기 어렵다.
코트라 HS코드 검색 사이트 <br>
![image](https://user-images.githubusercontent.com/58140426/133915191-df2e4105-da18-4d7a-802f-3da2f4b4e5e2.png)

## 2. Needs & Solution
   * 실제 품목 분류 사례 데이터를 바탕으로 현장용어로도 손쉽게 HSCODE 찾을 수 있다.

## 3. 프로젝트 목표
   * 누구나 쉽게 HSCODE를 찾을 수 있도록 HSCODE 네비게이터 역할

## 4. Tech stack
   - Android Studio Arctic Fox
   - Java8
   - MVP 패턴
   - REST API - Retrofit2

## 5. 시연 <br>

### 검색
<img src="https://user-images.githubusercontent.com/58140426/133915435-8883fe8e-781f-431c-a1c1-6648e2c66999.gif" width="300" height="690"> 

### 검색결과
<img src="https://user-images.githubusercontent.com/58140426/133915443-6477c065-399a-44d7-9cea-38d184d6dd49.gif"  width="300" height="690">
 
## 6. 기능 구현
### MVP 패턴 적용

프로젝트 파일 구조<br>
![image](https://user-images.githubusercontent.com/58140426/133915756-bff2f445-b63b-49aa-acb0-0badc2923827.png)

기능 구성에 MVP 패턴이 적용된 구조<Br>
![MVP 패턴](https://user-images.githubusercontent.com/58140426/133915736-882d01f9-1086-4992-9650-d559b714f609.jpg)

### retrofit2 + MVP 패턴 적용

- 예시) 검색 결과 
<br>
Contract 인터페이스 작성 

   
~~~java
public interface ProductListContract
{
    interface Model
    {
        interface onFinishedListener
        {
            void onFinished(List<Product> products);

            void onFailure(String message);
        }

        void getProductList(onFinishedListener onFinishedListener, ProductName productName);
    }

    interface View
    {
        void showResult(List<Product> products);
        void showProgress();
        void hideProgress();
        void showToast(String message);
        void onResponseFailure(Throwable throwable);
    }

    interface Presenter
    {
        void onDestroy();
        void requestProductDataFromServer(ProductName productName);
    }
}
~~~

검색 결과 Model 클래스 작성
~~~java
public class ProductListModel implements ProductListContract.Model
{

    List<Product> products  = new ArrayList<>();
    // retrofit2 싱글톤 객체 생성
    final Apiinterface service = ApiClient.getInstance().create(Apiinterface.class);

    @Override
    public void getProductList(onFinishedListener onFinishedListener, ProductName productName)
    {

        
        Call<List<Product>>call = service.getProduct(productName);
        // api 서버 비동기 요청
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response)
            {
                if(!response.isSuccessful()){
                    onFinishedListener.onFailure(
                            response.message()
                    );
                    return;
                }
                // 통신 성공
                products =response.body();
                onFinishedListener.onFinished(products);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // presenter 통신 실패 함수 호출

                onFinishedListener.onFailure(t.getMessage());
                Log.v("onFailure",t.getMessage());
            }

        });
    }
}
~~~
  
검색 결과 Presenter 클래스 작성
~~~java
 public class ProductListPresenter implements ProductListContract.Presenter, ProductListContract.Model.onFinishedListener
{
    private ProductListContract.View view;
    private ProductListContract.Model model;

    public ProductListPresenter(ProductListContract.View view) {
        this.view = view;
        this.model = new ProductListModel();
    }

    // Model
    @Override
    public void onFinished(List<Product> products)
    {
        // view 존재 (소멸) 체크
        if(view != null){
            view.hideProgress();
            view.showResult(products);
        }
    }
    //model
    @Override
    public void onFailure(String message) {
        if(view != null){
            view.hideProgress();
            view.showToast(message);
        }
    }

    //view
    @Override
    public void requestProductDataFromServer(ProductName productName)
    {
        if(view != null){
            view.showProgress();
        }
        model.getProductList(this,productName);
    }

    @Override
    public void onDestroy() {
        view=null;
    }
}
~~~
   
검색결과 View 작성

~~java 
   
public class ResultActivity extends AppCompatActivity implements ProductListContract.View
{
    // MVP
    private ProductListPresenter presenter;
    private List<Product> products;
    //  ...
   
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        init();
    }
   
    private void init()
    {
        ...
   
        //프레젠터
        presenter = new ProductListPresenter(this);
        presenter.requestProductDataFromServer(new ProductName(query)); //서버에 데이터 요청

        ...
    }
   
    // 서버에서 값을 받아온다.
    @Override
    public void showResult(List<Product> products)
    {
        Log.v("받았는지 확인", products.get(0).getHs6());
        this.products =products;
        ...
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
         ...

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
~~~
