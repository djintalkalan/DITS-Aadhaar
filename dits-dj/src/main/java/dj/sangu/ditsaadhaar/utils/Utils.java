package dj.sangu.ditsaadhaar.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dj.sangu.ditsaadhaar.R;

public class Utils {
    public static void setToolbar(final Context mCtx, Toolbar toolbar, String title) {
        ((AppCompatActivity) mCtx).setSupportActionBar(toolbar);
        toolbar.findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mCtx).finish();
                ((Activity) mCtx).overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }
        });
        ((TextView) toolbar.findViewById(R.id.title)).setText(title);
    }


    public static Calendar dateStringToCalender(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public  static void hideKeyboard(Context mCtx,View v) {
        try {
            InputMethodManager imm = (InputMethodManager) mCtx.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}