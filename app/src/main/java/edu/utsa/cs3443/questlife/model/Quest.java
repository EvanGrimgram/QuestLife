package edu.utsa.cs3443.questlife.model;

import java.util.ArrayList;

/**
 * The Quest class creates the Quest objects which are displayed on the main screens Quest list
 * A Quest object contains a userInput for the name, a difficulty, the completion status, and
 * an ArrayList containing all of the users Quests.
 *
 * @author JavaJuicers
 * UTSA CS 3443 - Final Application
 *
 */
public class Quest {
    private String userInput;
    private String difficulty;
    private boolean isComplete;
    private ArrayList<Quest> userQuests;

    /**
     * Constructs a new Quest object with the specified name and difficulty,
     * setting the completion status to false.
     *
     * @param userInput the name of the Quest
     * @param difficulty the difficulty of the Quest
     */
    public Quest(String userInput, String difficulty) {
        this.userInput = userInput;
        this.difficulty = difficulty;
        this.isComplete = false;
    }

    /**
     * Returns the completion status of the Quest
     * @return the completion status of the Quest
     */
    public boolean isComplete() {
        return isComplete;
    }

    /**
     * Sets the completion status of the Quest
     * @param complete the completion status of the Quest
     */
    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    /**
     * Returns the name of the Quest
     * @return the name of the Quest
     */
    public String getUserInput() {
        return userInput;
    }

    /**
     * Sets the name of the Quest
     * @param userInput the name of the Quest
     */
    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Returns the difficulty of the Quest
     * @return the difficulty of the Quest
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty of the Quest
     * @param difficulty the difficulty of the Quest
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the ArrayList of Quests
     * @return the ArrayList of Quests
     */
    public ArrayList<Quest> getUserQuests() {
        return userQuests;
    }

    /**
     * Sets the ArrayList of Quests
     * @param userQuests the ArrayList of Quests
     */
    public void setUserQuests(ArrayList<Quest> userQuests) {
        this.userQuests = userQuests;
    }
}
