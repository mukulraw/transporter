package com.app.onnwaytransporter.driver.Image_Picker;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.onnwaytransporter.R;


public class Dialog_Fragment extends DialogFragment {
    private TextView camera,gallery;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_fragment_user_kyc,container,false);
        //act = (Activity)view.getContext();
        camera=view.findViewById(R.id.camera);
        gallery=view.findViewById(R.id.gallery);
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"camera",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), FileUploadCamera.class);
                startActivity(intent);




            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "hilo gallery", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),FileUpload.class);
                startActivity(intent);

//                activity.startActivity(new Intent(activity,FileUploadCamera.class));

            }
        });




    }
    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity)cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)cont).getBaseContext());

        return null;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
//                int image_type =  KycDriverActivity.licenseSide;
//
//                String mobile_no=DriverMainActivity.currenntMobileActive;

}
