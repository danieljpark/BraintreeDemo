package com.example.dev_daniel.braintreedemo;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.TooManyListenersException;


public class MainActivity extends ActionBarActivity {

    private static final int MAX_CARD_INPUT_LENGTH = 16; //16 max plus 3 spaces for every 4 digits

    /** UI Elements **/
    EditText creditCardEditText;
    ImageView cardLogoImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup UI
        initialUISetup();
    }




    /*** Sets up the initial buttons & ui ***/
    private void initialUISetup(){
        //set as generic at first
        cardLogoImageView = (ImageView) findViewById(R.id.card_logo_imageView);
        cardLogoImageView.setImageResource(R.drawable.generic_card_2x);

        creditCardEditText = (EditText) findViewById(R.id.credit_card_editText);
        creditCardEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_CARD_INPUT_LENGTH)});
        creditCardEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Toast.makeText(getApplicationContext(), "before", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int charLen = s.length();
                Toast.makeText(getApplicationContext(), "changed and s len is " + charLen, Toast.LENGTH_SHORT).show();
                //TODO: fix
                creditCardEditText.getText().insert(start, " ");
                if(charLen == MAX_CARD_INPUT_LENGTH - 1) {
                    showAmexLogo();
                }
                else if(charLen== MAX_CARD_INPUT_LENGTH){


                    show16DigitCardLogo(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(getApplicationContext(), "after", Toast.LENGTH_SHORT).show();
            }
        });


    }


    /***
     * Method for Card Number Validation. Uses Luhn Validation. Changed from int input to string
     * since the former can cause overflow errors.
     * ***/
    public void validateCreditCard(String inputCardNum){





    }

    public void showAmexLogo(){
        cardLogoImageView.setImageResource(R.drawable.amex_2x);
    }

    public void show16DigitCardLogo(CharSequence cardNum){





    }


    /*** Options ***/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
