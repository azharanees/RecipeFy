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

public class RecipeActivity extends AppCompatActivity {
    //API KEY --- 0023b6accc1cda3fc464db16c41fbb02
    final String API_KEY = "0023b6accc1cda3fc464db16c41fbb02";
    ListView expandableListView;
    CustomExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    Map<String, List<String>> expandableListDetail;
    DatabaseHandler db;
    CheckedTextView checkedTextView;
    Button addToKitchen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);


        db = new DatabaseHandler(this);
        expandableListView = findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData(db);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, "display");
        expandableListView.setAdapter(expandableListAdapter);
        addToKitchen = findViewById(R.id.addKitchenButton);

    }

    public void findRecipeButton(View view) {
        System.out.println(expandableListAdapter.getChecked());
        List<String> recipes = expandableListAdapter.getChecked();
        String recipeString = "";
        for (String product : recipes) {
            recipeString = recipeString.concat(product + ",");
        }

        String apiCallString = "https://www.food2fork.com/api/search?key=" + API_KEY + "&q=" + recipeString + "page=1";
        System.out.println(apiCallString);

    }


}
