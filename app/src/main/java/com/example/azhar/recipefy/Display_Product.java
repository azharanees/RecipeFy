package com.example.azhar.recipefy;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Display_Product extends AppCompatActivity {
    ListView expandableListView;
    CustomExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    Map<String, List<String>> expandableListDetail;
    DatabaseHandler db;
    CheckedTextView checkedTextView ;
    Button addToKitchen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__product);
        db = new DatabaseHandler(this);
        expandableListView =  findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData(db);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this,expandableListTitle);
        expandableListView.setAdapter(expandableListAdapter);
        addToKitchen = findViewById(R.id.addKitchenButton);


//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();
//                checkedTextView = expandableListView.getChildAt(groupPosition).findViewById(R.id.listTitle);
//                if(checkedTextView.isChecked()){
//                    checkedTextView.setChecked(false);
//                }else checkedTextView.setChecked(true);
//            }
//        });
//
//        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        getApplicationContext(),
//                        expandableListTitle.get(groupPosition)
//                                + " -> "
//                                + expandableListDetail.get(
//                                expandableListTitle.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();
//                return false;
//            }
//        });




    }

    public void addToKitchen(View view) {
        System.out.println(expandableListAdapter.getChecked());
        DatabaseHandler db = new DatabaseHandler(this);
        for (String p:expandableListAdapter.getChecked()) {
            if(p!=null){
                Product product = db.getProductByName(p);

                product.setAvail("AVAILABLE");
                System.out.println(product);
                db.makeAvail(product);
                System.out.println("AFTER AVAiL = " +product);
            }
        }
    }
}
