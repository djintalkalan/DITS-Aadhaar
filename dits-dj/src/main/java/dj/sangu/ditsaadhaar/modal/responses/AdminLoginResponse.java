package dj.sangu.ditsaadhaar.modal.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import dj.sangu.ditsaadhaar.modal.AdminModal;

public class AdminLoginResponse {

    @SerializedName("data")
    @Expose
    private AdminModal adminModal;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("error")
    @Expose
    private String error;

    public AdminModal getAdminModal() {
        return adminModal;
    }

    public void setData(AdminModal adminModal) {
        this.adminModal = adminModal;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
