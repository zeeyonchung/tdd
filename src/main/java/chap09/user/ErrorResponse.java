package chap09.user;

public class ErrorResponse {
    private final String message;
    private final String errorType;

    public ErrorResponse(String message, String errorType) {
        this.message = message;
        this.errorType = errorType;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorType() {
        return errorType;
    }

    public static ErrorResponse error(Exception e) {
        return new ErrorResponse(e.getMessage(), e.getClass().getSimpleName());
    }
}
