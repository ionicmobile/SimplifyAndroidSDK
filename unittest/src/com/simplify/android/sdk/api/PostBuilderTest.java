package com.simplify.android.sdk.api;

import junit.framework.TestCase;

import com.simplify.android.sdk.api.card.Card;

public class PostBuilderTest extends TestCase {
	
	String cardNumber = "4111111111111111"; 
	String cvc = "testCvc";
	int expirationMonth = 1;
	int expirationYear = 2;
	Card actualCard;
	PostBuilder testObject;
	
	public void setUp() {
		actualCard = new Card(cardNumber, cvc, expirationMonth ,expirationYear);
		testObject = new PostBuilder();
	}
	
	public void testThatRequiredParametersAreReturnedAsValidJson() {
		String expectedJson = "{\"key\":\""+ApiConstants.API_KEY+"\",\"card\":{\"number\":\""+cardNumber+"\",\"cvc\":\""+cvc+"\",\"expMonth\":\""+expirationMonth+"\",\"expYear\":\""+expirationYear+"\"}}";

		String actual = testObject.toJson(actualCard);
		
		//TODO: remove printlns
		System.out.println("Actual: " + actual);
		System.out.println("Expected: " + expectedJson);

		assertEquals(expectedJson, actual);
	}
	
	public void testOptionalParamsDontAddEmptyJson() {
		String addressCity = "testCity";
		actualCard.setAddressCity(addressCity);
		String addressCountry = "testCountry";
		actualCard.setAddressCountry(addressCountry);
		String addressLine1 = "addressLine1";
		actualCard.setAddressLine1(addressLine1);
		String addressLine2 = "addressLine2";
		actualCard.setAddressLine2(addressLine2);
		String state = "state";
		actualCard.setAddressState(state);
		String zip = "zip";
		actualCard.setAddressZip(zip);
		String name = "name";
		actualCard.setName(name);

		String actual = testObject.toJson(actualCard);
		
		assertTrue(actual.contains(addressCity));
		assertTrue(actual.contains(addressCountry));		
		assertTrue(actual.contains(addressLine1));
		assertTrue(actual.contains(addressLine2));
		assertTrue(actual.contains(state));
		assertTrue(actual.contains(zip));
		assertTrue(actual.contains(name));		
	}

}
