package demo.disordia.weatherme.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import demo.disordia.weatherme.R;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-21.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.list
 * Descibe:
 */
 public class AreaAdapter extends ArrayAdapter<String> {
    /**
     * 存储需要显示的列表:
     */
    private int resourceId;

    public AreaAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //这里需要优化:
        String item=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView areaName= (TextView) view.findViewById(R.id.area_name);
        areaName.setText("  "+item);
        return view;
    }
}
