package edu.curtin.danieltucker.foode;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import edu.curtin.danieltucker.foode.model.BasketViewModel;
import edu.curtin.danieltucker.foode.model.DBAdapter;
import edu.curtin.danieltucker.foode.model.DataViewModel;
import edu.curtin.danieltucker.foode.model.MenuItem;
import edu.curtin.danieltucker.foode.model.Restaurant;


public class MenuFragment extends Fragment {

    private BasketViewModel basketViewModel;
    private DataViewModel dataViewModel;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        basketViewModel = new ViewModelProvider(requireActivity()).get(BasketViewModel.class);
        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        RecyclerView rv = v.findViewById(R.id.menuRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        Restaurant rest = dataViewModel.getCurrentRestSelected();

        rv.setAdapter(new MenuListAdapter(rest, getContext(), this));

        return v;
    }

    public void createAddToBasketPopup(MenuItem item) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View addToBasketWindow = li.inflate(R.layout.add_to_basket_view, null);

        AddToBasketPopupWindow pw = new AddToBasketPopupWindow(addToBasketWindow, item, basketViewModel);
        pw.createAddToBasketPopup(getView());
    }

}