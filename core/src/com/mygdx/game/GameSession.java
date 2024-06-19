package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;

import java.sql.Time;
import java.util.ArrayList;

public class GameSession {
    long sessionStartTime;
    long sessionPauseTime;
    long nextTrashSpawnTime;
    private int score;
    int destructedTrashNumber;
    GameState state = GameState.PLAYING;

    public void startGame() {
        sessionStartTime = TimeUtils.millis();
        nextTrashSpawnTime = sessionStartTime + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                * getTrashPeriodCoolDown());
        state = GameState.PLAYING;
        destructedTrashNumber = 0;
    }
    public void pauseGame() {
        sessionPauseTime = TimeUtils.millis();
        state = GameState.PAUSED;
    }
    public void resumeGame() {
        state = GameState.PLAYING;
        sessionStartTime += TimeUtils.millis() - sessionPauseTime;
    }
    public void endGame() {
        updateScore();

        state = GameState.ENDED;
        ArrayList<Integer> recordsTable = MemoryManager.loadRecordsTable();
        if (recordsTable == null) {
            recordsTable = new ArrayList<>();
        }
        int foundIdx = 0;
        for (; foundIdx < recordsTable.size(); foundIdx++) {
            if (recordsTable.get(foundIdx) < getScore()) break;
        }
        recordsTable.add(foundIdx, getScore());
        MemoryManager.saveTableOfRecords(recordsTable);


    }
    public boolean shouldSpawnTrash() {
        if (nextTrashSpawnTime <= TimeUtils.millis()) {
            nextTrashSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                    * getTrashPeriodCoolDown());
            return true;
        }
        return false;
    }
    public void destructionRegistration() {
        destructedTrashNumber += 1;
    }
    public void updateScore() {
        score = (int) (TimeUtils.millis() - sessionStartTime) / 100 + destructedTrashNumber * 100;
    }
    public int getScore() {
        return score;
    }
    private float getTrashPeriodCoolDown() {
        return (float) (((double) 800) - Math.sqrt(0.05*(TimeUtils.millis()-sessionStartTime)))/MemoryManager.loadLevel();
    }
}