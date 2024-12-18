package aleks.services;

import aleks.entity.User;

import java.util.Map;

public interface LinkService {
    //case of new entry
    public String createAShortLink(String longLink, User user);

    //checking if short link exists
    public boolean isAShortLink(String shortLink, User user);

    //checking if long link exists
    public boolean isALongLink(String longLink, User user);

    //case of parsing through available Links
    public void getPrettyListOfAvailableLinks(User user);

    //case of deleting the entry
    public void removeAShortLink(String shortLink, User user);

    //case of connecting to a link
    public void connectToLink(String shortLink, User user);

}
