package dj.sangu.ditsaadhaar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.activity.AdminHomeActivity;
import dj.sangu.ditsaadhaar.modal.requests.AdminLoginRequest;
import dj.sangu.ditsaadhaar.modal.responses.AdminLoginResponse;
import dj.sangu.ditsaadhaar.network.ApiClient;
import dj.sangu.ditsaadhaar.utils.SharedPrefManager;
import dj.sangu.ditsaadhaar.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminLoginFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    private ProgressBar progressBar;
    private EditText etPhone;
    private EditText etPassword;
    private Button btnLogin;
    private CheckBox cbShowPassword;
    private String phone;
    private String password;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_login, container, false);
        initViews(v);
        setListeners();
        return v;
    }

    private void setListeners() {
        btnLogin.setOnClickListener(this);
        cbShowPassword.setOnCheckedChangeListener(this);
    }

    private void initViews(View v) {
        progressBar = v.findViewById(R.id.progressBar);
        etPhone = v.findViewById(R.id.etPhone);
        etPassword = v.findViewById(R.id.etPassword);
        btnLogin = v.findViewById(R.id.btnLogin);
        cbShowPassword = v.findViewById(R.id.cbShowPassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                Utils.hideKeyboard(getActivity(),v);
//                Toast.makeText(getActivity(), "This feature will be available soon", Toast.LENGTH_SHORT).show();
                if (validateEntryFields()) {
                    callAdminLoginApi();
                }
                break;
            case R.id.btnAdminLogin:
                break;
        }

    }

    private void callAdminLoginApi() {
        btnLogin.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        AdminLoginRequest request = new AdminLoginRequest();
        request.setAdminPhone(phone);
        request.setAdminPassword(password);
        ApiClient.getClient().adminLogin(request).enqueue(new Callback<AdminLoginResponse>() {
            @Override
            public void onResponse(Call<AdminLoginResponse> call, Response<AdminLoginResponse> response) {
                if (response.isSuccessful()) {
                    btnLogin.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    if (response.body() != null) {
                        if (response.body().getSuccess()) {
                            SharedPrefManager.getInstance(getActivity()).adminLogin(response.body().getAdminModal());
                            btnLogin.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getActivity(), AdminHomeActivity.class));
                            ((Activity)getActivity()).overridePendingTransition(R.anim.left_in,R.anim.left_out);
                            getActivity().finish();
                        }
                        else {
                            Toast.makeText(getActivity(), response.body().getError(), Toast.LENGTH_SHORT).show();
                            btnLogin.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AdminLoginResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                btnLogin.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private boolean validateEntryFields() {
        phone = etPhone.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (phone.length() == 0) {
            etPhone.setError("Required");
            etPhone.requestFocus();
            return false;
        }
        if (phone.length() < 10) {
            etPhone.setError("Invalid Phone");
            etPhone.requestFocus();
            return false;
        }
        if (password.length() == 0) {
            etPassword.setError("Required");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.cbShowPassword) {
            if (isChecked) {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}
