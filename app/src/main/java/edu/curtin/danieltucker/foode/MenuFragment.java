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

        PopupWindow pw = new PopupWindow(addToBasketWindow, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pw.setFocusable(true);
        pw.setElevation(20.0f);

        // Get view elements
        TextView price = addToBasketWindow.findViewById(R.id.basketPriceText);
        TextView itemName = addToBasketWindow.findViewById(R.id.basketItemText);
        TextView itemCount = addToBasketWindow.findViewById(R.id.basketItemCount);
        Button decrease = addToBasketWindow.findViewById(R.id.basketMinusButton);
        Button increase = addToBasketWindow.findViewById(R.id.basketPlusButton);
        Button addToCart = addToBasketWindow.findViewById(R.id.basketAddButton);

        // Set button listeners
        decrease.setOnClickListener(v -> changeCountText(itemCount, false));
        increase.setOnClickListener(v -> changeCountText(itemCount, true));

        addToCart.setOnClickListener(v -> {
            basketViewModel.getBasket().getValue().put(item, Integer.parseInt(itemCount.getText().toString()));
            basketViewModel.notifyBasketChanged();
            basketViewModel.setRestaurant(item.getRestaurant());
        });

        price.setText(Float.toString(item.getPrice()));
        itemName.setText(item.getDescription());

        pw.showAtLocation(this.getView(), Gravity.BOTTOM, 0, 0);
    }

    private void changeCountText(TextView countView, boolean increase) {
        try {
            int count = Integer.parseInt(countView.getText().toString());

            if (count > 0 && !increase) {
                count--;
                countView.setText(String.format("%d", count));
            } else if (increase) {
                count++;
                countView.setText(String.format("%d", count));
            }
        } catch (NumberFormatException e) {
            Log.w("MenuFragment", e);
        }
    }
}