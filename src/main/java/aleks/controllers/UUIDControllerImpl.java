package aleks.controllers;

import aleks.entity.User;

import java.util.UUID;

public class UUIDControllerImpl implements UUIDController {

    @Override
    public void createNewEntity(int count) {
        User user = new User(UUIDController.generateUUID(), count);
    }
}
