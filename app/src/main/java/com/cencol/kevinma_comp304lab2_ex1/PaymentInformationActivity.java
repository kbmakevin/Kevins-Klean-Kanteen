package com.cencol.kevinma_comp304lab2_ex1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;

public class PaymentInformationActivity extends AppCompatActivity {

    private HashSet<String> checkoutItems;
    private HashMap<String, Double> foodMenu;
    private double paymentTotal;
    private String paymentOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_information);

        this._extractDataFromBundle(getIntent());

        if (paymentOption.equals(getResources().getString(R.string.payment_debit_card_extra_val)))
            ((TextView) findViewById(R.id.cardNumTextView)).setText(getResources().getString(R.string.payment_dcardnumber_label));
        else
            ((TextView) findViewById(R.id.cardNumTextView)).setText(getResources().getString(R.string.payment_ccardnumber_label));

        // additional event handlers
        final Spinner foodTypesSpinner = findViewById(R.id.foodTypesSpinner);
        final Spinner foodItemsSpinner = findViewById(R.id.foodItemsSpinner);

        foodTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                ArrayAdapter<CharSequence> adapter;
                switch (position) {
                    case 0:
                        adapter = ArrayAdapter.createFromResource(PaymentInformationActivity.this, R.array.vegetables, android.R.layout.simple_spinner_item);
                        break;
                    case 1:
                        adapter = ArrayAdapter.createFromResource(PaymentInformationActivity.this, R.array.fruits, android.R.layout.simple_spinner_item);
                        break;
                    case 2:
                        adapter = ArrayAdapter.createFromResource(PaymentInformationActivity.this, R.array.grains, android.R.layout.simple_spinner_item);
                        break;
                    case 3:
                        adapter = ArrayAdapter.createFromResource(PaymentInformationActivity.this, R.array.protein, android.R.layout.simple_spinner_item);
                        break;
                    default:
                        // default case is case 4, need a default to ensure compiler does not complain about array adapter not set
                        adapter = ArrayAdapter.createFromResource(PaymentInformationActivity.this, R.array.dairy, android.R.layout.simple_spinner_item);

                        break;
                }
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Apply the adapter to the spinner
                foodItemsSpinner.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        findViewById(R.id.paymentInfoSubmitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                //payment total
                bundle.putDouble(getResources().getString(R.string.payment_total_extra_key), paymentTotal);
                //payment option
                bundle.putString(getResources().getString(R.string.payment_option_extra_key), paymentOption);
                //checkout items
                bundle.putSerializable(getResources().getString(R.string.extras_key_chkout), checkoutItems);
                //food menu
                bundle.putSerializable(getResources().getString(R.string.extras_key_foodmenu), foodMenu);
                //customer full name
                bundle.putString(getResources().getString(R.string.payment_info_fullname_extra_key),
                        ((EditText) findViewById(R.id.fullNameEditText)).getText().toString()
                );
                //card number
                bundle.putString(getResources().getString(R.string.payment_info_cardnum_extra_key),
                        (((EditText) findViewById(R.id.cardNumEditText)).getText().toString())
                );
                //cvv number
                bundle.putInt(getResources().getString(R.string.payment_info_cvvnum_extra_key),
                        Integer.parseInt(((EditText) findViewById(R.id.cvvNumEditText)).getText().toString())
                );
                //fav food type
                bundle.putString(getResources().getString(R.string.payment_info_favfoodtype_extra_key),
                        ((Spinner) findViewById(R.id.foodTypesSpinner)).getSelectedItem().toString()
                );
                //fav food item
                bundle.putString(getResources().getString(R.string.payment_info_favfooditem_extra_key),
                        ((Spinner) findViewById(R.id.foodItemsSpinner)).getSelectedItem().toString()
                );
                //satisfaction level
                bundle.putInt(getResources().getString(R.string.payment_info_satisfactionlvl_extra_key),
                        Integer.parseInt(((Spinner) findViewById(R.id.satisfactionLevelSpinner)).getSelectedItem().toString())
                );

                startActivity(new Intent(PaymentInformationActivity.this, FinalOrderInfoActivity.class).putExtras(bundle));

            }
        });

        // card can only have 16 digits
        ((EditText) findViewById(R.id.cardNumEditText)).setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(16)
        });
    }

    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(getResources().getString(R.string.extras_key_chkout), checkoutItems);
        bundle.putDouble(getResources().getString(R.string.payment_total_extra_key), paymentTotal);
        bundle.putString(getResources().getString(R.string.payment_option_extra_key), paymentOption);
        setResult(RESULT_OK, new Intent().putExtras(bundle));
        finish();
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
        this.foodMenu = (HashMap<String, Double>) bundle.getSerializable(getResources().getString(R.string.extras_key_foodmenu));

    }
}
