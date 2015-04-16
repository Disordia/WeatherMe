package demo.disordia.weatherme.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

import demo.disordia.weatherme.optimization.GlobalApplication;

/**
 * Created by Disordia profaneden on 2015-04-12.
 */
public class HomeJudger {
    private static List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = GlobalApplication.getContext().getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }
    public static boolean isHome() {
        ActivityManager mActivityManager = (ActivityManager) GlobalApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return getHomes().contains(rti.get(0).topActivity.getPackageName());
    }
}
