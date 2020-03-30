package dj.sangu.ditsaadhaar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.modal.AdminModal;
import dj.sangu.ditsaadhaar.utils.Constants;
import dj.sangu.ditsaadhaar.utils.SharedPrefManager;
import dj.sangu.ditsaadhaar.utils.Utils;

public class AdminProfileActivity extends AppCompatActivity{
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhone;
    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        initViews();
        loadProfile();
    }

    private void loadProfile() {
        SharedPrefManager prefManager = SharedPrefManager.getInstance(AdminProfileActivity.this);
        if (prefManager.isLoggedIn() == Constants.ADMIN_LOGGED_IN_KEY) {
            setProfileData(prefManager.getAdmin());
        }
    }

    private void setProfileData(AdminModal modal) {
        tvName.setText(modal.getAdminName());
        tvEmail.setText(modal.getAdminEmail());
        tvPhone.setText(modal.getAdminPhone());
        mainLayout.setVisibility(View.VISIBLE);
    }


    private void initViews() {
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        mainLayout = findViewById(R.id.mainLayout);
        Utils.setToolbar(this, (Toolbar) findViewById(R.id.toolbar), "Admin Profile");
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

}
