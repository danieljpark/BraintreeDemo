package com.example.dev_daniel.braintreedemo;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dev_daniel.braintreedemo.data.NetworkName;
import com.example.dev_daniel.braintreedemo.inputValidation.CardValidator;

import java.util.Calendar;
import java.util.TooManyListenersException;


public class MainActivity extends ActionBarActivity {
    //TODO: test IIN and then write luhn
    private static final int MAX_CARD_INPUT_LENGTH = 16; //16 max plus 3 spaces for every 4 digits
    private static final int MIN_CARD_INPUT_LENGTH = 15;
    private static final int MAX_EXP_INPUT_LENGTH = 5;
    private static final int MAX_CVV_INPUT_LENGTH = 4;
    private static final String TAG=MainActivity.class.getName();

    /** UI Elements **/
    EditText creditCardEditText;
    ImageView cardLogoImageView;
    Button submitButton;
    EditText expEditText;
    ImageView cvvImageView;
    EditText cvvEditText;


    /** state vars **/
    private boolean isAmex = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardValidator.init();

        //setup UI
        initialUISetup();
    }

    /*** Sets up the initial buttons & ui ***/
    private void initialUISetup(){
        //set as generic at first
        cardLogoImageView = (ImageView) findViewById(R.id.card_logo_imageView);
        showGenericCardLogo();

        /** card edit text **/
        cardEditTextSetup();

        /** Exp date **/
        expDateEditTextSetup();

        /** CVV **/
        cvvEditTextAndImgSetup();

        /** submit button **/
        submitButtonSetup();

    }



    /** card edit text **/
    private void cardEditTextSetup(){

        creditCardEditText = (EditText) findViewById(R.id.credit_card_editText);
        creditCardEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_CARD_INPUT_LENGTH)});
        creditCardEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int charLen = s.length();

                //TODO: fix
                creditCardEditText.getText().insert(start, " ");
                if(charLen == 0){
                    showGenericCardLogo();
                }
                if(charLen == MAX_CARD_INPUT_LENGTH - 1 || charLen == MAX_CARD_INPUT_LENGTH) {
                    showNetworkCardLogo(s);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void expDateEditTextSetup(){
        expEditText = (EditText) findViewById(R.id.exp_editText);

        expEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_EXP_INPUT_LENGTH)});


    }

    private void submitButtonSetup(){

        submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmit();
            }
        });


    }

    private boolean hasUserEnteredCVV = false;

    private void cvvEditTextAndImgSetup(){

        cvvImageView = (ImageView) findViewById(R.id.cvv_hint_imageView);
        cvvImageView.setImageResource(R.drawable.cvv_2x);
        cvvImageView.setVisibility(View.INVISIBLE);

        cvvEditText = (EditText) findViewById(R.id.cvv_editText);

        //by default set cvv limit to 3, unless user enters amex.
        cvvEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_CVV_INPUT_LENGTH-1)});



        cvvEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //you should still show cvv hint if user entered text but erased them.
                if(!hasUserEnteredCVV){
                    cvvImageView.setVisibility(View.VISIBLE);
                    hasUserEnteredCVV = true;
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }




    /*** method for handling submit button; mostly validate data ***/
    private void handleSubmit(){
        StringBuilder errorLog = new StringBuilder();
        /** card num validation **/

        String enteredCardNumber = creditCardEditText.getText().toString();
        //validate credit card num
        int numLen = enteredCardNumber.length();
        if(numLen == 0){
            errorLog.append("Please enter a credit card number. ");
        } else if(numLen < MIN_CARD_INPUT_LENGTH){
            errorLog.append("Missing some digits of credit card. ");
        } else {
            boolean luhnValidationPassed = CardValidator.luhnValidation(enteredCardNumber);
            if(!luhnValidationPassed){
                errorLog.append("Invalid credit card number. Please check the digits. ");

            }
        }


        /** exp date validation **/
        String enteredExp = expEditText.getText().toString();
        int expLen = enteredExp.length();
        if(expLen < MAX_EXP_INPUT_LENGTH){
            errorLog.append("Please enter full expiration date. ");
        } else {
            //validate date
            if(enteredExp.charAt(2)!='/'){
                errorLog.append("Expiration date must follow MM/YY format. ");
            }
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;

            String inputMonth = enteredExp.substring(0,2);
            String inputYear = enteredExp.substring(3);

            if(!Integer.toString(currentYear).substring(2).equals(inputYear)){
                errorLog.append("Please enter valid year. ");
            }
            if(Integer.parseInt(inputMonth)<currentMonth){
                errorLog.append("Please enter valid month. ");
            }

            Log.i("i", "current year n month is  " + currentYear + " " + currentMonth + " inputmo" + inputMonth  + " " + inputYear);

        }

        /** cvv validation **/
        int cvvUserInputLen = cvvEditText.getText().toString().length();
        if(isAmex){
            if(cvvUserInputLen<MAX_CVV_INPUT_LENGTH){
                errorLog.append("AMEX Card has 4 CVV digits");
            }
        } else {
            if(cvvUserInputLen<MAX_CVV_INPUT_LENGTH-1){
                errorLog.append("Your card provider's CVV must be 3 digits");
            }
        }




        /** error processing **/
        //boolean hasPassedLuhn = luhnValidation(enteredCardNumber);
        String errorMsg = errorLog.toString();
        if(errorMsg.length()==0){
            showSuccess();
        } else {
            showErrorMessage(errorMsg);
        }



        //if luhn passes && all other fields are valid ==> show success, else ==> display helpful
        //error message

        //no need to persist data
    }

    private void showSuccess(){

        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Thanks your credit card is in good hands now")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private void showErrorMessage(String errorMsg){

        new AlertDialog.Builder(this)
                .setTitle("Hmm...Error")
                .setMessage(errorMsg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }



    /** showing card logos for each network **/

    private void showGenericCardLogo(){

        cardLogoImageView.setImageResource(R.drawable.generic_card_2x);

    }
    private void showAmexLogo(){
        cardLogoImageView.setImageResource(R.drawable.amex_2x);
    }
    private void showMasterCardLogo(){
        cardLogoImageView.setImageResource(R.drawable.mastercard_2x);
    }
    private void showVisaCardLogo(){
        cardLogoImageView.setImageResource(R.drawable.visa_2x);
    }
    private void showDiscoverCardLogo(){
        cardLogoImageView.setImageResource(R.drawable.discover_2x);
    }
    private void showJCBCardLogo(){
        cardLogoImageView.setImageResource(R.drawable.jcb_2x);
    }



    public void showNetworkCardLogo(CharSequence cardNum){


        /** logic for determining the iin **/

        NetworkName networkName = CardValidator.identifyNetwork(cardNum.toString());
        Log.i(TAG, "this is the network name retrieved from cardvalidator" + networkName);
        //enums are good for switch
        switch (networkName){
            case AMEX:
                isAmex = true;
                showAmexLogo();
                //set the cvv length to 4
                cvvEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_CVV_INPUT_LENGTH)});
                break;
            case MASTERCARD:
                showMasterCardLogo();
                break;
            case VISA:
                showVisaCardLogo();
                break;
            case DISCOVER:
                showDiscoverCardLogo();
                break;
            case JCB:
                showJCBCardLogo();
                break;
            case UNKNOWN:
                showGenericCardLogo();
                break;
            default:
                //TODO: @Braintree: what do you mean by "Numbers from unknown card networks should bypass all validation rules"?
                showGenericCardLogo();
                break;

        }
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
