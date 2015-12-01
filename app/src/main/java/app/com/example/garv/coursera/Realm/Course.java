package app.com.example.garv.coursera.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by garv on 28/11/15.
 */

public class Course extends RealmObject {
    @PrimaryKey
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Partner> getPartners() {
        return partners;
    }

    public void setPartners(RealmList<Partner> partners) {
        this.partners = partners;
    }

    private String name;

    public RealmList<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(RealmList<Instructor> instructors) {
        this.instructors = instructors;
    }

    private RealmList<Instructor> instructors;
    private RealmList<Partner> partners;

}