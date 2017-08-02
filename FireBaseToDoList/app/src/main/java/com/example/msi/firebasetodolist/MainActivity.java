package com.example.msi.firebasetodolist;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msi.firebasetodolist.Adapter.ToDoAdapter;
import com.example.msi.firebasetodolist.Model.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    DatabaseReference database;
    ImageView add;
    RecyclerView recyclerView;
    List<Task> tasks;
    ToDoAdapter toDoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         database = FirebaseDatabase.getInstance().getReference("TodoJobs");
//         tasks = new ArrayList<>();

        initializeView();
        addListener();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_job_layout);

                final TextView job = dialog.findViewById(R.id.tv1);
                Button addjob = dialog.findViewById(R.id.addjob);
                Button cancel = dialog.findViewById(R.id.cancel);


                addjob.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      String s = job.getText().toString();
                        addNew(s);
                        dialog.dismiss();
                    }
                });


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }


    private void addListener() {
        ValueEventListener valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Task> results = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, Task>>() {});

                tasks = new ArrayList<>(results.values());
                toDoAdapter = new ToDoAdapter(MainActivity.this,tasks,R.layout.todo_item_layout,database);
                recyclerView.setAdapter(toDoAdapter);

//                for (Task post : tasks) {
//                    Log.e("Post Title", post.getTodayTask());
//                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database.child("Task").addListenerForSingleValueEvent(valueEventListener);
    }

    private void addNew(String s) {
          String key = database.child("Task").push().getKey();
          database.child("Task/"+key).setValue(new Task(key,s));
          addListener();
//        toDoAdapter.notifyDataSetChanged();


    }

    private void initializeView() {
         add = (ImageView) findViewById(R.id.adds);
        recyclerView = (RecyclerView) findViewById(R.id.resv);

    }

}
