# BigKoalas

Inside there is an Android folder with demonstrate the **BigIot consumer in Android**. Also Mapbox is fully implemented.

Also, you can run the server for the **bigiot provider** just cloning the repository and doing a 'make run'. In the docker-compose you have to add your credentials and your IP.

### Provider
Category: Charging Station urn:big-iot:ChargingStationCategory

#### Output Data
latitude - http://schema.org/latitude

longitude - http://schema.org/longitude

streetAddress - http://schema.org/streetAddress

mobility:chargingNumberOfVacantStations - mobility:chargingNumberOfVacantStations

mobility:chargingNumberOfVacantStations - mobility:chargingNumberOfVacantStations

mobility:chargingNumberOfVacantStations - mobility:chargingNumberOfVacantStations

mobility:chargingStationConnectorType - mobility:chargingStationConnectorType

#### Example
``` json
[
    {
        "availableMennekeSpots": 3,
        "availableSpots": 3,
        "address": "C/ Castillejos, 328",
        "latitude": 41.40974,
        "spotType": "veh",
        "availableSchukoSpots": 3,
        "longitude": 2.174087
    },
    {
        "availableMennekeSpots": 5,
        "availableSpots": 5,
        "address": "C/ Badajoz, 168",
        "latitude": 41.40287,
        "spotType": "moto",
        "availableSchukoSpots": 5,
        "longitude": 2.189897
    }
...
]
```
