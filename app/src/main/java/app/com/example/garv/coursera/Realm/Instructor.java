package app.com.example.garv.coursera.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by garv on 28/11/15.
 */
public class Instructor extends RealmObject {
    @PrimaryKey
    private String id;
    private String fullName;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }



}
