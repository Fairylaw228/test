package com.example.android.stockkeepingassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.stockkeepingassistant.model.Product;
import com.example.android.stockkeepingassistant.model.Warehouse;
import com.example.android.stockkeepingassistant.view.adapter.CatalogAdapter;
import com.example.android.stockkeepingassistant.view.ui.ProductActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {



    private RecyclerView recyclerView;
    private CatalogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab = findViewById(R.id.fab);
     ;

        // Find the ListView which will be populated with the pet data
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        // Setup an Adapter to create a list item for each row of product data in the Cursor.
        Warehouse warehouse = Warehouse.getInstance(this);
        List<Product> products = warehouse.getProducts();

        adapter = new CatalogAdapter(this, products);
        recyclerView.setAdapter(adapter);
    }



}