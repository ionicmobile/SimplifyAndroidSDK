package com.simplify.android.sdk.api;

import android.os.AsyncTask;
import com.google.gson.Gson;
import com.simplify.android.sdk.api.ErrorHandling;
import com.simplify.android.sdk.api.card.CardToken;
import org.apache.http.client.HttpResponseException;

/**
 * Basis for making an asynchronous calls to the <strong>Simplify.com</strong> API.  Derived <code>Request</code> objects
 * need to be able to make the specific server calls, and map the return value to a call on the callback
 * listener interface.
 *
 * @param <I> The input type holding parameters for the <strong>Simplify.com</strong> call
 * @param <V> The return value, coming back from the <strong>Simplify.com</strong> call
 * @param <L> The callback listener interface
 */
public abstract class AsyncApiRequest<I,V,L extends ErrorHandling> extends AsyncTask<I, Void, V> {
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
