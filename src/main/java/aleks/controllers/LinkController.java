package aleks.controllers;

import aleks.entity.User;

import java.util.UUID;

public interface LinkController {
    //create entry
    public void createEntry(String shortLink, String longLink, User user);

    //remove entry
    public void removeEntry(String shortLink, User user);

    //analyze all the links validity duration - remove expired
    public void removeExpiredLinks(User user);

    //check if the entry is present
    public boolean checkIfLinkExists(String shortLink, User user);

    //check if long link entry is present
    public boolean checkIfLongLinkExists(String longLink, User user);

    //editing a long link
    public void editALongLink(String longLink, String longLinkNewValue, User user);

    //get the correct entry
    public String getTheLink(String shortLink, User user);
}
