package aleks.controllers;

import aleks.entity.User;

import java.time.Instant;
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

        if(!entryExists) {
            //invoke method to inform user - there is no such link
        }

    }

    @Override
    public void removeExpiredLinks(User user) {
        int countLinkUsed = user.getCountLinkUsed();
        //iterating through the Map with counts, if we have already expired links, invoking remove method
        //checking for both timestamp and count of used links
        Map<String, Integer> countMap = user.getShortLinksCount();
        Map<String, Long> timestampMap = user.getShortLinksTimeStamp();
        Iterator<Map.Entry<String, Long>> iterator2 = timestampMap.entrySet().iterator();
        while(iterator2.hasNext()){
            Instant instant = Instant.now();
            if(iterator2.next().getValue() + timeout < instant.getEpochSecond()){
                //call to printLinkExpired() due to timeout
                removeEntry(iterator2.next().getKey(), user);
            }
        }

        Iterator<Map.Entry<String, Integer>> iterator = countMap.entrySet().iterator();
        while(iterator.hasNext()){
            if(iterator.next().getValue() == countLinkUsed){
                //call to printLinkExpired() due to exceeding usage limits
                removeEntry(iterator.next().getKey(), user);
            }
        }

    }

    @Override
    public boolean checkIfLinkExists(String shortLink, User user) {
        //iterating through the maps
        boolean entryExists = false;
        Map<String, String> linksMap = user.getLinks();
        for (Map.Entry<String, String> stringStringEntry : linksMap.entrySet()) {
            entryExists = stringStringEntry.getKey().equals(shortLink);
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
