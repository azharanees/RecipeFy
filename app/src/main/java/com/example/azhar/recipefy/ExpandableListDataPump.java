package com.example.azhar.recipefy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Azhar on 3/23/2019.
 */

public class ExpandableListDataPump {
    public static Map<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("India");
        cricket.add("Pakistan");
        cricket.add("Australia");
        cricket.add("England");
        cricket.add("South Africa");

        List<String> football = new ArrayList<String>();
        football.add("Brazil");
        football.add("Spain");
        football.add("Germany");
        football.add("Netherlands");
        football.add("Italy");

        List<String> basketball = new ArrayList<String>();
        basketball.add("United States");
        basketball.add("Spain");
        basketball.add("Argentina");
        basketball.add("France");
        basketball.add("Russia");

        expandableListDetail.put("CRICKET TEAMS", cricket);
        expandableListDetail.put("FOOTBALL TEAMS", football);
        expandableListDetail.put("BASKETBALL TEAMS", basketball);
        return expandableListDetail;
    }

    public static Map<String, List<String>> getData(DatabaseHandler db) {
        Map<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        List<Product> allProducts = db.getAllProducts();

        for (Product p :
                allProducts) {
            List<String> details = new ArrayList<>();
            details.add("Price :" +p.getPrice().toString());
            expandableListDetail.put(p.getName().toUpperCase(),details);
        }
        expandableListDetail = new TreeMap<>(expandableListDetail);
        return expandableListDetail;
    }
}
