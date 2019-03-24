package com.example.azhar.recipefy;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Azhar on 3/23/2019.
 */

public class CustomExpandableListAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> expandableListTitle;
    private Map<String, List<String>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<String> list) {
        super(context,-1,list);
        this.context = context;
        this.expandableListTitle = list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_group, parent, false);
        final CheckedTextView textView =  rowView.findViewById(R.id.listTitle);
        textView.setText(expandableListTitle.get(position));
        // change the icon for Windows and iPhone
        ImageView imageView = rowView.findViewById(R.id.listImage);
        imageView.setImageResource(R.drawable.burger);


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if (textView.isChecked()){
textView.setChecked(false);
}else textView.setChecked(true);
            }
        });
        return rowView;
    }

    private String getImageLoc(int listPosition) {
        return this.expandableListDetail.get(listPosition).get(0);
    }



}
