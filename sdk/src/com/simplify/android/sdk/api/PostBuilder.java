package com.simplify.android.sdk.api;

import java.util.LinkedHashMap;
import java.util.Map;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.simplify.android.sdk.api.card.Card;

public class PostBuilder {

    private Gson gson = new Gson();

	public String toJson(Card card) {
		
		Map<String, Object> parameters = new LinkedHashMap<String, Object>();
		parameters.put("key", ApiConstants.API_KEY);
		
		Map<String, String> cardMap = new LinkedHashMap<String, String>();
		cardMap.put("number", card.getNumber());
		cardMap.put("cvc", card.getCvc());
		cardMap.put("expMonth", card.getExpMonth()+"");
		cardMap.put("expYear", card.getExpYear()+"");
		
		// Optional parameters
        cardMap = putIfNotEmpty(cardMap, "addressCountry", card.getAddressCountry());
        cardMap = putIfNotEmpty(cardMap, "addressCity", card.getAddressCity());
        cardMap = putIfNotEmpty(cardMap, "addressLine1", card.getAddressLine1());
        cardMap = putIfNotEmpty(cardMap, "addressLine2", card.getAddressLine2());
        cardMap = putIfNotEmpty(cardMap, "addressState", card.getAddressState());
        cardMap = putIfNotEmpty(cardMap, "addressZip", card.getAddressZip());
        cardMap = putIfNotEmpty(cardMap, "name", card.getName());

		parameters.put("card", cardMap);

		return gson.toJson(parameters);
	}

    private Map<String, String> putIfNotEmpty(Map<String, String> map, String jsonField, String cardField) {
        if (!TextUtils.isEmpty(cardField)) {
            map.put(jsonField, cardField);
        }
        return map;
    }

}
