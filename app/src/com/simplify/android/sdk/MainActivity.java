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
