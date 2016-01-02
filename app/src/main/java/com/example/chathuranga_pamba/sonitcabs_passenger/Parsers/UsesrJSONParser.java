package com.example.chathuranga_pamba.sonitcabs_passenger.Parsers;

import com.example.chathuranga_pamba.sonitcabs_passenger.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pamba on 8/29/2015.
 */
public class UsesrJSONParser {

    public static List<User> parseFeed(String content) {


        try {
            JSONArray ar = new JSONArray(content);
            List<User> userList = new ArrayList<User>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                User user = new User();

                user.setUserName(obj.getString("user_name"));
                user.setUserType(obj.getString("user_type"));
                user.setCustomerID(obj.getInt("customer_id"));

                userList.add(user);
            }

            return userList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


}
