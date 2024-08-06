package edu.utsa.cs3443.questlife.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The UserQuests class keeps a record of all quests currently active.
 * The UserQuests class contains an ArrayList of Quest objects.
 *
 * @author JavaJuicers
 * UTSA CS 3443 - Final Application
 *
 */
public class UserQuests {
    private static UserQuests instance = null;
    private List<Quest> quests;

    /**
     * Private constructor which initializes the UserQuests ArrayList
     */
    private UserQuests() {
        quests = new ArrayList<>();
    }

    /**
     * Provides access to the UserQuest class instance
     * If the instance is null, it creates a new one
     * @return the UserQuests instance
     */
    public static UserQuests getInstance() {
        if (instance == null) {
            instance = new UserQuests();
        }
        return instance;
    }

    /**
     * Returns the ArrayList of active quests
     * @return the ArrayList of active quests
     */
    public List<Quest> getQuests() {
        return quests;
    }

    /**
     * Sets the ArrayList of active quests
     * @param quests the active quests
     */
    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    /**
     * Adds an active quest to the quest list
     * @param quest an active quest to add
     */
    public void addQuest(Quest quest) {
        quests.add(quest);
    }
}
