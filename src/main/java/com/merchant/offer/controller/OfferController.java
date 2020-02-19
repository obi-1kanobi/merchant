package com.merchant.offer.controller;



import com.merchant.offer.domain.Offer;
import com.merchant.offer.service.OfferService;
import com.merchant.util.OfferUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("merchant/offers")
public class OfferController {

    @Autowired
    OfferService offerService;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Offer>> getOffers() {

        List<Offer> offers = offerService.getAllOffers();

        return new ResponseEntity<>(offers, HttpStatus.OK);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Offer> getOffer(@PathVariable("id") String id) {

        Offer offer = offerService.getOffer(id);

        if (offer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(offer, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Offer> addOffer(@Valid @RequestBody Offer offer) {

        String currency = Optional.ofNullable(offer.getOfferCurrency()).orElse("");

        if (currency == null || !OfferUtils.currencyValid(currency)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Offer offerAdded = offerService.addOffer(offer);

        return new ResponseEntity<>(offerAdded, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Offer> cancelOffer(@Valid @RequestBody Offer offer) {

        Offer offerCancelled = offerService.cancelOffer(offer.getOfferId());

        if (offerCancelled == null) {
            return new ResponseEntity<>(offerCancelled, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(offerCancelled, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Offer> deleteOffer(@PathVariable("id") String id) {

        Offer offer = offerService.getOffer(id);

        if (offer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        offerService.removeOffer(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
