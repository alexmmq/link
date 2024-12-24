package aleks.services;

import aleks.controllers.LinkControllerImpl;
import aleks.entity.User;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class LinkServiceImpl implements LinkService{
    LinkControllerImpl linkController = new LinkControllerImpl();
    @Override
    public String createAShortLink(String longLink, User user) {

        //a method of generating a random string - iterate through UUID, get first char,
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
        linkController.createEntry(stringBuilder.toString(),longLink, user);
        return stringBuilder.toString();
    }

    @Override
    public boolean isAShortLink(String shortLink, User user) {
        return linkController.checkIfLinkExists(shortLink, user);
    }

    @Override
    public boolean isALongLink(String longLink, User user) {
        return linkController.checkIfLongLinkExists(longLink, user);
    }

    @Override
    public void getPrettyListOfAvailableLinks(User user) {
        // force removing of expired links
        if(!user.getLinks().isEmpty()){
          //linkController.removeExpiredLinks(user);
            Map<String, String> links = user.getLinks();

            //pretty formatting of map for user
            System.out.println(" Short Links           " + "   Long Links");
            for(Map.Entry<String, String> set : links.entrySet()){
                System.out.println(set.getKey() + "  " + set.getValue());
            }
        }
    }

    @Override
    public void removeAShortLink(String shortLink, User user) {
        linkController.removeEntry(shortLink, user);
    }

    @Override
    public void connectToLink(String shortLink, User user) {
        // force removing of expired links
        linkController.removeExpiredLinks(user);
        //Positive Case
        if(linkController.checkIfLinkExists(shortLink, user)){
            try {
                Desktop.getDesktop().browse(new URI(linkController.getTheLink(shortLink, user)));
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
                //TODO call a method informing user about failure
            }
        } else{
            System.out.println("There is no such a link");
        }
    }

    @Override
    public void editTheLongLink(String longLink, String longLinkNewValue, User user) {
        linkController.editALongLink(longLink, longLinkNewValue, user);
    }
}
