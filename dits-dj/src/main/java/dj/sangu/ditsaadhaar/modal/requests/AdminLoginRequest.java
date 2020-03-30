package dj.sangu.ditsaadhaar.modal.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminLoginRequest {
    @SerializedName("admin_phone")
    @Expose
    private String adminPhone;
    @SerializedName("admin_password")
    @Expose
    private String adminPassword;

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
