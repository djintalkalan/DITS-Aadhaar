package dj.sangu.ditsaadhaar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;

import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.modal.OperatorModal;
import dj.sangu.ditsaadhaar.modal.requests.AddAadhaarDataRequest;
import dj.sangu.ditsaadhaar.modal.responses.LoginResponse;
import dj.sangu.ditsaadhaar.network.ApiClient;
import dj.sangu.ditsaadhaar.utils.Constants;
import dj.sangu.ditsaadhaar.utils.SharedPrefManager;
import dj.sangu.ditsaadhaar.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAadhaarRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvName;
    private TextView tvMachineId;
    private TextView tvStationId;
    private TextView tvMachineLocation;
    private TextView tvDate;
    private EditText etNewEnrollment;
    private EditText etMandatoryUpdate;
    private EditText etNormalUpdate;
    private OperatorModal operator;
    private LinearLayout llDate;
    private Button btnSubmit;
    private ProgressBar progressBar;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        initViews();
        getProps();
        setListeners();
    }

    private void setListeners() {
        tvDate.setOnClickListener(this);
    }


    private void setViews() {
        tvName.setText(operator.getCustName());
        tvMachineId.setText(operator.getMachineId());
        tvStationId.setText(operator.getStationId());
        tvMachineLocation.setText(operator.getMachineLocation());
    }


    private void getProps() {
        SharedPrefManager prefManager = SharedPrefManager.getInstance(AddAadhaarRecordActivity.this);
        if (prefManager.isLoggedIn() == Constants.ADMIN_LOGGED_IN_KEY) {
            operator =  (OperatorModal) getIntent().getParcelableExtra(Constants.OPERATOR_MODEL);
//            Log.d("operator",new Gson().toJson(operator));
            llDate.setVisibility(View.VISIBLE);
        } else
            operator = prefManager.getCustomer();
        setViews();
    }

    private void initViews() {
        tvName = findViewById(R.id.tvName);
        tvMachineId = findViewById(R.id.tvMachineId);
        tvStationId = findViewById(R.id.tvStationId);
        tvMachineLocation = findViewById(R.id.tvMachineLocation);
        tvDate = findViewById(R.id.tvDate);
        etNewEnrollment = findViewById(R.id.etNewEnrollment);
        etMandatoryUpdate = findViewById(R.id.etMandatoryUpdate);
        etNormalUpdate = findViewById(R.id.etNormalUpdate);
        llDate = findViewById(R.id.llDate);
        progressBar = findViewById(R.id.progressBar);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        Utils.setToolbar(this,(Toolbar) findViewById(R.id.toolbar),"Submit New Record");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDate:
                Utils.hideKeyboard(getApplicationContext(),v);
                openCalenderModal();
                break;
            case R.id.btnSubmit:
                Utils.hideKeyboard(getApplicationContext(),v);
                verifyAndSubmit();
                break;
        }
    }

    private void openCalenderModal() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddAadhaarRecordActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String DayOfMonth, MonthOfYear;
                        if (String.valueOf(dayOfMonth).length() == 1) {
                            DayOfMonth = "0" + String.valueOf(dayOfMonth);
                        } else {
                            DayOfMonth = String.valueOf(dayOfMonth);
                        }
                        if (String.valueOf(monthOfYear + 1).length() == 1) {
                            MonthOfYear = "0" + String.valueOf(monthOfYear + 1);
                        } else {
                            MonthOfYear = String.valueOf(monthOfYear + 1);
                        }
                            date = year + "-" + MonthOfYear + "-" + DayOfMonth+" 10:00:00";
                            tvDate.setText(DayOfMonth + "/" + MonthOfYear + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void verifyAndSubmit() {
        SharedPrefManager prefManager = SharedPrefManager.getInstance(this);
        int login = prefManager.isLoggedIn();
        String newEnrollment = etNewEnrollment.getText().toString();
        String normalUpdate = etNormalUpdate.getText().toString();
        String mandatoryUpdate = etMandatoryUpdate.getText().toString();
        if (login == Constants.ADMIN_LOGGED_IN_KEY && TextUtils.isEmpty(date)) {
            tvDate.setError("Required");
            tvDate.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(newEnrollment)) {
            etNewEnrollment.setError("Required");
            etNewEnrollment.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(normalUpdate)) {
            etNormalUpdate.setError("Required");
            etNormalUpdate.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mandatoryUpdate)) {
            etMandatoryUpdate.setError("Required");
            etMandatoryUpdate.requestFocus();
            return;
        }


        AddAadhaarDataRequest request = new AddAadhaarDataRequest();
        request.setCustId(operator.getCustId());
        request.setMachineId(operator.getMachineId());
        request.setStationId(operator.getStationId());
        request.setMachineLocation(operator.getMachineLocation());
        request.setNewEnrollment(newEnrollment);
        request.setMandatoryUpdate(mandatoryUpdate);
        request.setNormalUpdate(normalUpdate);
        if (login == Constants.ADMIN_LOGGED_IN_KEY) {
            request.setAddedon(date);
        }

        Log.d("AddAadhaarDataRequest",new Gson().toJson(request));

        progressBar.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);
        ApiClient.getClient().addAadhaarData(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        Toast.makeText(AddAadhaarRecordActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                        finish();

                    } else {
                        Toast.makeText(AddAadhaarRecordActivity.this, response.body().getError(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        btnSubmit.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(AddAadhaarRecordActivity.this, "Internet Not Available", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }
}
