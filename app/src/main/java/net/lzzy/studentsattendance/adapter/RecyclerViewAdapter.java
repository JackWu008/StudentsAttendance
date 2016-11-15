package net.lzzy.studentsattendance.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import net.lzzy.studentsattendance.fragments.RecyclerViewFragment;
import net.lzzy.studentsattendance.fragments.TableFragment;

import java.util.List;



public class RecyclerViewAdapter extends FragmentStatePagerAdapter {
    private List<TableFragment.LocalCourse> localCourses;

    public RecyclerViewAdapter(FragmentManager fm, List<TableFragment.LocalCourse> localCourses) {
        super(fm);
        this.localCourses = localCourses;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return RecyclerViewFragment.newInstance(String.valueOf(position + 1));
    }


    @Override
    public int getCount() {
        return localCourses.size();
    }
}
