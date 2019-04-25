package com.example.moderator.xcell;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

public class add_device extends AppCompatActivity {
    private ArrayList<device_data> devices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        //android.support.v7.widget.Toolbar tb = (android.support.v7.widget.Toolbar) findViewById(R.id.custom_bar);
        //setSupportActionBar(tb);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Available devices");
        initRecylerView();
    }

    private void initRecylerView()
    {


        RecyclerView conf_list = findViewById(R.id.available_devices_list);
        add_device_adapter adapter = new add_device_adapter(devices, this);
        conf_list.setAdapter(adapter);
        conf_list.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onBackPressed() {
        Intent myIntent = new Intent(getApplicationContext(), com.example.moderator.xcell.devices.class);
        getApplicationContext().startActivity(myIntent);
        return;
    }

}
