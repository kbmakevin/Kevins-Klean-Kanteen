package com.cencol.kevinma_comp304lab2_ex1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMainEnterBtn(View v) {
        Toast.makeText(this, R.string.enter_btn_clicked_text, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, FoodTypesActivity.class));
    }
}
