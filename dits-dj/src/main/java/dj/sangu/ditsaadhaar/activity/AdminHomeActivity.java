package dj.sangu.ditsaadhaar.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.utils.Constants;
import dj.sangu.ditsaadhaar.utils.SharedPrefManager;

public class AdminHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView cardAddOperator;
    private CardView cardOperatorList;
    private CardView cardProfile;
    private CardView cardPassword;
    private CardView cardAddAdmin;
    private LinearLayout llAddProfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        initViews();
        setListeners();
    }

    private void setListeners() {
        cardAddOperator.setOnClickListener(this);
        cardOperatorList.setOnClickListener(this);
        cardProfile.setOnClickListener(this);
        cardPassword.setOnClickListener(this);
        cardAddAdmin.setOnClickListener(this);
    }

    private void initViews() {
        cardAddOperator = findViewById(R.id.cardAddOperator);
        cardOperatorList = findViewById(R.id.cardOperatorList);
        cardProfile = findViewById(R.id.cardProfile);
        cardPassword = findViewById(R.id.cardPassword);
        cardAddAdmin = findViewById(R.id.cardAddAdmin);
        llAddProfiles = findViewById(R.id.llAddProfiles);
        if (SharedPrefManager.getInstance(this).getAdmin().getAdminType().equals(Constants.STANDERED_ADMIN)) {
            llAddProfiles.setVisibility(View.GONE);
        }
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardAddOperator:
                startActivity(new Intent(AdminHomeActivity.this, AddOperatorActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
            case R.id.cardOperatorList:
                startActivity(new Intent(AdminHomeActivity.this, OperatorListActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
            case R.id.cardProfile:
                startActivity(new Intent(AdminHomeActivity.this, AdminProfileActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
            case R.id.cardPassword:
                startActivity(new Intent(AdminHomeActivity.this, UpdatePasswordActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
            case R.id.cardAddAdmin:
                startActivity(new Intent(AdminHomeActivity.this, AddAdminActivity.class));
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminHomeActivity.this);
        alertDialog.setTitle("Logout");
        alertDialog.setMessage("Are you sure you want to logout?");
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefManager.getInstance(AdminHomeActivity.this).logout();
                        startActivity(new Intent(AdminHomeActivity.this, LoginActivity.class));
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
