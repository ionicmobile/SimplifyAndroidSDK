package com.simplify.android.sdk.api.payment;

import com.simplify.android.sdk.api.AsyncApiRequest;
import com.simplify.android.sdk.api.card.Card;
import org.apache.http.client.HttpResponseException;

public class PaymentByCardRequest extends AsyncApiRequest<Card, Void, PaymentReceipt, PaymentReceivedListener> {

    public PaymentByCardRequest(PaymentReceivedListener listener) {
        super(listener);
    }

    @Override
    protected PaymentReceipt callServer(Card... params) throws HttpResponseException {
        return null;
    }

    @Override
    protected void notifyDataReceived(PaymentReceivedListener listener, PaymentReceipt returnData) {
        listener.paymentReceived(returnData);
    }
}
