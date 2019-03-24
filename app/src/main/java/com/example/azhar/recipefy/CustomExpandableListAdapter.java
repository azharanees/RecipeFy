package com.example.azhar.recipefy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Azhar on 3/23/2019.
 */

public class CustomExpandableListAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> expandableListTitle;
    private List<String> checked = new ArrayList<>();
    private List<String> unchecked = new ArrayList<>();
    private Map<String, List<String>> expandableListDetail;
    private String assosClass;

    public CustomExpandableListAdapter(Context context, List<String> list, String assosClass) {
        super(context,-1,list);
        this.context = context;
        this.expandableListTitle = list;
        this.assosClass = assosClass;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_group, parent, false);
        final CheckedTextView textView =  rowView.findViewById(R.id.listTitle);
        textView.setText(expandableListTitle.get(position));

        // change the icon for Windows and iPhone
        ImageView imageView = rowView.findViewById(R.id.listImage);
        imageView.setImageResource(R.drawable.burger);

        if (assosClass.equalsIgnoreCase("avail")) {
            textView.setChecked(true);
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = expandableListTitle.get(position);
                if (textView.isChecked()){
textView.setChecked(false);
    checked.remove(name);
                    unchecked.add(name);
}else{
    textView.setChecked(true);
                    checked.add(name);
                    unchecked.remove(name);

}

            }
        });
        return rowView;
    }

    public List<String> getChecked() {
        return checked;
    }

    public void setChecked(List<String> checked) {
        this.checked = checked;
    }

    public List<String> getUnchecked() {
        return unchecked;
    }

    private String getImageLoc(int listPosition) {
        return this.expandableListDetail.get(listPosition).get(0);
    }



}
