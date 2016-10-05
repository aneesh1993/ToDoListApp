package com.example.aneesh.todolist;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

public class MainActivity extends Activity {

    private EditText taskTitle;
    private EditText taskDescription;
    private ListView taskList;
    private CheckBox chkBox;

    private HashMap<String, String> tasks;
    private String hashTaskTitle = "taskTitle";
    private String hashTaskDescription = "taskDescription";

    private ArrayList <HashMap<String, String>> taskArrayList = new ArrayList<>();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i("instanceState", "onSaveInstanceState");
        taskTitle = (EditText)findViewById(R.id.task_title);
        outState.putString("task_title", taskTitle.getText().toString());

        taskDescription = (EditText)findViewById(R.id.task_description);
        outState.putString("task_description", taskDescription.getText().toString());

        outState.putSerializable("taskArrayList", taskArrayList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.i("instanceState", "onRestoreInstanceState");
        //if (savedInstanceState != null){

            taskTitle = (EditText)findViewById(R.id.task_title);
            taskTitle.setText(savedInstanceState.getString("task_title"));

            taskDescription = (EditText)findViewById(R.id.task_description);
            taskDescription.setText(savedInstanceState.getString("task_description"));

            taskArrayList = (ArrayList<HashMap<String,String>>) savedInstanceState.getSerializable("taskArrayList");
            populateList(taskArrayList);
        //}

    }

    private boolean fileExists(String fileName){
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if (fileExists("ListState.txt")){
            try {
                Scanner loadListState = new Scanner(openFileInput("ListState1.txt"));
                while (loadListState.hasNextLine()){
                    String line = loadListState.nextLine();
                    String lineSplit[] = line.split("\t");
                    HashMap<String, String> hash = new HashMap<>();
                    hash.put(hashTaskTitle, lineSplit[0]);
                    hash.put(hashTaskDescription, lineSplit[1]);
                    taskArrayList.add(hash);
                    populateList(taskArrayList);
                }
            } catch (FileNotFoundException e) {
                Log.wtf("FileWriter", e);
                e.printStackTrace();
            }
        }


        Log.i("instanceState", "onCreate");
        /*if (savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);
        }else{
            Log.i("instanceState", "no saved state!");
        }*/


        //System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath());

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



    @Override
    protected void onStop() {
        super.onStop();
        Log.i("instanceState", "onStop");

        try {
            PrintStream saveListState = new PrintStream(openFileOutput("ListState1.txt", MODE_PRIVATE));
            for (int i = 0; i < taskArrayList.size(); i++){
                saveListState.println(taskArrayList.get(i).get(hashTaskTitle) + "\t" + taskArrayList.get(i).get(hashTaskDescription));
            }
            saveListState.close();

        } catch (FileNotFoundException e) {
            Log.wtf("FileWriter", e);
            e.printStackTrace();
        }

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
            taskTitle.setText("");
            taskDescription.setText("");

        }
        else if(taskTitleToAdd.isEmpty() && taskDescriptionToAdd.isEmpty()){
            if(!taskArrayList.isEmpty()){
                writeToFile(taskArrayList);
                Toast.makeText(this, "Writing the current ToDo List to a text file", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No Items in ToDo List!", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this, "Please Enter Text in both fields", Toast.LENGTH_SHORT).show();
        }

    }

    public void populateList(ArrayList<HashMap<String, String>> taskArrayList){
        ListAdapter myAdapter = new CustomAdapter(this, taskArrayList);
        taskList = (ListView) findViewById(R.id.listView);
        taskList.setAdapter(myAdapter);

    }

    public void writeToFile(ArrayList<HashMap<String, String>> taskArrayList){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_DD_HH:mm");
        String dateTime = formatter.format(Calendar.getInstance().getTime());
        String fileName = "To_Do_List_" + dateTime + ".txt";

        File root = getExternalFilesDir(null);

        File file = new File(root, fileName);
        System.out.println(file);
        try {

            if(!file.exists()){
                root.createNewFile();
            }

            FileWriter writer = new FileWriter(file);

            for(int i = 0; i < taskArrayList.size(); i++){
                String lineToWrite = taskArrayList.get(i).get(hashTaskTitle) + "\t" + taskArrayList.get(i).get(hashTaskDescription) + "\n";
                writer.append(lineToWrite);
                writer.flush();
            }
            writer.close();

        } catch (IOException e) {
            Log.wtf("FileWriter", e);
            e.printStackTrace();
        }
    }

}
