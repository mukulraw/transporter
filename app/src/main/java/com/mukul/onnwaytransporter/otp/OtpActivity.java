package com.mukul.onnwaytransporter.otp;

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

import com.mukesh.OnOtpCompletionListener;
import com.mukul.onnwaytransporter.SharePreferenceUtils;
import com.mukul.onnwaytransporter.driver.DriverMainActivity;
import com.mukul.onnwaytransporter.MainActivity;
import com.mukul.onnwaytransporter.networking.Post;
import com.mukul.onnwaytransporter.preferences.SaveSharedPreference;
import com.mukesh.OtpView;
import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.SelectUserType;
import com.mukul.onnwaytransporter.splash.SplashActivity;

import swarajsaaj.smscodereader.interfaces.OTPListener;
import swarajsaaj.smscodereader.receivers.OtpReader;

public class OtpActivity extends AppCompatActivity  implements OTPListener {

    SharedData sharedData;
    Button nextBtn;

    private OtpView otpview;
    private TextView mobileTv;

    public CheckingPreRegistered checkingPreRegistered = new CheckingPreRegistered();


    String phone , otp , id , type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        phone = getIntent().getStringExtra("phone");
        otp = getIntent().getStringExtra("otp");
        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");


//        nextBtn = (Button) findViewById(R.id.nextBtn);
//        nextBtn.setClickable(false);

        sharedData = new SharedData(OtpActivity.this);
        checkingPreRegistered.entered_mobile = NumberActivity.enteredPhone;
        //new Post().getIfUserRegistered(getApplication(), checkingPreRegistered);

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

        otpview = findViewById(R.id.linearLayout);

        mobileTv = findViewById(R.id.mobile_tv);
        mobileTv.setText(phone);
        //automatic OTP reader, library used: "swarajsaaj:otpreader:1.1"
        OtpReader.bind(OtpActivity.this,"SNDOTP");

        otpview.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String substr) {

                if (substr.equals(otp))
                {

                    SharePreferenceUtils.getInstance().saveString("userId" , id);
                    SharePreferenceUtils.getInstance().saveString("user" , type);

                    if (type.equals("transporter"))
                    {
                        Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        Toast.makeText(OtpActivity.this, "OTP Verified", Toast.LENGTH_LONG).show();
                    }
                    else if (type.equals("driver"))
                    {
                        Intent intent = new Intent(OtpActivity.this, DriverMainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        Toast.makeText(OtpActivity.this, "OTP Verified", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Intent intent = new Intent(OtpActivity.this, SelectUserType.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        Toast.makeText(OtpActivity.this, "OTP Verified", Toast.LENGTH_LONG).show();
                    }


                }
                else
                {
                    Toast.makeText(OtpActivity.this, "Invalid OTP", Toast.LENGTH_LONG).show();
                }

            }
        });

    }




    @Override
    public void otpReceived(String messageText) {

//when otp received then this method called
        Log.i("otp", messageText);
        int length = messageText.length();
        Log.i("otp", "Length:" + length);
        String substr = messageText.substring(length - 4, length);

        otpview.setText(messageText);





        /*new Handler().postDelayed(new Runnable() {
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
        }, 3000);*/
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
