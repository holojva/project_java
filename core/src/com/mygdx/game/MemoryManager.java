package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

public class MemoryManager {
    private static final Preferences preferences = Gdx.app.getPreferences("User saves");
    public static void saveTableOfRecords(ArrayList<Integer> table) {
        Json json = new Json();
        String tableInString = json.toJson(table);
        preferences.putString("recordTable", tableInString);
        preferences.flush();
    }
    public static ArrayList<Integer> loadRecordsTable() {
        if (!preferences.contains("recordTable"))
            return null;

        String scores = preferences.getString("recordTable");
        Json json = new Json();
        ArrayList<Integer> table = json.fromJson(ArrayList.class, scores);
        return table;
    }

    public static void saveSoundSettings(boolean isOn) {
        preferences.putBoolean("isSoundOn", isOn);
        preferences.flush();
    }
    public static void saveMusicSettings(boolean isOn) {
        preferences.putBoolean("isMusicOn", isOn);
        preferences.flush();
    }
    public static void saveLevel(int level) {
        preferences.putInteger("level", level);
        preferences.flush();
    }
    public static int loadLevel() {
        return preferences.getInteger("level", 1);
    }
    public static boolean loadIsSoundOn() {
        System.out.println(preferences.getBoolean("isSoundOn", true));
        return preferences.getBoolean("isSoundOn", true);
    }
    public static boolean loadIsMusicOn() {
        return preferences.getBoolean("isMusicOn", true);
    }
}
