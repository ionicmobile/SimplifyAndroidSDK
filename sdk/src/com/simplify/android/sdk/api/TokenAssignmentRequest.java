package com.simplify.android.sdk.api;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: paul.hawke
 * Date: 6/13/13
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class TokenAssignmentRequest extends AsyncTask<Card, Void, CardToken> {
    public static final String API_KEY = "sbpb_OTY1YmI4N2UtYTJiOS00ZWUzLTliMGItZTFmYzQ2OTRmYmQ3";
    public static final String URL_BASE = "https://sandbox.simplify.com/v1/api";
    private TokenAssignmentListener listener;
    private int statusCode;
    private String message;
    private final Gson gson;

    public TokenAssignmentRequest(TokenAssignmentListener listener) {
        this.listener = listener;
        this.gson = new Gson();
    }

    @Override
    protected CardToken doInBackground(Card... params) {
        CardToken token = null;
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(URL_BASE + "/payment/cardToken?" +
                    "key=" + API_KEY +
                    "&card.number=" + params[0].getNumber() +
                    "&card.cvc=" + params[0].getCvv() +
                    "&card.expMonth=" + params[0].getExpirationMonth() +
                    "&card.expYear=" + params[0].getExpirationYear());
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpget, responseHandler);
            token = gson.fromJson(responseBody, CardToken.class);
        } catch (HttpResponseException e) {
            statusCode = e.getStatusCode();
            message = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return token;
    }

    @Override
    protected void onPostExecute(CardToken cardToken) {
        if (cardToken != null) {
            listener.tokenAssigned(cardToken);
        } else {
            listener.handleError(statusCode, message);
        }
    }
}
