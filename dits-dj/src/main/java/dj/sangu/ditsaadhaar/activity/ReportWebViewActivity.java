package dj.sangu.ditsaadhaar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.io.UnsupportedEncodingException;

import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.utils.Constants;
import dj.sangu.ditsaadhaar.utils.SharedPrefManager;
import dj.sangu.ditsaadhaar.utils.URLs;
import dj.sangu.ditsaadhaar.utils.Utils;

public class ReportWebViewActivity extends AppCompatActivity {
    private WebView webView;
    private String custId;
    private String fromDate;
    private String toDate;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_web_view);
        getProps();
        initViews();
        try {
            initWebview();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void initWebview() throws UnsupportedEncodingException {
        webView.setWebViewClient(new CustomWebViewClient(progressBar));
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webSetting.setDisplayZoomControls(true);
        webView.getSettings().setBuiltInZoomControls(true);
        String path = "cust_id=" + custId + "&start_date=" + fromDate + "&end_date=" + toDate;

        webView.postUrl(URLs.CUSTOMER_REPORT, path.getBytes("UTF-8"));
    }

    private void initViews() {
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        Utils.setToolbar(this, (Toolbar) findViewById(R.id.toolbar), "Operator Report");
    }

    private void getProps() {
        Intent i = getIntent();
        custId = i.getStringExtra(Constants.OPERATOR_ID);
        fromDate = i.getStringExtra(Constants.FROM_DATE);
        toDate = i.getStringExtra(Constants.TO_DATE);
    }

    private class CustomWebViewClient extends WebViewClient {
        private ProgressBar progressBar;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public CustomWebViewClient(ProgressBar progressBar) {
            this.progressBar = progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.print_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.print_menu_item:
                createWebPagePrint(webView);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


        public void createWebPagePrint (WebView webView){

            SharedPrefManager prefManager = SharedPrefManager.getInstance(this);
            String jobName="";
            if(prefManager.isLoggedIn()==Constants.ADMIN_LOGGED_IN_KEY){
                jobName="ADMIN_PRINT_REPORT_ID";
            }else{
                jobName=prefManager.getCustomer().getCustName()+" Report "+fromDate+"-"+toDate;
            }
            PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
            PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);
            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setMediaSize(PrintAttributes.MediaSize.NA_LETTER.asLandscape());
            PrintJob printJob = printManager.print(jobName, printAdapter, builder.build());
            if (printJob.isCompleted()) {
                Toast.makeText(getApplicationContext(), "Print Complete", Toast.LENGTH_LONG).show();
            } else if (printJob.isFailed()) {
                Toast.makeText(getApplicationContext(), "Print Failed", Toast.LENGTH_LONG).show();
            }
            // Save the job object for later status checking
        }

}
