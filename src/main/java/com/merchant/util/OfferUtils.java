package com.merchant.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.merchant.offer.domain.Currency;

public class OfferUtils {

    public static String dateFormat(Date date){
        String DATE_FORMAT = "yyyy-MM-dd HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String dateString = format.format(date);
        return dateString;
    }

    public static boolean currencyValid(String currencyDenomination) {

        for ( Currency cur : Currency.values()) {
            if (cur.name().equals(currencyDenomination)) {
                return true;
            }
        }

        return false;
    }

}