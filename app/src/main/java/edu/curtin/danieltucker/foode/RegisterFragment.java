package edu.curtin.danieltucker.foode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import edu.curtin.danieltucker.foode.model.DBAdapter;


public class RegisterFragment extends Fragment {

    private TextView emailText;
    private TextView passwordText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        emailText = v.findViewById(R.id.signInEmail);
        passwordText = v.findViewById(R.id.signInPassword);

        if (getArguments() != null) {
            emailText.setText(getArguments().getString(LoginFragment.EMAIL));
            passwordText.setText(getArguments().getString(LoginFragment.PASSWORD));
        }

        DBAdapter dbAdapter = new DBAdapter(requireActivity());

        Button registerButton = v.findViewById(R.id.registerButton);



        registerButton.setOnClickListener(l -> {
            String email = emailText.getText().toString();
            String pass = passwordText.getText().toString();

            if (email.length() > 0 && pass.length() > 0) {
                int result = dbAdapter.addUser(email, pass);

                if (result == -1)
                    Snackbar.make(v, "User already exists!", Snackbar.LENGTH_LONG).show();
                else {
                    Snackbar.make(v, "Register and login successfully!", Snackbar.LENGTH_LONG).show();
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