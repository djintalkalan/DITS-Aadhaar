package dj.sangu.ditsaadhaar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.activity.OperatorHomeActivity;
import dj.sangu.ditsaadhaar.modal.requests.LoginRequest;
import dj.sangu.ditsaadhaar.modal.responses.LoginResponse;
import dj.sangu.ditsaadhaar.network.ApiClient;
import dj.sangu.ditsaadhaar.utils.SharedPrefManager;
import dj.sangu.ditsaadhaar.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OperatorLoginFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ProgressBar progressBar;
    private EditText etPhone;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private CheckBox cbShowPassword;
    private String phone;
    private String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_operator_login, container, false);
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
        tvForgotPassword = v.findViewById(R.id.tvForgotPassword);
        cbShowPassword = v.findViewById(R.id.cbShowPassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                Utils.hideKeyboard(getActivity(),v);
                if (validateEntryFields()) {
                    callLoginApi();
                }
                break;
            case R.id.btnAdminLogin:
                break;
        }

    }

    private void callLoginApi() {
        btnLogin.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        LoginRequest request = new LoginRequest();
        request.setCustPhone(phone);
        request.setCustPassword(password);
        ApiClient.getClient().login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    btnLogin.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    if (response.body() != null) {
                        if (response.body().getSuccess()) {
                            SharedPrefManager.getInstance(getActivity()).userLogin(response.body().getOperatorModal());
                            btnLogin.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getActivity(), OperatorHomeActivity.class));
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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                 Toast.makeText(getActivity(), "Server not found", Toast.LENGTH_SHORT).show();
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
