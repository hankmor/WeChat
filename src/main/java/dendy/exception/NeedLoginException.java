package dendy.exception;

public class NeedLoginException extends RuntimeException {
    private String target;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public NeedLoginException(String target) {
        super();
        this.target = target;
    }

    public NeedLoginException() {
        super();
    }
}
