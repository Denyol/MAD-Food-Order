package edu.curtin.danieltucker.foode;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import edu.curtin.danieltucker.foode.model.DBAdapter;
import edu.curtin.danieltucker.foode.model.Restaurant;


public class SpecialsMenuFragment extends MenuFragment {

    private class SpecialsListAdapter extends MenuListAdapter {
        public SpecialsListAdapter(Context context) {
            super(null, context, SpecialsMenuFragment.this);
        }

        @Override
        protected void setMenu(Restaurant restaurant) {
            this.menu = new DBAdapter(SpecialsMenuFragment.this.requireActivity())
                    .getSpecialsItems();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        recyclerView = v.findViewById(R.id.menuRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        this.recyclerView.setAdapter(new SpecialsListAdapter(getContext()));

        return v;
    }
}
