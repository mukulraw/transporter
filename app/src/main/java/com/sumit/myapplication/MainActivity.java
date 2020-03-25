package com.sumit.myapplication;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.sumit.myapplication.webview.AboutOnwayFragment;
import com.sumit.myapplication.webview.ContactUsFragment;
import com.sumit.myapplication.webview.FAQFragment;
import com.sumit.myapplication.webview.PaymentTermsFragment;
import com.sumit.myapplication.webview.PrivacyPolicyfragment;
import com.sumit.myapplication.webview.TermsAndConditionsFragment;
import com.sumit.myapplication.otp.SharedData;
import com.sumit.myapplication.truck.PostTruckFrag;
import com.sumit.myapplication.splash.SplashActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedData sharedData;

    private BottomNavigationView myBottomNav;
    private FrameLayout myFrameLayout;

    private MyBidFragment myBidFragment;
    private PostTruckFragment postTruckFragment;
    private PostedTruckFragment postedTruckFragment;
    private MyOrderFragment myOrderFragment;
    private PostTruckFrag postTruckFrag;

    private TextView profileName, profileMobile;

    private LinearLayout profileLl;
    public static String currenntMobileActive, currentUserName;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Onnway");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);


        //NavBar
        profileName = (TextView) view.findViewById(R.id.user_profile_name);
        profileMobile = (TextView) view.findViewById(R.id.user_profile_phone);


        //Bottom navigationn bar
        myBottomNav = (BottomNavigationView) findViewById(R.id.bottom_nav);
        myFrameLayout = (FrameLayout) findViewById(R.id.main_frame);

        //constructor for fragments of bottom navigation bar
        myBidFragment = new MyBidFragment();
        postTruckFragment = new PostTruckFragment();
        postedTruckFragment = new PostedTruckFragment();
        myOrderFragment = new MyOrderFragment();
        postTruckFrag=new PostTruckFrag();
        //set the default fragment of bottom navigation bar to be myBidFragment
        setFragment(myBidFragment);

        //changing the fragments on clicking the icon of the bottom navigation bar
        myBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.my_bids :
                        //sets the color of bottom navigation bar to coloPrimary on clicking the mybid icon
                        //myBottomNav.setItemBackgroundResource(R.color.colorPrimary);
//                        hideSoftKeyboard(MainActivity.this);
                        setFragment(myBidFragment);
                        return true;
                    case R.id.post_truck :
//                        hideSoftKeyboard(MainActivity.this);
                        //setFragment(postTruckFragment);
                        setFragment(postTruckFrag);
                        return true;
                    case R.id.posted_truck :
//                        hideSoftKeyboard(MainActivity.this);
                        setFragment(postedTruckFragment);
                        return true;
                    case R.id.my_orders :
//                        hideSoftKeyboard(MainActivity.this);
                        setFragment(myOrderFragment);
                        return true;
                    default :
                        return false;
                }

            }
        });


        //Profile Activity Button
        profileLl = (LinearLayout) view.findViewById(R.id.profile_ll);


        profileMobile.setText("+91 " + currenntMobileActive);
        profileName.setText(currentUserName);

        //handling the profile activity
        profileLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        

    }

    //method to set the fragment layout for the selected icon
    private void setFragment(Fragment fragment) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.file_paths.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //to be changed
            // Handle the camera action
            setFragment(myBidFragment);
        } else if (id == R.id.nav_about) {
            setFragment(new AboutOnwayFragment());

        } else if (id == R.id.nav_faq) {
            setFragment(new FAQFragment());

        } else if (id == R.id.nav_contact) {
            setFragment(new ContactUsFragment());

        } else if (id == R.id.nav_terms_and_condition) {
            setFragment(new TermsAndConditionsFragment());

        } else if (id == R.id.nav_payment_terms) {
            setFragment(new PaymentTermsFragment());

        } else if (id == R.id.nav_privacy_policy) {
            setFragment(new PrivacyPolicyfragment());

        } else if (id == R.id.nav_share) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }


        } else if (id == R.id.nav_logout) {
//            SelectUserType.userType = -1;
            //Clearing shared prefrences
//            SaveSharedPreference.setLoggedIn(getApplicationContext(), false);
//            SaveSharedPreference.setPhoneNo(getApplicationContext(), "Not found no");
//            SaveSharedPreference.setCounterBid(this, "1");
//            SaveSharedPreference.setCounterPosted(this, "1");
            SplashActivity.currentUserType = "";
            deleteData();
            startActivity(new Intent(this, SplashActivity.class));
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            tempFile = new File(tempFile.getPath() + "/" + getString(app.labelRes).replace(" ","").toLowerCase() + ".apk");
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
        if(cursor.getCount() == 0) {
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()) {
//            buffer.append("Name" + cursor.getString(0));
            currenntMobileActive = cursor.getString(1);
        }
        Toast.makeText(MainActivity.this, currenntMobileActive, Toast.LENGTH_LONG).show();
    }

    public void findName() {
        Cursor cursor = sharedData.getAllData();
        if(cursor.getCount() == 0) {
            return;
        }

//        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()) {
//            buffer.append("Name" + cursor.getString(0));
            currentUserName = cursor.getString(0);
        }

        Toast.makeText(MainActivity.this, currentUserName, Toast.LENGTH_LONG).show();
    }
    public void deleteData() {
        Integer deletedRow = sharedData.deleteData(currenntMobileActive);
        if(deletedRow > 0){
            //deleted
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
        }
    }

}