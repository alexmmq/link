package aleks;

import aleks.controllers.UUIDControllerImpl;
import aleks.entity.User;
import aleks.services.LinkServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static LinkServiceImpl linkService = new LinkServiceImpl();
    static UUIDControllerImpl uuidController = new UUIDControllerImpl();
    public static void main( String[] args )
    {
        //TODO create a service showing as prefix the UUID of user (imitation of logging in)
        //TODO create a service to switch the user
        //uuid string is asked on start up - unless user initiated user change or creating of new uuid
        //if succeeded - going on with entering a link
        String currentUserUuid = "";
        User currentUser;
        while(true){
            currentUserUuid = getUUID();
         currentUser = uuidController.getCurrentUser(currentUserUuid);
         getLink(currentUser);
        }
    }

    //asks in a recursion until user enters a correct UUID or initiate creating of new UUID instance
    public static String getUUID(){
        String input = "";
        String result = "";
            System.out.println("Please enter 'init' to create UUID");
            try {
                input = reader.readLine();
                //checking of the input, case 1 - init
                if(input.equals("init")){
                    System.out.println("Please enter amount of times link can be used, if unlimited - put 0");
                    input = reader.readLine();
                    if(Integer.parseInt(input) > 0){
                        uuidController.createNewEntity(Integer.parseInt(input));
                        //setting just added user as an active user
                        login();
                        result = uuidController.users.get(uuidController.users.size()-1).getUUID().toString();
                    } else if(Integer.parseInt(input) == 0){
                        uuidController.createNewEntity();
                        //setting just added user as an active user
                        login();
                        result = uuidController.users.get(uuidController.users.size()-1).getUUID().toString();
                    } else {
                        System.out.println("Please enter a valid number");
                        getUUID();
                    }
                } else if(input.equals("login")){
                    login();
                }
            } catch (IOException e) {
                System.out.println("Something went wrong");
                getUUID();
            }
            return result;
    }

    public static void login(){
        String input = "";
        ArrayList<User> users = uuidController.getUsers();
        //a method to parse through existing users, showing existing ones in case
        if(!users.isEmpty()){
            System.out.println("Registered Users:");
            for(User user: users){
                System.out.println(user.getUUID().toString());
            }
        } else {
            System.out.println("Currently there are no users");
        }
        try{
            System.out.println("Please enter a valid UUID");
            input = reader.readLine();
            //case 2 - expecting UUID, parsing through UUID embedded Arraylist of users
            boolean wrongInput = true;
            for(User user: uuidController.users) {
                if (user.getUUID().toString().equals(input)) {
                    //setting existing user as an active user
                    uuidController.setCurrentEntity(user.getUUID());
                    wrongInput = false;
                }
            }
            if(wrongInput){
                System.out.println("Wrong UUID");
                getUUID();
            }
        } catch(IOException e) {
            System.out.println("Something went wrong");
            getUUID();
        }
    }


    //gets a link from user depending on the action taken - does actual connection to the required link
    public static String getLink(User user){
        String input = "";
        String result = "";
        System.out.println(user.getUUID().toString() + "> " +
                "\n Enter 's' - to use a short link, \n 'l' - to create a short link from long one" +
                "\n 'x' - logout, \n 'e' - edit links, \n 'r' - remove links");
        try{
            input = reader.readLine();
            //case short link, invoke a method to show all available links
            switch(input){
                case "s":
                    linkService.getPrettyListOfAvailableLinks(user);
                    System.out.println(user.getUUID().toString() + "> "
                            + "Enter a short link to use");
                    input = reader.readLine();
                    linkService.connectToLink(input,user);
                    getLink(user);
                    case "l":
                        String shortLinkCreated;
                        System.out.println(user.getUUID().toString() + "> "
                                + "Enter a link to be transformed into short link");
                        input = reader.readLine();
                        shortLinkCreated = linkService.createAShortLink(input, user);
                        System.out.println(user.getUUID().toString() + "> "
                                + "You have created a short link: " + shortLinkCreated);
                        //invoking recursively to be in the starting point of menu
                        getLink(user);
                case "e":
                    linkService.getPrettyListOfAvailableLinks(user);
                    System.out.println("Enter a long link to edit");
                    input = reader.readLine();
                    //case of editing the long link
                    if(linkService.isALongLink(input, user)){
                        System.out.println("Please enter a new value for a long link");
                        String input2 = reader.readLine();
                        linkService.editTheLongLink(input, input2, user);
                    }
                    getLink(user);
                case "x":
                    input = "";
                    break;
                case "r":
                    linkService.getPrettyListOfAvailableLinks(user);
                    System.out.println("Enter a short link to remove");
                    input = reader.readLine();
                    if(linkService.isAShortLink(input, user)){
                        String deleted = input;
                        linkService.removeAShortLink(input, user);
                        System.out.println("The following link was removed: " + deleted);
                        getLink(user);
                    } else {
                        System.out.println("There is no such a short link");
                        getLink(user);
                    }
                default:
                    //case of typo
                    System.out.println(user.getUUID().toString() + "> "
                            + "Invalid input, please try again");
                    getLink(user);
            }
        } catch (Exception e){
            //TODO work on exception handling
            e.printStackTrace();
        }
        return result;
    }
}
