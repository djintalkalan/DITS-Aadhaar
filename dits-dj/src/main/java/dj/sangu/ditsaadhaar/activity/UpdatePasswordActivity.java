package dj.sangu.ditsaadhaar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.modal.requests.UpdatePasswordRequest;
import dj.sangu.ditsaadhaar.modal.responses.LoginResponse;
import dj.sangu.ditsaadhaar.network.ApiClient;
import dj.sangu.ditsaadhaar.utils.Constants;
import dj.sangu.ditsaadhaar.utils.SharedPrefManager;
import dj.sangu.ditsaadhaar.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etOldPassword;
    private EditText etNewPassword;
    private EditText etConfirmPassword;
    private Button btnSubmit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        initViews();

    }

    private void initViews() {
        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressBar);
        btnSubmit.setOnClickListener(this);
        Utils.setToolbar(this, (Toolbar) findViewById(R.id.toolbar), "Update Password");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {
            Utils.hideKeyboard(getApplicationContext(),v);
            String oldPassword = etOldPassword.getText().toString();
            String newPassword = etNewPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            if (TextUtils.isEmpty(oldPassword)) {
                etOldPassword.setError("Required");
                etOldPassword.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(newPassword)) {
                etNewPassword.setError("Required");
                etNewPassword.requestFocus();
                return;
            }

            if (newPassword.length() < 6) {
                etNewPassword.setError("Minimum 6 Digit Required");
                etNewPassword.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(confirmPassword)) {
                etConfirmPassword.setError("Required");
                etConfirmPassword.requestFocus();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                etConfirmPassword.setError("Password Mismatch");
                etConfirmPassword.requestFocus();
                return;
            }

            UpdatePasswordRequest request = new UpdatePasswordRequest();
            request.setNewPassword(newPassword);
            request.setPassword(oldPassword);
            SharedPrefManager prefManager = SharedPrefManager.getInstance(this);
            Call<LoginResponse> call = null;
            if (prefManager.isLoggedIn() == Constants.ADMIN_LOGGED_IN_KEY) {
                request.setPhone(prefManager.getAdmin().getAdminPhone());
                call = ApiClient.getClient().updatePasswordAdmin(request);
            }
            if (prefManager.isLoggedIn() == Constants.USER_LOGGED_IN_KEY) {
                request.setPhone(prefManager.getCustomer().getCustPhone());
                call = ApiClient.getClient().updatePasswordCustomer(request);
            }
            if (call != null) {
                progressBar.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.GONE);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                Toast.makeText(UpdatePasswordActivity.this, "Password Updated Successfully", Toast.LENGTH_LONG).show();
                                finish();

                            } else {
                                Toast.makeText(UpdatePasswordActivity.this, response.body().getError(), Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                btnSubmit.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(UpdatePasswordActivity.this, "Internet Not Available", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        btnSubmit.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }
}
