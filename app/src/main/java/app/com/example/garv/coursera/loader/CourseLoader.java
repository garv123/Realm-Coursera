package app.com.example.garv.coursera.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import app.com.example.garv.coursera.Realm.Course;
import app.com.example.garv.coursera.Realm.Instructor;
import app.com.example.garv.coursera.Realm.Partner;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by garv on 27/11/15.
 */
public class CourseLoader extends AsyncTaskLoader<RealmResults<Course>> {
    Realm realm;

    @Override
    public RealmResults<Course> loadInBackground() {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        URL url = null;
        String courseJsonString;
        realm = Realm.getInstance(getContext());
        try {
            url = new URL("https://api.coursera.org/api/courses.v1/?includes=partnerIds,instructorIds&fields=instructors.v1(firstName,lastName,suffix)&fields=partners.v1(name,shortName,description)&fields=photoUrl,partnerIds,instructorIds");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            courseJsonString = buffer.toString();
            parseJson(courseJsonString);
            return realm.where(Course.class).findAll();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public void parseJson(String courseJsonString)  {
        try {
            JSONObject jsonCourse = new JSONObject(courseJsonString);
            JSONArray courseArray = jsonCourse.getJSONArray("elements");
            JSONObject linked = jsonCourse.getJSONObject("linked");
            JSONArray partnerArray = linked.getJSONArray("partners.v1");
            JSONArray instructorArray = linked.getJSONArray("instructors.v1");
            realm.beginTransaction();
            realm.createOrUpdateAllFromJson(Instructor.class, instructorArray);
            realm.createOrUpdateAllFromJson(Partner.class, partnerArray);
            realm.commitTransaction();

            for(int i =0; i<courseArray.length();i++) {
                JSONObject item = courseArray.getJSONObject(i);
                JSONArray instructorIds = item.getJSONArray("instructorIds");
                JSONArray partnerIds = item.getJSONArray("partnerIds");
                ArrayList<String> instructorData = new ArrayList<String>();
                if (instructorIds != null) {
                    for (int j=0;j<instructorIds.length();j++){
                        instructorData.add(instructorIds.get(j).toString());
                    }
                }
                ArrayList<String> partnerData = new ArrayList<String>();
                if (partnerIds != null) {
                    for (int j=0;j<partnerIds.length();j++){
                        partnerData.add(partnerIds.get(j).toString());
                    }
                }
                RealmQuery<Partner> queryPartner = realm.where(Partner.class);

                for (int j = 0; j < partnerData.size()-1; j++) {
                    queryPartner = queryPartner.equalTo("id", partnerData.get(j)).or();
                }
                queryPartner.equalTo("id", partnerData.get(partnerData.size() - 1));
                RealmResults<Partner> resultPartner = queryPartner.findAll();

                RealmQuery<Instructor> queryInstructor = realm.where(Instructor.class);
                for (int j = 0; j < instructorData.size()-1; j++) {
                    queryInstructor.equalTo("id", instructorData.get(j)).or();
                }
                queryInstructor.equalTo("id", instructorData.get(instructorData.size() - 1));
                RealmResults<Instructor> resultInstructor = queryInstructor.findAll();
                try {
                    realm.beginTransaction();
                    Course course = realm.createObject(Course.class);
                    course.setName(item.getString("name"));
                    course.setId(item.getString("id"));
                    for (Instructor instructor : resultInstructor) {
                        course.getInstructors().add(instructor);
                    }

                    Log.d("yo", "sa");

                    for (Partner partner : resultPartner) {
                        course.getPartners().add(partner);
                    }
                    realm.commitTransaction();
                }
                catch (Exception e) {
                    realm.cancelTransaction();
                }
            }
//            realm.where(Course.class).equalTo("id","v1-228").findAll()
            Log.d("partners", realm.where(Course.class).findFirst().getPartners().toString());
            Log.d("instructors", realm.where(Course.class).findFirst().getInstructors().toString());
            Log.d("Done", "YAO");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("ERR0R", e.toString());
        }


    }

    public CourseLoader(Context context) {
        super(context);
    }
}

