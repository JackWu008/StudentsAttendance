package com.example.get;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/9.
 */
public class GetInfoUtils {
    private static final String LZZY_LOGIN_POST = "http://jw.lzzy.net/st/login.aspx";
    private static final String POST_PERSONAL = "http://jw.lzzy.net/st/student/st_edit.aspx";
    public static final String INFO_NAME = "name";
    public static final String INFO_BIRTHDAY = "birthday";
    public static final String INFO_SERIES = "series";
    public static final String INFO_ENTER_SCHOOL_TIME = "enter school time";
    public static final String INFO_SEX = "sex";
    public static final String INFO_ID = "id";
    public static final String INFO_PHONE = "phone";
    public static final String INFO_PROFESSION = "profession";
    public static final String INFO_WEIGHT = "weight";
    public static final String INFO_HEIGHT = "height";
    public static final String INFO_STATE = "state";
    public static final String INFO_CENSUS = "census";
    public static final String INFO_IDENTIFY = "identify";
    public static final String INFO_HOME_ADDRESS = "home address";
    public static final String INFO_NATION = "nation";
    public static final String INFO_ClASS = "class";
    public static final String INFO_GRADE = "2015";
    public static final String INFO_IMG = "img";
    public static final String INFO_EMAIL = "e-mail";
    private static final String HTML_SELECT_NAME = "input[name=txt_姓名]";
    private static final String HTML_SELECT_BIRTHDAY = "input[name=txt_出生日期]";
    private static final String HTML_SELECT_SERIES = "input[name=txt_系院]";
    private static final String HTML_SELECT_ENTER_SCHOOL_TIME = "input[name=txt_入学时间]";
    private static final String HTML_SELECT_ID = "input[name=txt_学号]";
    private static final String HTML_SELECT_WEIGHT = "input[name=txt_体重]";
    private static final String HTML_SELECT_SEX = "input[checked=checked]";
    private static final String HTML_SELECT_PHONE = "input[name=txt_学生手机]";
    private static final String HTML_SELECT_PROFESSION = "input[name=txt_专业]";
    private static final String HTML_SELECT_HEIGHT = "input[name=txt_身高]";
    private static final String HTML_SELECT_GRADE = "input[name=txt_年级]";
    private static final String HTML_SELECT_STATE = "input[name=txt_目前状态]";
    private static final String HTML_SELECT_CENSUS = "input[name=txt_籍贯]";
    private static final String HTML_SELECT_IDENTIFY = "input[name=txt_身份证号码]";
    private static final String HTML_SELECT_IMAGE = "img[id=Img_相片]";
    private static final String HTML_SELECT_HOME_ADDRESS = "input[name=txt_家庭通讯地址]";
    private static final String HTML_SELECT_HOME_NATION = "input[name=txt_民族]";
    private static final String HTML_SELECT_CLASS = "input[name=txt_班别]";
    private static final String HTML_SELECT_COURSE_WEEK = "span[id=lbl_b_周次]";
    private static final String HTML_SELECT_VIEWSTATE = "input[name=__VIEWSTATE]";
    private static final String HTML_SELECT_EVENTVALIDATION = "input[name=__EVENTVALIDATION]";
    private static final String HTML_SELECT_EMAIL = "input[name=txt_E_mail]";
    private static final String EVENTTARGET = "__EVENTTARGET";
    private static final String POST_GET_COURSE_POST = "http://jw.lzzy.net/st/student/st_p.aspx";
    private static final Random RANDOM = new Random();
    private static MyCookieJar cookieStore = new MyCookieJar();
    public final static int DAY_NEXT = 0;
    public final static int DAY_PREVIOUS = 1;
    public static MyCookieJar getCookieStore() {
        return cookieStore;
    }

    public static List<Comprehensive> getXueFen(String id) throws LoginException {
        List<Comprehensive> list = new ArrayList<>();
        try {
            OkHttpClient client = new OkHttpClient.Builder().cookieJar(cookieStore).build();
            Request request = new Request.Builder().url("http://jw.lzzy.net/st/student/st_c.aspx").post(new FormBody.Builder().add("id1", id).add("id2", "9").add("code", "4121450782").build()).build();
            String html = getHtml(client, request);
            if (html == null)
                throw new LoginException("获取失败");
            Document document = Jsoup.parse(html);
            if (!document.title().contains("综合测评积分明细")) {
                if (document.title().contains("欢迎使用数字化校园信息平台"))
                    throw new LoginException("未登录");
                throw new LoginException("获取失败");
            }
            Comprehensive.toalscore = document.getElementById("lbl_总积分").text();
            Elements elements = document.getElementsByClass("style751").get(0).getElementsByTag("tr");
            for (int i = 1; i < elements.size(); i++) {
                Elements lean = elements.get(i).getElementsByTag("td");
                list.add(new Comprehensive(lean.get(0).text(), lean.get(1).text(), lean.get(2).text(), lean.get(3).text(),
                        lean.get(4).text()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<MyLean> getMyLeaning(String id) throws LoginException {
        List<MyLean> myLeans = new ArrayList<>();
        OkHttpClient client = new OkHttpClient.Builder().cookieJar(GetInfoUtils.getCookieStore()).build();
        Request request = new Request.Builder().url("http://jw.lzzy.net/st/student/st_g.aspx").post(
                new FormBody.Builder().add("id1", id).add("id2", "8").add("code", "4056851293").build())
                .build();
        try {
            String html = getHtml(client, request);
            if (html == null)
                throw new LoginException("获取信息失败");
            Document document = Jsoup.parse(html);
            if (!document.title().contains("成绩表")) {
                if (document.title().contains("欢迎使用数字化校园信息平台"))
                    throw new LoginException("未登录");
                throw new LoginException("获取失败");
            }
            MyLean.totalCourse = document.getElementById("LinkButton_课程数").text();
            MyLean.totalScore = document.getElementById("LinkButton__总学分").text();
            MyLean.failAmount = document.getElementById("LinkButton_不及格").text();
            Element element = document.getElementById("GridView_学生成绩");
            Elements score = element.select("tr[bgcolor]");
            for (int i = 0; i < score.size(); i++) {
                Elements scoreList = score.get(i).attr("th", "scope").children();
                MyLean lean = new MyLean(scoreList.get(0).text(), scoreList.get(1).text(), scoreList.get(2).text(), scoreList.get(3).text(),
                        scoreList.get(4).text());
                myLeans.add(lean);
            }
        } catch (IOException e) {
            throw new LoginException(e.getMessage());
        }
        myLeans.remove(0);
        return myLeans;

    }

    public static Map<String, String> getPersonalInfo() throws LoginException {
        OkHttpClient client = new OkHttpClient.Builder().cookieJar(cookieStore).build();
        Map<String, String> map = new HashMap<>();
        String personalHtml = null;
        try {
            personalHtml = getHtml(client, new Request.Builder().url(POST_PERSONAL).build());
            if (personalHtml == null)
                throw new LoginException("个人信息获取失败");
            Document document = Jsoup.parse(personalHtml);
            if (!document.title().contains("学生基本信息"))
                new LoginException("登录失效");
            map.put(INFO_NAME, document.select(HTML_SELECT_NAME).val());
            map.put(INFO_BIRTHDAY, document.select(HTML_SELECT_BIRTHDAY).val().substring(0, 10));
            map.put(INFO_SERIES, document.select(HTML_SELECT_SERIES).val());
            map.put(INFO_ENTER_SCHOOL_TIME, document.select(HTML_SELECT_ENTER_SCHOOL_TIME).val().substring(0, 10));
            map.put(INFO_ID, document.select(HTML_SELECT_ID).val());
            map.put(INFO_WEIGHT, document.select(HTML_SELECT_WEIGHT).val());
            map.put(INFO_SEX, document.select(HTML_SELECT_SEX).val());
            map.put(INFO_PHONE, document.select(HTML_SELECT_PHONE).val());
            map.put(INFO_PROFESSION, document.select(HTML_SELECT_PROFESSION).val());
            map.put(INFO_HEIGHT, document.select(HTML_SELECT_HEIGHT).val());
            map.put(INFO_STATE, document.select(HTML_SELECT_STATE).val());
            map.put(INFO_CENSUS, document.select(HTML_SELECT_CENSUS).val());
            map.put(INFO_IDENTIFY, document.select(HTML_SELECT_IDENTIFY).val());
            map.put(INFO_HOME_ADDRESS, document.select(HTML_SELECT_HOME_ADDRESS).val());
            map.put(INFO_NATION, document.select(HTML_SELECT_HOME_NATION).val());
            map.put(INFO_ClASS, document.select(HTML_SELECT_CLASS).val());
            map.put(INFO_GRADE, document.select(HTML_SELECT_GRADE).val());
            map.put(INFO_EMAIL, document.select(HTML_SELECT_EMAIL).val());
            map.put(INFO_IMG, document.select(HTML_SELECT_IMAGE).attr("src").substring(7));
            return map;
        } catch (IOException e) {
            throw new LoginException(e.getMessage());
        }

    }

    public static Course getNextWeek() throws LoginException {

        try {
            OkHttpClient client = new OkHttpClient.Builder().cookieJar(cookieStore).build();

            String html = getHtml(client, new Request.Builder().url(POST_GET_COURSE_POST).build());
            if (html == null)
                throw new LoginException("网络出错,获取失败");
            return getCourse(html);
        } catch (IOException e) {
            e.printStackTrace();
            throw new LoginException(e.getMessage());
        }

    }



    private static Course getCourse(String html) {
        Document doc = Jsoup.parse(html);
        Course nextCourse = new Course();
        nextCourse.setWeek(doc.select(HTML_SELECT_COURSE_WEEK).text());
        nextCourse.setVIEWSTATE(doc.select(HTML_SELECT_VIEWSTATE).val());
        nextCourse.setEVENTVALIDATION(doc.select(HTML_SELECT_EVENTVALIDATION).val());
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 6; j++) {
                String raw = doc.getElementById(nextCourse.FORMAT[i][j]).text();
                nextCourse.setRawCourse(i + 1, j + 1, raw);
                nextCourse.setCourseName(i + 1, j + 1, CourseUtils.getCourseName(raw));
                nextCourse.setCourseTime(i + 1, j + 1, CourseUtils.getCourseTime(raw));
            }
        return nextCourse;
    }

    public static Course getAnotherWeek(Course course, int day) throws LoginException {
        try {
            OkHttpClient client = new OkHttpClient.Builder().cookieJar(cookieStore).build();
            String html = getHtml(client,
                    new Request.Builder().url(POST_GET_COURSE_POST).post(getAnotherParam(day, course)).build());
            if (html == null)
                throw new LoginException("网络出错,获取失败");
            return getCourse(html);
        } catch (IOException e) {
            throw new LoginException(e.getMessage());
        }

    }

    private static FormBody getAnotherParam(int day, Course course) {
        FormBody.Builder builder = new FormBody.Builder().add("__EVENTARGUMENT", "").add("__LASTFOCUS", "")
                .add("__EVENTVALIDATION", course.getEVENTVALIDATION()).add("__VIEWSTATE", course.getVIEWSTATE())
                .add("_cbo_学年学期", "2016-2017学年第一学期");

        switch (day) {
            case DAY_NEXT:
                builder.add(EVENTTARGET, "LinkButton_下一周");
                break;
            case DAY_PREVIOUS:
                builder.add(EVENTTARGET, "LinkButton_上一周");
                break;
        }
        return builder.build();
    }

    private static FormBody getLoginParam(String id, String password) {
        return new FormBody.Builder()
                .add("__VIEWSTATE",
                        "/wEPDwUJOTYxNDY3OTc0D2QWAgIBD2QWAgIHDxBkDxYBAgEWAQUJ6L6F5a+85ZGYZGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFDUJ1dHRvbl/nmbvpmYY5+nSLBhHqz30JO3nWw0VfDqWktA==")
                .add("__EVENTVALIDATION",
                        "/wEWCQLeuKaSDQLep8vjCAKu8uE5AvKm2soEAvLJzeAMAvWZ8TkC6fHPnw8CwsjN4QICyKiipgEA9oXb2ioPAMKqh7by35bI/EDoHg==")
                .add("txt_卡学号", id).add("txt_密码", password).add("Rad_角色", "学生")
                .add("Button_登陆.x", String.valueOf(RANDOM.nextInt(45) + 5))
                .add("Button_登陆.y", String.valueOf(RANDOM.nextInt(45) + 5)).build();

    }

    public static void login(String id, String password) throws LoginException {
        OkHttpClient client = new OkHttpClient.Builder().cookieJar(cookieStore).build();

        try {
            Response response = client
                    .newCall(new Request.Builder().url(LZZY_LOGIN_POST).post(getLoginParam(id, password)).build())
                    .execute();
            String html = response.body().string();
            if (html == null) {
                throw new LoginException("登录失败");
            }
            String title = Jsoup.parse(html).title();
            if (!title.contains("我的信息系统")) {
                throw new LoginException("账号或密码错误");
            }
        } catch (IOException e) {
            throw new LoginException(e.getMessage());
        }
    }

    private static String getHtml(OkHttpClient client, Request request) throws IOException {
        Response response = client.newCall(request).execute();
        return response.body().string();

    }

}
