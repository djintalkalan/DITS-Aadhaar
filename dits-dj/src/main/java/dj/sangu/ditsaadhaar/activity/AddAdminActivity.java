package dj.sangu.ditsaadhaar.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.modal.AdminModal;
import dj.sangu.ditsaadhaar.modal.responses.LoginResponse;
import dj.sangu.ditsaadhaar.network.ApiClient;
import dj.sangu.ditsaadhaar.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAdminActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView etName;
    private EditText etPhone;
    private EditText etEmail;
    private Button btnSubmit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);
        initViews();
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        progressBar = findViewById(R.id.progressBar);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        Utils.setToolbar(this, (Toolbar) findViewById(R.id.toolbar), "Add Admin");
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {
            Utils.hideKeyboard(getApplicationContext(),v);
            verifyAndSubmit();
        }
    }

    private void verifyAndSubmit() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Required");
            etName.requestFocus();
            return;
        }
        if (name.trim().length() < 3) {
            etName.setError("Invalid Name");
            etName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Required");
            etPhone.requestFocus();
            return;
        }
        if (TextUtils.getTrimmedLength(phone) < 10) {
            etPhone.setError("Invalid Phone");
            etPhone.requestFocus();
            return;
        }


        AdminModal request = new AdminModal();
        request.setAdminEmail(email);
        request.setAdminPhone(phone);
        request.setAdminName(name);
        request.setAdminType("2");
        request.setAdminPassword(phone);

        progressBar.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);
        ApiClient.getClient().addAdmin(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        Toast.makeText(AddAdminActivity.this, "Account Successfully Created", Toast.LENGTH_LONG).show();
                        onBackPressed();

                    } else {
                        Toast.makeText(AddAdminActivity.this, response.body().getError(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        btnSubmit.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(AddAdminActivity.this, "Internet Not Available", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }
}
