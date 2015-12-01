package app.com.example.garv.coursera.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import app.com.example.garv.coursera.R;
import app.com.example.garv.coursera.Realm.Course;
import app.com.example.garv.coursera.Realm.Instructor;
import app.com.example.garv.coursera.Realm.Partner;
import io.realm.RealmBaseAdapter;
import io.realm.RealmList;
import io.realm.RealmResults;


/**
 * Created by garv on 01/12/15.
 */
public class CourseRealmAdapter extends RealmBaseAdapter<Course> implements ListAdapter {
    RealmResults<Course> courses;
    public CourseRealmAdapter(Context context,RealmResults<Course> realmResults) {
        super(context, realmResults, true);
        courses = realmResults;

    }
    public class ViewHolder {
        TextView universityTextView;
        TextView tutorTextView;
        ImageView imageView;
        TextView courseTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_courses, parent, false);
            holder = new ViewHolder();
            holder.universityTextView = (TextView) convertView.findViewById(R.id.university_name);
            holder.tutorTextView= (TextView) convertView.findViewById(R.id.tutor_name);
            holder.imageView  = (ImageView) convertView.findViewById(R.id.icon);
            holder.courseTextView = (TextView) convertView.findViewById(R.id.course_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Course course = realmResults.get(position);
        String instructorFullName ="";
        String partnerName = "";
        RealmList<Instructor> instructors = course.getInstructors();
        for(Instructor instructor: instructors) {
            instructorFullName = instructorFullName + "," + instructor.getFullName();
        }

        RealmList<Partner> partners = course.getPartners();

        for(Partner partner: partners) {
            partnerName = partnerName + "," +partner.getName();
        }

        holder.courseTextView.setText(course.getName());
        holder.universityTextView.setText(partnerName);
        holder.tutorTextView.setText(instructorFullName);
        return convertView;
    }
}
