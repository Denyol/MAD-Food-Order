package edu.curtin.danieltucker.foode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.curtin.danieltucker.foode.model.Order;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.OrdersViewHolder> {

    private List<Order> orders;
    private Fragment f;
    private ImageView image;
    private TextView title;
    private TextView subtitle;

    public class OrdersViewHolder extends RecyclerView.ViewHolder {
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.orderRestIcon);
            title = itemView.findViewById(R.id.orderRestTitle);
            subtitle = itemView.findViewById(R.id.orderSubtitle);
        }

        public void bind(Order order) {
            Context context = f.getContext();
            int res = context.getResources().getIdentifier(order.getRestaurant().getBanner(),
                    "drawable", context.getPackageName());
            image.setImageResource(res);
            title.setText(order.getRestaurant().getName());
            subtitle.setText(String.format("%d items | $%.2f", order.size(), order.value()));
        }
    }

    public OrdersListAdapter(List<Order> orders, Fragment fragment) {
        this.orders = orders;
        this.f = fragment;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrdersViewHolder(LayoutInflater.from(f.getContext()).inflate(R.layout.order_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        holder.bind(orders.get(position));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


}
