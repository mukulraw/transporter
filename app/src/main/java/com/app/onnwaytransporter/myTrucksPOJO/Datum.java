package com.app.onnwaytransporter.myTrucksPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("truck_reg_no")
    @Expose
    private String truckRegNo;
    @SerializedName("trucktype")
    @Expose
    private String trucktype;
    @SerializedName("truck_type2")
    @Expose
    private String truck_type2;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("driver_mobile_no")
    @Expose
    private String driverMobileNo;

    public String getTruckRegNo() {
        return truckRegNo;
    }

    public void setTruckRegNo(String truckRegNo) {
        this.truckRegNo = truckRegNo;
    }

    public String getTrucktype() {
        return trucktype;
    }

    public void setTrucktype(String trucktype) {
        this.trucktype = trucktype;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverMobileNo() {
        return driverMobileNo;
    }

    public void setDriverMobileNo(String driverMobileNo) {
        this.driverMobileNo = driverMobileNo;
    }

    public String getTruck_type2() {
        return truck_type2;
    }

    public void setTruck_type2(String truck_type2) {
        this.truck_type2 = truck_type2;
    }
}

