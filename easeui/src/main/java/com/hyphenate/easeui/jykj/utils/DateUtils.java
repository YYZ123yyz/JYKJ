package com.hyphenate.easeui.jykj.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {


    public static String getDateToString(long milSecond) {
        Date date = new Date(milSecond* 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
      //  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    public static Date getDate(String time){
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        Date date = null;
        try {
            date = sdr.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String getDate(Date data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String string = simpleDateFormat.format(data);
        return string;
    }


    /**
     * 是否小于当前日期
     * @param startTime 开始时间
     * @return true or false
     */
    public static boolean isLessThanCurrentDate(String startTime){
        String currentTime = com.hyphenate.easeui.jykj.utils.DateUtils.getDeviceTimeOfYMD();
        int compareTo=0;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date nowDate_Date=null;
        Date endDate_Date=null;
        try {
            nowDate_Date = sdf.parse(startTime);
            endDate_Date= sdf.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        compareTo = nowDate_Date.compareTo(endDate_Date);
        if (compareTo==-1) {
            return true;
        }
        return false;

    }

    public static boolean isLessThanEndDate(String startTime,String endTime){
        int compareTo=0;
        Date nowDate_Date=null;
        Date endDate_Date=null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            nowDate_Date = sdf.parse(getDeviceTimeOfYMD()+" "+startTime);
            endDate_Date= sdf.parse(getDeviceTimeOfYMD()+" "+endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        compareTo = nowDate_Date.compareTo(endDate_Date);
        if (compareTo==-1) {
            return true;
        }
        return false;

    }
    public static int getAgeFromBirthTime(String birthTimeString) {
        // 先截取到字符串中的年、月、日
        String strs[] = birthTimeString.trim().split("-");
        int selectYear = Integer.parseInt(strs[0]);
        int selectMonth = Integer.parseInt(strs[1]);
        int selectDay = Integer.parseInt(strs[2]);
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        int yearMinus = yearNow - selectYear;
        int monthMinus = monthNow - selectMonth;
        int dayMinus = dayNow - selectDay;

        int age = yearMinus;// 先大致赋值
        if (yearMinus < 0) {// 选了未来的年份
            age = 0;
        } else if (yearMinus == 0) {// 同年的，要么为1，要么为0
            if (monthMinus < 0) {// 选了未来的月份
                age = 0;
            } else if (monthMinus == 0) {// 同月份的
                if (dayMinus < 0) {// 选了未来的日期
                    age = 0;
                } else if (dayMinus >= 0) {
                    age = 1;
                }
            } else if (monthMinus > 0) {
                age = 1;
            }
        } else if (yearMinus > 0) {
            if (monthMinus < 0) {// 当前月>生日月
            } else if (monthMinus == 0) {// 同月份的，再根据日期计算年龄
                if (dayMinus < 0) {
                } else if (dayMinus >= 0) {
                    age = age + 1;
                }
            } else if (monthMinus > 0) {
                age = age + 1;
            }
        }
        return age;
    }

    // 根据时间戳计算年龄
    public static int getAgeFromBirthDate(long birthTimeLong) {
        Date date = new Date(birthTimeLong );
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String birthTimeString = format.format(date);
        return getAgeFromBirthTime(birthTimeString);
    }


    public static int THEME_DEVICE_DEFAULT_LIGHT = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
    public static int THEME_DEVICE_DEFAULT_DARK = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
    public static int THEME_TRADITIONAL = AlertDialog.THEME_TRADITIONAL;
    public static int THEME_HOLO_LIGHT = AlertDialog.THEME_HOLO_LIGHT;
    public static int THEME_HOLO_DARK = AlertDialog.THEME_HOLO_DARK;
    private static DatePickerDialog mDatePickerDialog;//日期选择器

    /**
     * 将字符串时间转为Long时间
     *
     * @param time yyyy-MM-dd HH:mm:ss:SSS
     */
    public static Long getLongTimeOfSSS(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
        }
        return 0L;
    }

    /**
     * 将字符串时间转为Long时间
     *
     * @param time yyyy-MM-dd HH:mm:ss
     */
    public static Long getLongTime(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
        }
        return 0L;
    }

    /**
     * 将字符串时间转为Long时间
     *
     * @param time yyyy-MM-dd
     */
    public static Long getLongTimeOfYMD(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
        }
        return 0L;
    }

    /**
     * 将Long时间转成String时间
     *
     * @return yyyy-MM-dd HH:mm:ss:SSS
     */
    public static String getStringTimeOfSSS(Long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        return sdf.format(date);
    }

    /**
     * 将Long时间转成String时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getStringTime(Long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 将Long时间转成String时间
     *
     * @return yyyy-MM-dd
     */
    public static String getStringTimeOfYMD(Long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 当前的时间(设备)
     *
     * @return yyyy-MM-dd HH:mm:ss:SSS
     */
    public static String getDeviceTimeOfSSS() {
        String date = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            date = df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            date = new Date().getTime() + "";//当前时间的long字符串
        }
        return date;
    }

    /**
     * 当前的时间(设备)
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDeviceTime() {
        String date = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            date = new Date().getTime() + "";//当前时间的long字符串
        }
        return date;
    }

    /**
     * 当前的时间(年月日)
     *
     * @return yyyy-MM-dd
     */
    public static String getDeviceTimeOfYMD() {
        String date = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date = df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 当前的时间(年月)
     *
     * @return yyyy-MM
     */
    public static String getDeviceTimeOfYM() {
        String date = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
            date = df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取某月最后一天(年月日)
     *
     * @return yyyy-MM
     */
    public static String getLastDayOfMonthOfYMD(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }

    /**
     * 获取某月最后一天(日)
     */
    public static int getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        return lastDay;
    }

    /**
     * 显示日期选择器
     *
     * @param themeLight true 白色背景; false 黑色背景
     */
    public static DateUtils showDatePickerDialog(Context context, boolean themeLight, String title, int year, int month, int day, OnDatePickerListener onDateTimePickerListener) {
        int themeId = AlertDialog.THEME_HOLO_LIGHT;//默认白色背景
        if (!themeLight) {
            themeId = AlertDialog.THEME_HOLO_DARK;//黑色背景
        }
        return showDatePickerDialog(context, themeId, title, year, month, day, onDateTimePickerListener);
    }

    /**
     * 显示日期选择器, 默认白色背景
     */
    public static DateUtils showDatePickerDialog(Context context, String title, int year, int month, int day, OnDatePickerListener onDateTimePickerListener) {
        return showDatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, title, year, month, day, onDateTimePickerListener);
    }

    /**
     * 显示日期选择器
     */
    public static DateUtils showDatePickerDialog(Context context, int themeId, String title, int year, int month, int day,
                                                       final OnDatePickerListener onDateTimePickerListener) {
        mDatePickerDialog = new DatePickerDialog(context, themeId, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;//月份加一
                if (onDateTimePickerListener != null) {
                    onDateTimePickerListener.onConfirm(year, month, dayOfMonth);
                }
            }

        }, year, month - 1, day);//月份减一

        mDatePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (onDateTimePickerListener != null) {
                    onDateTimePickerListener.onCancel();
                }
            }
        });

        if (!TextUtils.isEmpty(title)) {
            mDatePickerDialog.setTitle(title);
        }
        mDatePickerDialog.show();
        return new DateUtils();
    }

    /**
     * 隐藏年, 只显示月和日
     */
    public void setYearGone() {
        setSpecialDatePicker(1);
    }

    /**
     * 隐藏日, 只显示年和月
     */
    public void setDayGone() {
        setSpecialDatePicker(2);
    }

    private void setSpecialDatePicker(int state) {
        try {
            DatePicker dp = mDatePickerDialog.getDatePicker();

            NumberPicker view0 = (NumberPicker) ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(0); //获取最前一位的宽度
            NumberPicker view1 = (NumberPicker) ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(1); //获取中间一位的宽度
            NumberPicker view2 = (NumberPicker) ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2);//获取最后一位的宽度

            //年的最大值为2100
            //月的最大值为11
            //日的最大值为28,29,30,31
            int value0 = view0.getMaxValue();//获取第一个View的最大值
            int value1 = view1.getMaxValue();//获取第二个View的最大值
            int value2 = view2.getMaxValue();//获取第三个View的最大值

            if (state == 1) {//隐藏年, 只显示月和日
                if (value0 > 252) {
                    view0.setVisibility(View.GONE);
                } else if (value1 > 252) {
                    view1.setVisibility(View.GONE);
                } else if (value2 > 252) {
                    view2.setVisibility(View.GONE);
                }
            } else if (state == 2) {//隐藏日, 只显示年和月
                if (value0 > 25 && value0 < 252) {
                    view0.setVisibility(View.GONE);
                } else if (value1 > 25 && value1 < 252) {
                    view1.setVisibility(View.GONE);
                } else if (value2 > 25 && value2 < 252) {
                    view2.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示时间选择器
     */
    public static void showTimerPickerDialog(Context context, boolean themeLight, String title, int hourOfDay, int minute, boolean is24HourView, final OnTimerPickerListener onTimerPickerListener) {
        int themeId = AlertDialog.THEME_HOLO_LIGHT;//默认白色背景
        if (!themeLight) {
            themeId = AlertDialog.THEME_HOLO_DARK;//黑色背景
        }
        showTimerPickerDialog(context, themeId, title, hourOfDay, minute, is24HourView, onTimerPickerListener);
    }

    /**
     * 显示时间选择器, 默认白色背景
     */
    public static void showTimerPickerDialog(Context context, String title, int hourOfDay, int minute, boolean is24HourView, final OnTimerPickerListener onTimerPickerListener) {
        showTimerPickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, title, hourOfDay, minute, is24HourView, onTimerPickerListener);
    }

    /**
     * 显示时间选择器
     */
    public static void showTimerPickerDialog(Context context, int themeId, String title, int hourOfDay, int minute, boolean is24HourView, final OnTimerPickerListener onTimerPickerListener) {
        TimePickerDialog dialog = new TimePickerDialog(context, themeId, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (onTimerPickerListener != null) {
                    onTimerPickerListener.onConfirm(hourOfDay, minute);
                }
            }
        }, hourOfDay, minute, is24HourView);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (onTimerPickerListener != null) {
                    onTimerPickerListener.onCancel();
                }
            }
        });

        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        dialog.show();
    }

    /**
     * 日期选择器监听
     */
    public interface OnDatePickerListener {
        void onConfirm(int year, int month, int dayOfMonth);

        void onCancel();
    }

    /**
     * 时间选择器监听
     */
    public interface OnTimerPickerListener {
        void onConfirm(int hourOfDay, int minute);

        void onCancel();
    }


}
