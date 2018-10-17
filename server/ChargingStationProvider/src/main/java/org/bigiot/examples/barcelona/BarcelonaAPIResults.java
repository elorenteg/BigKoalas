package org.bigiot.examples.barcelona;

import java.util.List;

public class BarcelonaAPIResults {

    private Result result;

    public Result getResultParent() {
        return result;
    }
    public void setResultParent(Result result) {
        this.result = result;
    }
    public String toString() { return "ResponseData[" + result.toString() + "]"; }

    static class Result {
        private List<Info> chargepoint;

        public List<Info> getChargepoint() {
            return chargepoint;
        }
        public void setChargepoint(List<Info> chargepoint) {
            this.chargepoint = chargepoint;
        }
        public String toString() { return "ResponseData[" + chargepoint.toString() + "]"; }
    }

    static class Info {
        private String ParkingID;
        private String ParkingName;
        private String Address;
        private Float Lat;
        private Float Lng;
        private Object ParkingLevel;
        private Object Place;
        private String Vehicle;
        private String ChargeBoxID;
        private String Schuko;
        private Object Menneke;
        private Object Status;
        private String Created;
        private String Updated;

        public String getParkingID() {
            return ParkingID;
        }

        public void setParkingID(String parkingID) {
            ParkingID = parkingID;
        }

        public String getParkingName() {
            return ParkingName;
        }

        public void setParkingName(String parkingName) {
            ParkingName = parkingName;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public Float getLat() {
            return Lat;
        }

        public void setLat(Float lat) {
            Lat = lat;
        }

        public Float getLng() {
            return Lng;
        }

        public void setLng(Float lng) {
            Lng = lng;
        }

        public Object getParkingLevel() {
            return ParkingLevel;
        }

        public void setParkingLevel(Object parkingLevel) {
            ParkingLevel = parkingLevel;
        }

        public Object getPlace() {
            return Place;
        }

        public void setPlace(Object place) {
            Place = place;
        }

        public String getVehicle() {
            return Vehicle;
        }

        public void setVehicle(String vehicle) {
            Vehicle = vehicle;
        }

        public String getChargeBoxID() {
            return ChargeBoxID;
        }

        public void setChargeBoxID(String chargeBoxID) {
            ChargeBoxID = chargeBoxID;
        }

        public String getSchuko() {
            return Schuko;
        }

        public void setSchuko(String schuko) {
            Schuko = schuko;
        }

        public Object getMenneke() {
            return Menneke;
        }

        public void setMenneke(Object menneke) {
            Menneke = menneke;
        }

        public Object getStatus() {
            return Status;
        }

        public void setStatus(Object status) {
            Status = status;
        }

        public String getCreated() {
            return Created;
        }

        public void setCreated(String created) {
            Created = created;
        }

        public String getUpdated() {
            return Updated;
        }

        public void setUpdated(String updated) {
            Updated = updated;
        }

        public String toString() { return getParkingID(); }
    }

}