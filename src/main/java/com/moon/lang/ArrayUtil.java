package com.moon.lang;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.IntFunction;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 * @date 2018/9/12
 */
public final class ArrayUtil {
    private ArrayUtil() {
        noInstanceError();
    }

    public final static Class getArrayType(Class componentType) {
        return Array.newInstance(componentType, 0).getClass();
    }

    /*
     * ----------------------------------------------------------------
     * length
     * ----------------------------------------------------------------
     */

    public static int length(Object[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(boolean[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(double[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(float[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(long[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(int[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(short[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(byte[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(char[] arr) {
        return arr == null ? 0 : arr.length;
    }

    /*
     * ----------------------------------------------------------------
     * fill
     * ----------------------------------------------------------------
     */

    public static <T> T[] fillFrom(T[] arr, int fromIndex, T value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static boolean[] fillFrom(boolean[] arr, int fromIndex, boolean value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static double[] fillFrom(double[] arr, int fromIndex, double value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static float[] fillFrom(float[] arr, int fromIndex, float value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static long[] fillFrom(long[] arr, int fromIndex, long value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static int[] fillFrom(int[] arr, int fromIndex, int value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static short[] fillFrom(short[] arr, int fromIndex, short value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static byte[] fillFrom(byte[] arr, int fromIndex, byte value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static char[] fillFrom(char[] arr, int fromIndex, char value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    /*
     * ----------------------------------------------------------------
     * fill to； 纯粹为了一个返回值
     * ----------------------------------------------------------------
     */

    public static <T> T[] fill(T[] arr, int fromIndex, int toIndex, T value) {
        Arrays.fill(arr, fromIndex, toIndex, value);
        return arr;
    }

    public static boolean[] fill(boolean[] arr, int fromIndex, int toIndex, boolean value) {
        Arrays.fill(arr, fromIndex, toIndex, value);
        return arr;
    }

    public static double[] fill(double[] arr, int fromIndex, int toIndex, double value) {
        Arrays.fill(arr, fromIndex, toIndex, value);
        return arr;
    }

    public static float[] fill(float[] arr, int fromIndex, int toIndex, float value) {
        Arrays.fill(arr, fromIndex, toIndex, value);
        return arr;
    }

    public static long[] fill(long[] arr, int fromIndex, int toIndex, long value) {
        Arrays.fill(arr, fromIndex, toIndex, value);
        return arr;
    }

    public static int[] fill(int[] arr, int fromIndex, int toIndex, int value) {
        Arrays.fill(arr, fromIndex, toIndex, value);
        return arr;
    }

    public static short[] fill(short[] arr, int fromIndex, int toIndex, short value) {
        Arrays.fill(arr, fromIndex, toIndex, value);
        return arr;
    }

    public static byte[] fill(byte[] arr, int fromIndex, int toIndex, byte value) {
        Arrays.fill(arr, fromIndex, toIndex, value);
        return arr;
    }

    public static char[] fill(char[] arr, int fromIndex, int toIndex, char value) {
        Arrays.fill(arr, fromIndex, toIndex, value);
        return arr;
    }

    /*
     * ----------------------------------------------------------------
     * remove index, 删除指定位置数据，后面的数据前移一位； 返回原数组
     * ----------------------------------------------------------------
     */

    public static <T> T[] removeIndex(T[] arr, int index) {
        arr[removeIndex(arr, arr.length, index)] = null;
        return arr;
    }

    public static boolean[] removeIndex(boolean[] arr, int index) {
        arr[removeIndex(arr, arr.length, index)] = false;
        return arr;
    }

    public static double[] removeIndex(double[] arr, int index) {
        arr[removeIndex(arr, arr.length, index)] = 0;
        return arr;
    }

    public static float[] removeIndex(float[] arr, int index) {
        arr[removeIndex(arr, arr.length, index)] = 0;
        return arr;
    }

    public static long[] removeIndex(long[] arr, int index) {
        arr[removeIndex(arr, arr.length, index)] = 0;
        return arr;
    }

    public static int[] removeIndex(int[] arr, int index) {
        arr[removeIndex(arr, arr.length, index)] = 0;
        return arr;
    }

    public static short[] removeIndex(short[] arr, int index) {
        arr[removeIndex(arr, arr.length, index)] = 0;
        return arr;
    }

    public static byte[] removeIndex(byte[] arr, int index) {
        arr[removeIndex(arr, arr.length, index)] = 0;
        return arr;
    }

    public static char[] removeIndex(char[] arr, int index) {
        arr[removeIndex(arr, arr.length, index)] = 0;
        return arr;
    }

    private static int removeIndex(Object arr, int length, int index) {
        int lastIndex = length - 1;
        System.arraycopy(arr, index + 1, arr, index, lastIndex - index);
        return lastIndex;
    }

    /*
     * ----------------------------------------------------------------
     * splice : 总是返回一个新数组
     * ----------------------------------------------------------------
     */

    public static <T> T[] splice(T[] arr, int fromIndex, int count, T... elements) {
        return (T[]) spliceArray(arr, arr.length, elements, elements.length, fromIndex, count,
            l -> Array.newInstance(arr.getClass().getComponentType(), l));
    }

    public static boolean[] splice(boolean[] arr, int fromIndex, int count, boolean... elements) {
        return (boolean[]) spliceArray(arr, arr.length, elements, elements.length, fromIndex, count, boolean[]::new);
    }

    public static char[] splice(char[] arr, int fromIndex, int count, char... elements) {
        return (char[]) spliceArray(arr, arr.length, elements, elements.length, fromIndex, count, char[]::new);
    }

    public static byte[] splice(byte[] arr, int fromIndex, int count, byte... elements) {
        return (byte[]) spliceArray(arr, arr.length, elements, elements.length, fromIndex, count, byte[]::new);
    }

    public static short[] splice(short[] arr, int fromIndex, int count, short... elements) {
        return (short[]) spliceArray(arr, arr.length, elements, elements.length, fromIndex, count, short[]::new);
    }

    public static int[] splice(int[] arr, int fromIndex, int count, int... elements) {
        return (int[]) spliceArray(arr, arr.length, elements, elements.length, fromIndex, count, int[]::new);
    }

    public static long[] splice(long[] arr, int fromIndex, int count, long... elements) {
        return (long[]) spliceArray(arr, arr.length, elements, elements.length, fromIndex, count, long[]::new);
    }

    public static float[] splice(float[] arr, int fromIndex, int count, float... elements) {
        return (float[]) spliceArray(arr, arr.length, elements, elements.length, fromIndex, count, float[]::new);
    }

    public static double[] splice(double[] arr, int fromIndex, int count, double... elements) {
        return (double[]) spliceArray(arr, arr.length, elements, elements.length, fromIndex, count, double[]::new);
    }

    private static Object spliceArray(
        Object arr, int arrLen, Object elements, int elementsLen, int fromIndex, int count, IntFunction creator
    ) {
        if (fromIndex < 0) {
            throw new IllegalArgumentException("Invalid value of fromIndex: " + count);
        }
        if (count < 0) {
            throw new IllegalArgumentException("Invalid value of count: " + count);
        }
        Object value = creator.apply(arrLen + elementsLen - count);
        System.arraycopy(arr, 0, value, 0, fromIndex);
        System.arraycopy(elements, 0, value, fromIndex, elementsLen);
        if ((count = fromIndex + count) < arrLen) {
            System.arraycopy(arr, count, value, fromIndex + elementsLen, arrLen - count);
        }
        return value;
    }

    /*
     * ----------------------------------------------------------------
     * converters
     * ----------------------------------------------------------------
     */

    public static boolean[] toPrimitives(Boolean[] value) {
        return (boolean[]) transformArray(value, boolean[]::new);
    }

    public static char[] toPrimitives(Character[] value) {
        return (char[]) transformArray(value, char[]::new);
    }

    public static byte[] toPrimitives(Byte[] value) {
        return (byte[]) transformArray(value, byte[]::new);
    }

    public static short[] toPrimitives(Short[] value) {
        return (short[]) transformArray(value, short[]::new);
    }

    public static int[] toPrimitives(Integer[] value) {
        return (int[]) transformArray(value, int[]::new);
    }

    public static long[] toPrimitives(Long[] value) {
        return (long[]) transformArray(value, long[]::new);
    }

    public static float[] toPrimitives(Float[] value) {
        return (float[]) transformArray(value, float[]::new);
    }

    public static double[] toPrimitives(Double[] value) {
        return (double[]) transformArray(value, double[]::new);
    }

    public static Boolean[] toObjects(boolean[] value) {
        return (Boolean[]) transformArray(value, Boolean[]::new);
    }

    public static Character[] toObjects(char[] value) {
        return (Character[]) transformArray(value, Character[]::new);
    }

    public static Byte[] toObjects(byte[] value) {
        return (Byte[]) transformArray(value, Byte[]::new);
    }

    public static Short[] toObjects(short[] value) {
        return (Short[]) transformArray(value, Short[]::new);
    }

    public static Integer[] toObjects(int[] value) {
        return (Integer[]) transformArray(value, Integer[]::new);
    }

    public static Long[] toObjects(long[] value) {
        return (Long[]) transformArray(value, Long[]::new);
    }

    public static Float[] toObjects(float[] value) {
        return (Float[]) transformArray(value, Float[]::new);
    }

    public static Double[] toObjects(double[] value) {
        return (Double[]) transformArray(value, Double[]::new);
    }

    private static Object transformArray(Object value, IntFunction creator) {
        if (value == null) {
            return null;
        }
        final int length = Array.getLength(value);
        Object arr = creator.apply(length);
        System.arraycopy(value, 0, arr, 0, length);
        return arr;
    }
}
