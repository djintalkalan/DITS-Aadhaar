package dj.sangu.ditsaadhaar.network;


import dj.sangu.ditsaadhaar.modal.AdminModal;
import dj.sangu.ditsaadhaar.modal.OperatorModal;
import dj.sangu.ditsaadhaar.modal.requests.AddAadhaarDataRequest;
import dj.sangu.ditsaadhaar.modal.requests.AdminLoginRequest;
import dj.sangu.ditsaadhaar.modal.requests.LoginRequest;
import dj.sangu.ditsaadhaar.modal.requests.UpdatePasswordRequest;
import dj.sangu.ditsaadhaar.modal.responses.AdminLoginResponse;
import dj.sangu.ditsaadhaar.modal.responses.LoginResponse;
import dj.sangu.ditsaadhaar.modal.responses.OperatorListResponse;
import dj.sangu.ditsaadhaar.utils.URLs;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST(URLs.CUSTOMER_LOGIN)
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @Headers("Content-Type: application/json")
    @POST(URLs.ADMIN_LOGIN)
    Call<AdminLoginResponse> adminLogin(@Body AdminLoginRequest adminLoginRequest);

    @GET(URLs.CUSTOMER_DETAILS)
    Call<LoginResponse> getOperatorDetail(@Query("cust_id") String custId);

    @Headers("Content-Type: application/json")
    @POST(URLs.ADD_AADHAAR_DATA)
    Call<LoginResponse> addAadhaarData(@Body AddAadhaarDataRequest addAadhaarDataRequest);

    @Headers("Content-Type: application/json")
    @POST(URLs.UPDATE_PASSWORD_ADMIN)
    Call<LoginResponse> updatePasswordAdmin(@Body UpdatePasswordRequest updatePasswordRequest);

    @Headers("Content-Type: application/json")
    @POST(URLs.UPDATE_PASSWORD_CUSTOMER)
    Call<LoginResponse> updatePasswordCustomer(@Body UpdatePasswordRequest updatePasswordRequest);

    @Headers("Content-Type: application/json")
    @POST(URLs.OPERATOR_ADD)
    Call<LoginResponse> addOperator(@Body OperatorModal operatorModal);

    @Headers("Content-Type: application/json")
    @POST(URLs.ADMIN_ADD)
    Call<LoginResponse> addAdmin(@Body AdminModal adminModal);

    @GET(URLs.CUSTOMER_LIST)
    Call<OperatorListResponse> getOperatorList();
}
