package com.simplify.android.sdk.api;

import android.os.AsyncTask;
import com.google.gson.Gson;
import com.simplify.android.sdk.api.ErrorHandling;
import com.simplify.android.sdk.api.card.CardToken;
import org.apache.http.client.HttpResponseException;

public abstract class AsyncApiRequest<I,T,V,L extends ErrorHandling> extends AsyncTask<I, T, V> {
    protected final L listener;
    protected final Gson gson;
    protected int statusCode;
    protected String message;

    public AsyncApiRequest(L listener) {
        this.listener = listener;
        this.gson = new Gson();
    }

    @Override
    protected V doInBackground(I... params) {
        V returnData = null;
        try {
            returnData = callServer(params);
        } catch (HttpResponseException e) {
            statusCode = e.getStatusCode();
            message = e.getMessage();
        }
        return returnData;
    }

    @Override
    protected void onPostExecute(V returnData) {
        if (returnData != null) {
            notifyDataReceived(listener, returnData);
        } else {
            listener.handleError(statusCode, message);
        }
    }

    protected abstract V callServer(I... params) throws HttpResponseException;

    protected abstract void notifyDataReceived(L listener, V returnData);
}
