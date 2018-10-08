package com.cencol.kevinma_comp304lab2_ex1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashSet;

/**
 * This Activity will be used as a base/template for other classes and contains common reusable elements
 */
public class BaseActivity extends AppCompatActivity {

    //using a Set here instead of ArrayList because can only order each item once in this application
    protected HashSet<String> checkoutItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.checkoutItems = new HashSet<>();
        this._extractCheckoutBagItems(this.getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.food_types_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //need to serialize checkoutItems to pass it onto next activity
        Bundle bundle = new Bundle();
        bundle.putSerializable(getResources().getString(R.string.extras_key_chkout), checkoutItems);

        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!this.getTitle().equals(getResources().getString(R.string.food_types_activity_label))) {
                    // prompt user to verify they would like to discard all current items in checkout bag by reurning to the food types main menu
                    ConfirmationDialogFragment confirmationDialogFragment = ConfirmationDialogFragment.newInstance(getResources().getString(R.string.cnfrm_dialog_title), getResources().getString(R.string.cnfrm_dialog_message));
                    confirmationDialogFragment.show(getFragmentManager(), "dialog");
                } else {
                    //returns to home screen no dialog needed
                    Toast.makeText(this, getResources().getString(R.string.rtn_home_msg), Toast.LENGTH_LONG).show();
                    NavUtils.navigateUpFromSameTask(this);
                }
                break;
            case R.id.veggies:
                Toast.makeText(this, R.string.menu_veg_selected, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, VegFoodItemsActivity.class).putExtras(bundle));
                break;
            case R.id.fruits:
                Toast.makeText(this, R.string.menu_fruits_selected, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, FruitFoodItemsActivity.class).putExtras(bundle));
                break;
            case R.id.grain:
                Toast.makeText(this, R.string.menu_grain_selected, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, GrainFoodItemsActivity.class).putExtras(bundle));
                break;
            case R.id.protein:
                Toast.makeText(this, R.string.menu_protein_selected, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, ProteinFoodItemsActivity.class).putExtras(bundle));
                break;
            case R.id.dairy:
                Toast.makeText(this, R.string.menu_dairy_selected, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, DairyFoodItemsActivity.class).putExtras(bundle));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //Helper Methods
    public void doPositiveClick() {
        Toast.makeText(this, getResources().getString(R.string.rtn_food_types_msg), Toast.LENGTH_LONG).show();
        NavUtils.navigateUpFromSameTask(this);
    }

    public void doNegativeClick() {
        Toast.makeText(this, getResources().getString(R.string.cancel_action_msg), Toast.LENGTH_LONG).show();
    }

    private void _extractCheckoutBagItems(Intent dataSource) {
        //get bundle data here
        Bundle bundle = dataSource.getExtras();
        // only get checkout items into variable if it exists in bundle
        // sometimes there will be no items checked out i.e. first starting app
        if (bundle != null && bundle.containsKey(getResources().getString(R.string.extras_key_chkout))) {
            this.checkoutItems = (HashSet<String>) bundle.getSerializable(getResources().getString(R.string.extras_key_chkout));

            if (checkoutItems.size() > 0) {
                String chkout_items = getResources().getString(R.string.checkout_bag_current_state_display);
                for (String s : checkoutItems) {
                    chkout_items += "- " + s + "\n";
                }
                Toast.makeText(this, chkout_items, Toast.LENGTH_LONG).show();
            }
        }
    }
}
