package com.example.dev_daniel.braintreedemo.inputValidation;

import android.util.Log;

import com.example.dev_daniel.braintreedemo.data.NetworkName;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dev_d on 3/1/15.
 */
public class CardValidator {

    public static HashMap<NetworkName, ArrayList<ArrayList<Integer>>> cardNetworksIIN;
    private static String TAG = CardValidator.class.getName();
    public static void init(){} //tells jvm to run static
    static{
        cardNetworksIIN = new HashMap<>();
        /** Amex **/ //34, 37
        ArrayList<ArrayList<Integer>> amex = new ArrayList<>();

        ArrayList<Integer> a1 = new ArrayList<>();
        a1.add(34);
        amex.add(a1);

        ArrayList<Integer> a2 = new ArrayList<>();
        a2.add(37);
        amex.add(a2);

        cardNetworksIIN.put(NetworkName.AMEX, amex);

        /** Discover **/
        //Discover numbers:  	6011, 622126-622925, 644-649, 65
        ArrayList<ArrayList<Integer>> discover = new ArrayList<>();

        ArrayList<Integer> d1 = new ArrayList<>();
        d1.add(6011);
        discover.add(d1);

        ArrayList<Integer> d2 = new ArrayList<>();
        d2.add(622126);
        d2.add(622925);
        discover.add(d2);

        ArrayList<Integer> d3 = new ArrayList<>();
        d3.add(644);
        d3.add(649);
        discover.add(d3);

        ArrayList<Integer> d4 = new ArrayList<>();
        d4.add(65);
        discover.add(d4);
        cardNetworksIIN.put(NetworkName.DISCOVER, discover);


        /** JCB **/ //3528-3589
        ArrayList<ArrayList<Integer>> jcb = new ArrayList<>();

        ArrayList<Integer> j1 = new ArrayList<>();
        j1.add(3528);
        j1.add(3589);
        jcb.add(j1);
        cardNetworksIIN.put(NetworkName.JCB, jcb);

        /** Mastercard **/ //51-55
        ArrayList<ArrayList<Integer>> mastercard = new ArrayList<>();
        ArrayList<Integer> m1 = new ArrayList<>();
        m1.add(51);
        m1.add(55);
        mastercard.add(m1);
        cardNetworksIIN.put(NetworkName.MASTERCARD, mastercard);

        /** visa **/ //4
        ArrayList<ArrayList<Integer>> visa = new ArrayList<>();
        ArrayList<Integer> v1 = new ArrayList<>();
        v1.add(4);
        visa.add(v1);
        cardNetworksIIN.put(NetworkName.VISA, visa);


    }






    public static NetworkName identifyNetwork(String inputNum){
        NetworkName match = NetworkName.UNKNOWN;
        //return network name

        //iterate through all values and find out which one
        for (NetworkName key : cardNetworksIIN.keySet()){
            ArrayList<ArrayList<Integer>> supportedIINs = cardNetworksIIN.get(key);
            //get the values and check each one;
            for(ArrayList<Integer> numberCheck : supportedIINs){
                int digitsRequiredForChecking = 0;
                if(numberCheck.size()==1) {
                        //single number
                    String iin =  Integer.toString(numberCheck.get(0));
                    digitsRequiredForChecking = iin.length();

                    //check first few digits
                    String inputFirstFew = inputNum.substring(0, digitsRequiredForChecking);
                    Log.i(TAG, "This is digits required for checking " + digitsRequiredForChecking);
                    Log.i(TAG, "THis is input first few " + inputFirstFew);
                    if(iin.equals(inputFirstFew)){
                        //we have a match
                        match = key;
                        return match;
                    }

                } else if(numberCheck.size()==2) {
                    //its an interval
                    //check interval;


                    int lowerBound = numberCheck.get(0);
                    int upperBound = numberCheck.get(1);
                    Log.i(TAG, "We are in " + key.getFriendlyName()+ " network and lower bound: " + lowerBound + " and upper bound: " + upperBound);
                    //extract first few digits from inputNum using lowerBound
                    int lowBoundDigitLen= (int)(Math.log10(lowerBound)+1);

                    int checkingNum = Integer.parseInt(inputNum.substring(0, lowBoundDigitLen)); // this is the extracted digit
                    if(checkingNum>=lowerBound && checkingNum<=upperBound){
                        match = key;
                        return match;
                    }


                }


            }


        }

        return match;
    }

}
