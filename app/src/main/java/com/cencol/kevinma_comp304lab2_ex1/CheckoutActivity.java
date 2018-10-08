package com.cencol.kevinma_comp304lab2_ex1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private HashMap<String, Double> foodMenu;
    private HashSet<String> checkoutItems;
    private double paymentTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // populate the food menu item/price map from the resource arrays
        this.foodMenu = new HashMap<>();

        this._populateFoodMenu();
        this._extractCheckoutBagItems(this.getIntent());

        String checkoutItemsDisplayString = ((TextView) findViewById(R.id.checkoutBagTextView)).getText().toString();

        for (String item : checkoutItems) {
            //add item to be displayed on screen
            checkoutItemsDisplayString += String.format("-> %-35s %s%.2f\n", item, "+ $", foodMenu.get(item));

            //calculate total bill
            paymentTotal += foodMenu.get(item);
        }
//        for (String key : foodMenu.keySet()) {
//            m += String.format("-> %-35s %s%.2f\n", key, "+ $", foodMenu.get(key));
//        }

        ((TextView) findViewById(R.id.checkoutBagTextView)).setText(checkoutItemsDisplayString);
        ((TextView) findViewById(R.id.paymentTotalTextView)).setText(String.format("TOTAL: $%.2f", paymentTotal));
    }

    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(getResources().getString(R.string.extras_key_chkout), checkoutItems);
        setResult(RESULT_OK, new Intent().putExtras(bundle));
        finish();
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
