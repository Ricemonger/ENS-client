package app.services;

public class InvalidPasswordException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Invalid Password:\n"+
                "Password's format: 6-16 symbols, without {}[]():;'\".,<>/|\\ or space symbols";
    }
}
