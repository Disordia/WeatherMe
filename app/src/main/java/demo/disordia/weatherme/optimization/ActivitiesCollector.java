package demo.disordia.weatherme.optimization;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Disordia profaneden on 2015-03-20.
 * 此类用于存放所有activity的实例，便于关闭与管理
 */
public class ActivitiesCollector {
    public static List<Activity> activities=new ArrayList<Activity>();

    /**
     * @param activity
     */
    //在onCreate()方法中使用:
    public static void addActivity(Activity activity){
        //防止重复添加:
       if(!activities.contains(activity)) {
           activities.add(activity);
       }
    }

    /**
     * @param activity
     */
    //在onDestroy()方法中使用:
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    //停止所有的活动:
    public static void finishAll()
    {
        for (Activity activity:activities){
            if (!activity.isFinishing()) //判断活动是否终止
            {
                activity.finish();
            }
        }
    }

}
