package ru.otus;


import com.google.gson.Gson;

import java.util.List;

public class SerializationApp {
    public static void main(String[] args) {
        MyGson myGson = new MyGson();
        AnyObject obj = new AnyObject(List.of(1, 57, 55, 77), "String", new int[]{2, 3}, 4);
        String myJson = myGson.toJson(obj);

        Gson gson = new Gson();
        AnyObject obj2 = gson.fromJson(myJson, AnyObject.class);
        System.out.println(obj.equals(obj2));
    }
}
