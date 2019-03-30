package com.example.moderator.xcell;

import java.util.ArrayList;

public class device_data {
    private String deviceType = "switch"; //device type could be "switch", "seekbar", "color_picker".
    private boolean deviceSwitch = false;
    private int seekbarValue = 0;
    private boolean seekbarVisibility = false;
    private int[] RGB = {255,0,0}; // RGB values
    private int value = 100;

    private String deviceName = "Name";
    ////////////////////////////////////////Add communication specific variables////////////////////////////////////////


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isSeekbarVisibility() {
        return seekbarVisibility;
    }

    public void setSeekbarVisibility(boolean seekbarVisibility) {
        this.seekbarVisibility = seekbarVisibility;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public boolean isDeviceSwitch() {
        return deviceSwitch;
    }

    public void setDeviceSwitch(boolean deviceSwitch) {
        this.deviceSwitch = deviceSwitch;
    }

    public int getSeekbarValue() {
        return seekbarValue;
    }

    public void setSeekbarValue(int seekbarValue) {
        this.seekbarValue = seekbarValue;
    }

    public int[] getRGB() {
        return RGB;
    }

    public void setRGB(int[] RGB) {
        this.RGB = RGB;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public device_data(String deviceType, String deviceName) {
        this.deviceName = deviceName;
        this.deviceType = deviceType;

    }
}
