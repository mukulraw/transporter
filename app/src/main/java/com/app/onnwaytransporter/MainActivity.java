package com.app.onnwaytransporter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.google.firebase.iid.FirebaseInstanceId;
import com.app.onnwaytransporter.networking.AppController;
import com.app.onnwaytransporter.updateProfilePOJO.updateProfileBean;
import com.app.onnwaytransporter.otp.SharedData;
import com.app.onnwaytransporter.splash.SplashActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedData sharedData;

    private BottomNavigationView myBottomNav;
    private FrameLayout myFrameLayout;

    private NewBidsFragment myBidFragment;
    private PostTruckFragment postTruckFragment;
    private PostedTruckFragment postedTruckFragment;
    private MyOrderFragment myOrderFragment;
    private FindTruckFragment postTruckFrag;

    private TextView name, phone;

    private LinearLayout profileLl;
    public static String currenntMobileActive, currentUserName;

    ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());


        sharedData = new SharedData(MainActivity.this);
        findMobile();
        findName();

        //setting the color of STATUS BAR of MainActivity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);

        home = findViewById(R.id.home);
        //NavBar
        name = view.findViewById(R.id.user_profile_name);
        phone = view.findViewById(R.id.user_profile_phone);


        //Bottom navigationn bar
        myBottomNav = findViewById(R.id.bottom_nav);
        myFrameLayout = findViewById(R.id.main_frame);

        //constructor for fragments of bottom navigation bar
        myBidFragment = new NewBidsFragment();
        postTruckFragment = new PostTruckFragment();
        postedTruckFragment = new PostedTruckFragment();
        myOrderFragment = new MyOrderFragment();
        postTruckFrag = new FindTruckFragment();
        //set the default fragment of bottom navigation bar to be myBidFragment
        setFragment(myBidFragment);

        //changing the fragments on clicking the icon of the bottom navigation bar
        myBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.my_bids:
                        //sets the color of bottom navigation bar to coloPrimary on clicking the mybid icon
                        //myBottomNav.setItemBackgroundResource(R.color.colorPrimary);
//                        hideSoftKeyboard(MainActivity.this);
                        setFragment(myBidFragment);
                        return true;
                    case R.id.post_truck:
//                        hideSoftKeyboard(MainActivity.this);
                        //setFragment(postTruckFragment);
                        setFragment(postTruckFrag);
                        return true;
                    case R.id.posted_truck:
//                        hideSoftKeyboard(MainActivity.this);
                        setFragment(postedTruckFragment);
                        return true;
                    case R.id.my_orders:
//                        hideSoftKeyboard(MainActivity.this);
                        setFragment(myOrderFragment);
                        return true;
                    default:
                        return false;
                }

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myBottomNav.setSelectedItemId(R.id.my_bids);

            }
        });


        //Profile Activity Button
        profileLl = (LinearLayout) view.findViewById(R.id.profile_ll);

        //handling the profile activity
        profileLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                //startActivity(intent);
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();


        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);



        //Log.d("name", SharePreferenceUtils.getInstance().getString("userId"));
        Call<updateProfileBean> call = cr.getNewName(
                SharePreferenceUtils.getInstance().getString("userId")
        );

        call.enqueue(new Callback<updateProfileBean>() {
            @Override
            public void onResponse(Call<updateProfileBean> call, Response<updateProfileBean> response) {

                Log.d("name", response.body().getMessage());
                SharePreferenceUtils.getInstance().saveString("name", response.body().getMessage());
                phone.setText("Ph. - " + SharePreferenceUtils.getInstance().getString("phone"));
                name.setText(SharePreferenceUtils.getInstance().getString("name"));
            }

            @Override
            public void onFailure(Call<updateProfileBean> call, Throwable t) {
                t.printStackTrace();
            }
        });




    }

    //method to set the fragment layout for the selected icon
    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this, Web.class);
            intent.putExtra("title", "About Onnway");
            intent.putExtra("url", "https://onnway.com/about-us.php");
            startActivity(intent);
        } else if (id == R.id.nav_faq) {
            Intent intent = new Intent(MainActivity.this, Web.class);
            intent.putExtra("title", "FAQs");
            intent.putExtra("url", "https://onnway.com/faq.php");
            startActivity(intent);
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(MainActivity.this, Web.class);
            intent.putExtra("title", "Contact Us");
            intent.putExtra("url", "https://onnway.com/contact-us.php");
            startActivity(intent);
        } else if (id == R.id.nav_terms_and_condition) {
            Intent intent = new Intent(MainActivity.this, Web.class);
            intent.putExtra("title", "Terms and Conditions");
            intent.putExtra("url", "https://onnway.com/terms-n-condition.php");
            startActivity(intent);
        } else if (id == R.id.nav_payment_terms) {
            Intent intent = new Intent(MainActivity.this, Web.class);
            intent.putExtra("title", "Payment Terms");
            intent.putExtra("url", "https://onnway.com/payment_terms.php");
            startActivity(intent);
        } else if (id == R.id.nav_privacy_policy) {
            Intent intent = new Intent(MainActivity.this, Web.class);
            intent.putExtra("title", "Privacy Policy");
            intent.putExtra("url", "https://onnway.com/privacy_policy.php");
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        } else if (id == R.id.nav_logout) {


            new AlertDialog.Builder(MainActivity.this, R.style.MyDialogTheme)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to Logout?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, int which) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        FirebaseInstanceId.getInstance().deleteInstanceId();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                            SharePreferenceUtils.getInstance().deletePref();
                            Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                            startActivity(intent);
                            finishAffinity();

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

            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        FirebaseInstanceId.getInstance().deleteInstanceId();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();*/


            /*SharePreferenceUtils.getInstance().deletePref();
            startActivity(new Intent(this, SplashActivity.class));
            finishAffinity();*/

        } else if (id == R.id.feedback) {
            Intent intent = new Intent(MainActivity.this, Feedback.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    //method to hind Keyboard if active
//    private void hideSoftKeyboard(Activity activity) {
//        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
//                Activity.INPUT_METHOD_SERVICE
//        );
//        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0);
//    }


    //share the app
    private void shareApplication() {
        ApplicationInfo app = getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;

        Intent intent = new Intent(Intent.ACTION_SEND);

        // MIME of .apk is "application/vnd.android.package-archive".
        // but Bluetooth does not accept this. Let's use "*/*" instead.
        intent.setType("*/*");

        // Append file and send Intent
        File originalApk = new File(filePath);

        try {
            //Make new directory in new location
            File tempFile = new File(getExternalCacheDir() + "/ExtractedApk");
            //If directory doesn't exists create new
            if (!tempFile.isDirectory())
                if (!tempFile.mkdirs())
                    return;
            //Get application's name and convert to lowercase
            tempFile = new File(tempFile.getPath() + "/" + getString(app.labelRes).replace(" ", "").toLowerCase() + ".apk");
            //If file doesn't exists create new
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return;
                }
            }
            //Copy file to new location
            InputStream in = new FileInputStream(originalApk);
            OutputStream out = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
            //Open share dialog
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            startActivity(Intent.createChooser(intent, "Share app via"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findMobile() {
        Cursor cursor = sharedData.getAllData();
        if (cursor.getCount() == 0) {
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
//            buffer.append("Name" + cursor.getString(0));
            currenntMobileActive = cursor.getString(1);
        }
        //Toast.makeText(MainActivity.this, currenntMobileActive, Toast.LENGTH_LONG).show();
    }

    public void findName() {
        Cursor cursor = sharedData.getAllData();
        if (cursor.getCount() == 0) {
            return;
        }

//        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
//            buffer.append("Name" + cursor.getString(0));
            currentUserName = cursor.getString(0);
        }

        //Toast.makeText(MainActivity.this, currentUserName, Toast.LENGTH_LONG).show();
    }

    public void deleteData() {
        Integer deletedRow = sharedData.deleteData(currenntMobileActive);
        if (deletedRow > 0) {
            //deleted
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
        }
    }

}