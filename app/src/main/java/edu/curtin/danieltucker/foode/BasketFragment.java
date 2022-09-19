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
import android.widget.TextView;

import edu.curtin.danieltucker.foode.model.BasketViewModel;
import edu.curtin.danieltucker.foode.model.Restaurant;


public class BasketFragment extends Fragment {

    private BasketViewModel basket;
    private TextView basketTitle;
    private TextView total;

    public BasketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        basket = new ViewModelProvider(requireActivity()).get(BasketViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        RecyclerView rv = view.findViewById(R.id.basketRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        BasketListAdapter basketListAdapter = new BasketListAdapter(this, requireContext());
        basketListAdapter.setObserve(this.getViewLifecycleOwner());

        basketTitle = view.findViewById(R.id.basketStoreText);
        total = view.findViewById(R.id.basketTotalText);

        rv.setAdapter(basketListAdapter);

        basket.getBasketData().observe(getViewLifecycleOwner(), e -> changeOccur(basketListAdapter));
        basket.getRestaurantData().observe(getViewLifecycleOwner(), e -> setTitleAndTotal());

        // Initialise title and checkout button.
        setTitleAndTotal();

        return view;
    }

    private void setTitleAndTotal() {
        String name = basket.getRestaurantName();
        basketTitle.setText(name == null ? "Empty Basket" : name + " Basket");
        total.setText(String.format("Total: $%.2f", basket.getTotal()));
    }

    private void changeOccur(RecyclerView.Adapter a) {
        a.notifyDataSetChanged();
        setTitleAndTotal();
    }
}