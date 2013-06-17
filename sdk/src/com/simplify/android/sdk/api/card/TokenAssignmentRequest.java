package com.simplify.android.sdk.api.card;

import com.google.gson.JsonSyntaxException;
import com.simplify.android.sdk.api.ApiConstants;
import com.simplify.android.sdk.api.AsyncApiRequest;
import com.simplify.android.sdk.api.UrlBuilder;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Used by a <code>Card</code> to makes an asynchronous call to the <strong>Simplify.com</strong>
 * API to request a <code>CardToken</code>, returning it to application code via a listener interface.
 *
 * @see com.simplify.android.sdk.api.card.Card
 * @see com.simplify.android.sdk.api.card.CardToken
 */
public class TokenAssignmentRequest extends AsyncApiRequest<Card, CardToken, TokenAssignmentListener> {

    public TokenAssignmentRequest(TokenAssignmentListener listener) {
        super(listener);
    }

    @Override
    protected CardToken callServer(Card... params) throws HttpResponseException {
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(new UrlBuilder(ApiConstants.URL_BASE)
                    .addPath("/payment/cardToken")
                    .addParam("key", ApiConstants.API_KEY)
                    .addParam("card.number", params[0].getNumber())
                    .addParam("card.cvc", params[0].getCvc())
                    .addParam("card.expMonth", "" + params[0].getExpMonth())
                    .addParam("card.expYear", "" + params[0].getExpYear())
                    .addOptionalParam("card.addressCity", "" + params[0].getAddressCity())
                    .addOptionalParam("card.addressCountry", "" + params[0].getAddressCountry())
                    .addOptionalParam("card.addressLine1", "" + params[0].getAddressLine1())
                    .addOptionalParam("card.addressLine2", "" + params[0].getAddressLine2())
                    .addOptionalParam("card.addressState", "" + params[0].getAddressState())
                    .addOptionalParam("card.addressZip", "" + params[0].getAddressCity())
                    .addOptionalParam("card.name", "" + params[0].getName())
                    .build());
            String responseBody = httpclient.execute(httpget, new BasicResponseHandler());
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
