package app.com.example.garv.coursera.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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
    ImageLoader imageLoader;
    public CourseRealmAdapter(Context context,RealmResults<Course> realmResults) {
        super(context, realmResults, true);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

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
            instructorFullName += instructor.getFullName() + " ";
        }

        RealmList<Partner> partners = course.getPartners();

        for(Partner partner: partners) {
            partnerName += partner.getName() + " ";
        }
        holder.imageView.setImageResource(R.drawable.ic_launcher);
        imageLoader.displayImage(course.getPhotoUrl(), holder.imageView);
        holder.courseTextView.setText(course.getName());
        holder.universityTextView.setText(partnerName);
        holder.tutorTextView.setText(instructorFullName);

        HashMap<Integer,Integer> hash = new HashMap<Integer,Integer>();
        Stack<Integer> s = new Stack<Integerr>();
        return convertView;
    }
}
