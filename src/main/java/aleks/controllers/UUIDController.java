package aleks.controllers;

import aleks.entity.User;

import java.util.UUID;

public interface UUIDController {

    //generates random UUID when prompted
    public static UUID generateUUID() {
        return UUID.randomUUID();
    }


    public void createNewEntity(int count);

    public void createNewEntity();

    public void setCurrentEntity(UUID uuid);

    public UUID getCurrentEntity();

    public User getCurrentUser(String uuid);

}
