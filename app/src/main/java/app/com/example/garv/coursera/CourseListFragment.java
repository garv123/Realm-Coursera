package app.com.example.garv.coursera;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import app.com.example.garv.coursera.Realm.Course;
import app.com.example.garv.coursera.adapter.CourseAdapter;
import app.com.example.garv.coursera.adapter.CourseRealmAdapter;
import app.com.example.garv.coursera.loader.CourseLoader;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;

import android.support.v4.app.LoaderManager;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by garv on 27/11/15.
 */
public class CourseListFragment extends Fragment implements LoaderManager.LoaderCallbacks<RealmResults<Course>> {
    private ArrayAdapter<String> mCourseAdapter;
    private ListView listView;

    @Override
    public String toString() {
        return super.toString();
    }

    public CourseListFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.listview_course);
        getLoaderManager().initLoader(1, null, this).forceLoad();
        return rootView;

    }


    @Override
    public Loader<RealmResults<Course>> onCreateLoader(int id, Bundle args) {
        return new CourseLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<RealmResults<Course>> loader, RealmResults<Course> data) {
        Realm realm = Realm.getInstance(getActivity());
        CourseRealmAdapter adapter = new CourseRealmAdapter(getContext(),realm.where(Course.class).findAll());
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<RealmResults<Course>> loader) {

    }


}



