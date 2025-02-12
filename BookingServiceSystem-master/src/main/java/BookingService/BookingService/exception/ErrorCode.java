package BookingService.BookingService.exception;

public enum ErrorCode {
    USER_EXISTED(1002,"User existed"),
    UNCATEGORIZED(9999,"Uncategorized error"),
    INVALID_EMAIL(1003,"Email is invalid"),
    PASSWORD_INVALID(1004, "Password must be at least 4 characters"),
    USER_NOT_EXISTED(1005, "User not existed"),
    BOOKING_NOT_EXISTED(1011, "Booking not existed"),
    UNAUTHENTICATED(1006, "Unauthenticated"),
    INVALID_KEY(1001, "Invalid message key"),
    SKIN_THERAPIST_NOT_EXISTED(1010, "Skin therapist not existed"),
    NAME_INVALID(1007, "Name must not be blank");

    private int code;
    private String message;

    ErrorCode(int code, String message){
        this.code = code;
        this.message = message;
    }
    public int getCode(){return code;}
    public String getMessage(){return message;}

    public void setCode(int code){this.code=code;}
    public void setMessage(String msg){this.message=msg;}
}
