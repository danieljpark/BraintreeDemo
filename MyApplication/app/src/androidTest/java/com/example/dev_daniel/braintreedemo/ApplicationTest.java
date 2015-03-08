package com.example.dev_daniel.braintreedemo;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.example.dev_daniel.braintreedemo.data.NetworkName;
import com.example.dev_daniel.braintreedemo.inputValidation.CardValidator;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class  ApplicationTest extends ApplicationTestCase<Application> {



    public ApplicationTest() {
        super(Application.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();


    }

    public void testCardNetworkIdentification(){
        assertEquals(NetworkName.AMEX, CardValidator.identifyNetwork("371449635398431"));
        assertEquals(NetworkName.DISCOVER, CardValidator.identifyNetwork("6011111111111117"));
        assertEquals(NetworkName.JCB, CardValidator.identifyNetwork("3530111333300000"));
        assertEquals(NetworkName.MASTERCARD, CardValidator.identifyNetwork("5555555555554444"));
        assertEquals(NetworkName.VISA, CardValidator.identifyNetwork("4111111111111111"));


    }

    public void testLuhnValidation(){

        assertEquals(true, CardValidator.luhnValidation("49927398716"));
        assertEquals(false, CardValidator.luhnValidation("49927398714"));
        assertEquals(false, CardValidator.luhnValidation("49927398715"));
        assertEquals(false, CardValidator.luhnValidation("36377398714"));
        assertEquals(true, CardValidator.luhnValidation("12345678903555"));


    }
}