package aleks;

import aleks.controllers.UUIDControllerImpl;
import aleks.entity.User;
import aleks.services.LinkServiceImpl;

import java.io.BufferedReader;
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
    static boolean isLoggedIn = false;
    public static void main( String[] args )
    {

        while(true){
          try{
              if(isLoggedIn){
                  User currentUser = uuidController.getCurrentUser(uuidController.getCurrentEntity().toString());
                  System.out.println(currentUser.getUUID().toString() + "> " +
                          "Please use following commands: " +
                          " \n 's'  - to use a short link, " +
                          "\n 'l' - to create a short link from long one" +
                          "\n 'e' - for editing links, " +
                          "\n 'r' - for removing links" +
                          "\n 'logout' - for logout " +
                          "\n 'exit' - for exit" );
                  String input = reader.readLine();
                  switch (input){
                      //using a short link to connect
                      case "s":
                          if(!linkService.availableLinks(currentUser).isEmpty()){
                              linkService.getPrettyListOfAvailableLinks(currentUser);
                              System.out.println(currentUser.getUUID().toString() + "> "
                                      + "Enter a short link you want to connect to");
                              input = reader.readLine();
                              linkService.connectToLink(input, currentUser);
                          } else{
                              System.out.println("You don't have available links");
                              break;
                          }
                          break;

                          //creating a new link
                      case "l":
                          String shortLinkCreated;
                        System.out.println(currentUser.getUUID().toString() + "> "
                                + "Enter a link to be transformed into short link");
                        input = reader.readLine();
                        shortLinkCreated = linkService.createAShortLink(input, currentUser);
                        System.out.println(currentUser.getUUID().toString() + "> "
                                + "You have created a short link: " + shortLinkCreated);
                        break;

                          //editing existing long link
                      case "e":
                          linkService.getPrettyListOfAvailableLinks(currentUser);
                          System.out.println(currentUser.getUUID().toString() + "> "
                                  + "Enter a long link to edit");
                          input = reader.readLine();
                          if(linkService.isALongLink(input, currentUser)){
                            System.out.println(currentUser.getUUID().toString() + "> "
                                    + "Please enter a new value for a long link");
                            String input2 = reader.readLine();
                            linkService.editTheLongLink(input, input2, currentUser);
                              System.out.println("You have reassigned new link!" + input2);
                          }
                          else{
                              System.out.println("There is no such a link");
                          }
                          break;
                          //removing an existing link
                      case "r":
                          linkService.getPrettyListOfAvailableLinks(currentUser);
                          System.out.println(currentUser.getUUID().toString() + "> "
                                  + "Enter a short link to remove");
                          input = reader.readLine();
                          if(linkService.isAShortLink(input, currentUser)){
                              String deleted = input;
                              linkService.removeAShortLink(input, currentUser);
                              System.out.println(currentUser.getUUID().toString() + "> "
                                      + "The following link was removed: " + deleted);
                              break;
                          } else {
                              System.out.println("There is no such a short link");
                              break;
                    }
                      case "logout":
                          isLoggedIn = false;
                          break;
                      case "exit":
                          System.exit(0);
                  }
              } else{
                  //call to printService, prelogged state
                  System.out.println("Please enter following commands to proceed: "
                   + "\n'init' - to create a UUID"
                          + "\n'login' - to log in using existing UUID"
                  + "\n'exit' - to exit the program");
                  String input = reader.readLine();
                  switch (input){
                      case "init":
                          createUUID();
                          break;
                      case "login":
                          login();
                          break;
                      case "exit":
                          System.exit(0);
                  }
              }
          } catch (Exception E){
              E.printStackTrace();
          }
        }

    }

    //method for creating a new UUID
    public static void createUUID(){
        //call to printService asking for providing count of link expiration
        String input = "";
        try{
            System.out.println("Please enter amount of times a link can be used, " +
                     " number cannot be less 0, in case of 0 - no limit for links");
            input = reader.readLine();
            if(Integer.parseInt(input)>0){
                uuidController.createNewEntity(Integer.parseInt(input));
            } else if(Integer.parseInt(input)==0){
                uuidController.createNewEntity();
            } else {
                System.out.println("Please enter a valid number");
                createUUID();
            }
        }catch (Exception e){

        }
    }

    public static void login(){
        ArrayList<User> users = uuidController.getUsers();
        if(users.size()>0){
            System.out.println("Following UUIDs are available:");
            for(User user : users){
                System.out.println(user.getUUID());
            }
            System.out.println("Please enter a valid UUID");
            String input = "";
            try{
                input = reader.readLine();
                //checking if the UUID exists

                boolean found = false;
                for(User user : users){
                    if(user.getUUID().toString().equals(input)){
                        uuidController.setCurrentEntity(user.getUUID());
                        found = true;
                        isLoggedIn = true;
                        break;
                    }
                }
                if(!found){
                    System.out.println("Please enter a valid UUID");
                    login();
                }
            }catch (Exception e){

            }
        }else{
            System.out.println("No UUIDs found");
            createUUID();
        }


    }

}
