package com.mukul.onnwaytransporter.profilerelated;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.mukul.onnwaytransporter.MainActivity;
import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.SharePreferenceUtils;
import com.mukul.onnwaytransporter.profilerelated.changeprovidername.ChangeProviderNameDetails;
import com.mukul.onnwaytransporter.networking.Post;

public class UpdateNameActivity extends AppCompatActivity {

    private EditText userNameReq;
    private Button requestBtn;

    private ChangeProviderNameDetails changeProviderNameDetails;

    EditText old;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_name);



        //adding toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar_update_name);
        mToolbar.setTitle(getString(R.string.name_update_request));
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Edit Text
        userNameReq = findViewById(R.id.user_name_et);
        old = findViewById(R.id.old);

        userNameReq.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(userNameReq, InputMethodManager.SHOW_IMPLICIT);

        old.setText(SharePreferenceUtils.getInstance().getString("name"));
        //Button
        requestBtn = findViewById(R.id.requestBtnName);

        //handlinf activity for button click
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProviderNameDetails = new ChangeProviderNameDetails();
                changeProviderNameDetails.providerCurrentMobile = MainActivity.currenntMobileActive;
                changeProviderNameDetails.providerChangedName = userNameReq.getText().toString();
                new Post().doPostChangeName(UpdateNameActivity.this, userNameReq.getText().toString());
                finish();
            }
        });
    }
}
