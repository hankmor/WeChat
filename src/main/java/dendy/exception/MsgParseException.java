package dendy.exception;

public class MsgParseException extends RuntimeException {
    public MsgParseException(String msg) {
        super(msg);
    }

    public MsgParseException() {
        super();
    }
}
