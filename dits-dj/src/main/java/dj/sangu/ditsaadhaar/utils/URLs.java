package dj.sangu.ditsaadhaar.utils;

public class URLs {
    private static final String ROOT_URL = "http://dits.sportsontheweb.net/aadhaar_project_api/";

    public static final String Url_Api = ROOT_URL + "api/";
    public static final String URL_IMAGES = ROOT_URL + "assets/img/";
    public static final String URL_ICONS = ROOT_URL + "assets/icons/";

    public static final String CUSTOMER_LOGIN = "customer/login.php";
    public static final String OPERATOR_ADD = "customer/create.php";
    public static final String ADMIN_LOGIN = "admin/login.php";
    public static final String ADMIN_ADD = "admin/create.php";
    public static final String CUSTOMER_DETAILS = "customer/read_single.php";
    public static final String CUSTOMER_LIST = "customer/read.php";
    public static final String ADD_AADHAAR_DATA= "aadhaar_data/create.php";
    public static final String UPDATE_PASSWORD_ADMIN= "admin/update_password.php";
    public static final String UPDATE_PASSWORD_CUSTOMER= "customer/update_password.php";
    public static final String CUSTOMER_REPORT= ROOT_URL+"report/report_customers.php";
}
