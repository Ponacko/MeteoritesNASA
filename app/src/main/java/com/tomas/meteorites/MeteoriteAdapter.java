package com.tomas.meteorites;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tomas on 6. 9. 2017.
 */

public class MeteoriteAdapter extends ArrayAdapter<Meteorite> {
    public MeteoriteAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Meteorite> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.item, parent, false);
        }

        Meteorite meteorite = getItem(position);
        if (meteorite!= null) {
            TextView text = (TextView)convertView.findViewById(R.id.meteoriteName);
            TextView mass = (TextView)convertView.findViewById(R.id.meteoriteMass);
            text.setText(meteorite.name);
            mass.setText(meteorite.mass);
        }
        return convertView;
    }
}
