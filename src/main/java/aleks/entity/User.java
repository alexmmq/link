package aleks.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    private java.util.UUID UUID;
    private int countLinkUsed;
    private Map<String, String> links;
    private Map<String, Long> shortLinksTimeStamp;
    private Map<String, Integer> shortLinksCount;
    public User(UUID UUID){
        this.UUID = UUID;
        this.countLinkUsed = -1;
        links = new HashMap<>();
        shortLinksTimeStamp = new HashMap<>();
        shortLinksCount = new HashMap<>();
    }
    public User(UUID UUID, int countLinkUsed) {
        this.UUID = UUID;
        this.countLinkUsed = countLinkUsed;
        //structure for storing data
        //1. key - String short link, value - long link
        //2. key - String short link, value - Map <Long timestamp, Integer countUsed>
        links = new HashMap<>();
        shortLinksTimeStamp = new HashMap<>();
        shortLinksCount = new HashMap<>();
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public Map<String, Long> getShortLinksTimeStamp() {
        return shortLinksTimeStamp;
    }

    public void setShortLinksTimeStamp(Map<String, Long> shortLinksTimeStamp) {
        this.shortLinksTimeStamp = shortLinksTimeStamp;
    }

    public Map<String, Integer> getShortLinksCount() {
        return shortLinksCount;
    }

    public void setShortLinksCount(Map<String, Integer> shortLinksCount) {
        this.shortLinksCount = shortLinksCount;
    }

    public int getCountLinkUsed() {
        return countLinkUsed;
    }

    public void setCountLinkUsed(int countLinkUsed) {
        this.countLinkUsed = countLinkUsed;
    }

    public UUID getUUID() {
        return UUID;
    }
}
