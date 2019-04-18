package com.example.moderator.xcell;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

public class light_control extends AppCompatActivity {
    ImageView circle;
    TextView name;
    SeekBar brightness;
    Bitmap bitmap;
    int pixel,p, index;
    int RGB[] = {0,0,0};
    boolean pressed = false, changed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.widget.Toolbar tb = (android.support.v7.widget.Toolbar) findViewById(R.id.custom_bar);
        setSupportActionBar(tb);

        /*
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Light picker");
        */

        Intent myIntent = getIntent();
        RGB = myIntent.getIntArrayExtra("RGB");
        p = myIntent.getIntExtra("value", 100);
        index = myIntent.getIntExtra("index", 100);
        setContentView(R.layout.activity_light_control);
        circle = findViewById(R.id.brightness);
        circle.setDrawingCacheEnabled(true);
        circle.buildDrawingCache(true);
        circle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    pressed = true;
                    bitmap = circle.getDrawingCache();
                    pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());
                    int r = Color.red(pixel);
                    int b = Color.blue(pixel);
                    int g = Color.green(pixel);
                    String hex = "#" + Integer.toHexString(pixel);
                    float[] hsv = new float[3];
                    Color.RGBToHSV(r, g, b, hsv);
                    hsv[2] = (float) 1/(100-p);
                    pixel = Color.HSVToColor(hsv);
                    r = Color.red(pixel);
                    b = Color.blue(pixel);
                    g = Color.green(pixel);
                    brightness.setBackgroundColor(Color.rgb(r,g,b));

                }
                return false;
            }
        });
        name = findViewById(R.id.brightness_text);
        brightness = findViewById(R.id.brightness_control);
        brightness.setBackgroundColor(Color.rgb(RGB[0],RGB[1],RGB[2]));
        brightness.setProgress(p);
        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                p = (int) seekBar.getProgress()/10 + 90;
                changed= true;
                int r, g, b;
                if(pressed)
                {
                    r = Color.red(pixel);
                    b = Color.blue(pixel);
                    g = Color.green(pixel);
                }
                else
                {
                    r = RGB[0];
                    g = RGB[1];
                    b = RGB[2];
                }
                float[] hsv = new float[3];
                Color.RGBToHSV(r, g, b, hsv);
                hsv[2] = (float) 1/(100-p);
                pixel = Color.HSVToColor(hsv);
                r = Color.red(pixel);
                b = Color.blue(pixel);
                g = Color.green(pixel);
                brightness.setBackgroundColor(Color.rgb(r,g,b));
                int RGB2[] = {r, g, b};
                RGB = RGB2;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                p = (int) seekBar.getProgress()/10 + 90;
                int r = Color.red(pixel);
                int b = Color.blue(pixel);
                int g = Color.green(pixel);
                float[] hsv = new float[3];
                Color.RGBToHSV(r, g, b, hsv);
                hsv[2] = (float) 1/(100-p);
                pixel = Color.HSVToColor(hsv);
                r = Color.red(pixel);
                b = Color.blue(pixel);
                g = Color.green(pixel);
                brightness.setBackgroundColor(Color.rgb(r,g,b));
                int RGB2[] = {r, g, b};
                RGB = RGB2;
            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(getApplicationContext(), com.example.moderator.xcell.devices.class);
        myIntent.putExtra("RGB", RGB);
        if(pressed || changed)
        {
            myIntent.putExtra("value", (p-90)*10);
        }
        else
        {
            myIntent.putExtra("value", p);
        }

        myIntent.putExtra("index", index);
        myIntent.putExtra("source", "color_picker");
        getApplicationContext().startActivity(myIntent);
        return;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

}
