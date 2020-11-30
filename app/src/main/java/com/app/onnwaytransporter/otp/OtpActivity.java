package com.app.onnwaytransporter.otp;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mukesh.OnOtpCompletionListener;
import com.app.onnwaytransporter.AllApiIneterface;
import com.app.onnwaytransporter.MySMSBroadcastReceiver;
import com.app.onnwaytransporter.SharePreferenceUtils;
import com.app.onnwaytransporter.driver.DriverMainActivity;
import com.app.onnwaytransporter.MainActivity;
import com.app.onnwaytransporter.loginBean;
import com.app.onnwaytransporter.networking.AppController;
import com.app.onnwaytransporter.networking.Post;
import com.app.onnwaytransporter.preferences.SaveSharedPreference;
import com.mukesh.OtpView;
import com.app.onnwaytransporter.R;
import com.app.onnwaytransporter.SelectUserType;
import com.app.onnwaytransporter.splash.SplashActivity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OtpActivity extends AppCompatActivity  implements  MySMSBroadcastReceiver.OTPReceiveListener {

    SharedData sharedData;
    Button nextBtn;

    private OtpView otpview;
    ProgressBar progress;
    private TextView mobileTv, resend;

    public CheckingPreRegistered checkingPreRegistered = new CheckingPreRegistered();

    private MySMSBroadcastReceiver smsReceiver;

    String phone , otp , id , type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        phone = getIntent().getStringExtra("phone");
        otp = getIntent().getStringExtra("otp");
        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        resend = findViewById(R.id.textView5);
        progress = findViewById(R.id.progressBar4);


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

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress.setVisibility(View.VISIBLE);

                AppController b = (AppController) getApplicationContext();

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.level(HttpLoggingInterceptor.Level.HEADERS);
                logging.level(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient client = new OkHttpClient.Builder().writeTimeout(1000, TimeUnit.SECONDS).readTimeout(1000, TimeUnit.SECONDS).connectTimeout(1000, TimeUnit.SECONDS).addInterceptor(logging).build();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .client(client)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                Call<loginBean> call = cr.resend(phone);

                call.enqueue(new Callback<loginBean>() {
                    @Override
                    public void onResponse(Call<loginBean> call, Response<loginBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            SharePreferenceUtils.getInstance().saveString("phone" , response.body().getPhone());
                            SharePreferenceUtils.getInstance().saveString("name" , response.body().getName());
                            SharePreferenceUtils.getInstance().saveString("email" , response.body().getEmail());
                            SharePreferenceUtils.getInstance().saveString("city" , response.body().getCity());

                            otp = response.body().getOtp();

                        } else {
                            Toast.makeText(OtpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                        progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<loginBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                        t.printStackTrace();
                    }
                });

            }
        });

        smsReceiver = new MySMSBroadcastReceiver();
        smsReceiver.setOTPListener(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        this.registerReceiver(smsReceiver, intentFilter);

        // Get an instance of SmsRetrieverClient, used to start listening for a matching
// SMS message.
        SmsRetrieverClient client = SmsRetriever.getClient(this /* context */);

// Starts SmsRetriever, which waits for ONE matching SMS message until timeout
// (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
// action SmsRetriever#SMS_RETRIEVED_ACTION.
        Task<Void> task = client.startSmsRetriever();

// Listen for success/failure of the start Task. If in a background thread, this
// can be made blocking using Tasks.await(task, [timeout]);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Successfully started retriever, expect broadcast intent
                // ...
                MySMSBroadcastReceiver receiver = new MySMSBroadcastReceiver();

            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Failed to start retriever, inspect Exception for more details
                // ...
            }
        });

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

    @Override
    public void onOTPReceived(String messageText) {
        Log.i("otp", messageText);
        int length = messageText.length();
        Log.i("otp", "Length:" + length);
        String substr = messageText.substring(length - 16, length);

        otpview.setText(substr);
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
            smsReceiver = null;
        }

    }

    @Override
    public void onOTPTimeOut() {

    }

    @Override
    public void onOTPReceivedError(String error) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
        }
    }

}
