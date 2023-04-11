package com.example.android.stockkeepingassistant.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.stockkeepingassistant.R;
import com.example.android.stockkeepingassistant.model.ProductContract.ProductEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Warehouse {
    /* Class variables */
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private static Warehouse warehouse;
    private Map<String, String> supplierToEmailMap;
    private SQLiteDatabase database;

    private void makeEmailDirectory() {
        supplierToEmailMap = new HashMap<>(10);
        supplierToEmailMap.put(context.getString(R.string.supplier_1), "isip_a.yu.ivanov@mpt.ru");
        supplierToEmailMap.put(context.getString(R.string.supplier_2), "2isip_a.yu.ivanov@mpt.ru");
        supplierToEmailMap.put(context.getString(R.string.supplier_3), "3isip_a.yu.ivanov@mpt.ru");
        supplierToEmailMap.put(context.getString(R.string.supplier_4), "4isip_a.yu.ivanov@mpt.ru");
        supplierToEmailMap.put(context.getString(R.string.supplier_5), "5isip_a.yu.ivanov@mpt.ru");
        supplierToEmailMap.put(context.getString(R.string.supplier_6), "6isip_a.yu.ivanov@mpt.ru");
        supplierToEmailMap.put(context.getString(R.string.supplier_7), "7isip_a.yu.ivanov@mpt.ru");
        supplierToEmailMap.put(context.getString(R.string.supplier_8), "8isip_a.yu.ivanov@mpt.ru");
        supplierToEmailMap.put(context.getString(R.string.supplier_9), "9isip_a.yu.ivanov@mpt.ru");
        supplierToEmailMap.put(context.getString(R.string.supplier_10), "0isip_a.yu.ivanov@mpt.ru");
    }

    private Warehouse(Context context) {
        this.context = context;
        makeEmailDirectory();

        database = new ProductDbHelper(this.context).getWritableDatabase();
    }

    public static Warehouse getInstance(@NonNull Context context) {
        if (warehouse == null) {
            warehouse = new Warehouse(context.getApplicationContext());
        }

        return warehouse;
    }

    public String resolveEmail(String supplierName) {
        return supplierToEmailMap.get(supplierName);
    }

    @Nullable
    public String resolveName(String supplierEmail) {
        for (Map.Entry<String, String> entry : supplierToEmailMap.entrySet()) {
            if (entry.getValue().equals(supplierEmail)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void addProduct(Product p) {
        ContentValues values = getContentValues(p);

        database.insert(ProductEntry.TABLE_NAME, null, values);
    }

    public void updateProduct(Product product) {
        String uuidString = product.getId().toString();
        ContentValues values = getContentValues(product);

        database.update(ProductEntry.TABLE_NAME, values, ProductEntry.UUID + " = ?", new String[]{uuidString});
    }

    public void deleteProduct(UUID productId) {
        database.delete(
                ProductEntry.TABLE_NAME,
                ProductEntry.UUID + " = ?",
                new String[]{productId.toString()}
        );
    }

    @Nullable
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        ProductCursorWrapper cursor = queryProducts(null, null);

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                products.add(cursor.getProduct());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return products;
    }

    @Nullable
    public Product getProduct(UUID productId) {
        try (ProductCursorWrapper cursor =
                     queryProducts(ProductEntry.UUID + " = ?", new String[]{productId.toString()})) {
            if (cursor.getColumnCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getProduct();
        }
    }

    public File getPhotoFile(Product product) {
        File filesDir = context.getFilesDir();
        return new File(filesDir, product.getPhotoFilename());
    }

    @SuppressLint("Recycle")
    private ProductCursorWrapper queryProducts(@Nullable String where, @Nullable String[] args) {
        Cursor c = context.getContentResolver().query(
                ProductEntry.CONTENT_URI,
                null,
                where,
                args,
                null
        );

        return new ProductCursorWrapper(c);
    }

    private ContentValues getContentValues(Product product) {
        ContentValues values = new ContentValues();
        values.put(ProductEntry.UUID, product.getId().toString());
        values.put(ProductEntry.COLUMN_PRODUCT_TITLE, product.getTitle());
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, product.getQuantity());
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE, product.getPrice().toString());
        values.put(ProductEntry.COLUMN_SUPPLIER_NAME, product.getSupplierName());
        values.put(ProductEntry.COLUMN_SUPPLIER_EMAIL, product.getSupplierEmail());

        return values;
    }
}
