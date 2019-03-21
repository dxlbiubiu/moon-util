package com.moon.models;

import com.moon.enums.EnumDescriptor;

import java.util.Objects;

/**
 * @author benshaoye
 */
public final class KeyValue {
    private String key;
    private String value;

    public KeyValue() {
    }

    public static KeyValue of() {return new KeyValue();}

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static KeyValue of(String key, String value) {
        return new KeyValue(key, value);
    }

    public static <T extends Enum<T>> KeyValue of(T enumItem) {
        KeyValue one = of();
        one.key = enumItem.name();
        one.value = enumItem instanceof EnumDescriptor
            ? ((EnumDescriptor) enumItem).getText() : enumItem.name();
        return one;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyValue keyValue = (KeyValue) o;
        return Objects.equals(key, keyValue.key) &&
            Objects.equals(value, keyValue.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KeyValue{");
        sb.append("key='").append(key).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
