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

        //storing shortLink and longLink
        linksMap.put(shortLink, longLink);
        user.setLinks(linksMap);

        //getting current timestamp, storing
        Instant instant = Instant.now();
        timestampMap.put(shortLink, instant.toEpochMilli());
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
        if(!entryExists) {
            //invoke method to inform user - there is no such link
        }

    }

    @Override
    public void removeExpiredLinks(int countLinkUsed, User user) {
        //iterating through the Map with counts, if we have already expired links, invoking remove method
        //checking for both timestamp and count of used links
        Map<String, Integer> countMap = user.getShortLinksCount();
        Map<String, Long> timestampMap = user.getShortLinksTimeStamp();
        Iterator<Map.Entry<String, Long>> iterator2 = timestampMap.entrySet().iterator();
        while(iterator2.hasNext()){
            Instant instant = Instant.now();
            if(iterator2.next().getValue() + timeout > instant.toEpochMilli() ){
                removeEntry(iterator2.next().getKey(), user);
            }
        }

        Iterator<Map.Entry<String, Integer>> iterator = countMap.entrySet().iterator();
        while(iterator.hasNext()){
            if(iterator.next().getValue() == countLinkUsed){
                removeEntry(iterator.next().getKey(), user);
            }
        }

    }
}
