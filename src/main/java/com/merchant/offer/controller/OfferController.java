package com.merchant.offer.controller;


import com.merchant.offer.domain.Offer;
import com.merchant.offer.domain.Offers;
import com.merchant.offer.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("merchant/offers")
public class OfferController  {

    @Autowired
    OfferService offerService;

    @RequestMapping( method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Offers> getOffers() {

        Offers offers = new Offers();
        offers.setOfferList(offerService.getAllOffers());

        return new ResponseEntity<>(offers, HttpStatus.OK);

    }

    @RequestMapping( value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Offer> getOffer(@PathVariable("id") String id){

        Offer offer = offerService.getOffer(id);

        if (offer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(offer,HttpStatus.OK);
    }


    @RequestMapping(  method = RequestMethod.POST, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Offer> addOffer(@Valid @RequestBody Offer offer){

        Offer offerAdded = offerService.addOffer(offer);

        //computerAdded.getLinks().add(linkTo(methodOn(ComputerController.class).getComputer(computerAdded.getComputerId())).withSelfRel());

        return new ResponseEntity<>(offerAdded,HttpStatus.NO_CONTENT);
    }

    @RequestMapping( method = RequestMethod.PUT)
    public ResponseEntity<Offer> cancelOffer(){
        return new ResponseEntity<>(new Offer(),HttpStatus.NO_CONTENT);
    }


}
