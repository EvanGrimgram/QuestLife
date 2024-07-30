package edu.utsa.cs3443.questlife.model;

import java.util.ArrayList;

public class Quest {
    private String userInput;
    private String difficulty; // Added difficulty variable
    private boolean isComplete;
    private ArrayList<Quest> userQuests;

    // Constructor
    public Quest(String userInput, String difficulty) {
        this.userInput = userInput;
        this.difficulty = difficulty; // Initialize difficulty
        this.isComplete = false; // set to false first
    }

    // Getter and Setter for User Input
    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    // Getter and Setter for Difficulty
    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    // Getter and Setter for userQuests array
    public ArrayList<Quest> getUserQuests() {
        return userQuests;
    }

    public void setUserQuests(ArrayList<Quest> userQuests) {
        this.userQuests = userQuests;
    }

    // Method to load quests from array
    public void loadQuests(ArrayList<Quest> questToLoad) {
        this.userQuests = questToLoad;
    }

    // Method to add quests
    public void addQuest(Quest quest) {
        this.userQuests.add(quest);
    }
}
