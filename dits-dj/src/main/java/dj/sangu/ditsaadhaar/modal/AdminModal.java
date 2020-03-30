package dj.sangu.ditsaadhaar.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminModal {

    @SerializedName("admin_id")
    @Expose
    private String adminId;
    @SerializedName("admin_name")
    @Expose
    private String adminName;
    @SerializedName("admin_email")
    @Expose
    private String adminEmail;
    @SerializedName("admin_phone")
    @Expose
    private String adminPhone;
    @SerializedName("admin_type")
    @Expose
    private String adminType;

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @SerializedName("admin_password")
    @Expose
    private String adminPassword;
    @SerializedName("addedon")
    @Expose
    private String addedon;
    @SerializedName("updatedon")
    @Expose
    private String updatedon;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getAdminType() {
        return adminType;
    }

    public void setAdminType(String adminType) {
        this.adminType = adminType;
    }

    public String getAddedon() {
        return addedon;
    }

    public void setAddedon(String addedon) {
        this.addedon = addedon;
    }

    public String getUpdatedon() {
        return updatedon;
    }

    public void setUpdatedon(String updatedon) {
        this.updatedon = updatedon;
    }

}