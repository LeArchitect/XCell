package com.example.moderator.xcell;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class add_device_adapter extends RecyclerView.Adapter<add_device_adapter.myViewHolder>{
    private ArrayList<device_data> devices = new ArrayList<>();
    private Context mcontext;

    public add_device_adapter(ArrayList<device_data> devices, Context mcontext) {
        this.devices = devices;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_device_element, viewGroup, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, final int i) {
        final String device_name = WiFiServiceDiscovery.getInstance().getServicesArray().get(i);
        final String type = new String(WiFiServiceDiscovery.getInstance().getServiceInfos().get(device_name).getAttributes().get("type"));
        myViewHolder.device_name.setText(device_name);
        myViewHolder.device_name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent myIntent = new Intent(mcontext, com.example.moderator.xcell.devices.class);
                myIntent.putExtra("source", "add_device");
                myIntent.putExtra("name", device_name);
                myIntent.putExtra("type", type);
                mcontext.startActivity(myIntent);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return WiFiServiceDiscovery.getInstance().getServicesArray().size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView device_name;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            device_name = itemView.findViewById(R.id.device_name_add_devices);
        }
    }
}

