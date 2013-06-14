package com.simplify.android.sdk.api.payment;

import com.simplify.android.sdk.api.AsyncApiRequest;
import com.simplify.android.sdk.api.card.CardToken;
import org.apache.http.client.HttpResponseException;

public class PaymentByCardTokenRequest extends AsyncApiRequest<CardToken, Void, PaymentReceipt, PaymentReceivedListener> {

    public PaymentByCardTokenRequest(PaymentReceivedListener listener) {
        super(listener);
    }

    @Override
    protected PaymentReceipt callServer(CardToken... params) throws HttpResponseException {
        return null;
    }

    @Override
    protected void notifyDataReceived(PaymentReceivedListener listener, PaymentReceipt returnData) {
        listener.paymentReceived(returnData);
    }
}
