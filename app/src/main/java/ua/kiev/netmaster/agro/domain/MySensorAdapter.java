package ua.kiev.netmaster.agro.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ua.kiev.netmaster.agro.R;

/**
 * Created by ПК on 27.11.2015.
 */
public class MySensorAdapter extends BaseAdapter {


    private Context ctx;
    private LayoutInflater lInflater;
    private List<Sensor> objects;



    public MySensorAdapter(Context context, List<Sensor> zones) {
        ctx = context;
        objects = zones;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = lInflater.inflate(R.layout.item, viewGroup, false);
        }

        Sensor sensor = getSensor(position);

        //((TextView) view.findViewById(R.id.zoneId)).setText(sensor.getId());
        ((TextView) view.findViewById(R.id.zoneName)).setText(sensor.getType()+" = "+sensor.getValue());


        return view;
    }

    private Sensor getSensor(int position) {
        return ((Sensor) getItem(position));
    }
}
