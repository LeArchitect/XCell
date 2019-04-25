package com.example.moderator.xcell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class edit_confs_adapter  extends RecyclerView.Adapter<edit_confs_adapter.myViewHolder>{

    private device_data device;
    private Context mcontext;

    public edit_confs_adapter(device_data device, Context mcontext) {
        this.device = device;
        this.mcontext = mcontext;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.edit_configuration_item, viewGroup, false);
        edit_confs_adapter.myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
        if(device.getDeviceType().equals("seekbar"))
        {
            //disable other views
            myViewHolder.parent_layout_confs.removeView(myViewHolder.switch_layout);
            myViewHolder.seekbar_device_name.setText(device.getDeviceName());
            //to do , get rest of the values
        }
        else if(device.getDeviceType().equals("switch"))
        {
            //disable other views
            myViewHolder.parent_layout_confs.removeView(myViewHolder.seekbar_layout);
            myViewHolder.switch_device_name.setText(device.getDeviceName());
            //to do , get rest of the values

        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView seekbar_device_name,seekbar_device_value, switch_device_name, clickable_device_name, clickable_device_value;
        Switch switch_button;
        SeekBar seekbar;
        LinearLayout seekbar_layout, switch_layout, clickable_layout, parent_layout_confs;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_layout_confs = itemView.findViewById(R.id.parent_layout_edit_configurations);
            //seekbar
            seekbar_device_name = itemView.findViewById(R.id.textLightBar);
            seekbar_device_value = itemView.findViewById(R.id.seekbar_value_editconfs);
            seekbar = itemView.findViewById(R.id.light_intensity_bar);
            seekbar_layout = itemView.findViewById(R.id.light_bar_layout);
            //switch
            switch_button = itemView.findViewById(R.id.switch_bool_light);
            switch_layout = itemView.findViewById(R.id.light_bool_layout);
            switch_device_name =  itemView.findViewById(R.id.textLightBool);
            //clickable
            //clickable_device_name = itemView.findViewById(R.id.clickable_layout_text);
            //clickable_device_value = itemView.findViewById(R.id.clickable_layout_value);
            //clickable_layout = itemView.findViewById(R.id.clickable_layout);
        }
    }
}
