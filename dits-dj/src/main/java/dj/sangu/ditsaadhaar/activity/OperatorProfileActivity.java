package dj.sangu.ditsaadhaar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.modal.OperatorModal;
import dj.sangu.ditsaadhaar.modal.responses.LoginResponse;
import dj.sangu.ditsaadhaar.network.ApiClient;
import dj.sangu.ditsaadhaar.utils.SharedPrefManager;
import dj.sangu.ditsaadhaar.utils.Constants;
import dj.sangu.ditsaadhaar.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperatorProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvMachineId;
    private TextView tvStationId;
    private TextView tvMachineLocation;
    private ProgressBar progressBar;
    private LinearLayout mainLayout;
    private LinearLayout bottomButtonLayout;
    private Button btnNewEntry;
    private Button btnReport;
    private String custId;
    private OperatorModal operator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_profile);
        getProps();
        initViews();
        loadProfile();
        setListeners();
    }

    private void setListeners() {
        btnNewEntry.setOnClickListener(this);
        btnReport.setOnClickListener(this);
    }

    private void loadProfile() {
        SharedPrefManager prefManager = SharedPrefManager.getInstance(OperatorProfileActivity.this);
        if (prefManager.isLoggedIn() == Constants.USER_LOGGED_IN_KEY) {
            setProfileData(prefManager.getCustomer());
        } else {
            callProfileApi();
        }
    }

    private void setProfileData(OperatorModal modal) {
        Log.d("REQ:", modal.getCustPhone());
        tvName.setText(modal.getCustName());
        tvEmail.setText(modal.getCustEmail());
        tvPhone.setText(modal.getCustPhone());
        tvMachineId.setText(modal.getMachineId());
        tvMachineLocation.setText(modal.getMachineLocation());
        tvStationId.setText(modal.getStationId());
        mainLayout.setVisibility(View.VISIBLE);
    }

    private void callProfileApi() {
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.getClient().getOperatorDetail(custId).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    if (response.body() != null) {
                        if (response.body().getSuccess()) {
                            bottomButtonLayout.setVisibility(View.VISIBLE);
                            operator = response.body().getOperatorModal();
                            if(SharedPrefManager.getInstance(OperatorProfileActivity.this).getAdmin().getAdminType().equals(Constants.STANDERED_ADMIN)){
                                btnNewEntry.setVisibility(View.GONE);
                            }
                            setProfileData(operator);
                        } else {
                            Toast.makeText(OperatorProfileActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(OperatorProfileActivity.this, "Internet Not Available", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getProps() {
        Intent i = getIntent();
        custId = i.getStringExtra(Constants.OPERATOR_ID);
    }

    private void initViews() {
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvMachineId = findViewById(R.id.tvMachineId);
        tvStationId = findViewById(R.id.tvStationId);
        tvMachineLocation = findViewById(R.id.tvMachineLocation);
        progressBar = findViewById(R.id.progressBar);
        mainLayout = findViewById(R.id.mainLayout);
        btnNewEntry = findViewById(R.id.btnNewEntry);
        btnReport = findViewById(R.id.btnReport);
        bottomButtonLayout = findViewById(R.id.bottomButtonLayout);
        Utils.setToolbar(this, (Toolbar) findViewById(R.id.toolbar), "Operator Profile");
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewEntry:
                navigateTo(AddAadhaarRecordActivity.class, 1);
                break;
            case R.id.btnReport:
                navigateTo(OperatorReportActivity.class, 2);
                break;
        }
    }

    private void navigateTo(Class myClass, int type) {
        Intent intent = new Intent(OperatorProfileActivity.this, myClass);
        if (SharedPrefManager.getInstance(OperatorProfileActivity.this).isLoggedIn() == Constants.ADMIN_LOGGED_IN_KEY) {
            if (type == 1) intent.putExtra(Constants.OPERATOR_MODEL, operator);
            else intent.putExtra(Constants.OPERATOR_ID, operator.getCustId());
        }
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
}
