package com.example.aneesh.todolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Aneesh on 9/29/2016.
 */
class CustomAdapter extends ArrayAdapter<HashMap<String,String>>{


    public CustomAdapter(Context context, ArrayList<HashMap<String,String>> resource) {
        super(context,R.layout.custom_view, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater adapterInflater = LayoutInflater.from(getContext());
        View customView = adapterInflater.inflate(R.layout.custom_view, parent, false);

        HashMap<String,String> singleArrayEntry = getItem(position);

        TextView taskTitle = (TextView)customView.findViewById(R.id.task_item);
        TextView taskDescription = (TextView)customView.findViewById(R.id.description_item);

        taskTitle.setText(singleArrayEntry.get("taskTitle"));
        taskDescription.setText(singleArrayEntry.get("taskDescription"));

        return customView;
    }

}
