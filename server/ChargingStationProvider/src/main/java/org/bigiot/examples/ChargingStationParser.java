package org.bigiot.examples;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class ChargingStationParser {
    public GoogleResults parseInfo() {
        String google = "https://api.bsmsa.eu/ext/api/bsm/chargepoints/v1/chargepoints";
        String charset = "UTF-8";

        URL url = null;
        GoogleResults results = null;

        try {
            url = new URL(google);
            Reader reader = new InputStreamReader(url.openStream(), charset);

            int intValueOfChar;
            String targetString = "";
            while ((intValueOfChar = reader.read()) != -1) {
                targetString += (char) intValueOfChar;
            }
            System.out.println(targetString);

            results = new Gson().fromJson(targetString, GoogleResults.class);
            System.out.println(results);
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
