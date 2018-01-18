package org.tyaa.notebookandroid.fetchr;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.tyaa.notebookandroid.interfaces.IFetchedDataHandler;
import org.tyaa.notebookandroid.interfaces.IFetchr;
import org.tyaa.notebookandroid.model.Order;

import java.util.List;

/**
 * Created by yurii on 18.01.18.
 */

public class JsonFetchr implements IFetchr {

    private IFetchedDataHandler mFetchedDataHandler;

    public JsonFetchr(IFetchedDataHandler _fetchedDataHandler){

        mFetchedDataHandler = _fetchedDataHandler;
    }

    @Override
    public void fetch(Object _args) {

        String urlString = _args.toString();

        RequestQueue queue = Volley.newRequestQueue((Context)mFetchedDataHandler);
        Log.d("my", urlString);
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(
                        urlString
                        , new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                Log.d("my", response.toString());
                                List<Order> orders = null;
                                try {
                                    orders = new JsonParser().parseOrders(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mFetchedDataHandler.onOrdersFetched(orders);
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
}
