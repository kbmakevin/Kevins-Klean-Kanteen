package com.cencol.kevinma_comp304lab2_ex1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class GrainFoodItemsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView sv = new ScrollView(this);
        final LinearLayout ll = new LinearLayout(this);
        LinearLayout.LayoutParams llLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llLayoutParams.setMargins(20, 20, 20, 20);

        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);

        TextView instructionsTV = new TextView(getApplicationContext());
        instructionsTV.setText(R.string.grains_food_items_instructions);
        instructionsTV.setLayoutParams(llLayoutParams);
        ll.addView(instructionsTV);

        // dynamically generate checkboxes based on predefined resources
        for (String vegName : getResources().getStringArray(R.array.grains)) {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(vegName);
            cb.setLayoutParams(llLayoutParams);
            if (checkoutItems.contains(cb.getText())) cb.setChecked(true);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((CheckBox) view).isChecked()) {
                        Toast.makeText(view.getContext(), ((CheckBox) view).getText() + getResources().getString(R.string.food_items_checkbox_selected), Toast.LENGTH_SHORT).show();
                        checkoutItems.add(((CheckBox) view).getText().toString());
                    } else {
                        Toast.makeText(view.getContext(), ((CheckBox) view).getText() + getResources().getString(R.string.food_items_checkbox_deselected), Toast.LENGTH_SHORT).show();
                        checkoutItems.remove(((CheckBox) view).getText().toString());
                    }
                }
            });
            ll.addView(cb);
        }

        Button checkoutBtn = new Button(getApplicationContext());
        checkoutBtn.setText(R.string.food_items_checkout_button_text);
        checkoutBtn.setLayoutParams(llLayoutParams);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(getResources().getString(R.string.extras_key_chkout), checkoutItems);
                startActivityForResult(new Intent(getApplicationContext(), CheckoutActivity.class).putExtras(bundle), Integer.parseInt(getResources().getString(R.string.checkout_activity_request_code)));
            }
        });
        ll.addView(checkoutBtn);

        this.setContentView(sv);
    }

    public void onBackPressed() {
        // prevents user from going back by accident
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //disable the grains menu item to prevent end user confusion
        menu.getItem(2).setEnabled(false);
        return true;
    }
}