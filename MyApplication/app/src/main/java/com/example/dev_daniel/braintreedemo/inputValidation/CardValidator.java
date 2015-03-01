package com.example.dev_daniel.braintreedemo.inputValidation;

import com.example.dev_daniel.braintreedemo.model.NetworkName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dev_d on 3/1/15.
 */
public class CardValidator {

    public static HashMap<NetworkName, ArrayList<ArrayList<Integer>>> cardNetworksIIN;

    static{
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

        //return network name
    }

}
