package com.cencol.kevinma_comp304lab2_ex1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;

public class FinalOrderInfoActivity extends AppCompatActivity {

    private HashSet<String> checkoutItems;
    private HashMap<String, Double> foodMenu;
    private double paymentTotal;
    private String paymentOption;
    private String fullName;
    private String cardNumber;
    private int cvvNumber;
    private String favFoodType;
    private String favFoodItem;
    private int satisfactionLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order_info);
        this._extractDataFromBundle(getIntent());
        this._updateViews();

        //add additional event handler
        findViewById(R.id.finalCloseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FinalOrderInfoActivity.this, getResources().getString(R.string.thank_you_msg), Toast.LENGTH_LONG).show();
                moveTaskToBack(true);
            }
        });
    }

    public void onBackPressed() {
        // cannot go back once you reach final page...
    }

    private void _extractDataFromBundle(Intent dataSource) {
        //get bundle data here
        Bundle bundle = dataSource.getExtras();
        // only get checkout items into variable if it exists in bundle
        // sometimes there will be no items checked out i.e. first starting app
        if (bundle != null && bundle.containsKey(getResources().getString(R.string.extras_key_chkout))) {
            this.checkoutItems = (HashSet<String>) bundle.getSerializable(getResources().getString(R.string.extras_key_chkout));
        }
        paymentTotal = bundle.getDouble(getResources().getString(R.string.payment_total_extra_key));
        paymentOption = bundle.getString(getResources().getString(R.string.payment_option_extra_key));
        foodMenu = (HashMap<String, Double>) bundle.getSerializable(getResources().getString(R.string.extras_key_foodmenu));

        // only extract more info if not cash payment method
        if (!paymentOption.equals(getResources().getString(R.string.checkout_option_cash).toUpperCase())) {
            fullName = bundle.getString(getResources().getString(R.string.payment_info_fullname_extra_key));
            cardNumber = bundle.getString(getResources().getString(R.string.payment_info_cardnum_extra_key));
            cvvNumber = bundle.getInt(getResources().getString(R.string.payment_info_cvvnum_extra_key));
            favFoodType = bundle.getString(getResources().getString(R.string.payment_info_favfoodtype_extra_key));
            favFoodItem = bundle.getString(getResources().getString(R.string.payment_info_favfooditem_extra_key));
            satisfactionLevel = bundle.getInt(getResources().getString(R.string.payment_info_satisfactionlvl_extra_key));
        }
    }

    private void _updateViews() {

        //ordered items
        String checkoutItemsDisplayString = "";

        for (String item : checkoutItems) {
            //add items to be displayed on screen
            checkoutItemsDisplayString += String.format("-> %-35s %s%.2f\n", item, "+ $", foodMenu.get(item));
        }

        ((TextView) findViewById(R.id.finalOrderDetailsTextView)).setText(checkoutItemsDisplayString);
        ((TextView) findViewById(R.id.finalTotalPaymentTextView)).setText(String.format("%s%.2f", getResources().getString(R.string.final_total_payment), paymentTotal));
        ((TextView) findViewById(R.id.finalPaymentTypeTextView)).setText(String.format("%s %s", getResources().getString(R.string.final_payment_type), paymentOption));

        // additional info recorded if not paying by cash
        if (!paymentOption.equals(getResources().getString(R.string.checkout_option_cash).toUpperCase())) {
            String paymentAdditionalInfoString = "";

            paymentAdditionalInfoString += getResources().getString(R.string.payment_fullname_label) + " " + fullName + "\n";
            if (paymentOption.equals(getResources().getString(R.string.payment_credit_card_extra_val))) {
                paymentAdditionalInfoString += getResources().getString(R.string.payment_ccardnumber_label) + " " + cardNumber + "\n";
            } else {
                paymentAdditionalInfoString += getResources().getString(R.string.payment_dcardnumber_label) + " " + cardNumber + "\n";
            }
            paymentAdditionalInfoString += getResources().getString(R.string.payment_cvvnumber_label) + " " + cvvNumber + "\n";
            paymentAdditionalInfoString += getResources().getString(R.string.payment_favfoodtype_label) + " " + favFoodType + "\n";
            paymentAdditionalInfoString += getResources().getString(R.string.payment_favfooditem_label) + " " + favFoodItem + "\n";
            paymentAdditionalInfoString += getResources().getString(R.string.payment_satisfactionlvl_label) + " " + satisfactionLevel + "\n";

            ((TextView) findViewById(R.id.finalExtraPayDetailsTextView)).setText(paymentAdditionalInfoString);
        }


    }
}
