package com.wisebirds.firebaseappeventproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private WebView webView;
    private FirebaseAnalytics firebaseAnalytics;
    private Button btnAddCart, btnPurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

//        webView = findViewById(R.id.web_view);
//        webView.loadUrl("http://www.naver.com");

        btnAddCart = findViewById(R.id.btn_add_to_cart);
        btnPurchase = findViewById(R.id.btn_purchase);

        btnAddCart.setOnClickListener(this);
        btnPurchase.setOnClickListener(this);
    }

//    @JavascriptInterface
    public void setAddToCart(String json) {
        Bundle bundle = new Bundle();

        if(TextUtils.isEmpty(json)) {
            return;
        }


        json = "{\"item_id\" : \"123456\", \"item_name\":\"nike\", \"item_category\":\"t-shirts\", \"quantity\":2}";
        try {
            JSONObject jsonObject = new JSONObject(json);
            if(!jsonObject.getString(FirebaseAnalytics.Param.ITEM_ID).isEmpty()) {
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, jsonObject.getString(FirebaseAnalytics.Param.ITEM_ID));
            }

            if(!jsonObject.getString(FirebaseAnalytics.Param.ITEM_NAME).isEmpty()) {
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, jsonObject.getString(FirebaseAnalytics.Param.ITEM_NAME));
            }

            if(!jsonObject.getString(FirebaseAnalytics.Param.ITEM_CATEGORY).isEmpty()) {
                bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, jsonObject.getString(FirebaseAnalytics.Param.ITEM_CATEGORY));
            }

            if(jsonObject.getLong(FirebaseAnalytics.Param.QUANTITY) > 0) {
                bundle.putLong(FirebaseAnalytics.Param.QUANTITY, jsonObject.getLong(FirebaseAnalytics.Param.QUANTITY));
            }

            //Optional
            bundle.putDouble(FirebaseAnalytics.Param.PRICE, 10000);
            bundle.putString(FirebaseAnalytics.Param.CURRENCY, "KRW");

            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, bundle);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @JavascriptInterface
    public void setPurchase(){
        Bundle bundle = new Bundle();
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, bundle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_to_cart :
                setAddToCart("ABD");
                break;
            case R.id.btn_purchase:
                setPurchase();
                break;
        }
    }
}
