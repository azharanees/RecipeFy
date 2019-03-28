package com.example.azhar.recipefy;

import android.os.AsyncTask;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Azhar on 3/29/2019.
 */

public class FetchRecipes extends AsyncTask<String, String, String> {

    ListView listView;
    String recipeList;


    FetchRecipes(String recipeList, ListView listView) {
        this.listView = listView;
        this.recipeList = recipeList;
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
                        System.out.println(title);
                        //  listView.setText(title);

                    } else {
//                        mTitleText.get().setText(R.string.no_results);
//                        mAuthorText.get().setText("");
                        System.out.println("NO RESULtS");
                    }
                } catch (Exception e) {
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
