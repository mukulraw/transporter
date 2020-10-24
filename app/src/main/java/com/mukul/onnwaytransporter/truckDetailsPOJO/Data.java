package com.mukul.onnwaytransporter.truckDetailsPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("laod_type")
    @Expose
    private String laodType;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("truck_type")
    @Expose
    private String truckType;
    @SerializedName("truck_type2")
    @Expose
    private String truckType2;
    @SerializedName("schedule")
    @Expose
    private String schedule;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("load_passing")
    @Expose
    private String loadPassing;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("length")
    @Expose
    private String length;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("material")
    @Expose
    private String material;
    @SerializedName("truckTypeDetails")
    @Expose
    private String truckTypeDetails;
    @SerializedName("truckCapacity")
    @Expose
    private String truckCapacity;
    @SerializedName("boxLength")
    @Expose
    private String boxLength;
    @SerializedName("boxWidth")
    @Expose
    private String boxWidth;
    @SerializedName("boxArea")
    @Expose
    private String boxArea;
    @SerializedName("selectedArea")
    @Expose
    private String selectedArea;
    @SerializedName("remainingArea")
    @Expose
    private String remainingArea;
    @SerializedName("selected")
    @Expose
    private String selected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLaodType() {
        return laodType;
    }

    public void setLaodType(String laodType) {
        this.laodType = laodType;
    }

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

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLoadPassing() {
        return loadPassing;
    }

    public void setLoadPassing(String loadPassing) {
        this.loadPassing = loadPassing;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getTruckCapacity() {
        return truckCapacity;
    }

    public String getBoxLength() {
        return boxLength;
    }

    public String getTruckTypeDetails() {
        return truckTypeDetails;
    }

    public String getBoxArea() {
        return boxArea;
    }

    public String getBoxWidth() {
        return boxWidth;
    }

    public String getRemainingArea() {
        return remainingArea;
    }

    public String getSelected() {
        return selected;
    }

    public String getSelectedArea() {
        return selectedArea;
    }

    public void setBoxLength(String boxLength) {
        this.boxLength = boxLength;
    }

    public void setTruckCapacity(String truckCapacity) {
        this.truckCapacity = truckCapacity;
    }

    public void setBoxArea(String boxArea) {
        this.boxArea = boxArea;
    }

    public void setBoxWidth(String boxWidth) {
        this.boxWidth = boxWidth;
    }

    public void setRemainingArea(String remainingArea) {
        this.remainingArea = remainingArea;
    }

    public void setTruckTypeDetails(String truckTypeDetails) {
        this.truckTypeDetails = truckTypeDetails;
    }

    public void setSelectedArea(String selectedArea) {
        this.selectedArea = selectedArea;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getTruckType2() {
        return truckType2;
    }

    public void setTruckType2(String truckType2) {
        this.truckType2 = truckType2;
    }
}
