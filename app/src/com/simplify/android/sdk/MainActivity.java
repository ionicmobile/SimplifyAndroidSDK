package com.simplify.android.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.simplify.android.sdk.api.CardToken;
import com.simplify.android.sdk.api.TokenAssignmentListener;
import com.simplify.android.sdk.api.TokenAssignmentRequest;

import java.util.HashMap;

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
                Log.e("SIMP", String.valueOf(token.getId()));
            }
        })) {
            Toast.makeText(this, "Sure thing, boss", 2000).show();
        }
    }
}
