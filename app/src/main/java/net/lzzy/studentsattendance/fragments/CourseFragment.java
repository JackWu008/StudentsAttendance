package net.lzzy.studentsattendance.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import net.lzzy.studentsattendance.adapter.RecyclerViewAdapter;
import net.lzzy.studentsattendance.R;
import net.lzzy.studentsattendance.adapter.TableAdapter;
import net.lzzy.studentsattendance.activitys.MainActivity;
import net.lzzy.studentsattendance.activitys.TimeTableActivity;
import net.lzzy.studentsattendance.constants.Constants;
import net.lzzy.studentsattendance.dataAcquisition.GetAllCourseAndSaveTask;
import net.lzzy.studentsattendance.utils.CourseFactory;
import net.lzzy.studentsattendance.utils.PromptlyToast;
import net.lzzy.studentsattendance.utils.UserConfigInfoUtils;
import net.lzzy.studentsattendance.view.CustomAlertDialog;
import net.lzzy.studentsattendance.view.SelectWeekView;

import java.util.Date;
import java.util.List;


public class CourseFragment extends Fragment implements MainActivity.OnMenuClick {
    private View view;
    private SelectWeekView weekView;
    public static int currentWeek;
    private PopupWindow pop;
    private int height;
    private int width;
    private ViewPager pager;
    private List<TableFragment.LocalCourse> courses;
    private TextView tv_hint;
    private ImageView iv_arrow;
    private PagerAdapter courseAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentWeek = CourseFactory.getInstance(getContext()).getWeek(new Date(), 20);
        height = getResources().getDisplayMetrics().heightPixels;
        width = getResources().getDisplayMetrics().widthPixels;
        courses = CourseFactory.getInstance(getContext()).getAllCourses().getLocalCourses();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_course, container, false);

        pager = (ViewPager) view.findViewById(R.id.fragment_course_viewpaper);
        tv_hint = (TextView) view.findViewById(R.id.fragment_course_tv_hint);
        initView();
        return view;
    }

    private PagerAdapter getAdapter() {
        if (UserConfigInfoUtils.getCourseTableMode(getContext()))
            return new TableAdapter(getFragmentManager(), courses);
        else
            return new RecyclerViewAdapter(getFragmentManager(), courses);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.fragment_course_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if (courses.size() == 0)
            tv_hint.setVisibility(View.VISIBLE);
        courseAdapter = getAdapter();
        pager.setAdapter(courseAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                weekView.setClickPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        iv_arrow = (ImageView) view.findViewById(R.id.fragment_course_iv);
        View action_view = View.inflate(getContext(), R.layout.pop_course_menu, null);
        int popHeight = (int) (height * 0.15);
        int popWidth = (int) (width * 0.4);
        pop = new PopupWindow(action_view, popWidth, popHeight, true);
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        Button btn = (Button) view.findViewById(R.id.fragment_course_btn_week);
        weekView = new SelectWeekView(getActivity(), onItemClickListener, btn, iv_arrow);

        final ImageView iv_menu = (ImageView) view.findViewById(R.id.fragment_course_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.showAsDropDown(iv_menu);
            }
        });

        TextView tv_getData = (TextView) action_view.findViewById(R.id.pop_course_menu_tv_set_course_data);
        TextView tv_timeTable = (TextView) action_view.findViewById(R.id.pop_course_menu_tv_time_table);
        tv_getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                if (!Constants.isLogined) {
                    PromptlyToast.getInstance(getContext()).show("请先登录");
                    return;
                }

                new GetAllCourseAndSaveTask(getContext()) {
                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        if (isConn(getContext())) {
                            if (aBoolean) {
                                CourseFactory.getInstance(getContext()).getAllCourses();
                                courseAdapter.notifyDataSetChanged();
                                tv_hint.setVisibility(View.GONE);
                                PromptlyToast.getInstance(getContext()).show("获取成功");
                                currentWeek = CourseFactory.getInstance(getContext()).getWeek(new Date(), 20);
                                showCourse(currentWeek - 1);
                            } else
                                PromptlyToast.getInstance(getContext()).show("获取失败");
                        } else
                            setNetworkMethod(getContext());
                    }
                }.execute();
            }
        });
        tv_timeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                Intent intent = new Intent(getActivity(), TimeTableActivity.class);
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CourseFactory.getInstance(getContext()).getCourseCount() < 20) {
                    PromptlyToast.getInstance(getContext()).show("请点击右上角的菜单的获取课程表获取数据");
                    return;
                }
                weekView.show();
                iv_arrow.setImageResource(R.drawable.ic_course_arrow_up);
            }
        });

    }

    private void showCourse(int position) {
        pager.setCurrentItem(position);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showCourse(position);
        }
    };

    public static boolean isConn(Context context) {
        boolean bisConnFlag = false;
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if (network != null) {
            bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
        }

        return bisConnFlag;
    }

    public void setNetworkMethod(final Context context) {
        final CustomAlertDialog custom_dialog = new CustomAlertDialog(getContext());
        custom_dialog.setTitle("提示");
        custom_dialog.setMessage("网络连接不可用,是否进行设置?");
        custom_dialog.setPositiveButton("设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                context.startActivity(intent);
            }
        });
        custom_dialog.setNegativeButton("取消", null);
        custom_dialog.show();
    }


    @Override
    public void onMenuClick() {
        showCourse(currentWeek - 1);
    }
}
