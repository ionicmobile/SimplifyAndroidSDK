/*
 * Copyright (c) 2013, Asynchrony Solutions, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     * Neither the name of Asynchrony nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ASYNCHRONY SOLUTIONS, INC. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.simplify.android.sdk.api.card;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.simplify.android.sdk.api.ApiConstants;
import com.simplify.android.sdk.api.AsyncApiRequest;
import com.simplify.android.sdk.api.UrlBuilder;

/**
 * Used by a <code>Card</code> to makes an asynchronous call to the <strong>Simplify.com</strong>
 * API to request a <code>CardToken</code>, returning it to application code via a listener interface.
 *
 * @see com.simplify.android.sdk.api.card.Card
 * @see com.simplify.android.sdk.api.card.CardToken
 */
class TokenAssignmentRequest extends AsyncApiRequest<Card, CardToken, TokenAssignmentListener> {

    public TokenAssignmentRequest(TokenAssignmentListener listener) {
        super(listener);
    }

    @Override
    protected CardToken callServer(Card... params) throws HttpResponseException {
        HttpClient httpclient = new DefaultHttpClient();
        try {
        	HttpPost httpPost = new HttpPost(ApiConstants.URL_BASE);
        	httpPost.setHeader("Accept","application/json");
        	httpPost.setHeader("Content-type","application-json");
        	
//    		Map<String, Object> requiredParameters = new HashMap<String, Object>();
//    		requiredParameters.put("key", ApiConstants.API_KEY);
//    		
//    		Map<String, String> cardMap = new HashMap<String, String>();
//    		Card card = params[0];
//    		cardMap.put("card.number", card.getNumber());
//    		cardMap.put("card.cvc", card.getCvc());
//    		cardMap.put("card.expMonth", card.getExpMonth()+"");
//    		cardMap.put("card.expYear", card.getExpYear()+"");
//    		cardMap.put("card.address", card.getExpYear()+"");
//
//    		if (params[0].getAddressCity()) {
//    			cardMap.put("card.addressCity", params[0].getAddressCity());
//    		}
//          .addOptionalParam("card.addressCountry", params[0].getAddressCountry())
//          .addOptionalParam("card.addressLine1", params[0].getAddressLine1())
//          .addOptionalParam("card.addressLine2", params[0].getAddressLine2())
//          .addOptionalParam("card.addressState", params[0].getAddressState())
//          .addOptionalParam("card.addressZip", params[0].getAddressZip())
//          .addOptionalParam("card.name", "" + params[0].getName())
    		
    		
//    		requiredParameters.put("card", cardMap);     
    		
    		Gson gson = new Gson();
    		httpPost.setEntity(new StringEntity(gson.toJson(params[0])));
    		
        	
//            HttpGet httpget = new HttpGet(new UrlBuilder(ApiConstants.URL_BASE)
//                    .addPath("/payment/cardToken")
//                    .addParam("key", ApiConstants.API_KEY)
//                    .addParam("card.number", params[0].getNumber())
//                    .addParam("card.cvc", params[0].getCvc())
//                    .addParam("card.expMonth", "" + params[0].getExpMonth())
//                    .addParam("card.expYear", "" + params[0].getExpYear())
//                    .addOptionalParam("card.addressCity", params[0].getAddressCity())
//                    .addOptionalParam("card.addressCountry", params[0].getAddressCountry())
//                    .addOptionalParam("card.addressLine1", params[0].getAddressLine1())
//                    .addOptionalParam("card.addressLine2", params[0].getAddressLine2())
//                    .addOptionalParam("card.addressState", params[0].getAddressState())
//                    .addOptionalParam("card.addressZip", params[0].getAddressZip())
//                    .addOptionalParam("card.name", "" + params[0].getName())
//                    .build());
//            String responseBody = httpclient.execute(httpget, new BasicResponseHandler());
            String responseBody = httpclient.execute(httpPost, new BasicResponseHandler());
            return gson.fromJson(responseBody, CardToken.class);
            
        } catch (IOException e) {
            return null;
        } catch (JsonSyntaxException e) {
            return null;
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    @Override
    protected void notifyDataReceived(TokenAssignmentListener listener, CardToken returnData) {
        listener.tokenAssigned(returnData);
    }

}
