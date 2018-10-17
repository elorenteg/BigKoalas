    /*
     *	Copyright (c) 2017 by Contributors of the BIG IoT Project Consortium (see below).
     *	All rights reserved.
     *
     *	This source code is licensed under the MIT license found in the
     * 	LICENSE file in the root directory of this source tree.
     *
     *	Contributor:
     *	- Robert Bosch GmbH
     *	    > Stefan Schmid (stefan.schmid@bosch.com)
     */
    package org.bigiot.examples;

    import java.io.IOException;
    import java.util.*;

    import org.bigiot.examples.barcelona.BarcelonaOffering;
    import org.eclipse.bigiot.lib.ProviderSpark;
    import org.eclipse.bigiot.lib.exceptions.IncompleteOfferingDescriptionException;
    import org.eclipse.bigiot.lib.exceptions.NotRegisteredException;
    import org.eclipse.bigiot.lib.misc.BridgeIotProperties;
    import org.eclipse.bigiot.lib.offering.Endpoints;
    import org.eclipse.bigiot.lib.offering.RegistrableOfferingDescription;

    /**
     * Example for using BIG IoT API as a Provider. This example corresponds with Example Consumer project *
     */
    public class ExampleProvider {

        private static Random rand = new Random();

        public static void main(String args[]) throws InterruptedException, IncompleteOfferingDescriptionException, IOException, NotRegisteredException {

            // Load example properties file
            BridgeIotProperties prop = BridgeIotProperties.load("example.properties");

            // Initialize a provider with Provider ID and Marketplace URI, the local IP/DNS, etc., and authenticate it on the Marketplace
            ProviderSpark provider = ProviderSpark.create(prop.PROVIDER_ID, prop.MARKETPLACE_URI, prop.PROVIDER_DNS_NAME, prop.PROVIDER_PORT)
                                            .authenticate(prop.PROVIDER_SECRET);

            // provider.setProxy(prop.PROXY, prop.PROXY_PORT); //Enable this line if you are behind a proxy
            // provider.addProxyBypass(prop.PROXY_BYPASS); //Enable this line and the addresses for internal hosts

            // Barcelona offering
            BarcelonaOffering barcelonaOffering = new BarcelonaOffering();
            RegistrableOfferingDescription barcerlonaOfferingDescription = barcelonaOffering.createOfferingDescription();
            Endpoints barcerlonaOfferingEndpoint = barcelonaOffering.createEndpoint();

            // Register offerings
            provider.register(barcerlonaOfferingDescription, barcerlonaOfferingEndpoint);

            // Run until user presses the ENTER key
            System.out.println(">>>>>>  Terminate ExampleProvider by pressing ENTER  <<<<<<");
            Scanner keyboard = new Scanner(System.in);
            keyboard.nextLine();

            System.out.println("Deregister Offering");

            // Deregister the Offering from the Marketplace
            provider.deregister(barcerlonaOfferingDescription);

            // Terminate the Provider instance
            provider.terminate();

        }

    }

