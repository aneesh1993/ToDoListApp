package com.example.aneesh.todolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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

        final HashMap<String,String> singleArrayEntry = getItem(position);
        int taskNum = position + 1;
        TextView taskTitle = (TextView)customView.findViewById(R.id.task_item);
        TextView taskDescription = (TextView)customView.findViewById(R.id.description_item);
        CheckBox chkbox = (CheckBox)customView.findViewById(R.id.task_CheckBox);

        taskTitle.setText("Task " + taskNum + ": " +singleArrayEntry.get("taskTitle"));
        taskDescription.setText(singleArrayEntry.get("taskDescription"));

        chkbox.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox)v;
                        if (cb.isChecked()){

                            if(isEmpty()){
                                Toast.makeText(getContext(), "Congrats, you've done it all!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), singleArrayEntry.get("taskTitle").toString() + " is done!", Toast.LENGTH_SHORT).show();
                            }
                            remove(singleArrayEntry);

                        }


                    }
                }
        );

        return customView;
    }



}
