package com.simplify.android.sdk.api;

import android.os.AsyncTask;
import com.google.gson.Gson;
import org.apache.http.client.HttpClient;
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
public class TokenAssignmentRequest extends AsyncTask<Card, Void, HashMap> {
    private TokenAssignmentListener listener;

    public TokenAssignmentRequest(TokenAssignmentListener listener) {
        this.listener = listener;
    }

    @Override
    protected HashMap doInBackground(Card... params) {
        String urlStr = "https://sandbox.simplify.com/v1/api/payment/cardToken?key=sbpb_OTY1YmI4N2UtYTJiOS00ZWUzLTliMGItZTFmYzQ2OTRmYmQ3&card.number=4111111111111111&card.cvc=123&card.expMonth=10&card.expYear=16";
        HashMap map = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(urlStr);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpget, responseHandler);
            Gson gson = new Gson();
            map = gson.fromJson(responseBody, HashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    protected void onPostExecute(HashMap cardToken) {
        listener.tokenAssigned(cardToken);
    }
}
