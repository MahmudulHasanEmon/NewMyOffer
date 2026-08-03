package com.holystock.newmyoffer.utils.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

public final class DeviceUtils {

    private DeviceUtils() {
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


    /**
     * Gets the unique Android Device ID.
     *
     * @param context Application or Activity context
     * @return 64-bit unique ID string, or "unknown" if unavailable
     */
    public static String getDeviceId(Context context) {
        if (context == null) return "unknown";

        try {
            @SuppressLint("HardwareIds")
            String androidId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID
            );

            return (androidId != null) ? androidId : "unknown";
        } catch (Exception e) {
            e.printStackTrace();
            return "unknown";
        }
    }

}
