package com.sumit.myapplication.testing;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import com.sumit.myapplication.R;

public class TestingActivity extends AppCompatActivity {


AutoCompleteTextView autoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        autoCompleteTextView=findViewById(R.id.autoCompleteEditText);
    }
}
