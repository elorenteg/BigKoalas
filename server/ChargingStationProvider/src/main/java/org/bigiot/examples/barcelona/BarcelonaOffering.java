package org.bigiot.examples.barcelona;

import org.eclipse.bigiot.lib.handlers.AccessRequestHandler;
import org.eclipse.bigiot.lib.model.BigIotTypes;
import org.eclipse.bigiot.lib.model.Price;
import org.eclipse.bigiot.lib.offering.Endpoints;
import org.eclipse.bigiot.lib.offering.OfferingDescription;
import org.eclipse.bigiot.lib.offering.RegistrableOfferingDescription;
import org.eclipse.bigiot.lib.serverwrapper.BigIotHttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BarcelonaOffering {
    private RegistrableOfferingDescription offeringDescription;
    private Endpoints endpoints;

    public RegistrableOfferingDescription createOfferingDescription() {
        this.offeringDescription =
                OfferingDescription.createOfferingDescription("BarcelonaChargingStations")
                        .withName("Barcelona Charging Stations")
                        .withCategory("urn:big-iot:ChargingStationCategory")
                        .addInputData("latitude", "schema:latitude", BigIotTypes.ValueType.NUMBER)
                        .addInputData("longitude", "schema:longitude", BigIotTypes.ValueType.NUMBER)
                        .addInputData("radius", "schema:geoRadius", BigIotTypes.ValueType.NUMBER)
                        .addOutputData("latitude", "schema:latitude", BigIotTypes.ValueType.NUMBER)
                        .addOutputData("longitude", "schema:longitude", BigIotTypes.ValueType.NUMBER)
                        .addOutputData("address", "schema:streetAddress", BigIotTypes.ValueType.TEXT)
                        .addOutputData("availableSpots", "mobility:chargingNumberOfVacantStations", BigIotTypes.ValueType.NUMBER)
                        .addOutputData("availableMennekeSpots", "mobility:chargingNumberOfVacantStations", BigIotTypes.ValueType.NUMBER)
                        .addOutputData("availableSchukoSpots", "mobility:chargingNumberOfVacantStations", BigIotTypes.ValueType.NUMBER)
                        .addOutputData("spotType", "mobility:chargingStationConnectorType", BigIotTypes.ValueType.TEXT)
                        //.inRegion(BoundingBox.create(Location.create(42.1, 9.0), Location.create(43.2, 10.0)))
                        //.withTimePeriod(new DateTime(2017, 1, 1, 0, 0, 0), new DateTime())
                        .withPrice(Price.Euros.amount(0.001))
                        .withPricingModel(BigIotTypes.PricingModel.PER_ACCESS)
                        .withLicenseType(BigIotTypes.LicenseType.OPEN_DATA_LICENSE);

        return this.offeringDescription;
    }

    public Endpoints createEndpoint() {
        Endpoints endpoints = Endpoints.create(this.offeringDescription)
                .withAccessRequestHandler(this.accessCallback);

        this.endpoints = endpoints;

        return endpoints;
    }

    private static AccessRequestHandler accessCallback = new AccessRequestHandler() {
        @Override
        public BigIotHttpResponse processRequestHandler (OfferingDescription offeringDescription, Map<String,Object> inputData, String subscriberId, String consumerInfo) {

            BarcelonaChargingStationParser chargingStationParser = new BarcelonaChargingStationParser();
            BarcelonaAPIResults barcelonaAPIResults = chargingStationParser.parseInfo();
            List<BarcelonaAPIResults.Info> infos = barcelonaAPIResults.getResultParent().getChargepoint();

            // Prepare the offering response as a JSONObject/Array - according to the Output Data defined in the Offering Description
            JSONArray jsonArray = new JSONArray();
            ArrayList<String> ids = new ArrayList<>();
            for (BarcelonaAPIResults.Info info : infos) {
                String infoid = info.getParkingID() + "-" + info.getVehicle();
                if (!ids.contains(infoid)) {
                    int count = 0;
                    int countMenneke = 0;
                    int countSchuko = 0;
                    for (BarcelonaAPIResults.Info aux : infos) {
                        if (aux.getParkingID().equals(info.getParkingID()) && aux.getVehicle().equals(info.getVehicle())) {
                            count++;
                            if (Boolean.parseBoolean(aux.getMenneke().toString())) countMenneke++;
                            if (Boolean.parseBoolean(aux.getSchuko().toString())) countSchuko++;
                        }
                    }

                    boolean hasMeneke = Boolean.parseBoolean(info.getMenneke().toString());
                    boolean hasSchuko = Boolean.parseBoolean(info.getSchuko());

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("latitude", info.getLat());
                    jsonObject.put("longitude", info.getLng());
                    jsonObject.put("address", info.getAddress());
                    jsonObject.put("availableSpots", count);
                    jsonObject.put("availableMennekeSpots", countMenneke);
                    jsonObject.put("availableSchukoSpots", countSchuko);
                    jsonObject.put("spotType", info.getVehicle().equals("0")? "veh" : "moto");
                    //jsonObject.put("plugType", (hasMeneke && !hasSchuko)? "meneke" : (!hasMeneke && hasSchuko)? "schuko": "both");

                    ids.add(infoid);

                    jsonArray.put(jsonObject);
                }
            }

            // Send the response as JSON in the form: { [ { "value" : 0.XXX, "timestamp" : YYYYYYY } ] }
            return BigIotHttpResponse.okay().withBody(jsonArray).asJsonType();
        }
    };
}
