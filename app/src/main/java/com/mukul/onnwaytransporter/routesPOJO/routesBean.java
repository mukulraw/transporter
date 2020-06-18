package com.mukul.onnwaytransporter.routesPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class routesBean {
    @SerializedName("devices")
    @Expose
    private List<Device> devices = null;

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

}
