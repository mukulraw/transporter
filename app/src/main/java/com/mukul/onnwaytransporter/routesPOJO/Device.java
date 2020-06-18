package com.mukul.onnwaytransporter.routesPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device {
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("destination")
    @Expose
    private String destination;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}
