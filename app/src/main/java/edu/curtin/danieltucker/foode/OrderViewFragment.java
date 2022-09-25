package edu.curtin.danieltucker.foode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import edu.curtin.danieltucker.foode.model.Order;


public class OrderViewFragment extends Fragment {

    private static final String ORDER_PARAM = "ORDER";

    private Order order;
    private TextView title;

   public static OrderViewFragment newInstance(Order order) {
        OrderViewFragment fragment = new OrderViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ORDER_PARAM, order);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            order = (Order) getArguments().getSerializable(ORDER_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_view, container, false);

        title = v.findViewById(R.id.orderDetails);

        RecyclerView rv = v.findViewById(R.id.orderDetailsRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        rv.setAdapter(new OrderViewListAdapter(this, getContext(), order));

        setTitle();

        return v;
    }

    private void setTitle() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(order.getDate());

        title.setText(String.format("Your Order | $%.2f %s", order.getTotal(), date));
    }
}