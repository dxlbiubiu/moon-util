package com.moon.lang;

import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author benshaoye
 */
public class ArrayUtilTest {
    static final Assertions assertions = Assertions.of();

    @Test
    void testSplice() {
        boolean[] arr = {false, true, false, true};
        assertions.assertEquals(JoinerUtil.join(arr, ","), "false,true,false,true");
        boolean[] newArr = ArrayUtil.splice(arr, 1, 1, true, true);
        assertions.assertEquals(JoinerUtil.join(newArr, ","), "false,true,true,false,true");

        int[] ints = {1, 2, 3, 4, 5};
        assertions.assertEquals(JoinerUtil.join(ints, ","), "1,2,3,4,5");
        int[] newInts = ArrayUtil.splice(ints, 1, 4, 11, 11, 11, 11);
        assertions.assertEquals(JoinerUtil.join(newInts, ","), "1,11,11,11,11");
        newInts = ArrayUtil.splice(ints, 1, 1, 11, 11, 11, 11);
        assertions.assertEquals(JoinerUtil.join(newInts, ","), "1,11,11,11,11,3,4,5");

        Object[] objects = {1, 2, 3, 4, 5};
        assertions.assertEquals(JoinerUtil.join(objects, ","), "1,2,3,4,5");
        Object[] newObjects = ArrayUtil.splice(objects, 1, 4, 11, 11, 11, 11);
        assertions.assertEquals(JoinerUtil.join(newObjects, ","), "1,11,11,11,11");
    }
}
