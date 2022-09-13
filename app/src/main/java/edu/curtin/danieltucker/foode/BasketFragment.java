package edu.curtin.danieltucker.foode;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.curtin.danieltucker.foode.model.BasketViewModel;
import edu.curtin.danieltucker.foode.model.Restaurant;


public class BasketFragment extends Fragment {

    private BasketViewModel basket;

    public BasketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        basket = new ViewModelProvider(getActivity()).get(BasketViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        RecyclerView rv = view.findViewById(R.id.basketRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        BasketListAdapter basketListAdapter = new BasketListAdapter(this);
        basketListAdapter.setObserve(this.getViewLifecycleOwner());

        rv.setAdapter(basketListAdapter);

        basket.getRestaurant().observe(getViewLifecycleOwner(), new Observer<Restaurant>() {
            @Override
            public void onChanged(Restaurant restaurant) {
                Log.d("Basket Fragment", "Restaurant changed");
            }
        });

        return view;
    }
}