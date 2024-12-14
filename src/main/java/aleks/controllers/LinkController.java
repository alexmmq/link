package aleks.controllers;

import aleks.entity.User;

import java.util.UUID;

public interface LinkController {
    //create entry
    public void createEntry(String shortLink, String longLink, User user);

    //remove entry
    public void removeEntry(String shortLink, User user);

    //analyze all the links validity duration - remove expired
    public void removeExpiredLinks(int countLinkUsed, User user);
}