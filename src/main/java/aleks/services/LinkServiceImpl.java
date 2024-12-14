package aleks.services;

import aleks.controllers.LinkControllerImpl;
import aleks.entity.User;

import java.util.Map;

public class LinkServiceImpl implements LinkService{
    @Override
    public String createAShortLink(String longLink, User user) {
        LinkControllerImpl linkController = new LinkControllerImpl();

        //invoke a create entry method of LinkController
        return "";
    }

    @Override
    public Map<String, String> getListOfAvailableLinks(User user) {
        LinkControllerImpl linkController = new LinkControllerImpl();

        //invoke remove expired links method of controller
        return null;
    }
}
