package edu.curtin.danieltucker.foode;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import edu.curtin.danieltucker.foode.model.Restaurant;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {

    private ArrayList<Restaurant> restaurants;
    private final Context context;

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private Restaurant restaurant;
        private TextView name;
        private ShapeableImageView image;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.restaurantTitle);
            this.image = itemView.findViewById(R.id.restaurantImageView);
        }

        public void bind(Restaurant restaurant) {
            this.restaurant = restaurant;

            name.setText(restaurant.getName());
            int res = context.getResources().getIdentifier(restaurant.getResourceId(), "drawable", context.getPackageName());
            this.image.setImageResource(res);

            Log.d("ListAdapter", "Binding " + restaurant.getName() + " Res " + res);
        }
    }

    public RestaurantListAdapter(ArrayList<Restaurant> restaurants, Context context) {
        this.restaurants = restaurants;
        this.context = context;

        Log.d("ListAdapter", "Created data size " + restaurants.size());
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());

        View restaurantView = li.inflate(R.layout.restaurant_item_view, parent, false);

        return new RestaurantViewHolder(restaurantView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.bind(restaurants.get(position));
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }


}
