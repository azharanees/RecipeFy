package com.example.azhar.recipefy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class FetchedView extends AppCompatActivity {

    ListView expandableListView;
    CustomExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetched_view);

        expandableListView = findViewById(R.id.expandableListView);
        expandableListTitle = FetchRecipes.getFetchedRecipes();
        //  expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, "display");
        ArrayAdapter a = new ArrayAdapter(this, android.R.layout.simple_list_item_1, expandableListTitle);
        expandableListView.setAdapter(a);
    }

}
