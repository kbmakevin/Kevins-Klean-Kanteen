package com.cencol.kevinma_comp304lab2_ex1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;

public class CheckoutActivity extends AppCompatActivity {

    private HashMap<String, Double> foodMenu;
    private HashSet<String> checkoutItems;
    private double paymentTotal;
    private String paymentOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        this._init();
    }

    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(getResources().getString(R.string.extras_key_chkout), checkoutItems);
        setResult(RESULT_OK, new Intent().putExtras(bundle));
        finish();
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (reqCode == Integer.parseInt(getResources().getString(R.string.payInfo_activity_request_code))) {
            if (resCode == RESULT_OK) {
                this._extractDataFromBundle(data);
            }
        }
    }

//    public void onResume(){
//        super.onResume();
//
//    }

    private void _init() {
        // populate the food menu item/price map from the resource arrays
        this.foodMenu = new HashMap<>();
        this._populateFoodMenu();
        this._extractCheckoutBagItems(this.getIntent());

        String checkoutItemsDisplayString = ((TextView) findViewById(R.id.checkoutBagTextView)).getText().toString();

        for (String item : checkoutItems) {
            //add items to be displayed on screen
            checkoutItemsDisplayString += String.format("-> %-35s %s%.2f\n", item, "+ $", foodMenu.get(item));

            //calculate total bill
            paymentTotal += foodMenu.get(item);
        }

        // add aditional eventlisteners
        ((RadioGroup) findViewById(R.id.paymentOptRadioGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int idChecked) {
                Bundle bundle;

                switch (idChecked) {
                    case R.id.cashRadioButton:
                        paymentOption = getResources().getString(R.string.payment_cash_extra_val);
                        break;
                    case R.id.creditCardRadioButton:
                        paymentOption = getResources().getString(R.string.payment_credit_card_extra_val);
                        break;
                    case R.id.debitCardRadioButton:
                        paymentOption = getResources().getString(R.string.payment_debit_card_extra_val);
                        break;
                }
            }
        });

        findViewById(R.id.checkoutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (((RadioGroup) findViewById(R.id.paymentOptRadioGroup)).getCheckedRadioButtonId()) {
                    case R.id.cashRadioButton:
                        // if paying by cash go directly to the final activity screen displaying all submitted info
                        break;
                    case R.id.creditCardRadioButton:
                    case R.id.debitCardRadioButton:
                        // need payment information if paying by card
                        Bundle bundle;
                        bundle = new Bundle();
                        bundle.putSerializable(getResources().getString(R.string.extras_key_chkout), checkoutItems);
                        bundle.putDouble(getResources().getString(R.string.payment_total_extra_key), paymentTotal);
                        bundle.putString(getResources().getString(R.string.payment_option_extra_key), paymentOption);
                        startActivityForResult(new Intent(CheckoutActivity.this, PaymentInformationActivity.class).putExtras(bundle), Integer.parseInt(getResources().getString(R.string.payInfo_activity_request_code)));
                        break;
                }
            }
        });

        // Display
        ((TextView) findViewById(R.id.checkoutBagTextView)).setText(checkoutItemsDisplayString);
        ((TextView) findViewById(R.id.paymentTotalTextView)).setText(String.format("TOTAL: $%.2f", paymentTotal));
    }

    private void _extractDataFromBundle(Intent dataSource) {
        this._extractCheckoutBagItems(dataSource);
        Bundle bundle = dataSource.getExtras();
        paymentTotal = bundle.getDouble(getResources().getString(R.string.payment_total_extra_key));
        paymentOption = bundle.getString(getResources().getString(R.string.payment_option_extra_key));
    }

    private void _extractCheckoutBagItems(Intent dataSource) {
        //get bundle data here
        Bundle bundle = dataSource.getExtras();
        // only get checkout items into variable if it exists in bundle
        // sometimes there will be no items checked out i.e. first starting app
        if (bundle != null && bundle.containsKey(getResources().getString(R.string.extras_key_chkout))) {
            this.checkoutItems = (HashSet<String>) bundle.getSerializable(getResources().getString(R.string.extras_key_chkout));
        }
    }

    private void _populateFoodMenu() {

        //Vegetables
        this._populateFoodItem(R.array.vegetables, R.array.vegetables_prices);

        //Fruits
        this._populateFoodItem(R.array.fruits, R.array.fruits_prices);

        //Grains
        this._populateFoodItem(R.array.grains, R.array.grains_prices);

        //Protein
        this._populateFoodItem(R.array.protein, R.array.protein_prices);

        //Dairy
        this._populateFoodItem(R.array.dairy, R.array.dairy_prices);
    }

    private void _populateFoodItem(int itemNamesArrayId, int itemPricesArrayId) {
        double[] itemPrices;
        String[] itemNames;
        itemNames = getResources().getStringArray(itemNamesArrayId);
        itemPrices = new double[itemNames.length];
        for (int i = 0; i < itemNames.length; i++) {
            //populate price array
            itemPrices[i] = Double.parseDouble(getResources().getStringArray(itemPricesArrayId)[i]);
            //populate hashmap
            foodMenu.put(itemNames[i], itemPrices[i]);
        }
    }
}
