package com.moon.modals;

import com.moon.enums.EnumDescriptor;

import java.util.Objects;

/**
 * @author benshaoye
 */
public final class IDName {
    private String id;
    private String name;

    public IDName() {
    }

    public static IDName of() {return new IDName();}

    public IDName(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static IDName of(String id, String name) {
        return new IDName(id, name);
    }

    public static <T extends Enum<T>> IDName of(T enumItem) {
        IDName one = of();
        one.id = enumItem.name();
        one.name = enumItem instanceof EnumDescriptor
            ? ((EnumDescriptor) enumItem).getText() : enumItem.name();
        return one;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IDName idName = (IDName) o;
        return Objects.equals(id, idName.id) &&
            Objects.equals(name, idName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IDName{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
