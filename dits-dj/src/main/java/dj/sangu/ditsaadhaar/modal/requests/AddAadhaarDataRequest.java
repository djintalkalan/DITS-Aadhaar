package dj.sangu.ditsaadhaar.modal.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddAadhaarDataRequest {
    @SerializedName("cust_id")
    @Expose
    private String custId;
    @SerializedName("machine_id")
    @Expose
    private String machineId;
    @SerializedName("station_id")
    @Expose
    private String stationId;
    @SerializedName("machine_location")
    @Expose
    private String machineLocation;
    @SerializedName("new_enrollment")
    @Expose
    private String newEnrollment;
    @SerializedName("mandatory_update")
    @Expose
    private String mandatoryUpdate;
    @SerializedName("normal_update")
    @Expose
    private String normalUpdate;
    @SerializedName("addedon")
    @Expose
    private Object addedon;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getMachineLocation() {
        return machineLocation;
    }

    public void setMachineLocation(String machineLocation) {
        this.machineLocation = machineLocation;
    }

    public String getNewEnrollment() {
        return newEnrollment;
    }

    public void setNewEnrollment(String newEnrollment) {
        this.newEnrollment = newEnrollment;
    }

    public String getMandatoryUpdate() {
        return mandatoryUpdate;
    }

    public void setMandatoryUpdate(String mandatoryUpdate) {
        this.mandatoryUpdate = mandatoryUpdate;
    }

    public String getNormalUpdate() {
        return normalUpdate;
    }

    public void setNormalUpdate(String normalUpdate) {
        this.normalUpdate = normalUpdate;
    }

    public Object getAddedon() {
        return addedon;
    }

    public void setAddedon(Object addedon) {
        this.addedon = addedon;
    }
}
