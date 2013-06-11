package com.simplify.android.sdk;

import android.test.AndroidTestCase;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class CreditCardEditorTest extends AndroidTestCase {

    @Mock
    private BrandChangedListener brandChangedListener;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
    }

    public void testBrandChangeEventFiredWhenTextChanges() {
        CreditCardEditor view = new CreditCardEditor(getContext());
        view.addBrandChangedListener(brandChangedListener);

        view.setCardNumber("4111");
        view.setCardNumber("3400");
        view.setCardNumber("0000");

        ArgumentCaptor<Card.Brand> captor = ArgumentCaptor.forClass(Card.Brand.class);
        verify(brandChangedListener, times(3)).brandChanged(eq(view), captor.capture());
        assertEquals(Arrays.asList(Card.Brand.VISA, Card.Brand.AMEX, Card.Brand.UNKNOWN), captor.getAllValues());
    }

}
