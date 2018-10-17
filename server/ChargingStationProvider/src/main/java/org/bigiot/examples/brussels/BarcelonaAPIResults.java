package org.bigiot.examples.brussels;

import java.util.List;

public class BarcelonaAPIResults {

    private String type;
    private int totalFeatures;
    private List<Feature> features;

    public String  toString() {
        return totalFeatures + "";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalFeatures() {
        return totalFeatures;
    }

    public void setTotalFeatures(int totalFeatures) {
        this.totalFeatures = totalFeatures;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    static class Properties{

        private int id;
        private int number;
        private String owner;
        private String type;
        private String type_fr;
        private String type_nl;
        private String payment;
        private String charging;
        private String charging_fr;
        private String charging_nl;
        private String plug;
        private String plug_nl;
        private String plug_fr;
        private String road_fr;
        private String road_nl;
        private String housenr;
        private String pccp;
        private String mu_fr;
        private String mu_nl;
        private List<Float> bbox;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType_fr() {
            return type_fr;
        }

        public void setType_fr(String type_fr) {
            this.type_fr = type_fr;
        }

        public String getType_nl() {
            return type_nl;
        }

        public void setType_nl(String type_nl) {
            this.type_nl = type_nl;
        }

        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }

        public String getCharging() {
            return charging;
        }

        public void setCharging(String charging) {
            this.charging = charging;
        }

        public String getCharging_fr() {
            return charging_fr;
        }

        public void setCharging_fr(String charging_fr) {
            this.charging_fr = charging_fr;
        }

        public String getCharging_nl() {
            return charging_nl;
        }

        public void setCharging_nl(String charging_nl) {
            this.charging_nl = charging_nl;
        }

        public String getPlug() {
            return plug;
        }

        public void setPlug(String plug) {
            this.plug = plug;
        }

        public String getPlug_nl() {
            return plug_nl;
        }

        public void setPlug_nl(String plug_nl) {
            this.plug_nl = plug_nl;
        }

        public String getPlug_fr() {
            return plug_fr;
        }

        public void setPlug_fr(String plug_fr) {
            this.plug_fr = plug_fr;
        }

        public String getRoad_fr() {
            return road_fr;
        }

        public void setRoad_fr(String road_fr) {
            this.road_fr = road_fr;
        }

        public String getRoad_nl() {
            return road_nl;
        }

        public void setRoad_nl(String road_nl) {
            this.road_nl = road_nl;
        }

        public String getHousenr() {
            return housenr;
        }

        public void setHousenr(String housenr) {
            this.housenr = housenr;
        }

        public String getPccp() {
            return pccp;
        }

        public void setPccp(String pccp) {
            this.pccp = pccp;
        }

        public String getMu_fr() {
            return mu_fr;
        }

        public void setMu_fr(String mu_fr) {
            this.mu_fr = mu_fr;
        }

        public String getMu_nl() {
            return mu_nl;
        }

        public void setMu_nl(String mu_nl) {
            this.mu_nl = mu_nl;
        }

        public List<Float> getBbox() {
            return bbox;
        }

        public void setBbox(List<Float> bbox) {
            this.bbox = bbox;
        }
    }

    static class Geometry{
        private String type;
        private List<Float> coordinates;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Float> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<Float> coordinates) {
            this.coordinates = coordinates;
        }
    }

    static class Feature {
        private String type;
        private String id;
        private Geometry geometry;
        private String geometry_name;
        private Properties properties;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getGeometry_name() {
            return geometry_name;
        }

        public void setGeometry_name(String geometry_name) {
            this.geometry_name = geometry_name;
        }

        public Properties getProperties() {
            return properties;
        }

        public void setProperties(Properties properties) {
            this.properties = properties;
        }
    }

}