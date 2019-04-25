package com.example.moderator.xcell;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.nsd.NsdServiceInfo;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;


import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Devices_adapter extends RecyclerView.Adapter<Devices_adapter.myViewHolder>{

    private ArrayList<device_data> devices = new ArrayList<>();
    private Context mcontext ;



    public Devices_adapter(ArrayList<device_data> devices, Context mcontext) {
        this.devices = devices;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.device_item, viewGroup, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, final int i) {
        final String device_name = WiFiServiceDiscovery.getInstance().getServicesArray().get(i);
        NsdServiceInfo info = WiFiServiceDiscovery.getInstance().getServiceInfos().get(device_name);
        final String type = new String(WiFiServiceDiscovery.getInstance().getServiceInfos().get(device_name).getAttributes().get("type"));
        if(type.equals("switch"))
        {
            myViewHolder.parent_layout_devices.removeView(myViewHolder.clickable_layout);
            myViewHolder.parent_layout_devices.removeView(myViewHolder.seekbar_layout);
            //do the rest of the listeners
            myViewHolder.switch_device_name.setText(device_name);
            myViewHolder.switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(mcontext,device_name +" "+ isChecked, Toast.LENGTH_SHORT).show();
                    devices.get(i).setDeviceSwitch(isChecked);
                    if(isChecked)
                        {
                            new CommunicationTask().execute(device_name,"ON");
                        }
                    else
                        {
                            new CommunicationTask().execute(device_name,"OFF");
                        }
                }
            });
        }
        /*
        else if(devices.get(i).getDeviceType().equals("seekbar"))
        {
            myViewHolder.parent_layout_devices.removeView(myViewHolder.clickable_layout);
            myViewHolder.parent_layout_devices.removeView(myViewHolder.switch_layout);
            //do the rest of the listeners and name setting
            myViewHolder.switch_device_name.setText(devices.get(i).getDeviceName());
            myViewHolder.seekbar_device_value.setText("" + devices.get(i).getSeekbarValue());
            myViewHolder.seekbar_layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(devices.get(i).isSeekbarVisibility())
                    { myViewHolder.seekbar.setVisibility(View.VISIBLE);
                        devices.get(i).setSeekbarVisibility(false);
                    }
                    else{
                        myViewHolder.seekbar.setVisibility(View.GONE);
                        devices.get(i).setSeekbarVisibility(true);
                    }
                    notifyDataSetChanged();

                }
            });
            myViewHolder.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    devices.get(i).setSeekbarValue(seekBar.getProgress());
                    notifyDataSetChanged();

                }
            });


        }
        else if(devices.get(i).getDeviceType().equals("color_picker"))
        {
            myViewHolder.parent_layout_devices.removeView(myViewHolder.switch_layout);
            myViewHolder.parent_layout_devices.removeView(myViewHolder.seekbar_layout);
            //do the rest of the listeners
            myViewHolder.clickable_device_name.setText(devices.get(i).getDeviceName());
            myViewHolder.clickable_layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(mcontext, com.example.moderator.xcell.light_control.class);
                    myIntent.putExtra("RGB", devices.get(i).getRGB());
                    myIntent.putExtra("value", devices.get(i).getValue());
                    myIntent.putExtra("index", i);
                    mcontext.startActivity(myIntent);

                }
            });


        }
        */
        else {
            myViewHolder.parent_layout_devices.removeView(myViewHolder.switch_layout);
            myViewHolder.parent_layout_devices.removeView(myViewHolder.seekbar_layout);
            myViewHolder.parent_layout_devices.removeView(myViewHolder.clickable_layout);
        }

        /*
        myViewHolder.device_name.setText(devices.get(i));

        myViewHolder.radio_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(mcontext,""+ isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        */

    }

    @Override
    public int getItemCount() {
        return WiFiServiceDiscovery.getInstance().getServicesArray().size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView seekbar_device_name,seekbar_device_value, switch_device_name,switch_device_value, clickable_device_name, clickable_device_value;
        Switch switch_button;
        SeekBar seekbar;
        LinearLayout seekbar_layout, switch_layout, clickable_layout, parent_layout_devices;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_layout_devices = itemView.findViewById(R.id.parent_layout_devices);
            //seekbar
            seekbar_device_name = itemView.findViewById(R.id.seek_bar_layout_text);
            seekbar_device_value = itemView.findViewById(R.id.seek_bar_layout_value);
            seekbar = itemView.findViewById(R.id.seek_bar_layout_seekbar);
            seekbar_layout = itemView.findViewById(R.id.seek_bar_layout);
            //switch
            switch_button = itemView.findViewById(R.id.switch_button_layout_switch);
            switch_layout = itemView.findViewById(R.id.switch_button_layout);
            switch_device_name =  itemView.findViewById(R.id.switch_button_layout_text);
            //clickable
            clickable_device_name = itemView.findViewById(R.id.clickable_layout_text);
            clickable_device_value = itemView.findViewById(R.id.clickable_layout_value);
            clickable_layout = itemView.findViewById(R.id.clickable_layout);
        }
    }
}
