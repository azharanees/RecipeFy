package com.example.azhar.recipefy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Azhar on 3/29/2019.
 */

public class RecipeLoader extends AsyncTaskLoader<String> {
    private String mQueryString;

    public RecipeLoader(@NonNull Context context) {
        super(context);

    }

    RecipeLoader(Context context, String queryString) {
        super(context);
        mQueryString = queryString;
        System.out.println(mQueryString + " at RecipeLoader 22");
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return APIConnector.getRecipes(mQueryString);

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }
}
