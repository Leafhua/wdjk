package com.wdjk.webdemo624.utils;

import com.wdjk.webdemo624.constant.api.SetConst;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest {
    @Test
    public void testIsStringUtilRunnable(){
        assertNotNull(StringUtil.getTodayTwentyFourClockTimestamp());
    }

    @Test
    public void testStringUtilEqual(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, SetConst.TIME_TWENTY_FOUR_HOUR);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertEquals(String.valueOf(calendar.getTimeInMillis()),StringUtil.getTodayTwentyFourClockTimestamp());
    }
}