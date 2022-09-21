package edu.curtin.danieltucker.foode;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        TextView emailText = v.findViewById(R.id.signInEmail);
        TextView passwordText = v.findViewById(R.id.signInPassword);
        Button registerButton = v.findViewById(R.id.registerButton);

        if (getArguments() != null) {
            emailText.setText(getArguments().getString(LoginFragment.EMAIL));
            passwordText.setText(getArguments().getString(LoginFragment.PASSWORD));
        }

        //TODO Implement register functionality

        return v;
    }
}