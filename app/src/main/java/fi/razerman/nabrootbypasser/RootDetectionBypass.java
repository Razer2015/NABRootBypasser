package fi.razerman.nabrootbypasser;

/**
 * Created by Razerman on 1.11.2016.
 */


import android.util.Log;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import static de.robv.android.xposed.XC_MethodReplacement.returnConstant;

public class RootDetectionBypass implements IXposedHookLoadPackage {
    private static final String TAG = RootDetectionBypass.class.getSimpleName();

    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("au.com.nab.mobile")){
            Log.d(TAG, "app detected, starting to bypass root detection!");

            // Check for test-keys
            // Check for the following files
            /* /system/app/Superuser.apk
            * /sbin/su
            * /system/bin/su
            * /system/xbin/su
            * /data/local/xbin/su
            * /data/local/bin/su
            * /system/sd/xbin/su
            * /system/bin/failsafe/su
            * /data/local/su */
            // Check if can execute su
            findAndHookMethod("au.com.nab.coreSdk.device.DeviceRootUtils", lpparam.classLoader, "isRooted", returnConstant(false));                     // Check 1
            findAndHookMethod("au.com.nab.coreSdk.device.DeviceRootUtils", lpparam.classLoader, "numberOfRootChecksTriggered", returnConstant(0));      // Check 1

            // Check for the following files
            /*
            * /sbin/su
            * /system/bin/su
            * /system/xbin/su
            * /data/local/xbin/su
            * /data/local/bin/su
            * /system/sd/xbin/su
            * /system/bin/failsafe/su
            * /data/local/su */
            findAndHookMethod("com.ca.a.au", lpparam.classLoader, "c", returnConstant(false));                                          // Check 2

            Log.d(TAG, "Bypassed " + TAG + "'s root detection!");
        }
    }
}
