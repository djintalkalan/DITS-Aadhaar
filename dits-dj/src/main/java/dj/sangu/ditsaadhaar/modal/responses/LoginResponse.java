
package dj.sangu.ditsaadhaar.modal.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import dj.sangu.ditsaadhaar.modal.OperatorModal;

public class LoginResponse {

    @SerializedName("data")
    @Expose
    private OperatorModal operatorModal;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("error")
    @Expose
    private String error;

    public OperatorModal getOperatorModal() {
        return operatorModal;
    }

    public void setOperatorModal(OperatorModal operatorModal) {
        this.operatorModal = operatorModal;
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