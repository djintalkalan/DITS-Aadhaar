package dj.sangu.ditsaadhaar.modal.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import dj.sangu.ditsaadhaar.modal.OperatorModal;

public class OperatorListResponse {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("data")
    @Expose
    private ArrayList<OperatorModal> operatorList = null;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<OperatorModal> getOperatorList() {
        return operatorList;
    }

    public void setOperatorList(ArrayList<OperatorModal> operatorList) {
        this.operatorList = operatorList;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}