package org.tyaa.notebookandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.tyaa.notebookandroid.AddOrderActivity;
import org.tyaa.notebookandroid.MainActivity;
import org.tyaa.notebookandroid.OrderDetailsActivity;
import org.tyaa.notebookandroid.R;
import org.tyaa.notebookandroid.model.Order;

import java.util.List;

/**
 * Created by student on 13.01.2018.
 */

public class NotesListAdapter extends ArrayAdapter<Order> {

    private List<Order> mOrders;
    private Context mContext;
    private int mResource;

    //private LayoutInflater mInflater;

    public NotesListAdapter(@NonNull Context context, int resource, List<Order> _orders) {

        super(context, resource, _orders);

        mOrders = _orders;
        mContext = context;
        mResource = resource;

        //mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;// = convertView;
        //if(view == null) {
            LayoutInflater inflater = ((MainActivity) mContext).getLayoutInflater();
            view = inflater.inflate(mResource, parent, false);
        //}

        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(mContext, mOrders.get(position).getCustomer(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                intent.putExtra(MainActivity.DETAILS_EXTRA, mOrders.get(position).getText());
                ((MainActivity) mContext)
                        .startActivityForResult(intent,MainActivity.DETAILS_REQUEST);
            }
        });*/

        //Клик по пункту списка заданий
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final CharSequence[] items = {
                        "details"
                        , "edit"
                        , "remove"
                };

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(mContext);
                builder.setTitle("Actions:");
                //Клик по пункту контекстного меню, вызванного для одного задания
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (items[i].equals("details")) {

                            Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                            intent.putExtra(MainActivity.DETAILS_EXTRA, mOrders.get(position).getText());
                            ((MainActivity) mContext)
                                    .startActivityForResult(intent,MainActivity.DETAILS_REQUEST);
                        } else if (items[i].equals("edit")) {

                            Intent intent = new Intent(mContext, AddOrderActivity.class);
                            intent.putExtra(
                                    MainActivity.ADD_EDIT_EXTRA
                                    , MainActivity.EDIT_ORDER_REQUEST
                            );
                            //Идентификатор выбранного пункта в списке заказов
                            Long id =
                                    mOrders.get(position).getId();
                            intent.putExtra(
                                    MainActivity.ORDER_ID_EXTRA
                                    , id
                            );
                            ((MainActivity) mContext)
                                    .startActivityForResult(intent, MainActivity.EDIT_ORDER_REQUEST);
                        } else if (items[i].equals("remove")) {

                            //Идентификатор выбранного пункта в списке заказов
                            Long id =
                                    mOrders.get(position).getId();
                            //Log.d("remove_order", String.valueOf(id));
                            //Строка http-запроса, удаляющего из БД данные о заказе
                            //с указанным идентификатором
                            String urlString =
                                    "https://notebookgae.appspot.com/hello?action=delete_order&id="
                                            + String.valueOf(id);
                            //Log.d("remove_order", urlString);

                            //long x = 5086441721823232L;
                            //int y = (int)x;
                            //Log.d("test1", String.valueOf(x));
                            //Log.d("test2", String.valueOf(y));

                            //Создаем очередь http-запросов
                            RequestQueue queue = Volley.newRequestQueue(mContext);
                            //Создаем объект запроса с обработчиками
                            //успешного выполнения и ошибки
                            StringRequest stringRequest =
                                    new StringRequest(
                                            StringRequest.Method.GET
                                            , urlString
                                            , new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {

                                                    //Отображаем сообщение об успешном удалении
                                                    //заказа из удаленной БД
                                                    Toast
                                                        .makeText(mContext, "Order deleted", Toast.LENGTH_LONG)
                                                        .show();
                                                    //Обновляем список заказов в представлении
                                                    ((MainActivity) mContext).refreshOrdersList();
                                                }
                                            }
                                            , new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast
                                                        .makeText(mContext, "Error", Toast.LENGTH_LONG)
                                                        .show();
                                                }
                                            }
                                    );
                            //Добавляем объект запроса в очередь на выполнение,
                            //при этом он стартует
                            queue.add(stringRequest);
                        }
                    }
                });
                builder.show();
                return false;
            }
        });

        TextView customerTextView = view.findViewById(R.id.customerTextView);
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);

        customerTextView.setText("Customer: " + mOrders.get(position).getCustomer());
        descriptionTextView.setText(
                "Task: " +
                (mOrders.get(position).getText().length() > 25
                ? mOrders.get(position).getText().substring(0, 25) + " ..."
                : mOrders.get(position).getText())
        );

        return view;
    }


}
