package com.sumit.myapplication.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.sumit.myapplication.preferences.PreferencesUtility.COUNTER_LOADING_BID;
import static com.sumit.myapplication.preferences.PreferencesUtility.COUNTER_LOADING_PAST;
import static com.sumit.myapplication.preferences.PreferencesUtility.COUNTER_LOADING_POSTED_TRUCK;
import static com.sumit.myapplication.preferences.PreferencesUtility.COUNTER_LOADING_UPCOMING;
import static com.sumit.myapplication.preferences.PreferencesUtility.LOGGED_IN_PREF;
import static com.sumit.myapplication.preferences.PreferencesUtility.PHONE_NUMBER;

public class SaveSharedPreference {

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    public static  void setPhoneNo(Context context,String phoneNumber)
    {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(PHONE_NUMBER,phoneNumber);
        editor.apply();
    }

    public static void setCounterBid(Context context, String counter) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(COUNTER_LOADING_BID, counter);
        editor.apply();
    }

    public static void setCounterPosted(Context context, String counter) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(COUNTER_LOADING_POSTED_TRUCK, counter);
        editor.apply();
    }
    public static void setCounterUpcoming(Context context, String counter) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(COUNTER_LOADING_UPCOMING, counter);
        editor.apply();
    }
    public static void setCounterPast(Context context, String counter) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(COUNTER_LOADING_PAST, counter);
        editor.apply();
    }

    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }

    public static String getPhoneNoStatus(Context context)
    {
        return getPreferences(context).getString(PHONE_NUMBER, "Not found no");
    }

    public static String getCounterBidStatus(Context context)
    {
        return getPreferences(context).getString(COUNTER_LOADING_BID, "0");
    }

    public static String getCounterPostedStatus(Context context)
    {
        return getPreferences(context).getString(COUNTER_LOADING_POSTED_TRUCK, "0");
    }

    public static String getCounterUpcomingStatus(Context context)
    {
        return getPreferences(context).getString(COUNTER_LOADING_UPCOMING, "0");
    }

    public static String getCounterPastStatus(Context context) {
        return getPreferences(context).getString(COUNTER_LOADING_PAST, "0");
    }
}