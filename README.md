# Glossary

* Offer - A rest service to create, query, update and remove an offer.

* Assumptions

When creating an offer, if no offer duration is included in request, offer is set to expire in 90days

Offer can only be created with any of the following currency denominations EUR, GBP and USD

**Sample Request for service:**

Create an offer

POST /merchant/offers HTTP/1.1
Accept: application/json
Content-Type: application/json

```json
{
	"offerName" :"3yrs interest free",
	"offerDescription" : "This is a three year interest free offer",
	"offerCurrency": "EUR",
	"offerDuration" : 5
}```

Body without offer duration
```json
{
	"offerName" :"1yrs interest free",
	"offerDescription" : "This is a one year interest free offer",
	"offerCurrency": "EUR"
}```

Retrieve all offer

GET /merchant/offers HTTP/1.1
Host: localhost:8080
Accept: application/json


Cancel an offer
PUT /merchant/offers HTTP/1.1
Content-Type: application/json

```json
{ "offerId" : "cc8d79b1-daaf-4870-85c2-592521e02f21"}```


Remove an offer
DELETE /merchant/offers/cc8d79b1-daaf-4870-85c2-592521e02f21 HTTP/1.1


Retrieve an offer
GET /merchant/offers/bdc2c64f-5b32-4f04-a421-c774922f819f HTTP/1.1
Accept: application/json

# Build project

Within root directory `merchant`
Run to test: `mvn test`
Run to package:  `mvn package`


# Run locally

* Open project in your favorite Java IDE and run the MerchantOffersApplication

or

* From Terminal and assuming you package as described above
```
java -jar <path to jar >/merchant-0.0.1-SNAPSHOT.jar
```
