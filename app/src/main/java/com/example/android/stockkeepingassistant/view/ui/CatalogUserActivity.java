package com.example.android.stockkeepingassistant.view.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.stockkeepingassistant.R;
import com.example.android.stockkeepingassistant.model.Product;
import com.example.android.stockkeepingassistant.model.Warehouse;
import com.example.android.stockkeepingassistant.view.adapter.CatalogAdapter;
import com.example.android.stockkeepingassistant.view.adapter.CatalogUserAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CatalogUserActivity extends AppCompatActivity {

	private RecyclerView recyclerView;
	private CatalogUserAdapter adapter;

	private Button sell_btn;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);



		AlertDialog.Builder builder;
		builder = new AlertDialog.Builder(CatalogUserActivity.this);
		builder.setTitle("Товар куплен!");
		AlertDialog dialog = builder.create();



		// Find the ListView which will be populated with the pet data
		recyclerView = findViewById(R.id.list);
		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		recyclerView.setHasFixedSize(true);

		// Setup an Adapter to create a list item for each row of product data in the Cursor.
		Warehouse warehouse = Warehouse.getInstance(this);
		List<Product> products = warehouse.getProducts();

		adapter = new CatalogUserAdapter(this, products);
		recyclerView.setAdapter(adapter);
	}




	@Override
	protected void onResume() {
		Warehouse warehouse = Warehouse.getInstance(this);
		List<Product> products = warehouse.getProducts();

		if (adapter == null) {
			adapter = new CatalogUserAdapter(this, products);
			recyclerView.setAdapter(adapter);
		} else {
			adapter.setData(products);
			adapter.notifyDataSetChanged();
		}
		super.onResume();
	}



}
