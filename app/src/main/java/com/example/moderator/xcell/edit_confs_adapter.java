package com.example.moderator.xcell;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class edit_confs_adapter {












    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView seekbar_device_name,seekbar_device_value, switch_device_name,switch_device_value, clickable_device_name, clickable_device_value;
        Switch switch_button;
        SeekBar seekbar;
        LinearLayout seekbar_layout, switch_layout, clickable_layout, parent_layout_devices;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_layout_devices = itemView.findViewById(R.id.parent_layout_edit_configurations);
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
