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
 * Created by ПК on 12.08.2015.
 */
public class MyZoneAdapter extends BaseAdapter {


    private Context ctx;
    private LayoutInflater lInflater;
    private List<Zone> objects;



    public MyZoneAdapter(Context context, List<Zone> zones) {
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

        Zone zone = getOrder(position);

        //((TextView) view.findViewById(R.id.zoneId)).setText(zone.getId());
        ((TextView) view.findViewById(R.id.zoneName)).setText(zone.getName());


        return view;
    }

    private Zone getOrder(int position) {
        return ((Zone) getItem(position));
    }
}
