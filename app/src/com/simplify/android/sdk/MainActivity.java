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
package com.simplify.android.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.simplify.android.sdk.api.card.CardToken;
import com.simplify.android.sdk.api.card.TokenAssignmentListener;
import com.simplify.android.sdk.api.payment.Payment;
import com.simplify.android.sdk.api.payment.PaymentReceipt;
import com.simplify.android.sdk.api.payment.PaymentReceivedListener;

public class MainActivity extends Activity {

    private CreditCardEditor cardEditor;
    private Button lookupButton;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lookupButton = (Button) findViewById(R.id.send_button);

        cardEditor = (CreditCardEditor) findViewById(R.id.credit_card);
        cardEditor.addEntryCompleteListener(new EntryCompleteListener() {
            @Override
            public void entryComplete(View editorView) {
                lookupButton.setEnabled(true);
            }

            @Override
            public void entryIncomplete(View editorView) {
                lookupButton.setEnabled(false);
            }
        });
    }

    public void lookupToken(View eventSource) {
        if (cardEditor.getCard().requestToken(new TokenAssignmentListener() {
            @Override
            public void tokenAssigned(CardToken token) {
                showToast("Token received: "+token.getId());

                // Note: this is where I could easily send a payment for $10 using the token
                Payment payment = new Payment(1000);
                if (payment.submitPayment(token, new PaymentReceivedListener() {
                    @Override
                    public void paymentReceived(PaymentReceipt receipt) {
                        // Payment succeeded, so process the receipt.
                    }

                    @Override
                    public void handleError(int statusCode, String message) {
                        // Payment failed.
                    }
                })) {
                    showToast("Payment transmitted");
                };
            }

            @Override
            public void handleError(int statusCode, String message) {
                Log.e("SIMP", "### Error Code: "+statusCode);
                Log.e("SIMP", "### Error Message: "+message);
            }
        })) {
            showToast("Token requested");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, 2000).show();
    }
}
