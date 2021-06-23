package com.wdjk.webdemo624.utils;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomUtilTest {


    @Test
    public void testIsRandomUtilRunnable(){
        for(int i=0;i<200;i++){
            assertNotNull(RandomUtil.generateRandomNumbers(-2000,50000));
        }
    }

    @Test
    public void testRandomUtilBorder(){
        for (int i = 0; i < 200; i++) {
            int a = RandomUtil.generateRandomNumbers(0,100);
            assertTrue(a>=0&&a<=100);
        }
    }

}