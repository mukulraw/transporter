package com.sumit.myapplication.otp;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.sumit.myapplication.networking.Post;
import com.sumit.myapplication.preferences.SaveSharedPreference;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.sumit.myapplication.R;

public class NumberActivity extends AppCompatActivity {

    public static  String mPhoneNumber="";
    MaterialTextField materialTextField;
    EditText ePhoneNumber;

    public static String enteredPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        //finding and checking the regex
        ePhoneNumber=(EditText) findViewById(R.id.mPhoneNumber);
//        materialTextField=findViewById(R.id.materialTextField);

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
        if(mPhoneNumber.isEmpty())
        {
            ePhoneNumber.setError("Please Fill");
            Toast.makeText(this, "Please Enter the phone number", Toast.LENGTH_LONG).show();
        }
        else
        {
            enteredPhone = mPhoneNumber;
            //geting otp from server
            new Post().doPost(this,mPhoneNumber);
            SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
            SaveSharedPreference.setPhoneNo(getApplicationContext(), mPhoneNumber);
            //todo sending to the next OtpActivity
//            startActivity(new Intent(this,OtpActivity.class));
            Intent intent = new Intent(NumberActivity.this, OtpActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
