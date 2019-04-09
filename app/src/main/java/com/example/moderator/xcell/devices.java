package com.example.moderator.xcell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class devices extends AppCompatActivity {
    private ArrayList<device_data> devices = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        device_data x = new device_data("color_picker", "room  lamp");
        devices.add(x);
        device_data y = new device_data("seekbar", "temperature");
        devices.add(y);
        device_data z = new device_data("switch", "kitchen lamp");
        devices.add(z);
        setContentView(R.layout.activity_devices);
        Intent myIntent = getIntent();
        String source = myIntent.getStringExtra("source");
        if( source != null)
        {

            if(source.equals("color_picker"))
            {
                int index = myIntent.getIntExtra("index", 100);
                devices.get(index).setRGB(myIntent.getIntArrayExtra("RGB"));
                devices.get(index).setValue(myIntent.getIntExtra("value", 100));

            }
            else if (source.equals("add_device"))
            {
                device_data tmp = new device_data(myIntent.getStringExtra("type") , myIntent.getStringExtra("name"));
                devices.add(tmp);
            }

        }

        initRecylerView();

    }

    private void initRecylerView()
    {


        RecyclerView conf_list = findViewById(R.id.rooms_list);
        Devices_adapter adapter = new Devices_adapter(devices, this);
        conf_list.setAdapter(adapter);
        conf_list.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addItem(View view) {
        //change to add devices layout
        Intent myIntent = new Intent(getApplicationContext(), com.example.moderator.xcell.add_device.class);
        getApplicationContext().startActivity(myIntent);
    }
}
