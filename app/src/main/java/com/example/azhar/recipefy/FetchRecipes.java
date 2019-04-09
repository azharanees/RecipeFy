package com.example.azhar.recipefy;

import android.os.AsyncTask;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azhar on 3/29/2019.
 */

public class FetchRecipes extends AsyncTask<String, String, String> {

    static List<String> recipeListTitle;
    ListView listView;
    String recipeList;



    FetchRecipes(String recipeList, ListView listView) {
        this.listView = listView;
        this.recipeList = recipeList;
    }


    public static List<String> getFetchedRecipes() {
        List<String> finalList = recipeListTitle;
        //recipeListTitle.clear();
        return finalList;
    }

    protected String doInBackground(String... params) {

        return APIConnector.getRecipes(params[0]);
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("recipes");
            //  recipeListTitle = new ArrayList<>();
            int i = 1;
            String title = null;
            String authors = null;
            recipeListTitle = new ArrayList<>();
            while (i < itemsArray.length() &&
                    (authors == null && title == null)) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);

                String volumeInfo = book.get("title").toString();
                //authors = volumeInfo.getString("authors");
                if (volumeInfo != null) {
                    System.out.println(volumeInfo);
                    recipeListTitle.add(volumeInfo);


                    //   System.out.println(title);
                } else {
//                        mTitleText.get().setText(R.string.no_results);
//                        mAuthorText.get().setText("");
                    System.out.println("NO RESULtS");
                }
                //  System.out.println("-->"+volumeInfo);
                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    // title = volumeInfo.getString("title");

                } catch (Exception e) {
                    //TODO : Handle no results from API
                    // If onPostExecute does not receive a proper JSON string,
                    // update the UI to show failed results.
//                    mTitleText.get().setText(R.string.no_results);
//                    mAuthorText.get().setText("");
                    e.printStackTrace();
                }

                // Move to the next item.
                i++;

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
