package com.example.aneesh.todolist;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

    private EditText taskTitle;
    private EditText taskDescription;
    private ListView taskList;

    private HashMap<String, String> tasks;
    private String hashTaskTitle = "taskTitle";
    private String hashTaskDescription = "taskDescription";

    private ArrayList <HashMap<String, String>> taskArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void addItem(View view) {
        taskTitle = (EditText) findViewById(R.id.task_title);
        taskDescription = (EditText) findViewById(R.id.task_description);

        tasks = new HashMap<>();
        tasks.put(hashTaskTitle, taskTitle.getText().toString());
        tasks.put(hashTaskDescription, taskDescription.getText().toString());

        taskArrayList.add(tasks);
        ListAdapter myAdapter = new CustomAdapter(this, taskArrayList);
        taskList = (ListView) findViewById(R.id.listView);
        taskList.setAdapter(myAdapter);

        taskTitle.setText("");
        taskDescription.setText("");
    }


}
