package aleks;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

//        //uuid string is asked on start up - unless user initiated user change or creating of new uuid
//        //if succeeded - going on with entering a link
//        String currentUserUuid = "";
//        User currentUser;
//        ArrayList<User> users = new ArrayList<>();
//        String longLink1 = "https://products.biamp.com/product-details/-/o/d/Tesira-EX-UBT/ecom-item/910.1770.900";
//        String longlink2 = "https://stackoverflow.com/questions/10993403/how-to-replace-hashmap-values-while-iterating-over-them-in-java";
//        String longlink3 = "https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html";
//        //creating entity
//        uuidController.createNewEntity(3);
//        //setting up the current user
//        users = uuidController.getUsers();
//        currentUser = users.get(0);
//        uuidController.setCurrentEntity(users.get(users.size()-1).getUUID());
//        //creating links
//        linkService.createAShortLink(longLink1, currentUser);
//        linkService.createAShortLink(longlink2, currentUser);
//        linkService.createAShortLink(longlink3, currentUser);
//        //connecting to the links
//        linkService.getPrettyListOfAvailableLinks(currentUser);
//        Map<String, String> availableLinks = linkService.availableLinks(currentUser);
//        ArrayList<String> shortLinks = new ArrayList<>();
//        for(Map.Entry<String, String> entry : availableLinks.entrySet()) {
//            shortLinks.add(entry.getKey());
//        }
//        for(int i = 1; i < 15; i++){
//            for(String shortLink:shortLinks){
//                linkService.connectToLink(shortLink, currentUser);
//            }
//        }

//        while(true){
//            currentUserUuid = getUUID();
//         currentUser = uuidController.getCurrentUser(currentUserUuid);
//         getLink(currentUser);
//        }
}
