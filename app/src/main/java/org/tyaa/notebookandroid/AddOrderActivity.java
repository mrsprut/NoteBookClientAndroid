package org.tyaa.notebookandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.tyaa.notebookandroid.model.Order;

import java.util.List;

public class AddOrderActivity extends AppCompatActivity {

    private Context mContext;

    private EditText mCustomerEditText;
    private EditText mTaskEditText;
    private ImageButton mAddOrderImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        mContext = this;

        mCustomerEditText = findViewById(R.id.customerEditText);
        mTaskEditText = findViewById(R.id.taskEditText);
        mAddOrderImageButton = findViewById(R.id.addOrderButton);

        mAddOrderImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String urlString =
                        "https://notebookgae.appspot.com/hello?action=create_order&customer_name="
                                + mCustomerEditText.getText()
                                + "&description="
                                + mTaskEditText.getText();

                RequestQueue queue = Volley.newRequestQueue(mContext);
                JsonObjectRequest jsonArrayRequest =

                        new JsonObjectRequest(
                                urlString
                                , new JSONObject()
                            , new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("my", response.toString());
                                    MainActivity.refreshOrdersList();
                                    finish();
                                }
                            }
                            , new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("my", error.toString());
                                }
                            }
                        );
                queue.add(jsonArrayRequest);
            }
        });
    }
}
