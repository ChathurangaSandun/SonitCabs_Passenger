package com.example.chathuranga_pamba.sonitcabs_passenger.Parsers;

import com.example.chathuranga_pamba.sonitcabs_passenger.models.JobDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pamba on 10/18/2015.
 */
public class JobDetailJSONParser {
    public static List<JobDetail> parseFeed(String content) {


        try {

            List<JobDetail> userList = new ArrayList<JobDetail>();



            JSONObject obj = new JSONObject(content);
            JobDetail jobDetail = new JobDetail();

            jobDetail.setJobId(obj.getInt("job_id"));
            jobDetail.setPlateNumber(obj.getString("plate_number"));
            jobDetail.setLatitude(obj.getDouble("latitude"));
            jobDetail.setLongtiitude(obj.getDouble("longtitude"));
            jobDetail.setFirstName(obj.getString("first_name"));
            jobDetail.setLastName(obj.getString("last_name"));
            jobDetail.setTelephoneNumber(obj.getInt("telephone_number"));


            userList.add(jobDetail);


            return userList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


}
