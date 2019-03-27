package com.example.azhar.recipefy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AvailableProducts extends AppCompatActivity {
    ListView expandableListView;
    CustomExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    Map<String, List<String>> expandableListDetail;
    DatabaseHandler db;
    CheckedTextView checkedTextView;
    List<String> availList = new ArrayList<>();
    Button addToKitchen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_products);
        db = new DatabaseHandler(this);
        List<Product> productList = db.getAllProducts();
        System.out.println("LIST   = " + productList);
        for (Product product : productList) {
            System.out.println("PAAAAAAAAAAAAAA ---------------" + product);
            if ("Available".equalsIgnoreCase(product.getAvail())) {
                availList.add(product.getName());
            }
        }
        expandableListView = findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData(db);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, availList, "AVAIL");
        expandableListView.setAdapter(expandableListAdapter);


        addToKitchen = findViewById(R.id.addKitchenButton);
    }

    public void removeFromKitchen(View view) {
        System.out.println(expandableListAdapter.getChecked());
        DatabaseHandler db = new DatabaseHandler(this);
        for (String p : expandableListAdapter.getUnchecked()) {
            if (p != null) {
                Product product = db.getProductByName(p);
                product.setAvail("NOT AVAILABLE");
                System.out.println(product);
                db.makeNotAvail(product);
                System.out.println("AFTER NOT AVAiL = " + product);
            }
        }
        recreate();
    }
}
