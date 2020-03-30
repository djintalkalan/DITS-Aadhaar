package dj.sangu.ditsaadhaar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.utils.Constants;
import dj.sangu.ditsaadhaar.utils.SharedPrefManager;
import dj.sangu.ditsaadhaar.utils.Utils;

public class OperatorReportActivity extends AppCompatActivity implements View.OnClickListener {
    private String custId;
    private TextView tvFromDate;
    private TextView tvToDate;
    private TextView tvError;
    private Button btnSubmit;
    private String fromDate = null;
    private String toDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_report);
        getProps();
        initViews();
        setListeners();
    }

    private void setListeners() {
        tvFromDate.setOnClickListener(this);
        tvToDate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void getProps() {
        SharedPrefManager prefManager = SharedPrefManager.getInstance(this);
        if (prefManager.isLoggedIn() == Constants.ADMIN_LOGGED_IN_KEY) {
            custId = getIntent().getStringExtra(Constants.OPERATOR_ID);
        } else {
            custId = prefManager.getCustomer().getCustId();
        }
    }

    private void initViews() {
        tvFromDate = findViewById(R.id.tvFromDate);
        tvToDate = findViewById(R.id.tvToDate);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvError = findViewById(R.id.tvError);
        Utils.setToolbar(this, (Toolbar) findViewById(R.id.toolbar), "Generate Report");
    }

    private void setErrorText(String errorText) {
        if (errorText == null || errorText.equals("")) {
            tvError.setText("");
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setText("Error: " + errorText);
            tvError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvFromDate:
                openCalenderModal(1);
                break;
            case R.id.tvToDate:
                openCalenderModal(2);
                break;
            case R.id.btnSubmit:
                validate();
                break;
        }
    }

    private void validate() {
        if (TextUtils.isEmpty(fromDate)) {
            setErrorText("From Date Missing");
            return;
        }
        if (TextUtils.isEmpty(toDate)) {
            setErrorText("To Date Missing");
            return;
        }
        if (fromDate.equals(toDate)) {
            setErrorText("Dates can't be same");
            return;
        }
        long msDifference = Utils.dateStringToCalender(toDate).getTimeInMillis() - Utils.dateStringToCalender(fromDate).getTimeInMillis();
        long daysDifference = TimeUnit.MILLISECONDS.toDays(msDifference);
        if (daysDifference + 1 > 90) {
            setErrorText("Can't generate report of more than 90 days");
            return;
        }
        Intent i = new Intent(OperatorReportActivity.this, ReportWebViewActivity.class);
        i.putExtra(Constants.OPERATOR_ID, custId);
        i.putExtra(Constants.FROM_DATE, fromDate);
        i.putExtra(Constants.TO_DATE, toDate);
        startActivity(i);
        overridePendingTransition(R.anim.downtoup,R.anim.fade_out);
    }


    private void openCalenderModal(final int type) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(OperatorReportActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String DayOfMonth, MonthOfYear;
                        setErrorText(null);
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
                        if (type == 1) {
                            fromDate = year + "-" + MonthOfYear + "-" + DayOfMonth;
                            tvFromDate.setText(DayOfMonth + "/" + MonthOfYear + "/" + year);
                        } else {
                            toDate = year + "-" + MonthOfYear + "-" + DayOfMonth;
                            tvToDate.setText(DayOfMonth + "/" + MonthOfYear + "/" + year);
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
