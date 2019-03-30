package com.example.moderator.xcell;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Configuration_adapter extends RecyclerView.Adapter<Configuration_adapter.myViewHolder>{

    private ArrayList<String> devices = new ArrayList<>();
    private Context mcontext;

    public Configuration_adapter(ArrayList<String> devices, Context mcontext) {
        this.devices = devices;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.configurations_item, viewGroup, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, final int i) {
        myViewHolder.device_name_conf.setText(devices.get(i));
        myViewHolder.setting_conf.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //to do: add transition to edit configuration layout
                Intent myIntent = new Intent(mcontext, com.example.moderator.xcell.Edit_configurations.class);
                mcontext.startActivity(myIntent);
                Toast.makeText(mcontext, devices.get(i), Toast.LENGTH_SHORT).show();
            }

        });
        myViewHolder.delete_conf.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mcontext);
                alertDialogBuilder.setMessage("Are you sure You want to delete " +devices.get(i));
                alertDialogBuilder.setNegativeButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                String x = devices.get(i);
                                devices.remove(x);
                                Toast.makeText(mcontext,x  + " is deleted", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                            }
                        });
                alertDialogBuilder.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        });


    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView device_name_conf;
        ConstraintLayout parentLayout;
        ImageButton setting_conf;
        ImageButton delete_conf;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            device_name_conf = itemView.findViewById(R.id.device_name_conf);
            parentLayout = itemView.findViewById(R.id.parent_layout_conf);
            setting_conf = itemView.findViewById(R.id.conf_settings);
            delete_conf = itemView.findViewById(R.id.conf_delete);
        }
    }
}
