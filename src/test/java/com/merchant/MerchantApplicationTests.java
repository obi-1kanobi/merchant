package com.merchant;



import org.junit.Test;
import org.junit.runner.RunWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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



	@Test
	public void getAllOffers () throws  Exception{

		mockMvc.perform(
				get("/merchant/offers").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.offerList", hasSize(2)));

	}

	@Test
	public void getOffer () throws  Exception{

		mockMvc.perform(
				get("/merchant/offers/"+ "49b16a83f2f2").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.offerName", is("3 Year Interest Free")));

	}

	@Test
	public void createOffer () throws  Exception{

		String offerId = UUID.randomUUID().toString();

		String json_data = "{\n"+
				"  \"offerId\": \"" + offerId + "\",\n"+
				"  \"offerName\": \"3 Year Interest Free\",\n"+
				"  \"offerDescription\": \"Get Interest free for five years\",\n"+
				"  \"offerCurrency\": \"EUR\",\n"+
				"  \"offerExpiration\": 10,\n"+
				"  \"offerExpired\": false\n"+
				"}";

		mockMvc.perform(
				post("/merchant/offers")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json_data)
		)
				.andExpect(status().isNoContent());


		mockMvc.perform(
				get("/merchant/offers/"+ offerId ).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.offerName", is("3 Year Interest Free")))
				.andExpect(jsonPath("$.offerId", is(offerId)));

	}

	@Test
	public void cancelOffer () throws  Exception{

		String offerId = UUID.randomUUID().toString();

		String json_data = "{\n"+
				"  \"offerId\": \"" + offerId + "\",\n"+
				"  \"offerName\": \"3 Year Interest Free\",\n"+
				"  \"offerDescription\": \"Get Interest free for five years\",\n"+
				"  \"offerCurrency\": \"EUR\",\n"+
				"  \"offerExpiration\": 10,\n"+
				"  \"offerExpired\": false\n"+
				"}";


		mockMvc.perform(
				get("/merchant/offers/"+ offerId ).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.offerName", is("3 Year Interest Free")))
				.andExpect(jsonPath("$.offerExpired", is(false)));

		mockMvc.perform(
				put("/merchant/offers/"+ offerId)
						.contentType(MediaType.APPLICATION_JSON)
		)
				.andExpect(status().isNoContent());


		mockMvc.perform(
				get("/merchant/offers/"+ offerId ).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.offerName", is("3 Year Interest Free")))
				.andExpect(jsonPath("$.offerExpired", is(true)));

	}









}
