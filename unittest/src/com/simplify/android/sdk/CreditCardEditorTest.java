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

import android.test.AndroidTestCase;
import android.view.View;
import com.simplify.android.sdk.api.card.Card;
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

    public void testCreditCardEntryIncompleteEvent_CVC() {
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
