package org.bigiot.examples.brussels;

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

public class BrusselsOffering {
    private RegistrableOfferingDescription offeringDescription;
    private Endpoints endpoints;

    public RegistrableOfferingDescription createOfferingDescription() {
        this.offeringDescription =
                OfferingDescription.createOfferingDescription("BrusselsChargingStations")
                        .withName("Brussels Charging Stations")
                        .withCategory("urn:big-iot:ChargingStationCategory")
                        .addInputData("latitude", "schema:latitude", BigIotTypes.ValueType.NUMBER)
                        .addInputData("longitude", "schema:longitude", BigIotTypes.ValueType.NUMBER)
                        .addInputData("radius", "schema:geoRadius", BigIotTypes.ValueType.NUMBER)
                        .addOutputData("latitude", "schema:latitude", BigIotTypes.ValueType.NUMBER)
                        .addOutputData("longitude", "schema:longitude", BigIotTypes.ValueType.NUMBER)
                        .addOutputData("freeDispenserCount", "mobility:chargingNumberOfVacantStations", BigIotTypes.ValueType.NUMBER)
                        .addOutputData("countMenneke", "mobility:chargingNumberOfVacantStations", BigIotTypes.ValueType.NUMBER)
                        .addOutputData("countSchuko", "mobility:chargingNumberOfVacantStations", BigIotTypes.ValueType.NUMBER)
                        .addOutputData("placeType", "mobility:chargingStationConnectorType", BigIotTypes.ValueType.TEXT)
                        .addOutputData("plugType", "mobility:chargingStationConnectorType", BigIotTypes.ValueType.TEXT)
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
            List<BarcelonaAPIResults.Feature> infos = barcelonaAPIResults.getFeatures();

            // Prepare the offering response as a JSONObject/Array - according to the Output Data defined in the Offering Description
            JSONArray jsonArray = new JSONArray();
            ArrayList<String> ids = new ArrayList<>();
            for (BarcelonaAPIResults.Feature info : infos) {
                String infoid = info.getId() + "-" + info.getProperties().getType();

                int countMenneke = 0;
                int countSchuko = 0;

                boolean hasMeneke = (info.getProperties().getPlug().equals("Type 2"));
                boolean hasSchuko =(info.getProperties().getPlug().equals("Type 1"));

                if(hasMeneke){
                    countMenneke = info.getProperties().getNumber();
                }
                else{
                    countSchuko = info.getProperties().getNumber();
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("latitude", info.getGeometry().getCoordinates().get(0));
                jsonObject.put("longitude", info.getGeometry().getCoordinates().get(1));
                jsonObject.put("freeDispenserCount", info.getProperties().getNumber());
                jsonObject.put("mennekeCount", countMenneke);
                jsonObject.put("schukoCount", countSchuko);
                jsonObject.put("placeType", "veh");
                jsonObject.put("plugType", (hasMeneke && !hasSchuko)? "meneke" : "schuko");

                ids.add(infoid);

                jsonArray.put(jsonObject);

                }
      //      }

                    // Send the response as JSON in the form: { [ { "value" : 0.XXX, "timestamp" : YYYYYYY } ] }
            return BigIotHttpResponse.okay().withBody(jsonArray).asJsonType();
        }
    };
}
