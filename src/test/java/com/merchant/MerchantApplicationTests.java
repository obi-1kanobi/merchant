package com.merchant;


import com.merchant.offer.domain.Offer;
import com.merchant.offer.service.OfferService;
import com.merchant.util.OfferUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MerchantApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    OfferService offerService;

    String offer3Id = "49b16a83f2f2";

    @Before
    public void setup() {

        Offer offer = new Offer();
        offer.setOfferId(UUID.randomUUID().toString());
        offer.setOfferExpired(false);

        offerService.addOffer(offer);

        Offer offer2 = new Offer();
        offer2.setOfferId(UUID.randomUUID().toString());
        offer2.setOfferExpired(false);

        offerService.addOffer(offer2);

        Offer offer3 = new Offer();
        offer3.setOfferId(offer3Id);
        offer3.setOfferExpired(false);
        offer3.setOfferName("3 Year Interest Free");

        offerService.addOffer(offer3);

    }

    @After
    public void tearDown() {
        offerService.removeAllOffers();
    }


    @Test
    public void getAllOffers() throws Exception {

        mockMvc.perform(
                get("/merchant/offers").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(3)));

    }

    @Test
    public void getAnOffer() throws Exception {

        mockMvc.perform(
                get("/merchant/offers/" + offer3Id).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.offerName", is("3 Year Interest Free")));

    }

    @Test
    public void createAnOffer() throws Exception {

        String offerId = UUID.randomUUID().toString();

        String json_data = "{\n" +
                "  \"offerId\": \"" + offerId + "\",\n" +
                "  \"offerName\": \"3 Year Interest Free\",\n" +
                "  \"offerDescription\": \"Get Interest free for five years\",\n" +
                "  \"offerCurrency\": \"EUR\",\n" +
                "  \"offerExpiration\": 10,\n" +
                "  \"offerExpired\": false\n" +
                "}";

        mockMvc.perform(
                post("/merchant/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_data)
        )
                .andExpect(status().isNoContent());


        mockMvc.perform(
                get("/merchant/offers/" + offerId).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.offerName", is("3 Year Interest Free")))
                .andExpect(jsonPath("$.offerId", is(offerId)));

    }

    @Test
    public void cancelAnOffer() throws Exception {

        mockMvc.perform(
                get("/merchant/offers/" + offer3Id).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.offerName", is("3 Year Interest Free")))
                .andExpect(jsonPath("$.offerExpired", is(false)));


        String json_data = "{" + "\"offerId\":\"" + offer3Id + "\"" + "}";

        mockMvc.perform(
                put("/merchant/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_data)
        )
                .andExpect(status().isNoContent());

        mockMvc.perform(
                get("/merchant/offers/" + offer3Id).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.offerName", is("3 Year Interest Free")))
                .andExpect(jsonPath("$.offerExpired", is(true)))
                .andExpect(jsonPath("$.comment", is("This offer has expired")));

    }

    @Test
    public void offerExpiredIn3daysFromCreation() throws Exception {

        String offerId = UUID.randomUUID().toString();
        int offerExpirationInDays = 3;

        String json_data = "{\n" +
                "  \"offerId\": \"" + offerId + "\",\n" +
                "  \"offerName\": \"3 Year Interest Free\",\n" +
                "  \"offerDescription\": \"Get Interest free for five years\",\n" +
                "  \"offerCurrency\": \"EUR\",\n" +
                "  \"offerDuration\": \"" + offerExpirationInDays + "\",\n" +
                "  \"offerExpired\": false\n" +
                "}";

        mockMvc.perform(
                post("/merchant/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_data)
        )
                .andExpect(status().isNoContent());


        mockMvc.perform(
                get("/merchant/offers/" + offerId).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.offerName", is("3 Year Interest Free")))
                .andExpect(jsonPath("$.offerId", is(offerId)))
                .andExpect(jsonPath("$.offerExpirationDate", is(OfferUtils.dateFormat(Date.from(LocalDateTime.now().plusDays(offerExpirationInDays).atZone(ZoneId.systemDefault()).toInstant())))));
    }

    @Test
    public void deleteAnOffer() throws Exception {

        mockMvc.perform(
                get("/merchant/offers").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(3)));

        mockMvc.perform(
                delete("/merchant/offers/" + offer3Id))
                .andExpect(status().isNoContent());

        mockMvc.perform(
                get("/merchant/offers/").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(
                get("/merchant/offers/" + offer3Id).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());


    }

    @Test
    public void invalidOfferCurrency() throws Exception {

        String offerId = UUID.randomUUID().toString();

        String json_data = "{\n" +
                "  \"offerId\": \"" + offerId + "\",\n" +
                "  \"offerName\": \"3 Year Interest Free\",\n" +
                "  \"offerDescription\": \"Get Interest free for five years\",\n" +
                "  \"offerCurrency\": \"CHF\",\n" +
                "  \"offerExpiration\": 10,\n" +
                "  \"offerExpired\": false\n" +
                "}";

        mockMvc.perform(
                post("/merchant/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_data)
        )       .andExpect(status().isBadRequest());
    }




}
