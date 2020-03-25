package com.sumit.myapplication.otp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sumit.myapplication.driver.DriverMainActivity;
import com.sumit.myapplication.MainActivity;
import com.sumit.myapplication.networking.Post;
import com.sumit.myapplication.preferences.SaveSharedPreference;
import com.mukesh.OtpView;
import com.sumit.myapplication.R;
import com.sumit.myapplication.SelectUserType;
import com.sumit.myapplication.splash.SplashActivity;

import swarajsaaj.smscodereader.interfaces.OTPListener;
import swarajsaaj.smscodereader.receivers.OtpReader;

public class OtpActivity extends AppCompatActivity  implements OTPListener {

    SharedData sharedData;
    Button nextBtn;

    private EditText otp1, otp2, otp3, otp4;
    private TextView mobileTv;

    public CheckingPreRegistered checkingPreRegistered = new CheckingPreRegistered();
    private OtpView otpView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

//        nextBtn = (Button) findViewById(R.id.nextBtn);
//        nextBtn.setClickable(false);

        sharedData = new SharedData(OtpActivity.this);
        checkingPreRegistered.entered_mobile = NumberActivity.enteredPhone;
        new Post().getIfUserRegistered(getApplication(), checkingPreRegistered);

//        //otp
//        OtpReader.bind(this,"ONNWAY");
//
//        otpView = findViewById(R.id.otp_view);
//        otpView.requestFocus();
//
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(otpView, InputMethodManager.SHOW_IMPLICIT);
//        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
//            @Override
//            public void onOtpCompleted(String otp) {
//                Toast.makeText(OtpActivity.this, "hello", Toast.LENGTH_SHORT).show();
//            }
//        });
//
        //shared prefernces getting state of phone no and session
        String s1=SaveSharedPreference.getPhoneNoStatus(getApplicationContext());
        Toast.makeText(this, ""+s1, Toast.LENGTH_SHORT).show();
        otp1 = findViewById(R.id.otp_et1);
        otp2 = findViewById(R.id.otp_et2);
        otp3 = findViewById(R.id.otp_et3);
        otp4 = findViewById(R.id.otp_et4);

        mobileTv = findViewById(R.id.mobile_tv);
        mobileTv.setText(NumberActivity.enteredPhone);
        //automatic OTP reader, library used: "swarajsaaj:otpreader:1.1"
        OtpReader.bind(OtpActivity.this,"ONNWAY");
    }


    public void next(View view) {
        //startActivity(new Intent(this, FirstTimeProfileActivity.class));
        Intent intent = new Intent(OtpActivity.this, SelectUserType.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void otpReceived(String messageText) {

        //when otp received then this method called
        String message = messageText;
        Log.i("otp", messageText);
        int length = message.length();
        Log.i("otp", "Length:" + length);
        String substr = message.substring(length - 4, length);
        String s1 = String.valueOf(substr.charAt(0));
        String s2 = String.valueOf(substr.charAt(1));
        String s3 = String.valueOf(substr.charAt(2));
        String s4 = String.valueOf(substr.charAt(3));
        otp1.setText(s1);
        otp2.setText(s2);
        otp3.setText(s3);
        otp4.setText(s4);
        Toast.makeText(OtpActivity.this, "Otp Received", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Post.cityName.equals("")){
                    Intent intent = new Intent(OtpActivity.this, SelectUserType.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    Toast.makeText(OtpActivity.this, "Not found", Toast.LENGTH_LONG).show();
                } else {
                    addData();
                    Toast.makeText(OtpActivity.this, Post.cityName+Post.mobileNumber+Post.transportName+Post.userType, Toast.LENGTH_LONG).show();
                    if(Post.userType.equals("1")){
                        Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(OtpActivity.this, DriverMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
//                nextBtn.setClickable(true);
//                nextBtn.setBackgroundColor(getResources().getColor(R.color.red_active_button));
            }
        }, 3000);
//        finish();
    }

    public void addData() {
        SplashActivity.currentUserType = Post.userType;
        boolean isInserted = sharedData.insertData(Post.userName, Post.mobileNumber, Post.userType, Post.cityName, Post.transportName);
        if(isInserted) {
            Toast.makeText(OtpActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
        }

    }

    public void viewAll() {
        Cursor cursor = sharedData.getAllData();
        if(cursor.getCount() == 0) {
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()) {
            buffer.append("Name" + cursor.getString(0));
        }
    }

    public void updateData() {
        boolean isUpdated = sharedData.updateData("hello", "hello", "hello", "hello", "hello");
        if(isUpdated) {

        }
    }
    public void deleteData() {
        Integer deletedRow = sharedData.deleteData("");
        if(deletedRow > 0){
            //deleted
        }
    }
}
