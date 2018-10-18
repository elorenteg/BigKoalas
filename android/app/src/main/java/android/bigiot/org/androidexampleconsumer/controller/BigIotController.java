package android.bigiot.org.androidexampleconsumer.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.bigiot.lib.android.Consumer;
import org.eclipse.bigiot.lib.android.IAuthenticationHandler;
import org.eclipse.bigiot.lib.android.IDiscoveryHandler;
import org.eclipse.bigiot.lib.android.IResponseHandler;
import org.eclipse.bigiot.lib.android.ISubscriptionHandler;
import org.eclipse.bigiot.lib.exceptions.IncompleteOfferingQueryException;
import org.eclipse.bigiot.lib.model.BigIotTypes;
import org.eclipse.bigiot.lib.model.Price;
import org.eclipse.bigiot.lib.model.RDFType;
import org.eclipse.bigiot.lib.model.ValueType;
import org.eclipse.bigiot.lib.offering.AccessParameters;
import org.eclipse.bigiot.lib.offering.AccessResponse;
import org.eclipse.bigiot.lib.offering.OfferingCore;
import org.eclipse.bigiot.lib.offering.OfferingDescription;
import org.eclipse.bigiot.lib.query.OfferingQuery;

import java.util.List;
import java.util.Random;

public class BigIotController implements IDiscoveryHandler, IResponseHandler, ISubscriptionHandler, IAuthenticationHandler {
    private static final String TAG = BigIotController.class.getSimpleName();
    private static final String MARKETPLACE_URI = "https://market.big-iot.org";
    private static final String CONSUMER_ID = "LaQuay-KoalaConsumer";
    private static final String CONSUMER_SECRET = "zzicf5Q3TzqgRYwbYWIh-Q==";
    private static BigIotController instance;
    private Context context;
    private Consumer consumer = null;
    private OfferingCore offering = null;

    private BigIotController(Context ctx) {
        this.context = ctx;
        consumer = new Consumer(CONSUMER_ID, MARKETPLACE_URI);
        consumer.authenticateByTask(CONSUMER_SECRET, this);
    }

    public static BigIotController getInstance(Context ctx) {
        if (instance == null) {
            createInstance(ctx);
        }
        return instance;
    }

    private synchronized static void createInstance(Context ctx) {
        if (instance == null) {
            instance = new BigIotController(ctx);
        }
    }

    public void accessOffering() throws Exception {
        try {
            Random r = new Random();
            int i1 = r.nextInt(100000);
            OfferingQuery query = Consumer.createOfferingQuery("CHARGING_ID" + i1)
                    .withCategory("urn:big-iot:ParkingSpaceCategory")
                    //.addOutputData(new RDFType("schema:longitude"), ValueType.NUMBER)
                    //.addOutputData(new RDFType("schema:latitude"), ValueType.NUMBER)
                    //.addOutputData(new RDFType("schema:geoRadius"), ValueType.NUMBER)
                    //.inRegion("Berlin")
                    .withPricingModel(BigIotTypes.PricingModel.PER_ACCESS);
                    //.withMaxPrice(Price.Euros.amount(1.0))
                    //.withLicenseType(BigIotTypes.LicenseType.OPEN_DATA_LICENSE);

            consumer.discoverByTask(query, this);
        } catch (IncompleteOfferingQueryException e) {
            Log.e(TAG, "ERROR: Offering Query invalid - " + e.toString());
        }
    }

    @Override
    public void onAuthenticate(String result) {
        if (result.equals(IAuthenticationHandler.AUTHENTICATION_OK)) {
            Log.e(TAG, "Authentication Successful");
        } else {
            Log.e(TAG, "ERROR: Authentication on Marketplace failed - check secret");
        }
    }

    @Override
    public void onDiscoveryResponse(OfferingQuery query, List<OfferingDescription> offeringDescriptions) {
        boolean offeringQueryIsNotEmpty = (offeringDescriptions != null) && !offeringDescriptions.isEmpty();
        if (offeringQueryIsNotEmpty) {
            for (int i = 0; i < offeringDescriptions.size(); ++i) {
                OfferingDescription selectedOfferingDescription = offeringDescriptions.get(i);
                Log.e(TAG, selectedOfferingDescription.getId());
                if (selectedOfferingDescription.getId().equals("Thingful-Thingful-torino_parking_total_capacity")) {
                    consumer.subscribeByTask(selectedOfferingDescription, this);
                    Toast.makeText(context, "Offering found: " + selectedOfferingDescription.getId(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Offering found: " + selectedOfferingDescription.getId());
                }
            }
        }
        else {
            Log.e(TAG, "No Offerings");
            Toast.makeText(context, "No Offerings found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSubscriptionResponse(OfferingDescription offeringDescription, OfferingCore offering) {
        if (offering != null) {
            this.offering = offering;
            Log.e(TAG, "Subscription successful");
            Toast.makeText(context, "Subscription successful", Toast.LENGTH_SHORT).show();

            AccessParameters accessParameters = AccessParameters.create();
            consumer.accessByTask(this.offering, accessParameters, this);
        } else {
            Log.e(TAG, "Subscription failed");
            Toast.makeText(context, "Subscription failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAccessResponse(OfferingCore offeringCore, AccessResponse accessResponse) {
        if (accessResponse != null) {
            Log.e(TAG, "Access response " + accessResponse.getBody());
        }
    }
}
