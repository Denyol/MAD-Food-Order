package edu.curtin.danieltucker.foode;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.curtin.danieltucker.foode.model.BasketViewModel;
import edu.curtin.danieltucker.foode.model.DataViewModel;
import edu.curtin.danieltucker.foode.model.Restaurant;

public class MenuFragment extends Fragment {

    private BasketViewModel basketViewModel;
    private DataViewModel dataViewModel;
    protected RecyclerView recyclerView;

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

        recyclerView = v.findViewById(R.id.menuRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Restaurant rest = dataViewModel.getCurrentRestSelected();

        recyclerView.setAdapter(new MenuListAdapter(rest, getContext(), this));

        return v;
    }

}