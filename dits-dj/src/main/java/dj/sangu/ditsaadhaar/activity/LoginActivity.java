package dj.sangu.ditsaadhaar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.fragment.AdminLoginFragment;
import dj.sangu.ditsaadhaar.fragment.OperatorLoginFragment;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnUserLogin;
    private Button btnAdminLogin;
    private FrameLayout loginContainer;
    private OperatorLoginFragment operatorLoginFragment;
    private AdminLoginFragment adminLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initFragments();
        initFragmentManager();
        setListeners();
    }

    private void setListeners() {
        btnUserLogin.setOnClickListener(this);
        btnAdminLogin.setOnClickListener(this);
    }

    private void initFragments() {
        operatorLoginFragment = new OperatorLoginFragment();
        adminLoginFragment = new AdminLoginFragment();
    }

    private void initFragmentManager() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.loginContainer, operatorLoginFragment);
        fragmentTransaction.commit();
    }

    private void initViews() {
        btnAdminLogin = findViewById(R.id.btnAdminLogin);
        btnUserLogin = findViewById(R.id.btnUserLogin);
        loginContainer = findViewById(R.id.loginContainer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUserLogin:
                toggleFragments(operatorLoginFragment);
                btnAdminLogin.setEnabled(true);
                btnUserLogin.setEnabled(false);
                btnUserLogin.setBackgroundResource(R.drawable.button_solid_pink);
                btnAdminLogin.setBackgroundResource(R.drawable.button_outline_white);
                break;
            case R.id.btnAdminLogin:
                toggleFragments(adminLoginFragment);
                btnUserLogin.setEnabled(true);
                btnAdminLogin.setEnabled(false);
                btnUserLogin.setBackgroundResource(R.drawable.button_outline_white);
                btnAdminLogin.setBackgroundResource(R.drawable.button_solid_primary_button);
                break;

        }
    }

    private void toggleFragments(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.loginContainer, fragment);
        fragmentTransaction.commit();
    }

}
