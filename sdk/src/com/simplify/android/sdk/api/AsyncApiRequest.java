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
package com.simplify.android.sdk.api;

import android.os.AsyncTask;
import com.google.gson.Gson;
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
public abstract class AsyncApiRequest<I,V,L extends ErrorHandler> extends AsyncTask<I, Void, V> {
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
