package edu.curtin.danieltucker.foode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import edu.curtin.danieltucker.foode.model.BasketViewModel;
import edu.curtin.danieltucker.foode.model.MenuItem;

public class BasketListAdapter extends RecyclerView.Adapter<BasketListAdapter.BasketViewHolder> {

    private BasketViewModel basket;
    private Context context;
    private final Fragment fragment;

    public class BasketViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView desc;
        private TextView price;
        private TextView quantity;

        public BasketViewHolder(@NonNull View itemView) {
            super(itemView);

            desc = itemView.findViewById(R.id.orderRestTitle);
            imageView = itemView.findViewById(R.id.basketViewImage);
            price = itemView.findViewById(R.id.basketViewItemPrice);
            quantity = itemView.findViewById(R.id.basketViewItemCount);
        }

        public void bind(MenuItem item, int quantity) {
            desc.setText(item.getDescription());
            price.setText("$" + item.getPrice());
            int res = context.getResources().getIdentifier(item.getResourceID(), "drawable", context.getPackageName());
            imageView.setImageResource(res);
            this.quantity.setText(String.valueOf(quantity));
        }
    }

    public BasketListAdapter(BasketFragment basketFragment, Context context) {
        this.basket = new ViewModelProvider(basketFragment.requireActivity()).get(BasketViewModel.class);
        this.context = context;
        this.fragment = basketFragment;
    }

    public void setObserve(LifecycleOwner owner) {
        basket.getRestaurantData().observe(owner, o -> onBasketChanged());
    }

    private void onBasketChanged() {
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());

        View itemView = li.inflate(R.layout.basket_item_view, parent, false);

        return new BasketViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketViewHolder holder, int position) {
        Map.Entry<MenuItem, Integer> item = (Map.Entry<MenuItem, Integer>) basket.getBasket().entrySet().toArray()[position];
        holder.bind(item.getKey(), item.getValue());

        holder.itemView.setOnClickListener(v -> {
            LayoutInflater li = LayoutInflater.from(context);
            View addToBasketWindow = li.inflate(R.layout.add_to_basket_view, null);

            AddToBasketPopupWindow pw = new AddToBasketPopupWindow(addToBasketWindow, item.getKey(), basket);
            pw.createAddToBasketPopup(fragment.getView());
        });
    }

    @Override
    public int getItemCount() {
        return basket.getBasket().size();
    }


}
