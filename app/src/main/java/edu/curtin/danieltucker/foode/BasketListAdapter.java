package edu.curtin.danieltucker.foode;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import edu.curtin.danieltucker.foode.model.BasketViewModel;
import edu.curtin.danieltucker.foode.model.MenuItem;

public class BasketListAdapter extends RecyclerView.Adapter<BasketListAdapter.BasketViewHolder> {

    private BasketViewModel basket;

    public class BasketViewHolder extends RecyclerView.ViewHolder {

        private MenuItem item;

        public BasketViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public BasketListAdapter(BasketFragment basketFragment) {
        this.basket = new ViewModelProvider(basketFragment.getActivity()).get(BasketViewModel.class);
    }

    public void setObserve(LifecycleOwner owner) {
        basket.getRestaurant().observe(owner, o -> onBasketChanged());
    }

    private void onBasketChanged() {
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BasketViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return basket.getBasket().getValue().size();
    }


}
