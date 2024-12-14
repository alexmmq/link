package aleks.services;

import aleks.entity.User;

import java.util.Map;

public interface LinkService {
    //case of new entry
    public String createAShortLink(String longLink, User user);

    //case of parsing through available Links
    public Map<String, String> getListOfAvailableLinks(User user);

}
