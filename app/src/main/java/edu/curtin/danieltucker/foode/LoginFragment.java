package edu.curtin.danieltucker.foode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import edu.curtin.danieltucker.foode.model.DBAdapter;


public class LoginFragment extends Fragment {

    private TextView emailText;
    private TextView passwordText;

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        emailText = v.findViewById(R.id.signInEmail);
        passwordText = v.findViewById(R.id.signInPassword);
        Button signInButton = v.findViewById(R.id.signInButton);

        TextView signUpText = v.findViewById(R.id.signInSignUpText);

        signInButton.setOnClickListener(c -> tryLogin());
        signUpText.setOnClickListener(c -> {
            Log.d("LoginFragment", "Sign up text clicked");
            Bundle b = new Bundle();
            b.putString(EMAIL, emailText.getText().toString());
            b.putString(PASSWORD, passwordText.getText().toString());
            getParentFragmentManager().beginTransaction()
                    .add(R.id.loginRegisterFrame, RegisterFragment.class, b)
                    .commit();
        });

        return v;
    }

    private void tryLogin() {
        DBAdapter dbAdapter = new DBAdapter(requireActivity());

        int user = dbAdapter.login(emailText.getText().toString(),
                passwordText.getText().toString());

        if (user != -1) {
            Intent i = new Intent();
            i.putExtra(LoginRegisterActivity.USER_RESULT, user);
            requireActivity().setResult(Activity.RESULT_OK, i);
            requireActivity().finish();
        } else
            Snackbar.make(this.getView(), "No User Found, Try Again or Register!", Snackbar.LENGTH_LONG).show();
    }

}