package aleks;

import aleks.controllers.UUIDControllerImpl;
import aleks.entity.User;
import aleks.services.LinkServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

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
        //create a service showing as prefix the UUID of user (imitation of logging in)
        //uuid string is asked on start up - unless user initiated user change or creating of new uuid
        String uuid = getUUID();


    }

    //asks in a recursion until user enters a correct UUID or initiate creating of new UUID instance
    public static String getUUID(){
        String input = "";
        String result = "";
            System.out.println("Please enter a UUID number or enter 'init' to create one");
            try {
                input = reader.readLine();
                //checking of the input, case 1 - init
                if(input.equals("init")){
                    System.out.println("Please enter amount of times link can be used, if unlimited - put 0");
                    input = reader.readLine();
                    if(Integer.parseInt(input) > 0){
                        uuidController.createNewEntity(Integer.parseInt(input));
                        //setting just added user as an active user
                        uuidController.setCurrentEntity(uuidController.users.get(uuidController.users.size()-1).getUUID());
                        result = uuidController.users.get(uuidController.users.size()-1).getUUID().toString();
                    } else if(Integer.parseInt(input) == 0){
                        uuidController.createNewEntity();
                        //setting just added user as an active user
                        uuidController.setCurrentEntity(uuidController.users.get(uuidController.users.size()-1).getUUID());
                        result = uuidController.users.get(uuidController.users.size()-1).getUUID().toString();
                    } else {
                        System.out.println("Please enter a valid number");
                        getUUID();
                    }
                } else {
                    //case 2 - expecting UUID, parsing through UUID embedded Arraylist of users
                    for(User user: uuidController.users){
                        if(user.getUUID().toString().equals(input)){
                            //setting existing user as an active user
                            result = user.getUUID().toString();
                            uuidController.setCurrentEntity(user.getUUID());
                        } else{
                            System.out.println("Wrong UUID");
                            getUUID();
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Something went wrong");
                getUUID();
            }
            return result;
    }
}
