package com.example.librouter;

import android.app.Activity;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

public class Router {

    private final Map<String, Map<String, Class<?>>> groupMap = new HashMap<>();
    private final Map<String, Class<?>> routerMap = new HashMap<>();

    private Router() {
    }

    public final static class Holder {
        static Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * @param path eg. /main/MainActivity
     * @param clz
     */
    public void register(String path, Class<?> clz) {
        String[] strArray = path.split("/");
        if (strArray.length > 2) {
            String groupName = strArray[1];
            String routerName = path;
            Map<String, Class<?>> group = null;

            if (groupMap.containsKey(groupName)) {
                group = groupMap.get(groupName);
            }
            if (group == null) {
                group = new HashMap<>();
                groupMap.put(groupName, group);
            }
            group.put(routerName, clz);
        }
    }

    public void startActivity(Activity activity, String path) {
        String[] strArray = path.split("/");
        if (strArray.length > 2) {
            String groupName = strArray[1];
            String routerName = path;
            Map<String, Class<?>> group = null;

            if (groupMap.containsKey(groupName)) {
                group = groupMap.get(groupName);
            }

            if (group != null && group.containsKey(routerName)) {
                Class<?> clz = group.get(routerName);
                activity.startActivity(new Intent(activity, clz));
            }
        }
    }
}
