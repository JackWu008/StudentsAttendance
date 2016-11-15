package net.lzzy.studentsattendance.dataAcquisition;

import android.content.Context;
import android.os.AsyncTask;

import com.example.get.Course;
import com.example.get.GetInfoUtils;
import com.example.get.LoginException;


public class GetCurrentCourseTask extends AsyncTask<Void, String, Course> {
    private Context context;


    public GetCurrentCourseTask(Context context) {
        this.context = context;
    }

    @Override
    protected Course doInBackground(Void... params) {
        try {
            return GetInfoUtils.getAnotherWeek(GetInfoUtils.getNextWeek(), GetInfoUtils.DAY_PREVIOUS);
        } catch (LoginException e) {
            Course s = new Course();
            s.setInfo(e.getMessage());
            return s;
        }
    }
}
