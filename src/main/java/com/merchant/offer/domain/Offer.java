package com.merchant.offer.domain;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)

public class Offer {

    String  offerId;
    String  offerName;
    String  offerDescription;
    String  offerCurrency;
    String  comment;
    int     offerDuration;
    String  timeTillExpiration;
    Boolean offerExpired;

    public Date getExpiredOn() {
        return expiredOn;
    }

    public void setExpiredOn(Date expiredOn) {
        this.expiredOn = expiredOn;
    }

    Date    expiredOn;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getOfferCurrency() {
        return offerCurrency;
    }

    public void setOfferCurrency(String offerCurrency) {
        this.offerCurrency = offerCurrency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getOfferDuration() {
        return offerDuration;
    }

    public void setOfferDuration(int offerDuration) {
        this.offerDuration = offerDuration;
    }

    public String getTimeTillExpiration() {
        return timeTillExpiration;
    }

    public void setTimeTillExpiration(String timeTillExpiration) {
        this.timeTillExpiration = timeTillExpiration;
    }

    public Boolean getOfferExpired() {
        return offerExpired;
    }

    public void setOfferExpired(Boolean offerExpired) {
        this.offerExpired = offerExpired;
    }


}
