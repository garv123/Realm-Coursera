package app.com.example.garv.coursera.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.example.garv.coursera.R;

/**
 * Created by garv on 27/11/15.
 */
public class CourseAdapter extends ArrayAdapter<String> {
    Integer[] image_url={};
    String[] tutor_name={};
    String[] course_name={};
    String[] university_name={};
    Context c;

    public CourseAdapter(Context context,String[] tutor_name, Integer[] image_url, String[] course_name, String[] university_name) {
        super(context,R.layout.list_item_courses,tutor_name);
        this.c = context;
        this.image_url=image_url;
        this.tutor_name=tutor_name;
        this.course_name=course_name;
        this.university_name = university_name;
    }

    public class ViewHolder {
        TextView universityTextView;
        TextView tutorTextView;
        ImageView imageView;
        TextView courseTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_courses, parent, false);
        final ViewHolder holder = new ViewHolder();
        holder.courseTextView = (TextView) rowView.findViewById(R.id.university_name);
        holder.tutorTextView= (TextView) rowView.findViewById(R.id.tutor_name);
        holder.imageView  = (ImageView) rowView.findViewById(R.id.icon);
        holder.courseTextView = (TextView) rowView.findViewById(R.id.course_name);

        holder.imageView.setImageResource(image_url[position]);
        holder.courseTextView.setText(course_name[position]);
        holder.tutorTextView.setText(tutor_name[position]);
        holder.universityTextView.setText(university_name[position]);

        return rowView;
    }
}
