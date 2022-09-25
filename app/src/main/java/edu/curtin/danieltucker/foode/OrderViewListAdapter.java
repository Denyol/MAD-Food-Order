package edu.curtin.danieltucker.foode;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Map;

import edu.curtin.danieltucker.foode.model.MenuItem;
import edu.curtin.danieltucker.foode.model.Order;

public class OrderViewListAdapter extends BasketListAdapter {

    private final Order order;

    public OrderViewListAdapter(Fragment fragment, Context context, Order order) {
        super(fragment, context);

        this.order = order;
    }

    @Override
    public void onBindViewHolder(@NonNull BasketViewHolder holder, int position) {
        Map.Entry<MenuItem, Integer> itemEntry =
                (Map.Entry<MenuItem, Integer>) order.getItems().entrySet().toArray()[position];

        holder.bind(itemEntry.getKey(), itemEntry.getValue());
    }

    @Override
    public int getItemCount() {
        return order.size();
    }
}
