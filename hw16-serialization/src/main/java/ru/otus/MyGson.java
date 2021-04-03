package ru.otus;

import ru.otus.serializer.SerializerHandler;
import ru.otus.serializer.SerializerHandlerImpl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.reflect.Modifier.isStatic;

public class MyGson {
    private SerializerHandler serializerHandler;

    public MyGson() {
        this.serializerHandler = new SerializerHandlerImpl();
    }

    public String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }

        List<Field> fields = Arrays.stream(obj.getClass().getDeclaredFields()).filter(field -> !(isStatic(field.getModifiers()))).collect(Collectors.toList());
        if (fields.isEmpty()) {
            return obj.toString();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                sb.append("\"").append(field.getName()).append("\"")
                        .append(":")
                        .append(serializerHandler.handle(field.get(obj)))
                        .append(",");
            } catch (IllegalAccessException e) {

            }
        }

        sb.replace(sb.length() - 1, sb.length(), "}");

        System.out.println("json = " + sb.toString());
        return sb.toString();
    }
}
