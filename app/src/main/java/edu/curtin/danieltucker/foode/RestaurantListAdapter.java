package edu.curtin.danieltucker.foode;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import edu.curtin.danieltucker.foode.model.DataViewModel;
import edu.curtin.danieltucker.foode.model.Restaurant;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurants;
    private final Context context;
    private final FragmentManager fm;
    private DataViewModel dataViewModel;

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
            int res = context.getResources().getIdentifier(restaurant.getBanner(), "drawable", context.getPackageName());
            this.image.setImageResource(res);

            itemView.setOnClickListener(v -> {
                fm.beginTransaction().replace(R.id.mainFrameLayout, MenuFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                dataViewModel.setCurrentRestSelected(restaurant);
            });

            Log.d("ListAdapter", "Binding " + restaurant.getName() + " Res " + res);
        }
    }

    public RestaurantListAdapter(List<Restaurant> restaurants, Context context, Fragment fragment) {
        this.restaurants = restaurants;
        this.context = context;
        this.fm = fragment.getParentFragmentManager();

        dataViewModel = new ViewModelProvider(fragment.requireActivity()).get(DataViewModel.class);

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
