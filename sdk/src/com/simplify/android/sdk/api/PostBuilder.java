package com.simplify.android.sdk.api;

import java.util.LinkedHashMap;
import java.util.Map;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.simplify.android.sdk.api.card.Card;

public class PostBuilder {
	
	public String toJson(Card card) {
		
		Map<String, Object> parameters = new LinkedHashMap<String, Object>();
		parameters.put("key", ApiConstants.API_KEY);
		
		Map<String, String> cardMap = new LinkedHashMap<String, String>();
		cardMap.put("number", card.getNumber());
		cardMap.put("cvc", card.getCvc());
		cardMap.put("expMonth", card.getExpMonth()+"");
		cardMap.put("expYear", card.getExpYear()+"");
		
		// Optional parameters
		if (!TextUtils.isEmpty(card.getAddressCountry())) {
			cardMap.put("addressCountry", card.getAddressCountry());	
		}
		if (!TextUtils.isEmpty(card.getAddressCity())) {
			cardMap.put("addressCity", card.getAddressCity());
		}
		if (!TextUtils.isEmpty(card.getAddressLine1())) {
			cardMap.put("addressLine1", card.getAddressLine1());
		}
		if (!TextUtils.isEmpty(card.getAddressLine2())) {
			cardMap.put("addressLine2", card.getAddressLine2());
		}
		if (!TextUtils.isEmpty(card.getAddressState())) {
			cardMap.put("addressState", card.getAddressState());
		}
		if (!TextUtils.isEmpty(card.getAddressZip())) {
			cardMap.put("addressZip", card.getAddressZip());
		}
		if (!TextUtils.isEmpty(card.getName())) {
			cardMap.put("name", card.getName());
		}
		
		parameters.put("card", cardMap);
		
		Gson gson = new Gson();
		return gson.toJson(parameters);
	}

}
