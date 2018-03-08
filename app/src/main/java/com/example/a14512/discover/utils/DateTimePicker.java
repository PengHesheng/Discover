package com.example.a14512.discover.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.a14512.discover.R;

import java.util.Calendar;

/**
 * Created by hasee on 2017/8/24.
 */

public class DateTimePicker {

    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private java.util.Calendar mCalendar;
    private Activity mActivity;
    private LayoutInflater mLayoutInflater;
    private int year, month, day, hour, minute;


    public DateTimePicker(Activity activity) {
        this.mActivity = activity;
        mLayoutInflater = LayoutInflater.from(activity);
    }

    @SuppressLint("DefaultLocale")
    public void setDatePicker (final TextView textView){
        mCalendar = Calendar.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View view = mLayoutInflater.inflate(R.layout.item_date_time_picker, null);
        mDatePicker = (DatePicker) view.findViewById(R.id.datepicker);
        mTimePicker = (TimePicker) view.findViewById(R.id.timepicker);

        mCalendar.setTimeInMillis(System.currentTimeMillis());
        mDatePicker.setCalendarViewShown(false);
        mDatePicker.init(mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH),
                null);

        mTimePicker.setIs24HourView(false);
        mTimePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
        mTimePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));

        builder.setView(view);
        builder.setTitle("设置时间");
        builder.setNegativeButton("确定", (dialog, which) -> {
            year = mDatePicker.getYear();
            month = mDatePicker.getMonth() + 1;
            day = mDatePicker.getDayOfMonth();
            hour = mTimePicker.getCurrentHour();
            minute = mTimePicker.getCurrentMinute();

            StringBuffer stringBuffer = new StringBuffer();

            stringBuffer.append(year).append("/")
                    .append(String.format("%02d", month)).append("/")
                    .append(day).append("\n")
                    .append(String.format("%02d", hour)).append(":")
                    .append(String.format("%02d", minute));
            textView.setText(stringBuffer);
            dialog.cancel();
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


}
