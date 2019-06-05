package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class FeedAdapter extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<feedentry> applications;

    public FeedAdapter(Context context, int resource, List<feedentry> applications) {
        super(context, resource);
        this.layoutResource=resource;
        this.layoutInflater=LayoutInflater.from(context);
        this.applications = applications;
    }

    @Override
    public int getCount() {
        return applications.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflater.inflate(layoutResource,parent,false);
        TextView name=(TextView) view.findViewById(R.id.name);
        TextView artist=(TextView) view.findViewById(R.id.artist);
        TextView summart=(TextView) view.findViewById(R.id.summary);
        feedentry currentapp =applications.get(position);
        name.setText(currentapp.getName());
        artist.setText(currentapp.getArtist());
        summart.setText(currentapp.getSummary());
        return view;
    }
}
