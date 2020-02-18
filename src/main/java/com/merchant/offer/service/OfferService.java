package com.merchant.offer.service;


import com.merchant.offer.domain.Offer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class OfferService {

    Map<String, Offer> offersStore = new HashMap<String,Offer>();

    public Offer getOffer(String offerId){

        return offersStore.get(offerId);
    }

    public List<Offer> getAllOffers(){

        return new ArrayList<Offer>(offersStore.values());
    }


    public void removeOffer(String offerId){

        offersStore.remove(offerId);
    }

    public Offer addOffer(Offer offer){

        Offer c = offer;
        String offerId = Optional.ofNullable(c.getOfferId()).orElse(UUID.randomUUID().toString());
        offer.setOfferId(offerId);
        offer.setOfferExpired(false);
        Date offerExpiredOn = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        offer.setExpiredOn( offerExpiredOn );
        offersStore.put(offerId,c);
        return c;
    }

    public void removeAllOffers(){
        offersStore.clear();
    }

    public Offer cancelOffer(String offerId){
        Offer c = offersStore.get(offerId);
        c.setOfferExpired(true);
        offersStore.put(offerId,c);
        return c;
    }

}
