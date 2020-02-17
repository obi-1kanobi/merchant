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

}
