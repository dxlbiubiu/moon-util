package com.moon.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @author benshaoye
 */
public final class IDCard18 {
    /**
     * 身份证号码
     */
    private final String value;
    /**
     * 出生年份
     */
    private final int birthdayYear;
    /**
     * 出生月份
     */
    private final int birthdayMonth;
    /**
     * 出生日子
     */
    private final int birthdayDay;
    /**
     * 性别
     */
    private final int gender;

    public IDCard18(String value) {
        this.value = Objects.requireNonNull(value);
        this.birthdayYear = Integer.valueOf(value.substring(6, 10));
        this.birthdayMonth = Integer.valueOf(value.substring(10, 12));
        this.birthdayDay = Integer.valueOf(value.substring(12, 14));
        this.gender = (value.charAt(16) - 48) % 2;
    }

    public final static IDCard18 of(String idCardValue) {
        return new IDCard18(idCardValue);
    }

    public boolean isMale() {
        return gender == 1;
    }

    public boolean isFemale() {
        return gender == 0;
    }

    public boolean isIllegal() {
        return isIllegal(value);
    }

    public boolean isLegal() {
        return isLegal(value);
    }

    public Date getBirthday() {
        Calendar day = Calendar.getInstance();
        day.clear();
        day.set(Calendar.YEAR, birthdayYear);
        day.set(Calendar.MONTH, birthdayMonth - 1);
        day.set(Calendar.DAY_OF_MONTH, birthdayDay);
        return day.getTime();
    }

    public final static boolean isLegal(String value) {
        return !isIllegal(value);
    }

    public final static boolean isIllegal(String value) {
        if (value == null || value.length() != 18) {
            return false;
        }
        int result = 0;
        final int end = 17, mod = 11;
        final int[] CODES = {'1', '0', 'X', '9',
            '8', '7', '6', '5', '4', '3', '2'};
        final int[] codes = {7, 9, 10, 5, 8, 4,
            2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        for (int i = 0, curr; i < end; i++) {
            curr = value.charAt(i);
            if (curr < 48 || curr > 57) {
                return false;
            }
            result += (curr - 48) * codes[i];
        }
        final int index = result % mod;
        return (index >= 0 && index < mod)
            && (value.charAt(end) == CODES[index]);
    }

    public String getAge() {
        return "";
    }

    public String getNominalAge() {
        return "";
    }
}
