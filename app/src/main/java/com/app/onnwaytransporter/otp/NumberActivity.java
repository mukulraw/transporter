package com.app.onnwaytransporter.otp;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.onnwaytransporter.AllApiIneterface;
import com.app.onnwaytransporter.SharePreferenceUtils;
import com.app.onnwaytransporter.loginBean;
import com.app.onnwaytransporter.networking.AppController;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.app.onnwaytransporter.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NumberActivity extends AppCompatActivity {

    public static  String mPhoneNumber="";
    MaterialTextField materialTextField;
    EditText ePhoneNumber;

    public static String enteredPhone;

    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        //finding and checking the regex
        ePhoneNumber=findViewById(R.id.cardView);
//        materialTextField=findViewById(R.id.materialTextField);
        progress = findViewById(R.id.progressBar4);
        ePhoneNumber.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(ePhoneNumber, InputMethodManager.SHOW_IMPLICIT);
        //whorl view added from github
//        WhorlView whorlView = (WhorlView) this.findViewById(R.id.whorl);
//        whorlView.start();

//        TextView textView = (TextView)findViewById(R.id.tvTerms);
        SpannableString content = new SpannableString("Terms and conditions");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        textView.setText(content);
    }

    public void next(View view)
    {
        mPhoneNumber = ePhoneNumber.getText().toString().trim();

        if(mPhoneNumber.length() == 10)
        {
            sendOTP();
        }
        else
        {
            Toast.makeText(this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
        }



    }

    void sendOTP() {

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

        Call<loginBean> call = cr.login(mPhoneNumber, SharePreferenceUtils.getInstance().getString("token"));

        call.enqueue(new Callback<loginBean>() {
            @Override
            public void onResponse(Call<loginBean> call, Response<loginBean> response) {

                if (response.body().getStatus().equals("1")) {

                    SharePreferenceUtils.getInstance().saveString("phone" , response.body().getPhone());
                    SharePreferenceUtils.getInstance().saveString("name" , response.body().getName());
                    SharePreferenceUtils.getInstance().saveString("email" , response.body().getEmail());
                    SharePreferenceUtils.getInstance().saveString("city" , response.body().getCity());

                    Intent intent = new Intent(NumberActivity.this, OtpActivity.class);
                    intent.putExtra("phone", mPhoneNumber);
                    intent.putExtra("otp", response.body().getOtp());
                    intent.putExtra("type", response.body().getType());
                    intent.putExtra("id", response.body().getUserId());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(NumberActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

}
