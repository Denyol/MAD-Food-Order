package edu.curtin.danieltucker.foode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import edu.curtin.danieltucker.foode.model.DBAdapter;
import edu.curtin.danieltucker.foode.model.DataViewModel;


public class OrdersFragment extends Fragment {

    private ActivityResultLauncher<Intent> resultLauncher;
    private DataViewModel appData;
    private DBAdapter dbAdapter;
    private RecyclerView rv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appData = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        dbAdapter = new DBAdapter(this.getContext());
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        appData.setCurrentUser(result.getData().getIntExtra(
                                LoginRegisterActivity.USER_RESULT, -1));
                        Log.d("BasketFragment", "Got user ID result");
                        setRecyclerAdapter();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orders, container, false);

        rv = v.findViewById(R.id.ordersRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Ensure user signed in
        if (appData.getCurrentUser() == -1) {
            Toast.makeText(getContext(), "Sign in required to view orders!", Toast.LENGTH_SHORT).show();
            resultLauncher.launch(new Intent(this.getContext(), LoginRegisterActivity.class));
        } else
            setRecyclerAdapter();

        return v;
    }

    private void setRecyclerAdapter() {
        OrdersListAdapter adapter = new OrdersListAdapter(dbAdapter.getOrders(appData.getCurrentUser()), this);
        rv.setAdapter(adapter);
    }
}