package org.tyaa.notebookandroid.fetchr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tyaa.notebookandroid.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 18.01.18.
 */

public class JsonParser {

    public List<Order> parseOrders(JSONArray _jsonArray) throws JSONException {

        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < _jsonArray.length(); i++){

            JSONObject orderJSONObject =
                    _jsonArray.getJSONObject(i);

            Order order = new Order();
            order.setId(orderJSONObject.getLong("id"));
            order.setText(orderJSONObject.getString("text"));
            order.setCustomer(orderJSONObject.getString("customer"));
            orders.add(order);
        }

        return orders;
    }
}
