package com.merchant.offer.service;


import com.merchant.offer.domain.Offer;
import com.merchant.util.OfferUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class OfferService {

    Map<String, Offer> offersStore = new HashMap<>();

    public Offer getOffer(String offerId) {
        return offersStore.get(offerId);
    }

    public List<Offer> getAllOffers() {
        return new ArrayList<>(offersStore.values());
    }


    public void removeOffer(String offerId) {
        offersStore.remove(offerId);
    }

    public Offer addOffer(Offer offer) {

        Offer offertoBeCreated = offer;
        Date offerExpiredOn;
        String offerId = Optional.ofNullable(offertoBeCreated.getOfferId()).orElse(UUID.randomUUID().toString());
        offertoBeCreated.setOfferId(offerId);
        offertoBeCreated.setOfferExpired(false);
        offertoBeCreated.setOfferCreatedOn(OfferUtils.dateFormat(new Date()));
        int duration = Optional.of(offertoBeCreated.getOfferDuration()).orElse(0);
        if (duration == 0) {
            offerExpiredOn = Date.from(LocalDateTime.now().plusDays(90).atZone(ZoneId.systemDefault()).toInstant());
            offertoBeCreated.setOfferDuration(90);
        } else
            offerExpiredOn = Date.from(LocalDateTime.now().plusDays(duration).atZone(ZoneId.systemDefault()).toInstant());
        offertoBeCreated.setOfferExpirationDate(OfferUtils.dateFormat(offerExpiredOn));
        offertoBeCreated.setComment("");
        offersStore.put(offerId, offertoBeCreated);
        return offertoBeCreated;
    }

    public void removeAllOffers() {
        offersStore.clear();
    }

    public Offer cancelOffer(String offerId) {
        Offer offertoBeCancelled = offersStore.get(offerId);
        if (offertoBeCancelled == null)
            return null;
        offertoBeCancelled.setOfferExpired(true);
        offertoBeCancelled.setComment("This offer has expired");
        offertoBeCancelled.setOfferExpirationDate(OfferUtils.dateFormat(new Date()));
        offertoBeCancelled.setOfferDuration(0);
        offersStore.put(offerId, offertoBeCancelled);
        return offertoBeCancelled;
    }

}
