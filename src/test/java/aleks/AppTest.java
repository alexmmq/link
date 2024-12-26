package aleks;

import aleks.controllers.UUIDControllerImpl;
import aleks.entity.User;
import aleks.services.LinkServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    static BufferedReader reader;
    static LinkServiceImpl linkService;
    static UUIDControllerImpl uuidController;
    static boolean isLoggedIn;
    static String longLink;
    static String longLink2;
    static String longLink3;
    static ArrayList<User> users;

    @BeforeAll
    public static void setUp() {
    reader = new BufferedReader(new InputStreamReader(System.in));
    linkService = new LinkServiceImpl();
    uuidController = new UUIDControllerImpl();
    isLoggedIn = false;
    longLink = "https://products.biamp.com/product-details/-/o/d/Tesira-EX-UBT/ecom-item/910.1770.900";
    longLink2 = "https://stackoverflow.com/questions/10993403/how-to-replace-hashmap-values-while-iterating-over-them-in-java";
    longLink3 = "https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html";
    users = new ArrayList<>();
}

     @Test
     public void testIfTheSameShortLinkCreatesDifferentLinksForDifferentUsers() {
     String currentUserUuid = "";
     User currentUser;
         //creating a user
     uuidController.createNewEntity(1);
     users = uuidController.getUsers();
     currentUser = users.get(0);
     uuidController.setCurrentEntity(users.get(users.size()-1).getUUID());
     linkService.createAShortLink(longLink, currentUser);
     Map<String, String> linksFirstUser = linkService.availableLinks(currentUser);
     String shortLinkFirstUser = "";
     for(String link: linksFirstUser.keySet()){
         shortLinkFirstUser = link;
     }
         //creating another user
     uuidController.createNewEntity(1);
     users = uuidController.getUsers();
     currentUser = users.get(1);
     uuidController.setCurrentEntity(users.get(users.size()-1).getUUID());
     linkService.createAShortLink(longLink, currentUser);
     Map<String, String> linksSecondUser = linkService.availableLinks(currentUser);
         String shortLinkSecondUser = "";
         for(String link: linksSecondUser.keySet()){
             shortLinkSecondUser = link;
         }
     //comparing of short links for different users
     assumeTrue(!shortLinkFirstUser.equals(shortLinkSecondUser));
}
    @Test
    public void testIfAutoRemoveOfExpiredLinksWorks(){
        String currentUserUuid = "";
        User currentUser;
        //creating a user
        uuidController.createNewEntity(1);
        users = uuidController.getUsers();
        currentUser = users.get(0);
        uuidController.setCurrentEntity(users.get(users.size()-1).getUUID());
        linkService.createAShortLink(longLink, currentUser);
        Map<String, String> linksFirstUser = linkService.availableLinks(currentUser);
        assumeTrue(!linksFirstUser.isEmpty());
        String shortLinkFirstUser = "";
        for(String link: linksFirstUser.keySet()){
            shortLinkFirstUser = link;
        }
        linkService.connectToLink(shortLinkFirstUser, currentUser);
        Map<String, String> linksAfterCall = linkService.availableLinks(currentUser);
        assumeTrue(linksAfterCall.isEmpty());
    }
}
