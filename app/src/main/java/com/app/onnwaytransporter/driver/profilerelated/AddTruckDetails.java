package com.app.onnwaytransporter.driver.profilerelated;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.onnwaytransporter.AllApiIneterface;
import com.app.onnwaytransporter.BuildConfig;
import com.app.onnwaytransporter.SharePreferenceUtils;
import com.app.onnwaytransporter.driver.profilerelated.DriverTruckDetailsRecyclerView.AddTruckDetailsUser;
import com.app.onnwaytransporter.R;
import com.app.onnwaytransporter.networking.AppController;
import com.app.onnwaytransporter.ordersPOJO.ordersBean;
import com.app.onnwaytransporter.truckTypePOJO.truckTypeBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AddTruckDetails extends AppCompatActivity {
    public AddTruckDetailsUser addTruckDetailsUser;
    private LinearLayout openTruckBtn, containerBtn, trailerBtn;

    String tid = "";

    private EditText registrationNumber,driverName, driverNumber;
    private TextView displaySelectedTruck;
    private Button addTruckBtn;
    ImageView front , back;

    File f1 , f2;
    Uri uri1 , uri2;

    ProgressBar progress;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_truck_details);
        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969


        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Add Truck Details");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //Linear Layout of truck types
        front = findViewById(R.id.front);
        progress = findViewById(R.id.progressBar3);
        back = findViewById(R.id.back);
        openTruckBtn = findViewById(R.id.open_truck_btn);
        containerBtn = findViewById(R.id.container_btn);
        trailerBtn = findViewById(R.id.trailer_btn);
        //edit text
        registrationNumber= findViewById(R.id.input_truck_reg_no);
        driverName= findViewById(R.id.name);
        driverNumber=findViewById(R.id.mobile);
        //display selected truck
        displaySelectedTruck= findViewById(R.id.sel_truck);
        //add truck btn
        addTruckBtn= findViewById(R.id.add_truck_details_btn);
        //handling alertDialog for Truck


        openTruckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOpenTruckType();
//                openTruckBtn.setBackgroundColor(Color.parseColor("#FF1001"));
//                containerBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                trailerBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });

        containerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContainerType();
//                openTruckBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                containerBtn.setBackgroundColor(Color.parseColor("#FF1001"));
//                trailerBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });

        trailerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTrailerType();
//                openTruckBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                containerBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                trailerBtn.setBackgroundColor(Color.parseColor("#FF1001"));
            }
        });

        //handling add truck button
        addTruckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String r = registrationNumber.getText().toString();
                String n = driverName.getText().toString();
                String m = driverNumber.getText().toString();

                if (tid.length() > 0)
                {
                    if (f1 != null)
                    {
                        if (f2 != null)
                        {
                            if (r.length() > 0)
                            {
                                if (n.length() > 0)
                                {
                                    if (m.length() > 0)
                                    {

                                        MultipartBody.Part body = null;

                                        try {

                                            RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                                            body = MultipartBody.Part.createFormData("front", f1.getName(), reqFile1);


                                        } catch (Exception e1) {
                                            e1.printStackTrace();
                                        }

                                        MultipartBody.Part body2 = null;

                                        try {

                                            RequestBody reqFile12 = RequestBody.create(MediaType.parse("multipart/form-data"), f2);
                                            body2 = MultipartBody.Part.createFormData("back", f2.getName(), reqFile12);


                                        } catch (Exception e1) {
                                            e1.printStackTrace();
                                        }

                                        progress.setVisibility(View.VISIBLE);

                                        AppController b = (AppController) getApplicationContext();

                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(b.baseurl)
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                        Call<ordersBean> call = cr.store_trucks_provider(
                                                SharePreferenceUtils.getInstance().getString("userId"),
                                                tid,
                                                r,
                                                n,
                                                m,
                                                body,
                                                body2
                                        );

                                        call.enqueue(new Callback<ordersBean>() {
                                            @Override
                                            public void onResponse(Call<ordersBean> call, Response<ordersBean> response) {

                                                if (response.body().getStatus().equals("1"))
                                                {
                                                    Toast.makeText(AddTruckDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                                else
                                                {
                                                    Toast.makeText(AddTruckDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                }



                                                progress.setVisibility(View.GONE);
                                            }

                                            @Override
                                            public void onFailure(Call<ordersBean> call, Throwable t) {
                                                progress.setVisibility(View.GONE);
                                            }
                                        });

                                    }
                                    else
                                    {
                                        Toast.makeText(AddTruckDetails.this, "Invalid driver mobile", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(AddTruckDetails.this, "Invalid driver name", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(AddTruckDetails.this, "Invalid registration number", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(AddTruckDetails.this, "Please select a back image", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(AddTruckDetails.this, "Please select a front image", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(AddTruckDetails.this, "Invalid vehicle type", Toast.LENGTH_SHORT).show();
                }



            }
        });

        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddTruckDetails.this, R.style.MyDialogTheme);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                            File newdir = new File(dir);
                            try {
                                newdir.mkdirs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                            f1 = new File(file);
                            try {
                                f1.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            uri1 = FileProvider.getUriForFile(Objects.requireNonNull(AddTruckDetails.this), BuildConfig.APPLICATION_ID + ".provider", f1);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri1);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 1);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 2);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddTruckDetails.this, R.style.MyDialogTheme);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                            File newdir = new File(dir);
                            try {
                                newdir.mkdirs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                            f2 = new File(file);
                            try {
                                f2.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            uri2 = FileProvider.getUriForFile(Objects.requireNonNull(AddTruckDetails.this), BuildConfig.APPLICATION_ID + ".provider", f2);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri2);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 3);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 4);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });

    }


    private void getOpenTruckType() {

        final Dialog dialog = new Dialog(AddTruckDetails.this, R.style.MyDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.truck_type_dialog);
        dialog.show();

        TextView title = dialog.findViewById(R.id.textView10);
        final RecyclerView grid = dialog.findViewById(R.id.recyclerView);
        final ProgressBar progress = dialog.findViewById(R.id.progressBar2);

        title.setText("Open Truck Size");


        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<List<truckTypeBean>> call = cr.getTrucks("open truck");

        call.enqueue(new Callback<List<truckTypeBean>>() {
            @Override
            public void onResponse(Call<List<truckTypeBean>> call, Response<List<truckTypeBean>> response) {

                TruckAdapter adapter = new TruckAdapter(AddTruckDetails.this , response.body() , "open truck" , dialog);
                GridLayoutManager manager = new GridLayoutManager(AddTruckDetails.this , 3);

                grid.setAdapter(adapter);
                grid.setLayoutManager(manager);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<truckTypeBean>> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    private void getContainerType() {
        final Dialog dialog = new Dialog(AddTruckDetails.this, R.style.MyDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.truck_type_dialog);
        dialog.show();

        TextView title = dialog.findViewById(R.id.textView10);
        final RecyclerView grid = dialog.findViewById(R.id.recyclerView);
        final ProgressBar progress = dialog.findViewById(R.id.progressBar2);

        title.setText("Container Size");


        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<List<truckTypeBean>> call = cr.getTrucks("container");

        call.enqueue(new Callback<List<truckTypeBean>>() {
            @Override
            public void onResponse(Call<List<truckTypeBean>> call, Response<List<truckTypeBean>> response) {

                TruckAdapter adapter = new TruckAdapter(AddTruckDetails.this , response.body() , "container" , dialog);
                GridLayoutManager manager = new GridLayoutManager(AddTruckDetails.this , 3);

                grid.setAdapter(adapter);
                grid.setLayoutManager(manager);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<truckTypeBean>> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void getTrailerType() {
        final Dialog dialog = new Dialog(AddTruckDetails.this, R.style.MyDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.truck_type_dialog);
        dialog.show();

        TextView title = dialog.findViewById(R.id.textView10);
        final RecyclerView grid = dialog.findViewById(R.id.recyclerView);
        final ProgressBar progress = dialog.findViewById(R.id.progressBar2);

        title.setText("Trailer Size");


        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<List<truckTypeBean>> call = cr.getTrucks("trailer");

        call.enqueue(new Callback<List<truckTypeBean>>() {
            @Override
            public void onResponse(Call<List<truckTypeBean>> call, Response<List<truckTypeBean>> response) {

                TruckAdapter adapter = new TruckAdapter(AddTruckDetails.this , response.body() , "trailer" , dialog);
                GridLayoutManager manager = new GridLayoutManager(AddTruckDetails.this , 3);

                grid.setAdapter(adapter);
                grid.setLayoutManager(manager);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<truckTypeBean>> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    class TruckAdapter extends RecyclerView.Adapter<TruckAdapter.ViewHolder>
    {
        Context context;
        List<truckTypeBean> list = new ArrayList<>();
        String type;
        Dialog dialog;

        TruckAdapter(Context context, List<truckTypeBean> list, String type , Dialog dialog)
        {
            this.context = context;
            this.list = list;
            this.type = type;
            this.dialog = dialog;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.truck_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final truckTypeBean item = list.get(position);

            if (tid.equals(item.getId()))
            {
                holder.card.setCardBackgroundColor(Color.parseColor("#F5DEDE"));
            }
            else
            {
                holder.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            if (type.equals("open truck")) {
                holder.image.setImageResource(R.drawable.open);
            } else if (type.equals("container")) {
                holder.image.setImageResource(R.drawable.container);
            } else {
                holder.image.setImageResource(R.drawable.trailer);
            }

            holder.text.setText(item.getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checktruckType(item.getId() , item.getType() , item.getTitle() , item.getCapcacity() , item.getBox_length() , item.getBox_width() , item.getTitle());
                    dialog.dismiss();

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            ImageView image;
            TextView text;
            CardView card;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.image);
                text = itemView.findViewById(R.id.text);
                card = itemView.findViewById(R.id.card);

            }
        }
    }

    private void checktruckType(String id, String type , String title , String capcaity , String length , String width , String trucktitle)
    {
        this.tid = id;


        displaySelectedTruck.setText(type + " - " + title);
        displaySelectedTruck.setVisibility(View.VISIBLE);


        if (type.equals("open truck")) {
            openTruckBtn.setBackgroundResource(R.drawable.red_back_round);
            containerBtn.setBackgroundResource(R.drawable.white_back_round);
            trailerBtn.setBackgroundResource(R.drawable.white_back_round);
        } else if (type.equals("container")) {
            openTruckBtn.setBackgroundResource(R.drawable.white_back_round);
            containerBtn.setBackgroundResource(R.drawable.red_back_round);
            trailerBtn.setBackgroundResource(R.drawable.white_back_round);
        } else {
            openTruckBtn.setBackgroundResource(R.drawable.white_back_round);
            containerBtn.setBackgroundResource(R.drawable.white_back_round);
            trailerBtn.setBackgroundResource(R.drawable.red_back_round);
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            uri1 = data.getData();

            Log.d("uri", String.valueOf(uri1));

            String ypath = getPath(AddTruckDetails.this, uri1);
            assert ypath != null;
            f1 = new File(ypath);

            Log.d("path", ypath);


            front.setImageURI(uri1);

        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            front.setImageURI(uri1);
        }



        if (requestCode == 4 && resultCode == RESULT_OK && null != data) {
            uri2 = data.getData();
            back.setImageURI(uri2);
            Log.d("uri", String.valueOf(uri2));

            String ypath = getPath(AddTruckDetails.this, uri2);
            assert ypath != null;
            f2 = new File(ypath);



        } else if (requestCode == 3 && resultCode == RESULT_OK) {
            back.setImageURI(uri2);
        }


    }


    private static String getPath(final Context context, final Uri uri) {

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        final String column = "_data";
        final String[] projection = {
                column
        };
        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                null)) {
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }
        return null;
    }

}
