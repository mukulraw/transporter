package com.app.onnwaytransporter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.app.onnwaytransporter.networking.AppController;
import com.app.onnwaytransporter.orderDetailsPOJO.Data;
import com.app.onnwaytransporter.orderDetailsPOJO.Doc;
import com.app.onnwaytransporter.orderDetailsPOJO.Pod;
import com.app.onnwaytransporter.orderDetailsPOJO.orderDetailsBean;
import com.app.onnwaytransporter.ordersPOJO.ordersBean;
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

public class OrderDetails3 extends AppCompatActivity {

    TextView orderid, orderdate, truck, source, destination, material, weight, date, status, fare, distance, paid, balance;

    TextView vehiclenumber, drivernumber;

    Button add, upload1, upload2;

    RecyclerView pod, documents;

    ProgressBar progress;

    String id;

    private Uri uri1;
    private File f1;

    String sourceLAT = "", sourceLNG = "", destinationLAT = "", destinationLNG = "";

    TextView dimension, equal, quantity, total, phototitle;
    ImageView photo;
    TextView laodernote;

    private CardView partLoad1, partLoad2, partLoad3, partLoad4, partLoad5, partLoad6, partLoad7, partLoad8;
    private double click1 = 0, click2 = 0, click3 = 0, click4 = 0, click5 = 0, click6 = 0, click7 = 0, click8 = 0;
    private TextView truckTypeDetails, truckCapacity, boxLength, boxWidth, boxArea;
    private TextView selectedArea, remainingArea;
    String trucktitle, srcAddress, destAddress, pickUpDate, mid, loadType;
    String length, width, height, desc, tid, passing;
    float capcaity, len, wid;
    List<String> selected;
    ImageView truckType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details3);

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

        truckType = findViewById(R.id.imageView5);
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

        dimension = findViewById(R.id.textView134);
        laodernote = findViewById(R.id.textView46);
        phototitle = findViewById(R.id.textView140);
        equal = findViewById(R.id.textView135);
        quantity = findViewById(R.id.textView137);
        total = findViewById(R.id.textView139);
        photo = findViewById(R.id.imageView18);
        orderid = findViewById(R.id.textView16);
        balance = findViewById(R.id.textView44);
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

        vehiclenumber = findViewById(R.id.textView291);
        drivernumber = findViewById(R.id.textView35);

        add = findViewById(R.id.button3);
        upload1 = findViewById(R.id.button5);
        upload2 = findViewById(R.id.button6);

        pod = findViewById(R.id.pod);
        documents = findViewById(R.id.recyclerView2);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(OrderDetails3.this, R.style.MyDialogTheme);
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
                                            Toast.makeText(OrderDetails3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                        bar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onFailure(Call<ordersBean> call, Throwable t) {
                                        bar.setVisibility(View.GONE);
                                    }
                                });

                            } else {
                                Toast.makeText(OrderDetails3.this, "Invalid Driver Number", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(OrderDetails3.this, "Invalid Vehicle Number", Toast.LENGTH_SHORT).show();
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
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OrderDetails3.this, R.style.MyDialogTheme);
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

                            uri1 = FileProvider.getUriForFile(Objects.requireNonNull(OrderDetails3.this), BuildConfig.APPLICATION_ID + ".provider", f1);

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
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OrderDetails3.this, R.style.MyDialogTheme);
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

                            uri1 = FileProvider.getUriForFile(Objects.requireNonNull(OrderDetails3.this), BuildConfig.APPLICATION_ID + ".provider", f1);

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

    }

    @Override
    protected void onResume() {
        super.onResume();

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
                truck.setText(item.getTruckType());
                source.setText(item.getPickupAddress() + ", " + item.getPickupCity() + ", " + item.getPickupPincode() + ", " + item.getSource());
                destination.setText(item.getDropAddress() + ", " + item.getDropCity() + ", " + item.getDropPincode() + ", " + item.getDestination());
                //destination.setText(item.getDestination());
                material.setText(item.getMaterial());
                weight.setText(item.getWeight());
                date.setText(item.getSchedule());
                status.setText(item.getStatus());
                fare.setText("₹ " + item.getFare());
                paid.setText("₹ " + item.getPaid());
                laodernote.setText(item.getRemarks());

                try {
                    if (item.getTruckType2().equals("open truck")) {
                        truckType.setImageDrawable(getDrawable(R.drawable.open));
                    } else if (item.getTruckType2().equals("trailer")) {
                        truckType.setImageDrawable(getDrawable(R.drawable.trailer));
                    } else {
                        truckType.setImageDrawable(getDrawable(R.drawable.container));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


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

                dimension.setText(item.getLength() + " X " + item.getWidth() + " X " + item.getHeight());
                if (item.getMaterial_image().length() > 0) {
                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                    ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(item.getMaterial_image(), photo, options);

                    photo.setVisibility(View.VISIBLE);
                    phototitle.setVisibility(View.VISIBLE);
                } else {
                    photo.setVisibility(View.GONE);
                    phototitle.setVisibility(View.GONE);
                }


                float ll = Float.parseFloat(item.getLength());
                float ww = Float.parseFloat(item.getWidth());
                float hh = Float.parseFloat(item.getHeight());
                float qq = Float.parseFloat(item.getQuantity());

                equal.setText("= " + (ll * ww * hh) + " cu.ft.");
                quantity.setText(item.getQuantity());
                total.setText((ll * ww * hh * qq) + " cu.ft.");

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

                float tot = Float.parseFloat(item.getFare());
                float pa = 0;
                if (item.getPaid().length() > 0) {
                    pa = Float.parseFloat(item.getPaid());
                } else {
                    pa = 0;
                }

                balance.setText("₹ " + (tot - pa));

                distance.setText((startPoint.distanceTo(endPoint) / 1000) + " km");

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


                PODAdapter adapter = new PODAdapter(OrderDetails3.this, item.getPod());
                GridLayoutManager manager = new GridLayoutManager(OrderDetails3.this, 2);
                pod.setAdapter(adapter);
                pod.setLayoutManager(manager);

                DocAdapter adapter2 = new DocAdapter(OrderDetails3.this, item.getDoc());
                GridLayoutManager manager2 = new GridLayoutManager(OrderDetails3.this, 2);
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

            String ypath = getPath(OrderDetails3.this, uri1);
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

                    Toast.makeText(OrderDetails3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(OrderDetails3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

            String ypath = getPath(OrderDetails3.this, uri1);
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

                    Toast.makeText(OrderDetails3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(OrderDetails3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

}