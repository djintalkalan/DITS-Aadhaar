package dj.sangu.ditsaadhaar.modal.requests;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("cust_phone")
    @Expose
    private String custPhone;
    @SerializedName("cust_password")
    @Expose
    private String custPassword;

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustPassword() {
        return custPassword;
    }

    public void setCustPassword(String custPassword) {
        this.custPassword = custPassword;
    }

}