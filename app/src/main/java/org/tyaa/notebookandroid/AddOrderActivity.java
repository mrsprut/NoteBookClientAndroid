package org.tyaa.notebookandroid;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.tyaa.notebookandroid.model.Order;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddOrderActivity extends AppCompatActivity {

    private Context mContext;

    //private EditText mCustomerEditText;
    //private EditText mTaskEditText;
    //private ImageButton mAddOrderImageButton;

    @BindView(R.id.customerEditText)
    EditText mCustomerEditText;

    @BindView(R.id.taskEditText)
    EditText mTaskEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        mContext = this;

        //mCustomerEditText = findViewById(R.id.customerEditText);
        //mTaskEditText = findViewById(R.id.taskEditText);
        //mAddOrderImageButton = findViewById(R.id.addOrderButton);

        setResult(RESULT_CANCELED);

        //Активируем процессор аннотаций ButterKnife,
        //чтобы произошли инициализации виджетов
        //и установки обработчиков событий
        ButterKnife.bind((Activity) mContext);

        /*mAddOrderImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String urlString =
                        "https://notebookgae.appspot.com/hello?action=create_order&customer_name="
                                + mCustomerEditText.getText()
                                + "&description="
                                + mTaskEditText.getText();

                RequestQueue queue = Volley.newRequestQueue(mContext);

                StringRequest stringRequest =
                        new StringRequest(
                                StringRequest.Method.GET
                                , urlString
                                , new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        clearEditTexts();
                                        setResult(RESULT_OK);
                                        Toast
                                            .makeText(mContext, "Order added", Toast.LENGTH_LONG)
                                            .show();
                                        finish();
                                    }
                                }
                                , new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        clearEditTexts();
                                        Toast
                                            .makeText(mContext, "Error", Toast.LENGTH_LONG)
                                            .show();
                                    }
                                }
                        );

                queue.add(stringRequest);
            }
        });*/
    }

    private void clearEditTexts(){

        mCustomerEditText.setText("");
        mTaskEditText.setText("");
    }

    @OnClick(R.id.addOrderButton)
    public void addOrder(View view) {

        String urlString =
                "https://notebookgae.appspot.com/hello?action=create_order&customer_name="
                        + mCustomerEditText.getText()
                        + "&description="
                        + mTaskEditText.getText();

        RequestQueue queue = Volley.newRequestQueue(mContext);

        StringRequest stringRequest =
                new StringRequest(
                        StringRequest.Method.GET
                        , urlString
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        clearEditTexts();
                        setResult(RESULT_OK);
                        Toast
                                .makeText(mContext, "Order added", Toast.LENGTH_LONG)
                                .show();
                        finish();
                    }
                }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        clearEditTexts();
                        Toast
                                .makeText(mContext, "Error", Toast.LENGTH_LONG)
                                .show();
                    }
                }
                );

        queue.add(stringRequest);
    }
}
