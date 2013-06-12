package com.simplify.android.sdk;

import android.test.AndroidTestCase;
import android.view.View;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class CreditCardEditorTest extends AndroidTestCase {

    @Mock
    private BrandChangedListener brandChangedListener;
    @Mock
    private EntryCompleteListener entryCompleteListener;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
    }

    public void testBrandChangeEventFiredWhenTextChanges() {
        CreditCardEditor view = new CreditCardEditor(getContext());
        view.addBrandChangedListener(brandChangedListener);

        view.setCard(new Card("4111", "", 1, 1));
        view.setCard(new Card("3400", "", 1, 1));
        view.setCard(new Card("0000", "", 1, 1));

        ArgumentCaptor<Card.Brand> captor = ArgumentCaptor.forClass(Card.Brand.class);
        verify(brandChangedListener, times(3)).brandChanged(eq(view), captor.capture());
        assertEquals(Arrays.asList(Card.Brand.VISA, Card.Brand.AMEX, Card.Brand.UNKNOWN), captor.getAllValues());
    }

    public void testCreditCardEntryIncompleteEvent_CardNumber() {
        CreditCardEditor view = new CreditCardEditor(getContext());
        view.addEntryCompleteListener(entryCompleteListener);

        view.setCard(new Card("", "123", 2, 20));

        verify(entryCompleteListener, never()).entryComplete(any(View.class));
        verify(entryCompleteListener, atLeastOnce()).entryIncomplete(any(View.class));
    }

    public void testCreditCardEntryIncompleteEvent_ExpirationMonth() {
        CreditCardEditor view = new CreditCardEditor(getContext());
        view.addEntryCompleteListener(entryCompleteListener);

        view.setCard(new Card("4111111111111111", "123", 0, 20));

        verify(entryCompleteListener, never()).entryComplete(any(View.class));
        verify(entryCompleteListener, atLeastOnce()).entryIncomplete(any(View.class));
    }

    public void testCreditCardEntryIncompleteEvent_ExpirationYear() {
        CreditCardEditor view = new CreditCardEditor(getContext());
        view.addEntryCompleteListener(entryCompleteListener);

        view.setCard(new Card("4111111111111111", "123", 12, 10));

        verify(entryCompleteListener, never()).entryComplete(any(View.class));
        verify(entryCompleteListener, atLeastOnce()).entryIncomplete(any(View.class));
    }

    public void testCreditCardEntryIncompleteEvent_CVV() {
        CreditCardEditor view = new CreditCardEditor(getContext());
        view.addEntryCompleteListener(entryCompleteListener);

        view.setCard(new Card("4111111111111111", "", 12, 25));

        verify(entryCompleteListener, never()).entryComplete(any(View.class));
        verify(entryCompleteListener, atLeastOnce()).entryIncomplete(any(View.class));
    }

    public void testCreditCardEntryCompleteEvent() {
        CreditCardEditor view = new CreditCardEditor(getContext());
        view.addEntryCompleteListener(entryCompleteListener);

        // Fields are valid - a Visa card expiring in Dec 2025.
        view.setCard(new Card("4111111111111111", "123", 12, 25));

        verify(entryCompleteListener, times(1)).entryComplete(any(View.class));
        verify(entryCompleteListener, times(5)).entryIncomplete(any(View.class));
    }
}
