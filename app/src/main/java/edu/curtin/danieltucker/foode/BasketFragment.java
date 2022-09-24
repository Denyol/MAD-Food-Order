package edu.curtin.danieltucker.foode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import edu.curtin.danieltucker.foode.model.BasketViewModel;
import edu.curtin.danieltucker.foode.model.DBAdapter;
import edu.curtin.danieltucker.foode.model.DataViewModel;


public class BasketFragment extends Fragment {

    private BasketViewModel basket;
    private DataViewModel appData;
    private TextView basketTitle;
    private TextView total;
    private ActivityResultLauncher<Intent> resultLauncher;
    private DBAdapter dbAdapter;
    private BasketListAdapter basketListAdapter;

    public BasketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        basket = new ViewModelProvider(requireActivity()).get(BasketViewModel.class);
        appData = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                                appData.setCurrentUser(result.getData().getIntExtra(
                                        LoginRegisterActivity.USER_RESULT, -1));
                                Log.d("BasketFragment", "Got user ID result");

                                checkoutClicked();
                            }
                        });

        dbAdapter = new DBAdapter(requireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        RecyclerView rv = view.findViewById(R.id.basketRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        basketListAdapter = new BasketListAdapter(this, requireContext());
        basketListAdapter.setObserve(this.getViewLifecycleOwner());

        basketTitle = view.findViewById(R.id.basketStoreText);
        total = view.findViewById(R.id.basketTotalText);
        Button checkout = view.findViewById(R.id.checkoutButton);

        rv.setAdapter(basketListAdapter);

        basket.getBasketData().observe(getViewLifecycleOwner(), e -> changeOccur());
        basket.getRestaurantData().observe(getViewLifecycleOwner(), e -> setTitleAndTotal());

        // Initialise title and checkout button.
        setTitleAndTotal();

        checkout.setOnClickListener(l -> checkoutClicked());

        return view;
    }

    private void checkoutClicked() {
        if (!basket.getBasket().isEmpty() && appData.getCurrentUser() == -1) {
            Log.d("BasketFragment", "Try login");

            // Launch log in/register activity
            resultLauncher.launch(new Intent(this.getContext(), LoginRegisterActivity.class));
        } else if (!basket.getBasket().isEmpty()){
            dbAdapter.addOrder(appData.getCurrentUser(), basket.getBasket());
            // reset basket
            basket.resetBasket();

            Snackbar.make(getView(), "Checkout success!", Snackbar.LENGTH_LONG).show();
        } else
            Snackbar.make(getView(), "Basket empty!", Snackbar.LENGTH_LONG).show();
    }

    private void setTitleAndTotal() {
        String name = basket.getRestaurantName();
        basketTitle.setText(name == null ? "Empty Basket" : name + " Basket");
        total.setText(String.format("Total: $%.2f", basket.getTotal()));
    }

    private void changeOccur() {
        basketListAdapter.notifyDataSetChanged();
        setTitleAndTotal();
    }
}