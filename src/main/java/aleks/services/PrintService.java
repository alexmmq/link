package aleks.services;

public interface PrintService {
    //invoke a message to inform user - there is no such link
    public void printNoLink();

    ////invoke a case UUID/userNotFound
    public void printUserNotFound();

    // the link is expired message, no longer active
    public void printLinkExpired(String cause);

    // the link has been created
    public void printLinkCreated();
}
