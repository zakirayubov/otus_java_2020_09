package ru.otus.serializer;

import java.util.Arrays;
import java.util.List;

public class SerializerHandlerImpl implements SerializerHandler{

    @Override
    public String handle(Object obj) {
        if (obj == null) {
            return "null";
        }else if (obj instanceof String || obj instanceof Character) {
            return "\"" + obj + "\"";
        } else if (obj.getClass().isArray()) {
            return Arrays.toString((int[]) obj).replaceAll(" ", "");
        } else if (obj instanceof List) {
            return obj.toString().replaceAll(" ", "");
        }

        return obj.toString();
    }
}
