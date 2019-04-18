package com.example.moderator.xcell;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class configurations extends AppCompatActivity {
    private ArrayList<String> devices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurations);

        android.support.v7.widget.Toolbar tb = (android.support.v7.widget.Toolbar) findViewById(R.id.custom_bar);
        setSupportActionBar(tb);

        /*
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Configurations");
        */
        initRecylerView();
    }
    private void initRecylerView()
    {
        devices.add("room 9");devices.add("room 2");devices.add("room 3");devices.add("room 4");devices.add("room 5");
        RecyclerView conf_list = findViewById(R.id.configurations_list);
        Configuration_adapter adapter = new Configuration_adapter(devices, this);
        conf_list.setAdapter(adapter);
        conf_list.setLayoutManager(new LinearLayoutManager(this));
    }

}
