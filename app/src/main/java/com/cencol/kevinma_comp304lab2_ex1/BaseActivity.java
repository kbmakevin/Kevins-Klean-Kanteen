package com.cencol.kevinma_comp304lab2_ex1;

import android.content.Intent;
import android.os.Bundle;
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
        //get bundle data here
        Bundle bundle = this.getIntent().getExtras();
        // only get checkout items into variable if it exists in bundle
        // sometimes there will be no items checked out i.e. first starting app
        if (bundle != null && bundle.containsKey(getResources().getString(R.string.extras_key_chkout))) {
            this.checkoutItems = (HashSet<String>) bundle.getSerializable(getResources().getString(R.string.extras_key_chkout));

            if (checkoutItems.size() > 0) {
                String chkout_items = "Food Items in Checkout Bag:\n";
                for (String s : checkoutItems) {
                    chkout_items += "- " + s + "\n";
                }
                Toast.makeText(this, chkout_items, Toast.LENGTH_LONG).show();
            }
        }
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
        Intent intent;

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.veggies:
                Toast.makeText(this, R.string.menu_veg_selected, Toast.LENGTH_LONG).show();
                intent = new Intent(this, VegFoodItemsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.fruits:
                Toast.makeText(this, R.string.menu_fruits_selected, Toast.LENGTH_LONG).show();
                intent = new Intent(this, FruitFoodItemsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.grain:
                Toast.makeText(this, R.string.menu_grain_selected, Toast.LENGTH_LONG).show();
                intent = new Intent(this, GrainFoodItemsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.protein:
                Toast.makeText(this, R.string.menu_protein_selected, Toast.LENGTH_LONG).show();
                intent = new Intent(this, ProteinFoodItemsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.dairy:
                Toast.makeText(this, R.string.menu_dairy_selected, Toast.LENGTH_LONG).show();
                intent = new Intent(this, DairyFoodItemsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
