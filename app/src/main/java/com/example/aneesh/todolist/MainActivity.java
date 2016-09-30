package com.example.aneesh.todolist;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        taskList = (ListView)findViewById(R.id.listView);
        taskList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, String> hash = taskArrayList.get(position);
                        String taskName = hash.get(hashTaskTitle);

                        taskArrayList.remove(position);
                        populateList(taskArrayList);

                        if (taskArrayList.isEmpty()){
                            Toast.makeText(MainActivity.this, "Congrats, you've done it all!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, taskName + " is done!", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                }
        );
    }

    public void addItem(View view) {
        taskTitle = (EditText) findViewById(R.id.task_title);
        taskDescription = (EditText) findViewById(R.id.task_description);

        tasks = new HashMap<>();

        String taskTitleToAdd = taskTitle.getText().toString();
        String taskDescriptionToAdd = taskDescription.getText().toString();
        
        if (!taskTitleToAdd.isEmpty() && !taskDescriptionToAdd.isEmpty()){
            tasks.put(hashTaskTitle, taskTitleToAdd);
            tasks.put(hashTaskDescription, taskDescriptionToAdd);

            taskArrayList.add(tasks);
            populateList(taskArrayList);
        }
        else{
            Toast.makeText(this, "Please Enter Text in both fields", Toast.LENGTH_SHORT).show();
            taskTitle.setText("");
            taskDescription.setText("");
        }

    }

    public void populateList(ArrayList<HashMap<String, String>> taskArrayList){
        ListAdapter myAdapter = new CustomAdapter(this, taskArrayList);
        taskList = (ListView) findViewById(R.id.listView);
        taskList.setAdapter(myAdapter);

        taskTitle.setText("");
        taskDescription.setText("");
    }



}
