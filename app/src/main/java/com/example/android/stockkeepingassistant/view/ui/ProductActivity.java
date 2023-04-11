package com.example.android.stockkeepingassistant.view.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.android.stockkeepingassistant.R;

import java.util.UUID;

public class ProductActivity extends AppCompatActivity {

    private static final String EXTRA_PRODUCT_ID = "com.example.android.stockkeepingassistant.product_id";

    public static Intent newIntent(Context packageContext, UUID productId) {
        Intent intent = new Intent(packageContext, ProductActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getSupportActionBar().setTitle(R.string.product_fragment_title);

        UUID productId = (UUID) getIntent().getSerializableExtra(EXTRA_PRODUCT_ID);

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_container, ProductFragment.newInstance(productId))
                    .commit();
        }
    }
    public void save_btn(View view){
        Intent intent = new Intent(this, CatalogActivity.class);
        startActivity(intent);
    }



}
