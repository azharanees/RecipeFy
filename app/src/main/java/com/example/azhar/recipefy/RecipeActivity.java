package com.example.azhar.recipefy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private final static String API_KEY = "0023b6accc1cda3fc464db16c41fbb02";
    //API KEY --- 0023b6accc1cda3fc464db16c41fbb02
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
        // System.out.println(apiCallString);
        //APIConnector api = new APIConnector(apiCallString);

    }

    public void searchBooks(View view) {

        System.out.println(expandableListAdapter.getChecked());
        List<String> recipes = expandableListAdapter.getChecked();
        String recipeString = "";
        for (String product : recipes) {
            recipeString = recipeString.concat(product + ",");
        }


        String apiCallString = "https://www.food2fork.com/api/search?key=" + API_KEY + "&q=" + recipeString + "page=1";

        String queryString = apiCallString;

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
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

            new FetchRecipes(queryString, expandableListView).execute(queryString);
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
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";

        if (args != null) {
            queryString = args.getString("queryString");
        }

        return new RecipeLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i = 0;
            String title = null;
            String authors = null;
            while (i < itemsArray.length() &&
                    (authors == null && title == null)) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                    if (title != null && authors != null) {
//                        mTitleText.setText(title);
//                        mAuthorText.setText(authors);
                    } else {
//                        mTitleText.setText(R.string.no_results);
//                        mAuthorText.setText("");
                    }
                } catch (Exception e) {
                    // If onPostExecute does not receive a proper JSON string,
                    // update the UI to show failed results.
//                    mTitleText.setText(R.string.no_results);
//                    mAuthorText.setText("");
                    e.printStackTrace();
                }

                // Move to the next item.
                i++;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

}
