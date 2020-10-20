package com.mukul.onnwaytransporter.driver;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.hsalf.smileyrating.SmileyRating;
import com.mukul.onnwaytransporter.AllApiIneterface;
import com.mukul.onnwaytransporter.FindTruckFragment;
import com.mukul.onnwaytransporter.FindTruckFragment2;
import com.mukul.onnwaytransporter.MainActivity;
import com.mukul.onnwaytransporter.MyOrderFragment;
import com.mukul.onnwaytransporter.MyOrderFragment2;
import com.mukul.onnwaytransporter.PostedTruckFragment;
import com.mukul.onnwaytransporter.ProfileActivity;
import com.mukul.onnwaytransporter.SharePreferenceUtils;
import com.mukul.onnwaytransporter.Web;
import com.mukul.onnwaytransporter.confirm_full_POJO.confirm_full_bean;
import com.mukul.onnwaytransporter.driver.profilerelated.DriverProfileActivity;
import com.mukul.onnwaytransporter.BuildConfig;
import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.networking.AppController;
import com.mukul.onnwaytransporter.updateProfilePOJO.updateProfileBean;
import com.mukul.onnwaytransporter.webview.AboutOnwayFragment;
import com.mukul.onnwaytransporter.webview.ContactUsFragment;
import com.mukul.onnwaytransporter.webview.FAQFragment;
import com.mukul.onnwaytransporter.webview.PaymentTermsFragment;
import com.mukul.onnwaytransporter.webview.PrivacyPolicyfragment;
import com.mukul.onnwaytransporter.webview.TermsAndConditionsFragment;
import com.mukul.onnwaytransporter.otp.SharedData;
import com.mukul.onnwaytransporter.splash.SplashActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DriverMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedData sharedData;
    private BottomNavigationView driverBottomNav;

    FrameLayout driverFrameLayout;

    private LoadRequestDriverFragment loadRequestDriverFragment;
    private MyOrderDriverFragment myOrderDriverFragment;
    private PostedTruckDriverFragment postedTruckDriverFragment;

    private PostedTruckFragment postedTruckFragment;
    private MyOrderFragment2 myOrderFragment;
    private FindTruckFragment2 postTruckFrag;

    private TextView name, phone;

    private LinearLayout profileLl;

    public static String currenntMobileActive;
    public static String currentUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        sharedData = new SharedData(DriverMainActivity.this);
        findMobile();
        findName();

        //setting the color of STATUS BAR of DriverMainActivity activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
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


        name = view.findViewById(R.id.user_profile_name);
        phone = view.findViewById(R.id.user_profile_phone);

        //bottom navigation bar
        driverBottomNav = (BottomNavigationView) findViewById(R.id.bottom_nav_driver);
        driverFrameLayout = (FrameLayout) findViewById(R.id.main_frame_driver);

        //nav header


        //constructor for the fragments
        loadRequestDriverFragment = new LoadRequestDriverFragment();
        myOrderDriverFragment = new MyOrderDriverFragment();
        postedTruckDriverFragment = new PostedTruckDriverFragment();


        postedTruckFragment = new PostedTruckFragment();
        myOrderFragment = new MyOrderFragment2();
        postTruckFrag = new FindTruckFragment2();

        //set the default fragment of bottom navigation bar to be myBidFragment
        setFragment(postTruckFrag);

        //changing the fragments on clicking the icon of the bottom navigation bar
        driverBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.load_request_driver:
                        //sets the color of bottom navigation bar to coloPrimary on clicking the mybid icon
                        //myBottomNav.setItemBackgroundResource(R.color.colorPrimary);
//                        hideSoftKeyboard(DriverMainActivity.this);
                        setFragment(postTruckFrag);
                        return true;
                    case R.id.posted_truck_driver:
//                        hideSoftKeyboard(DriverMainActivity.this);
                        setFragment(postedTruckFragment);
                        return true;
                    case R.id.my_order_driver:
//                        hideSoftKeyboard(DriverMainActivity.this);
                        setFragment(myOrderFragment);
                        return true;
                    default:
                        return false;
                }

            }
        });

        profileLl = (LinearLayout) view.findViewById(R.id.profile_ll);

        //handling the profile activity
        profileLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(DriverMainActivity.this, DriverProfileActivity.class);
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

        Log.d("name", SharePreferenceUtils.getInstance().getString("userId"));
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

        Log.d("asdsd", SharePreferenceUtils.getInstance().getString("phone"));
        Call<confirm_full_bean> call2 = cr.checkDriverRating(
                SharePreferenceUtils.getInstance().getString("phone")
        );

        call2.enqueue(new Callback<confirm_full_bean>() {
            @Override
            public void onResponse(Call<confirm_full_bean> call, final Response<confirm_full_bean> response) {

                if (response.body().getStatus().equals("1")) {
                    final Dialog dialog = new Dialog(DriverMainActivity.this, R.style.MyDialogTheme);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.rating_dialog);
                    dialog.show();

                    TextView title = dialog.findViewById(R.id.textView143);
                    final SmileyRating rating = dialog.findViewById(R.id.textView142);
                    Button submit = dialog.findViewById(R.id.button18);
                    title.setText("Please rate Order #" + response.body().getMessage());

                    rating.setRating(5);

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SmileyRating.Type smiley = rating.getSelectedSmiley();

                            // You can get the user rating too
                            // rating will between 1 to 5, but -1 is none selected
                            int rating2 = smiley.getRating();

                            Call<confirm_full_bean> call2 = cr.submitDriverRating(
                                    response.body().getMessage(),
                                    String.valueOf(rating2)
                            );

                            call2.enqueue(new Callback<confirm_full_bean>() {
                                @Override
                                public void onResponse(Call<confirm_full_bean> call, Response<confirm_full_bean> response) {

                                    if (response.body().getStatus().equals("1")) {
                                        dialog.dismiss();
                                    }

                                    Toast.makeText(DriverMainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                                }

                                @Override
                                public void onFailure(Call<confirm_full_bean> call, Throwable t) {

                                }
                            });

                        }
                    });


                }


            }

            @Override
            public void onFailure(Call<confirm_full_bean> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
            Intent intent = new Intent(DriverMainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(DriverMainActivity.this, Web.class);
            intent.putExtra("title", "About Onnway");
            intent.putExtra("url", "https://www.onnway.com/aboutonway.php");
            startActivity(intent);
        } else if (id == R.id.nav_faq) {
            Intent intent = new Intent(DriverMainActivity.this, Web.class);
            intent.putExtra("title", "FAQs");
            intent.putExtra("url", "https://www.onnway.com/faqonnway.php");
            startActivity(intent);
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(DriverMainActivity.this, Web.class);
            intent.putExtra("title", "Contact Us");
            intent.putExtra("url", "https://www.onnway.com/contactonnway.php");
            startActivity(intent);
        } else if (id == R.id.nav_terms_and_condition) {
            Intent intent = new Intent(DriverMainActivity.this, Web.class);
            intent.putExtra("title", "Terms and Conditions");
            intent.putExtra("url", "https://www.onnway.com/termsonnway.php");
            startActivity(intent);
        } else if (id == R.id.nav_payment_terms) {
            Intent intent = new Intent(DriverMainActivity.this, Web.class);
            intent.putExtra("title", "Payment Terms");
            intent.putExtra("url", "https://www.onnway.com/paymentonnway.php");
            startActivity(intent);
        } else if (id == R.id.nav_privacy_policy) {
            Intent intent = new Intent(DriverMainActivity.this, Web.class);
            intent.putExtra("title", "Privacy Policy");
            intent.putExtra("url", "https://www.onnway.com/privacyonnway.php");
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
            startActivity(new Intent(this, SplashActivity.class));
            finishAffinity();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    //method to set the fragment layout for the selected icon
    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_driver, fragment);
        fragmentTransaction.commit();
    }

    //method to hind Keyboard if active
    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
        );
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
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

        Toast.makeText(DriverMainActivity.this, currentUserName, Toast.LENGTH_LONG).show();
    }

    public void deleteData() {
        Integer deletedRow = sharedData.deleteData(currenntMobileActive);
        if (deletedRow > 0) {
            //deleted
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
        }
    }

}
