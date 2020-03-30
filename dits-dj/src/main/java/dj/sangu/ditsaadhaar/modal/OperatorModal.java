package dj.sangu.ditsaadhaar.modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatorModal implements Parcelable {

    @SerializedName("cust_id")
    @Expose
    private String custId;
    @SerializedName("cust_name")
    @Expose
    private String custName;
    @SerializedName("cust_email")
    @Expose
    private String custEmail;
    @SerializedName("pec_address")
    @Expose
    private String pecAddress;
    @SerializedName("cust_password")
    @Expose
    private String custPassword;
    @SerializedName("cust_phone")
    @Expose
    private String custPhone;
    @SerializedName("machine_id")
    @Expose
    private String machineId;
    @SerializedName("machine_location")
    @Expose
    private String machineLocation;
    @SerializedName("station_id")
    @Expose
    private String stationId;
    @SerializedName("addedon")
    @Expose
    private String addedon;
    @SerializedName("updatedon")
    @Expose
    private String updatedon;

    public OperatorModal(Parcel in) {
        custId = in.readString();
        custName = in.readString();
        custEmail = in.readString();
        pecAddress = in.readString();
        custPassword = in.readString();
        custPhone = in.readString();
        machineId = in.readString();
        machineLocation = in.readString();
        stationId = in.readString();
        addedon = in.readString();
        updatedon = in.readString();
    }

    public static final Creator<OperatorModal> CREATOR = new Creator<OperatorModal>() {
        @Override
        public OperatorModal createFromParcel(Parcel in) {
            return new OperatorModal(in);
        }

        @Override
        public OperatorModal[] newArray(int size) {
            return new OperatorModal[size];
        }
    };

    public OperatorModal() {

    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getPecAddress() {
        return pecAddress;
    }

    public void setPecAddress(String pecAddress) {
        this.pecAddress = pecAddress;
    }

    public String getCustPassword() {
        return custPassword;
    }

    public void setCustPassword(String custPassword) {
        this.custPassword = custPassword;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getMachineLocation() {
        return machineLocation;
    }

    public void setMachineLocation(String machineLocation) {
        this.machineLocation = machineLocation;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(custId);
        dest.writeString(custName);
        dest.writeString(custEmail);
        dest.writeString(pecAddress);
        dest.writeString(custPassword);
        dest.writeString(custPhone);
        dest.writeString(machineId);
        dest.writeString(machineLocation);
        dest.writeString(stationId);
        dest.writeString(addedon);
        dest.writeString(updatedon);
    }
}