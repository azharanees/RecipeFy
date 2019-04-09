package com.example.azhar.recipefy;

import android.content.Context;
import android.content.Intent;
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
    static List<Recipe> recipes;
    ListView listView;
    String recipeList;
    Context context;


    FetchRecipes(String recipeList, ListView listView, Context context) {
        this.listView = listView;
        this.recipeList = recipeList;
        this.context = context.getApplicationContext();

    }

    public static List<Recipe> getRecipes() {
        return recipes;
    }

    public static List<String> getFetchedRecipes() {
        //recipeListTitle.clear();
        return recipeListTitle;
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
            recipes = new ArrayList<>();
            while (i < itemsArray.length() &&
                    (authors == null && title == null)) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);

                String volumeInfo = book.get("title").toString();
                String url = book.get("source_url").toString();
                String img = book.get("image_url").toString();
                //authors = volumeInfo.getString("authors");
                if (volumeInfo != null) {
                    System.out.println(url);
                    Recipe recipe = new Recipe(volumeInfo, url, img);
                    recipes.add(recipe);
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

            context.startActivity(new Intent(context, FetchedView.class));


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
