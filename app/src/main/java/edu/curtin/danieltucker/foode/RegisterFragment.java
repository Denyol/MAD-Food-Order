package edu.curtin.danieltucker.foode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import edu.curtin.danieltucker.foode.model.DBAdapter;


public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        DBAdapter dbAdapter = new DBAdapter(requireActivity());

        TextView emailText = v.findViewById(R.id.signInEmail);
        TextView passwordText = v.findViewById(R.id.signInPassword);
        Button registerButton = v.findViewById(R.id.registerButton);

        if (getArguments() != null) {
            emailText.setText(getArguments().getString(LoginFragment.EMAIL));
            passwordText.setText(getArguments().getString(LoginFragment.PASSWORD));
        }

        String email = emailText.getText().toString();
        String pass = passwordText.getText().toString();

        registerButton.setOnClickListener(l -> {
            if (email.length() > 0 && pass.length() > 0) {
                int result = dbAdapter.addUser(email, pass);

                if (result == -1)
                    Snackbar.make(v, "User already exists!", Snackbar.LENGTH_LONG).show();
                else {
                    Intent i = new Intent();
                    i.putExtra(LoginRegisterActivity.USER_RESULT, result);
                    requireActivity().setResult(Activity.RESULT_OK, i);
                    requireActivity().finish();
                }
            } else
                Snackbar.make(v, "Email and password can not be blank!", Snackbar.LENGTH_LONG).show();
        });

        return v;
    }
}