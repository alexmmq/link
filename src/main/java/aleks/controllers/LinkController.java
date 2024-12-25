package aleks.controllers;

import aleks.entity.User;


public interface LinkController {
    //create entry
    void createEntry(String shortLink, String longLink, User user);

    //update count Entry for each link - counter of times link has been used
    void updateEntry(String shortLink, User user);

    //remove entry
    void removeEntry(String shortLink, User user);

    //analyze all the links validity duration - remove expired
    void removeExpiredLinks(User user);

    //check if the entry is present
    boolean checkIfLinkExists(String shortLink, User user);

    //check if long link entry is present
    boolean checkIfLongLinkExists(String longLink, User user);

    //editing a long link
    void editALongLink(String longLink, String longLinkNewValue, User user);

    //get the correct entry
    String getTheLink(String shortLink, User user);
}
