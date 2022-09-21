package edu.curtin.danieltucker.foode;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class LoginRegisterActivity extends AppCompatActivity {

    public static final String USER_RESULT = "login.user_id_result";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction().replace(R.id.loginRegisterFrame, LoginFragment.class, null).commit();
    }
}
