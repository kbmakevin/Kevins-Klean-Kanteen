package com.cencol.kevinma_comp304lab2_ex1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * This Activity will be used as a base/template for other classes and contains common reusable elements
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.food_types_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.veggies:
                Toast.makeText(this, R.string.menu_veg_selected, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, VegFoodItemsActivity.class));
                break;
            case R.id.fruits:
                Toast.makeText(this, R.string.menu_fruits_selected, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, FruitFoodItemsActivity.class));
                break;
            case R.id.grain:
                Toast.makeText(this, R.string.menu_grain_selected, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, GrainFoodItemsActivity.class));
                break;
            case R.id.protein:
                Toast.makeText(this, R.string.menu_protein_selected, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, ProteinFoodItemsActivity.class));
                break;
            case R.id.dairy:
                Toast.makeText(this, R.string.menu_dairy_selected, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, DairyFoodItemsActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
