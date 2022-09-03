package edu.curtin.danieltucker.foode;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.curtin.danieltucker.foode.model.FoodeData;
import edu.curtin.danieltucker.foode.model.Restaurant;
import edu.curtin.danieltucker.foode.model.RestaurantAdapter;

public class RestaurantsFragment extends Fragment {

    private FoodeData data;

    public RestaurantsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //data = new ViewModelProvider(requireActivity()).get(FoodeData.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_resturants, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.restaurantsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Restaurant> restaurants = new RestaurantAdapter(requireContext()).getRestaurants();
        recyclerView.setAdapter(new RestaurantListAdapter(restaurants, getContext()));

        return v;
    }
}