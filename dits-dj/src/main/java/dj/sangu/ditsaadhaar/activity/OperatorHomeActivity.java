package dj.sangu.ditsaadhaar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.utils.SharedPrefManager;

public class OperatorHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView cardNewEntry;
    private CardView cardReport;
    private CardView cardProfile;
    private CardView cardPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_home);
        initViews();
        setListeners();
    }

    private void setListeners() {
        cardNewEntry.setOnClickListener(this);
        cardReport.setOnClickListener(this);
        cardProfile.setOnClickListener(this);
        cardPassword.setOnClickListener(this);
    }

    private void initViews() {
        cardNewEntry = findViewById(R.id.cardNewEntry);
        cardReport = findViewById(R.id.cardReport);
        cardProfile = findViewById(R.id.cardProfile);
        cardPassword = findViewById(R.id.cardPassword);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardNewEntry:
                startActivity(new Intent(OperatorHomeActivity.this, AddAadhaarRecordActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
            case R.id.cardReport:
                startActivity(new Intent(OperatorHomeActivity.this, OperatorReportActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
            case R.id.cardProfile:
                startActivity(new Intent(OperatorHomeActivity.this, OperatorProfileActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
            case R.id.cardPassword:
                startActivity(new Intent(OperatorHomeActivity.this, UpdatePasswordActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu_item:
                showLogoutPopUp();
                break;
        }
        return true;
    }

    private void showLogoutPopUp() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OperatorHomeActivity.this);
        alertDialog.setTitle("Logout");
        alertDialog.setMessage("Are you sure you want to logout?");
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefManager.getInstance(OperatorHomeActivity.this).logout();
                        startActivity(new Intent(OperatorHomeActivity.this, LoginActivity.class));
                        finish();
                        overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
}
