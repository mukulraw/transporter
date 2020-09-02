package com.mukul.onnwaytransporter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.mukul.onnwaytransporter.networking.AppController;
import com.mukul.onnwaytransporter.orderDetailsPOJO.Data;
import com.mukul.onnwaytransporter.orderDetailsPOJO.Doc;
import com.mukul.onnwaytransporter.orderDetailsPOJO.Pod;
import com.mukul.onnwaytransporter.orderDetailsPOJO.orderDetailsBean;
import com.mukul.onnwaytransporter.ordersPOJO.ordersBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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

public class OrderDetails4 extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    // Used in checking for runtime permissions.
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    // The BroadcastReceiver used to listen from broadcasts from the service.
    private MyReceiver myReceiver;

    // A reference to the service used to get location updates.
    private LocationUpdatesService mService = null;

    // Tracks the bound state of the service.
    private boolean mBound = false;


    // Monitors the state of the connection to the service.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    TextView orderid, orderdate, truck, source, destination, material, weight, date, status, fare, distance, paid;

    TextView vehiclenumber, drivernumber;

    Button add, upload1, upload2;


    RecyclerView pod, documents;

    ProgressBar progress;

    String id;

    Button startend;

    private Uri uri1;
    private File f1;

    String oid;

    String sourceLAT = "", sourceLNG = "", destinationLAT = "", destinationLNG = "";

    TextView navigate;
    TextView laodernote;

    private CardView partLoad1, partLoad2, partLoad3, partLoad4, partLoad5, partLoad6, partLoad7, partLoad8;
    private double click1 = 0, click2 = 0, click3 = 0, click4 = 0, click5 = 0, click6 = 0, click7 = 0, click8 = 0;
    private TextView truckTypeDetails, truckCapacity, boxLength, boxWidth, boxArea;
    private TextView selectedArea, remainingArea;
    String trucktitle, srcAddress, destAddress, pickUpDate, mid, loadType;
    String length, width, height, desc, tid, passing;
    float capcaity, len, wid;
    List<String> selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details4);

        id = getIntent().getStringExtra("id");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Order Details");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        partLoad1 = findViewById(R.id.part_load_card_1);
        partLoad2 = findViewById(R.id.part_load_card_2);
        partLoad3 = findViewById(R.id.part_load_card_3);
        partLoad4 = findViewById(R.id.part_load_card_4);
        partLoad5 = findViewById(R.id.part_load_card_5);
        partLoad6 = findViewById(R.id.part_load_card_6);
        partLoad7 = findViewById(R.id.part_load_card_7);
        partLoad8 = findViewById(R.id.part_load_card_8);

        weight = findViewById(R.id.textView38);
        truckTypeDetails = findViewById(R.id.truck_type);
        truckCapacity = findViewById(R.id.truck_capacity);
        boxLength = findViewById(R.id.box_length);
        boxWidth = findViewById(R.id.box_width);
        boxArea = findViewById(R.id.box_area);
        selectedArea = findViewById(R.id.selected_area);
        remainingArea = findViewById(R.id.remaining_area);

        laodernote = findViewById(R.id.textView46);
        navigate = findViewById(R.id.imageButton);
        orderid = findViewById(R.id.textView16);
        orderdate = findViewById(R.id.textView17);
        truck = findViewById(R.id.textView19);
        source = findViewById(R.id.textView20);
        destination = findViewById(R.id.textView21);
        material = findViewById(R.id.textView23);
        weight = findViewById(R.id.textView25);
        date = findViewById(R.id.textView29);
        status = findViewById(R.id.textView30);
        fare = findViewById(R.id.textView31);
        distance = findViewById(R.id.textView11);
        paid = findViewById(R.id.textView32);
        progress = findViewById(R.id.progressBar);
        startend = findViewById(R.id.button);

        vehiclenumber = findViewById(R.id.textView291);
        drivernumber = findViewById(R.id.textView35);

        add = findViewById(R.id.button3);
        upload1 = findViewById(R.id.button5);
        upload2 = findViewById(R.id.button6);

        pod = findViewById(R.id.pod);
        documents = findViewById(R.id.recyclerView2);

        // Check that the user hasn't revoked permissions by going to Settings.
        if (Utils.requestingLocationUpdates(this)) {
            if (!checkPermissions()) {
                requestPermissions();
            }
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(OrderDetails4.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.add_vehicle_dialog);
                dialog.show();

                final EditText vnum = dialog.findViewById(R.id.editText2);
                final EditText dnum = dialog.findViewById(R.id.editText3);
                Button submit = dialog.findViewById(R.id.button7);
                final ProgressBar bar = dialog.findViewById(R.id.progressBar5);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {

                        String v = vnum.getText().toString();
                        String d = dnum.getText().toString();

                        if (v.length() > 0) {
                            if (d.length() > 0) {

                                bar.setVisibility(View.VISIBLE);

                                AppController b = (AppController) getApplicationContext();

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(b.baseurl)
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                Call<ordersBean> call = cr.addVehicleNumber(id, v, d);

                                call.enqueue(new Callback<ordersBean>() {
                                    @Override
                                    public void onResponse(Call<ordersBean> call, Response<ordersBean> response) {

                                        if (response.body().getStatus().equals("1")) {
                                            dialog.dismiss();
                                            onResume();
                                        } else {
                                            Toast.makeText(OrderDetails4.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                        bar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onFailure(Call<ordersBean> call, Throwable t) {
                                        bar.setVisibility(View.GONE);
                                    }
                                });

                            } else {
                                Toast.makeText(OrderDetails4.this, "Invalid Driver Number", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(OrderDetails4.this, "Invalid Vehicle Number", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
        });


        upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OrderDetails4.this);
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

                            uri1 = FileProvider.getUriForFile(Objects.requireNonNull(OrderDetails4.this), BuildConfig.APPLICATION_ID + ".provider", f1);

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

        upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OrderDetails4.this);
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

                            uri1 = FileProvider.getUriForFile(Objects.requireNonNull(OrderDetails4.this), BuildConfig.APPLICATION_ID + ".provider", f1);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri1);
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

        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("geo:" + sourceLAT + "," + sourceLNG);

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(LocationUpdatesService.ACTION_BROADCAST));

        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<orderDetailsBean> call = cr.providerOrderDetails(id);

        call.enqueue(new Callback<orderDetailsBean>() {
            @Override
            public void onResponse(Call<orderDetailsBean> call, Response<orderDetailsBean> response) {

                Data item = response.body().getData();

                oid = item.getId();
                sourceLAT = item.getSourceLAT();
                sourceLNG = item.getSourceLNG();
                destinationLAT = item.getDestinationLAT();
                destinationLNG = item.getDestinationLNG();

                Location startPoint = new Location("Source");
                startPoint.setLatitude(Double.parseDouble(sourceLAT));
                startPoint.setLongitude(Double.parseDouble(sourceLNG));

                Location endPoint = new Location("Destination");
                endPoint.setLatitude(Double.parseDouble(destinationLAT));
                endPoint.setLongitude(Double.parseDouble(destinationLNG));

                distance.setText((startPoint.distanceTo(endPoint) / 1000) + " km");

                truck.setText(item.getTruckType());
                source.setText(item.getPickupAddress() + ", " + item.getPickupCity() + ", " + item.getPickupPincode() + ", " + item.getSource());
                destination.setText(item.getDropAddress() + ", " + item.getDropCity() + ", " + item.getDropPincode() + ", " + item.getDestination());
                //destination.setText(item.getDestination());
                material.setText(item.getMaterial());
                weight.setText(item.getWeight());
                date.setText(item.getSchedule());
                status.setText(item.getStatus());
                fare.setText("₹ " + item.getFare());
                paid.setText(item.getPaid());
                laodernote.setText(item.getRemarks());

                if (item.getSelected().contains("1")) {
                    partLoad1.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                if (item.getSelected().contains("2")) {
                    partLoad2.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                if (item.getSelected().contains("3")) {
                    partLoad3.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                if (item.getSelected().contains("4")) {
                    partLoad4.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                if (item.getSelected().contains("5")) {
                    partLoad5.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                if (item.getSelected().contains("6")) {
                    partLoad6.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                if (item.getSelected().contains("7")) {
                    partLoad7.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                if (item.getSelected().contains("8")) {
                    partLoad8.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }


                truckTypeDetails.setText(item.getTruckTypeDetails());
                truckCapacity.setText(item.getTruckCapacity());
                boxLength.setText(item.getBoxLength());
                boxWidth.setText(item.getBoxWidth());
                boxArea.setText(item.getBoxArea() + " sq. ft.");
                selectedArea.setText(item.getSelectedArea() + " sq. ft.");
                remainingArea.setText(item.getRemainingArea() + " sq. ft.");

                vehiclenumber.setText(item.getVehicleNumber());
                drivernumber.setText(item.getDriverNumber());

                if (item.getVehicleNumber().length() > 0) {
                    add.setVisibility(View.GONE);
                    vehiclenumber.setVisibility(View.VISIBLE);
                    drivernumber.setVisibility(View.VISIBLE);
                } else {
                    add.setVisibility(View.VISIBLE);
                    vehiclenumber.setVisibility(View.GONE);
                    drivernumber.setVisibility(View.GONE);
                }

                if (item.getStatus().equals("started")) {
                    startend.setText("FINISH");
                } else {
                    startend.setText("START");
                }


                PODAdapter adapter = new PODAdapter(OrderDetails4.this, item.getPod());
                GridLayoutManager manager = new GridLayoutManager(OrderDetails4.this, 2);
                pod.setAdapter(adapter);
                pod.setLayoutManager(manager);

                DocAdapter adapter2 = new DocAdapter(OrderDetails4.this, item.getDoc());
                GridLayoutManager manager2 = new GridLayoutManager(OrderDetails4.this, 2);
                documents.setAdapter(adapter2);
                documents.setLayoutManager(manager2);

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<orderDetailsBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    class PODAdapter extends RecyclerView.Adapter<PODAdapter.ViewHolder> {

        List<Pod> list = new ArrayList<>();
        Context context;

        public PODAdapter(Context context, List<Pod> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.image_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Pod item = list.get(position);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getName(), holder.image, options);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

    class DocAdapter extends RecyclerView.Adapter<DocAdapter.ViewHolder> {

        List<Doc> list = new ArrayList<>();
        Context context;

        public DocAdapter(Context context, List<Doc> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.image_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Doc item = list.get(position);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getName(), holder.image, options);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            uri1 = data.getData();

            Log.d("uri", String.valueOf(uri1));

            String ypath = getPath(OrderDetails4.this, uri1);
            assert ypath != null;
            f1 = new File(ypath);

            Log.d("path", ypath);

            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("name", f1.getName(), reqFile1);


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

            Call<ordersBean> call = cr.uploadDocuments(id, body);

            call.enqueue(new Callback<ordersBean>() {
                @Override
                public void onResponse(Call<ordersBean> call, Response<ordersBean> response) {

                    Toast.makeText(OrderDetails4.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    onResume();

                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ordersBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });

        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("name", f1.getName(), reqFile1);


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

            Call<ordersBean> call = cr.uploadDocuments(id, body);

            call.enqueue(new Callback<ordersBean>() {
                @Override
                public void onResponse(Call<ordersBean> call, Response<ordersBean> response) {

                    Toast.makeText(OrderDetails4.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    onResume();

                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ordersBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });

        }


        if (requestCode == 4 && resultCode == RESULT_OK && null != data) {
            uri1 = data.getData();

            Log.d("uri", String.valueOf(uri1));

            String ypath = getPath(OrderDetails4.this, uri1);
            assert ypath != null;
            f1 = new File(ypath);

            Log.d("path", ypath);

            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("name", f1.getName(), reqFile1);


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

            Call<ordersBean> call = cr.uploadPOD(id, body);

            call.enqueue(new Callback<ordersBean>() {
                @Override
                public void onResponse(Call<ordersBean> call, Response<ordersBean> response) {

                    Toast.makeText(OrderDetails4.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    onResume();

                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ordersBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });

        } else if (requestCode == 3 && resultCode == RESULT_OK) {
            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("name", f1.getName(), reqFile1);


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

            Call<ordersBean> call = cr.uploadPOD(id, body);

            call.enqueue(new Callback<ordersBean>() {
                @Override
                public void onResponse(Call<ordersBean> call, Response<ordersBean> response) {

                    Toast.makeText(OrderDetails4.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    onResume();

                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ordersBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });

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

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);


        startend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (startend.getText().toString().equals("START")) {
                    if (oid.length() > 0) {


                        new AlertDialog.Builder(OrderDetails4.this)
                                .setTitle("Start Booking")
                                .setMessage("Are you sure you want to start this Booking?")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, int which) {

                                        progress.setVisibility(View.VISIBLE);

                                        AppController b = (AppController) getApplicationContext();

                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(b.baseurl)
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                        Call<orderDetailsBean> call = cr.start_order(oid);

                                        call.enqueue(new Callback<orderDetailsBean>() {
                                            @Override
                                            public void onResponse(Call<orderDetailsBean> call, Response<orderDetailsBean> response) {

                                                if (response.body().getStatus().equals("1")) {

                                                    SharePreferenceUtils.getInstance().saveString("order", oid);

                                                    if (!checkPermissions()) {
                                                        Log.d("orderrr", oid);

                                                        requestPermissions();
                                                    } else {
                                                        mService.requestLocationUpdates();
                                                    }

                                                    dialog.dismiss();
                                                    onResume();


                                                } else {
                                                    Toast.makeText(OrderDetails4.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                                progress.setVisibility(View.GONE);
                                            }

                                            @Override
                                            public void onFailure(Call<orderDetailsBean> call, Throwable t) {
                                                progress.setVisibility(View.GONE);
                                            }
                                        });


                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();


                    }
                } else {
                    if (oid.length() > 0) {


                        new AlertDialog.Builder(OrderDetails4.this)
                                .setTitle("Complete Booking")
                                .setMessage("Are you sure you want to complete this Booking?")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, int which) {

                                        progress.setVisibility(View.VISIBLE);

                                        AppController b = (AppController) getApplicationContext();

                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(b.baseurl)
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                        Call<orderDetailsBean> call = cr.complete(oid);

                                        call.enqueue(new Callback<orderDetailsBean>() {
                                            @Override
                                            public void onResponse(Call<orderDetailsBean> call, Response<orderDetailsBean> response) {

                                                if (response.body().getStatus().equals("1")) {
                                                    dialog.dismiss();

                                                    SharePreferenceUtils.getInstance().saveString("order", "");
                                                    mService.removeLocationUpdates();

                                                    finish();


                                                } else {
                                                    Toast.makeText(OrderDetails4.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                                progress.setVisibility(View.GONE);
                                            }

                                            @Override
                                            public void onFailure(Call<orderDetailsBean> call, Throwable t) {
                                                progress.setVisibility(View.GONE);
                                            }
                                        });


                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();


                    }
                }


            }
        });


        /*startend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (startend.getText().toString().equals("START"))
                {

                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<deliveryDetailsBean> call = cr.changeDeliveryStatus(del_id , "out for delivery");

                    call.enqueue(new Callback<deliveryDetailsBean>() {
                        @Override
                        public void onResponse(Call<deliveryDetailsBean> call, Response<deliveryDetailsBean> response) {

                            if (response.body().getStatus().equals("1"))
                            {

                                SharePreferenceUtils.getInstance().saveString("order" , order);

                                if (!checkPermissions()) {
                                    Log.d("orderrr" , order);

                                    requestPermissions();
                                } else {
                                    mService.requestLocationUpdates();
                                }
                            }
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<deliveryDetailsBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });


                }
                else
                {

                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<deliveryDetailsBean> call = cr.changeDeliveryStatus(del_id , "delivered");

                    call.enqueue(new Callback<deliveryDetailsBean>() {
                        @Override
                        public void onResponse(Call<deliveryDetailsBean> call, Response<deliveryDetailsBean> response) {

                            if (response.body().getStatus().equals("1"))
                            {
                                SharePreferenceUtils.getInstance().saveString("order" , "");
                                mService.removeLocationUpdates();
                                finish();
                            }
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<deliveryDetailsBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });


                }

            }
        });*/

        // Restore the state of the buttons when the activity (re)launches.
        setButtonsState(Utils.requestingLocationUpdates(this));

        // Bind to the service. If the service is in foreground mode, this signals to the service
        // that since this activity is in the foreground, the service can exit foreground mode.
        bindService(new Intent(this, LocationUpdatesService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }

    /**
     * Returns the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            Snackbar.make(
                    findViewById(R.id.activity_main),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(OrderDetails4.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(OrderDetails4.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                mService.requestLocationUpdates();
            } else {
                // Permission denied.
                setButtonsState(false);
                Snackbar.make(
                        findViewById(R.id.activity_main),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * Receiver for broadcasts sent by {@link LocationUpdatesService}.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
            if (location != null) {
                /*Toast.makeText(MainActivity.this, Utils.getLocationText(location),
                        Toast.LENGTH_SHORT).show();*/
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // Update the buttons state depending on whether location updates are being requested.
        if (s.equals(Utils.KEY_REQUESTING_LOCATION_UPDATES)) {
            setButtonsState(sharedPreferences.getBoolean(Utils.KEY_REQUESTING_LOCATION_UPDATES,
                    false));
        }
    }

    private void setButtonsState(boolean requestingLocationUpdates) {
        if (requestingLocationUpdates) {
            startend.setText("FINISH");
        } else {
            startend.setText("START");
        }
    }

}