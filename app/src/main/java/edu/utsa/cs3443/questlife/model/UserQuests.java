package edu.utsa.cs3443.questlife.model;

import java.util.ArrayList;
import java.util.List;


// This is the active quests
public class UserQuests {
    private static UserQuests instance = null;
    private List<Quest> quests;

    private UserQuests() {
        quests = new ArrayList<>();
    }

    public static UserQuests getInstance() {
        if (instance == null) {
            instance = new UserQuests();
        }
        return instance;
    }

    public void addQuest(Quest quest) {
        quests.add(quest);
    }

    public List<Quest> getQuests() {
        return quests;
    }
}
