package aleks.services;

import aleks.controllers.LinkControllerImpl;
import aleks.entity.User;

import java.util.Map;

public class LinkServiceImpl implements LinkService{
    @Override
    public String createAShortLink(String longLink, User user) {
        LinkControllerImpl linkController = new LinkControllerImpl();
        //create a method of generating a random string - iterate through UUID, get first char,
        //get last char of long link, convert both to int, calculate average, add Math.random()*10
        //take this as a one char for the new link
        String link = "https://link/";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(link);
        for(int i = 0; i < 8; i++){
            int a,b, average, random, result;
            a = user.getUUID().toString().charAt(i);
            b = longLink.charAt(longLink.length() - 1 - i);
            average = (a + b) / 2;
            random = (int)(Math.random() * 10);
            result = random + average;
            stringBuilder.append((char)result);
        }

        //invoke a create entry method of LinkController
        return stringBuilder.toString();
    }

    @Override
    public Map<String, String> getListOfAvailableLinks(User user) {
        LinkControllerImpl linkController = new LinkControllerImpl();

        //invoke remove expired links method of controller
        return null;
    }
}
