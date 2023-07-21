package app.services;

public class InvalidUsernameException  extends RuntimeException{
    @Override
    public String getMessage() {
        return "Invalid Username:\n"+
                "Username's format: 6-24 symbols, only letters ,digits and \"_\" symbol allowed";
    }
}
