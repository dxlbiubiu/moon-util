package com.moon.enums;

import com.moon.util.ListUtil;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * @author benshaoye
 */
class DateFormatEnumTestTest {

    @Test
    void testWith() {
        ListUtil.ofArrayList(DateFormatEnum.values()).forEach(item -> {
            item.with("yyyy-MM-dd");
        });
    }
}