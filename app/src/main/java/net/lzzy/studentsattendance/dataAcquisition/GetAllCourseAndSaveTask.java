package net.lzzy.studentsattendance.dataAcquisition;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.example.get.Course;
import com.example.get.GetInfoUtils;
import com.example.get.LoginException;

import net.lzzy.studentsattendance.R;
import net.lzzy.studentsattendance.fragments.TableFragment;
import net.lzzy.studentsattendance.utils.CourseFactory;
import net.lzzy.studentsattendance.utils.PromptlyToast;
import net.lzzy.studentsattendance.view.CustomDialog;


public class GetAllCourseAndSaveTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private CustomDialog progressDialog;
    private static final int MAX_WEEK = 20;
    private static final int MIN_WEEK = 1;
    private int currentWeek = 0;

    public GetAllCourseAndSaveTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        progressDialog.setTitle((int) (currentWeek / 20f * 100) + "%");
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new CustomDialog(context, R.style.custom_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel(true);
                PromptlyToast.getInstance(context).show("取消获取");
            }
        });
        progressDialog.setTitle("获取中");
        progressDialog.show();
    }

    public TableFragment.LocalCourse getCourse(Course currCourse) {
        TableFragment.LocalCourse localCourse = new TableFragment.LocalCourse();
        localCourse.setWeek(currCourse.getWeek());
        String raw;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                raw = currCourse.getRawCourse(i + 1, j + 1);
                if (raw.trim().length() <= 0)
                    buffer.append(" ").append("##");
                else
                    buffer.append(raw).append("##");
            }
            buffer.append("@@");
        }
        localCourse.setWeekRaw(buffer.toString());
        return localCourse;
    }

    private void savePreCourse(Course syllabus, int min) throws LoginException {
        Course current = syllabus;
        int week = Integer.valueOf(syllabus.getWeek());
        for (int i = week;  !isCancelled()&&i >= min ; i--) {
            publishProgress();
            currentWeek++;
            current = GetInfoUtils.getAnotherWeek(current, GetInfoUtils.DAY_PREVIOUS);
            TableFragment.LocalCourse localCourse = getCourse(current);
            CourseFactory.getInstance(context).addCourse(localCourse);

        }


    }

    private void saveAfterCourse(Course syllabus, int max) throws LoginException {
        Course current = syllabus;
        int week = Integer.valueOf(syllabus.getWeek());
        for (int i = week + 1; !isCancelled()&&i <= max; i++) {
            publishProgress();
            currentWeek++;
            current = GetInfoUtils.getAnotherWeek( current, GetInfoUtils.DAY_NEXT);
            TableFragment.LocalCourse localCourse = getCourse(current);
            CourseFactory.getInstance(context).addCourse(localCourse);

        }


    }

    private void saveAllCourse(Course course) throws LoginException {
        saveAfterCourse(course, MAX_WEEK);
        savePreCourse(course, MIN_WEEK);

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Course syllabus = GetInfoUtils.getNextWeek();
            TableFragment.LocalCourse localCourse = getCourse(syllabus);
            CourseFactory.getInstance(context).addCourse(localCourse);
            saveAllCourse(syllabus);
            return true;
        } catch (LoginException e) {
            e.printStackTrace();
            return false;
        }

    }
}
