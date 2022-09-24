package edu.curtin.danieltucker.foode;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.curtin.danieltucker.foode.model.BasketViewModel;
import edu.curtin.danieltucker.foode.model.DBAdapter;
import edu.curtin.danieltucker.foode.model.MenuItem;
import edu.curtin.danieltucker.foode.model.Restaurant;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuViewHolder> {

    protected ArrayList<MenuItem> menu;
    private final Context context;
    private final Fragment fragment;
    private final LayoutInflater li;
    private final BasketViewModel basketViewModel;

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private MenuItem item;
        private ImageView imageView;
        private TextView desc;
        private TextView price;

        public MenuViewHolder(@NonNull View view) {
            super(view);

            imageView = view.findViewById(R.id.basketViewImage);
            desc = view.findViewById(R.id.orderRestTitle);
            price = view.findViewById(R.id.basketViewItemPrice);
        }

        public void bind(MenuItem item) {
            this.item = item;

            desc.setText(item.getDescription());
            price.setText("$" + item.getPrice());
            int res = context.getResources().getIdentifier(item.getResourceID(), "drawable", context.getPackageName());
            this.imageView.setImageResource(res);

            Log.d("ListAdapter", "Binding " + item.getDescription() + " Res " + res);
        }
    }

    public MenuListAdapter(Restaurant restaurant, Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;

        setMenu(restaurant);

        li = LayoutInflater.from(fragment.getContext());
        basketViewModel = new ViewModelProvider(fragment.requireActivity())
                .get(BasketViewModel.class);
    }

    protected void setMenu(Restaurant restaurant) {
        this.menu = new DBAdapter(fragment.requireActivity()).getItemsForRestaurant(restaurant);
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());

        View itemView = li.inflate(R.layout.menu_item_view, parent, false);

        return new MenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem item = menu.get(position);
        holder.bind(item);


        holder.itemView.setOnClickListener(v -> {
            View addToBasketWindow = li.inflate(R.layout.add_to_basket_view, null);

            AddToBasketPopupWindow pw = new AddToBasketPopupWindow(addToBasketWindow, item, basketViewModel);
            pw.createAddToBasketPopup(fragment.getView());
        });
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }


}
