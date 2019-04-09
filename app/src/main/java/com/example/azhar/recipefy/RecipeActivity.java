package com.example.azhar.recipefy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeActivity extends AppCompatActivity {
    private final static String API_KEY = "0023b6accc1cda3fc464db16c41fbb02";
    //API KEY --- 0023b6accc1cda3fc464db16c41fbb02
    ListView expandableListView;
    CustomExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    Map<String, List<String>> expandableListDetail;
    DatabaseHandler db;
    CheckedTextView checkedTextView;
    Button addToKitchen;
    String recipeString = "";
    String apiCallString;

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
        recipeString = "";
        for (String product : recipes) {
            recipeString = recipeString.concat(product + ",");
        }


        String apiCallString = "https://www.food2fork.com/api/search?key=" + API_KEY + "&q=" + recipeString + "page=1";
        // System.out.println(apiCallString);
        //APIConnector api = new APIConnector(apiCallString);

    }
    public void searchBooks(View view) {

        System.out.println(expandableListAdapter.getChecked());
        List<String> recipes = expandableListAdapter.getChecked();
        recipeString = "";
        for (String product : recipes) {
            recipeString = recipeString.concat(product + ",");
        }


        apiCallString = "https://www.food2fork.com/api/search?key=" + API_KEY + "&q=" + recipeString + "page=1";

        String queryString = recipeString;

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {

            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            //getSupportLoaderManager().restartLoader(0, queryBundle, this);

            new FetchRecipes(queryString, expandableListView, this.getApplicationContext()).execute(queryString);
//            mAuthorText.setText("");
//            mTitleText.setText(R.string.loading);
        } else {
            if (queryString.length() == 0) {
//                mAuthorText.setText("");
//                mTitleText.setText(R.string.no_search_term);
            } else {
//                mAuthorText.setText("");
//                mTitleText.setText(R.string.no_network);
            }
        }
        recipeString = "";

    }

    public void changeActivity() {

        Intent intent = new Intent(this, FetchedView.class);
        startActivity(intent);
    }


}
