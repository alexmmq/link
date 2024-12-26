package aleks.controllers;

import aleks.entity.User;

import java.time.Instant;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class LinkControllerImpl implements LinkController{
    //1 hour timeout for the links
    private final long timeout = 3600000;

    @Override
    public void createEntry(String shortLink, String longLink, User user) {
        //retrieving existing data from user instance
        Map<String, String> linksMap = user.getLinks();
        Map<String, Long> timestampMap = user.getShortLinksTimeStamp();
        Map<String, Integer> countMap = user.getShortLinksCount();

        //call to printNewLinkCreated()

        //storing shortLink and longLink
        linksMap.put(shortLink, longLink);
        user.setLinks(linksMap);


        //getting current timestamp, storing
        Instant instant = Instant.now();
        timestampMap.put(shortLink, instant.getEpochSecond());
        user.setShortLinksTimeStamp(timestampMap);

        //setting used times to 0
        countMap.put(shortLink, 0);
        user.setShortLinksCount(countMap);
    }

    @Override
    public void updateEntry(String shortLink, User user) {
        Map<String, Integer> countMap = user.getShortLinksCount();
        int currentValue = 0;
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (entry.getKey().equals(shortLink)) {
                currentValue = entry.getValue();
            }
        }
        countMap.put(shortLink, currentValue + 1);
        user.setShortLinksCount(countMap);
    }


    @Override
    public void removeEntry(String shortLink, User user) {
        //checking if entry exists
        Map<String, String> linksMap = user.getLinks();
        Map<String, Long> timestampMap = user.getShortLinksTimeStamp();
        Map<String, Integer> countMap = user.getShortLinksCount();
        boolean entryExists = false;
        Iterator<Map.Entry<String,String>> iterator = linksMap.entrySet().iterator();
        while(iterator.hasNext()){
            if(iterator.next().getKey().equals(shortLink)) {
                iterator.remove();
                entryExists = true;
            }
        }

        Iterator<Map.Entry<String,Long>> iterator2 = timestampMap.entrySet().iterator();
        while(iterator2.hasNext()){
            if(iterator2.next().getKey().equals(shortLink)){
                iterator2.remove();
            }
        }

        Iterator<Map.Entry<String, Integer>> iterator3 = countMap.entrySet().iterator();
        while(iterator3.hasNext()){
            if(iterator3.next().getKey().equals(shortLink)){
                iterator3.remove();
            }
        }

        user.setLinks(linksMap);
        user.setShortLinksTimeStamp(timestampMap);
        user.setShortLinksCount(countMap);

    }

    @Override
    public void removeExpiredLinks(User user) {
        int countLinkUsed = user.getCountLinkUsed();
        //iterating through the Map with counts, if we have already expired links, invoking remove method
        //checking for both timestamp and count of used links
        Map<String, Integer> countMap = user.getShortLinksCount();
        Map<String, Long> timestampMap = user.getShortLinksTimeStamp();
        HashSet<String> shortLinksToRemove = new HashSet<>();

        for(Map.Entry<String, Long> entry : timestampMap.entrySet()) {
            Instant instant = Instant.now();
            if(entry.getValue() + timeout < instant.getEpochSecond()){
                shortLinksToRemove.add(entry.getKey());
            }
        }

        for(Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if(entry.getValue() >= countLinkUsed) {
                shortLinksToRemove.add(entry.getKey());
            }
        }

        //parsing through HashSet Values to remove all expired links
        for (String s : shortLinksToRemove) {
            removeEntry(s, user);
            System.out.println("Link " + s + " has been removed due to expiration");
        }
    }

    @Override
    public boolean checkIfLinkExists(String shortLink, User user) {
        //iterating through the maps
        boolean entryExists = false;
        Map<String, String> linksMap = user.getLinks();
        for (Map.Entry<String, String> stringStringEntry : linksMap.entrySet()) {
            if(stringStringEntry.getKey().equals(shortLink)) {
                entryExists = true;
                break;
            }
        }
        return entryExists;
    }

    @Override
    public boolean checkIfLongLinkExists(String longLink, User user) {
        boolean entryExists = false;
        Map<String, String> linksMap = user.getLinks();
        for (Map.Entry<String, String> stringStringEntry : linksMap.entrySet()) {
            entryExists = stringStringEntry.getValue().equals(longLink);
        }
        return entryExists;
    }

    @Override
    public void editALongLink(String longLink, String longLinkNewValue, User user) {
        if(checkIfLongLinkExists(longLink, user)) {
            Map<String, String> linksMap = user.getLinks();
            for(Map.Entry<String, String> entry : linksMap.entrySet()) {
                if(entry.getValue().equals(longLink)) {
                    entry.setValue(longLinkNewValue);
                }
            }
            user.setLinks(linksMap);
        } else{
            System.out.println("This link does not exist");
        }
    }

    @Override
    public String getTheLink(String shortLink, User user) {
        String value = "";
        Map<String, String> linksMap = user.getLinks();
        for (Map.Entry<String, String> stringStringEntry : linksMap.entrySet()) {
            if (stringStringEntry.getKey().equals(shortLink)) {
                value = stringStringEntry.getValue();
            }
        }
        return value;
    }
}
