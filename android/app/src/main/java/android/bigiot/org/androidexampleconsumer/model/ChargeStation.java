package android.bigiot.org.androidexampleconsumer.model;

public class ChargeStation {
    private double latitude;
    private double longitude;
    private String address;
    private int availableSpots;
    private int availableMennekeSpots;
    private int availableSchukoSpots;
    private String spotType;

    public ChargeStation(double latitude, double longitude, String address,
                         int availableSpots, int availableMennekeSpots, int availableSchukoSpots,
                         String spotType) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.availableSpots = availableSpots;
        this.availableMennekeSpots = availableMennekeSpots;
        this.availableSchukoSpots = availableSchukoSpots;

        setSpotType(spotType);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }

    public int getAvailableMennekeSpots() {
        return availableMennekeSpots;
    }

    public void setAvailableMennekeSpots(int availableMennekeSpots) {
        this.availableMennekeSpots = availableMennekeSpots;
    }

    public int getAvailableSchukoSpots() {
        return availableSchukoSpots;
    }

    public void setAvailableSchukoSpots(int availableSchukoSpots) {
        this.availableSchukoSpots = availableSchukoSpots;
    }

    public String getSpotType() {
        return spotType;
    }

    public void setSpotType(String spotType) {
        if (spotType.equals("moto")) {
            this.spotType = "Motorbike";
        } else {
            this.spotType = "Vehicle";
        }
    }
}
