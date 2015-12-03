package app.com.example.garv.coursera;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import app.com.example.garv.coursera.R;
import app.com.example.garv.coursera.Realm.Course;
import app.com.example.garv.coursera.Realm.Instructor;
import app.com.example.garv.coursera.Realm.Partner;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailCourseListFragment extends Fragment {
    private Course course;
    private ImageLoader imageLoader;
    public DetailCourseListFragment() {
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.detail_course_fragment_detail, container, false);
        if(intent!=null){
            String course_id = intent.getStringExtra("course");
            Realm realm = Realm.getInstance(getActivity());
            RealmResults<Course> courses = realm.where(Course.class).equalTo("id", course_id).findAll();
            Course course = courses.first();
            String partnerName ="";
            String instructorFullName="";
            RealmList<Instructor> instructors = course.getInstructors();
            for(Instructor instructor: instructors) {
                if(instructor.getFullName()!="") {
                    instructorFullName += instructor.getFullName() + " ";
                }
            }

            RealmList<Partner> partners = course.getPartners();

            for(Partner partner: partners) {
                if(partner.getName()!="") {
                    partnerName += partner.getName() + " ";
                }
            }
            ((TextView)rootView.findViewById(R.id.detail_description)).setText(course.getDescription());
            ((TextView)rootView.findViewById(R.id.course_name)).setText(course.getName());
            ((TextView)rootView.findViewById(R.id.instructor_name)).setText(instructorFullName);
            ((TextView)rootView.findViewById(R.id.university_name)).setText(partnerName);
            ImageView imageView = ((ImageView) rootView.findViewById(R.id.icon));
            imageLoader.displayImage(course.getPhotoUrl(),imageView);

        }
        return rootView;
    }
}
