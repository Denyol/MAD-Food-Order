package edu.curtin.danieltucker.foode;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.curtin.danieltucker.foode.model.DBAdapter;
import edu.curtin.danieltucker.foode.model.MenuItem;
import edu.curtin.danieltucker.foode.model.Restaurant;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuViewHolder> {

    private ArrayList<MenuItem> menu;
    private Restaurant restaurant;
    private final Context context;
    private final MenuFragment menuFragment;

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private MenuItem item;
        private ImageView imageView;
        private TextView desc;
        private TextView price;

        public MenuViewHolder(@NonNull View view) {
            super(view);

            imageView = view.findViewById(R.id.basketViewImage);
            desc = view.findViewById(R.id.basketViewItemDesc);
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

    public MenuListAdapter(Restaurant restaurant, Context context, MenuFragment menuFragment) {
        this.restaurant = restaurant;
        this.context = context;
        this.menuFragment = menuFragment;

        this.menu = new DBAdapter(menuFragment.requireActivity()).getItemsForRestaurant(restaurant);
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

        holder.itemView.setOnClickListener(v -> menuFragment.createAddToBasketPopup(item));
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }


}
