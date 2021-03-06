package org.bigiot.examples.brussels;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class BarcelonaChargingStationParser {
    public BarcelonaAPIResults parseInfo() {
        String google = "http://data-mobility.irisnet.be/geoserver/bm_equipment/wfs?service=wfs&version=1.1.0&request=GetFeature&typeName=bm_equipment:charging_stations&outputFormat=json&srsName=EPSG:4326";
        String charset = "UTF-8";

        URL url = null;
        BarcelonaAPIResults results = null;

        try {
            url = new URL(google);
            Reader reader = new InputStreamReader(url.openStream(), charset);

            int intValueOfChar;
            String targetString = "";
            while ((intValueOfChar = reader.read()) != -1) {
                targetString += (char) intValueOfChar;
            }
            System.out.println(targetString);

            results = new Gson().fromJson(targetString, BarcelonaAPIResults.class);
            System.out.println(results.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }
}
