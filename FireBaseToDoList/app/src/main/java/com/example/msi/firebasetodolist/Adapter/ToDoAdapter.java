package com.example.msi.firebasetodolist.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msi.firebasetodolist.Model.Task;
import com.example.msi.firebasetodolist.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by M.S.I on 8/2/2017.
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    Context context;
    List<Task> taskList;
    int layout;
    DatabaseReference databaseReference;
    public ToDoAdapter(Context context, List<Task> taskList, int layout,DatabaseReference database) {
        this.context = context;
        this.taskList = taskList;
        this.layout = layout;
        databaseReference = database;

    }

    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ToDoAdapter.ViewHolder holder, final int position) {

           holder.textView.setText(taskList.get(position).getTodayTask());
           holder.imageView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   PopupMenu menu = new PopupMenu(context,view, Gravity.END);
                   menu.getMenuInflater().inflate(R.menu.popup_menu,menu.getMenu());

                   menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                       @Override
                       public boolean onMenuItemClick(MenuItem item) {
                           switch (item.getItemId()){
                               case R.id.delete:
                                   databaseReference.child("Task/"+taskList.get(position).getKey()).removeValue();
                                   taskList.remove(position);
                                   notifyDataSetChanged();
                                   Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                                   break;
                               case R.id.edit:
                                   final Dialog dialog =new Dialog(context);
                                   dialog.setContentView(R.layout.add_job_layout);

                                   final TextView job = dialog.findViewById(R.id.tv1);
                                   Button addjob = dialog.findViewById(R.id.addjob);
                                   Button cancel = dialog.findViewById(R.id.cancel);

                                   addjob.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           String s = job.getText().toString();
                                           databaseReference.child("Task/"+taskList.get(position).getKey()).child("todayTask").setValue(s);
                                           taskList.get(position).setTodayTask(s);
                                           notifyDataSetChanged();
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
                                   Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
                           }
                           return true;
                       }
                   });
                  menu.show();
               }
           });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv2);
            imageView = itemView.findViewById(R.id.imgv);
        }
    }
}
