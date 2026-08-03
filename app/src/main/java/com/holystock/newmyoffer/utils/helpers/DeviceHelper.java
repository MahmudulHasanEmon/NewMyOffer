package com.holystock.newmyoffer.utils.helpers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

public final class DeviceHelper {

    private DeviceHelper() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Generates a User-Agent string identical to the Flutter implementation.
     * Output format: AppName/Version (Android OS_VERSION; MODEL)
     */
    public static String getUserAgent(Context context) {
        String appName = "App";
        String appVersion = "1.0.0";

        try {
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);

            // Get App Name
            appName = packageManager.getApplicationLabel(context.getApplicationInfo()).toString();

            // Get App Version Name
            if (packageInfo.versionName != null) {
                appVersion = packageInfo.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // Get Android OS Version and Model
        String osVersion = Build.VERSION.RELEASE; // e.g., "13" or "14"
        String model = Build.MODEL;               // e.g., "Pixel 6" or "SM-G998B"

        String deviceDetails = "Android " + osVersion + "; " + model;

        return appName + "/" + appVersion + " (" + deviceDetails + ")";
    }



}
