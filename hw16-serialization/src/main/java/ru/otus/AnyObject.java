package ru.otus;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class AnyObject {
    private final String str;
    private final int[] array;
    private final Integer obj;
    private final Collection<Integer> list;

    public AnyObject(List<Integer> list, String str, int[] array, Integer obj) {
        this.list = list;
        this.str = str;
        this.array = array;
        this.obj = obj;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnyObject anyObject = (AnyObject) o;
        return Objects.equals(list, anyObject.list) &&
                Objects.equals(str, anyObject.str) &&
                Arrays.equals(array, anyObject.array) &&
                Objects.equals(obj, anyObject.obj);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(list, str, obj);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    @Override
    public String toString() {
        return "AnyObject{" +
                "list=" + list +
                ", str='" + str + '\'' +
                ", msv=" + Arrays.toString(array) +
                ", obj=" + obj +
                '}';
    }
}
