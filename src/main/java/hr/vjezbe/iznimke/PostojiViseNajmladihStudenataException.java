package hr.vjezbe.iznimke;

public class PostojiViseNajmladihStudenataException extends Exception{
    public PostojiViseNajmladihStudenataException() {
    }

    public PostojiViseNajmladihStudenataException(String message) {
        super(message);
    }

    public PostojiViseNajmladihStudenataException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostojiViseNajmladihStudenataException(Throwable cause) {
        super(cause);
    }

    public PostojiViseNajmladihStudenataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
