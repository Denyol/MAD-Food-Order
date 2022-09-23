package edu.curtin.danieltucker.foode;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

public class LoginRegisterActivity extends AppCompatActivity {

    public static final String USER_RESULT = "login.user_id_result";
    private FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        fm = getSupportFragmentManager();

        if (savedInstanceState != null) {
            int current = savedInstanceState.getInt("CURRENT_FRAG", -1);

            if (current == 1)
                fm.beginTransaction().replace(R.id.loginRegisterFrame, RegisterFragment.class, null).commit();
            else
                fm.beginTransaction().replace(R.id.loginRegisterFrame, LoginFragment.class, null).commit();
        } else
            fm.beginTransaction().replace(R.id.loginRegisterFrame, LoginFragment.class, null).commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        List<Fragment> fragmentList = fm.getFragments();
        Fragment current = fragmentList.get(fragmentList.size() - 1);

        if (current.getClass() == RegisterFragment.class) {
            outState.putInt("CURRENT_FRAG", 1);
        }
    }
}
