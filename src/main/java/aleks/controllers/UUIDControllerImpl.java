package aleks.controllers;

import aleks.entity.User;

import java.util.ArrayList;
import java.util.UUID;


public class UUIDControllerImpl implements UUIDController {


    public ArrayList<User> users = new ArrayList<>();
    private User currentUser;

    public ArrayList<User> getUsers() {
        return users;
    }

    //case of provided count
    @Override
    public void createNewEntity(int count) {
        User user = new User(UUIDController.generateUUID(), count);
        users.add(user);
    }

    //case if count is not provided

    @Override
    public void createNewEntity() {
        User user = new User(UUIDController.generateUUID());
        users.add(user);
    }

    @Override
    public User getCurrentUser(String uuid) {
        User userToReturn = null;
        //parsing through users, returning appropriate user with UUID
        for (User user : users) {
            if(user.getUUID().toString().equals(uuid)){
                userToReturn = user;
            }
        }
        return userToReturn;
    }

    @Override
    public void setCurrentEntity(UUID uuid) {
        //checking if arraylist of users is empty
        if(!users.isEmpty()){
            for(User user : users){
                if(user.getUUID().equals(uuid)){
                    this.currentUser = user;
                }
            }
        } else{
            //invoke a method UUID/userNotFound
        }
    }

    @Override
    public UUID getCurrentEntity() {
        return this.currentUser.getUUID();
    }
}
