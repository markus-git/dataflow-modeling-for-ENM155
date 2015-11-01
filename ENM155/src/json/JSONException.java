package json;

/** Runtime exception used for reporting JSON specific exceptions. */
public class JSONException extends RuntimeException {

    private static final long serialVersionUID = 1L;
	
    public JSONException(final String message) {
        super(message);
    }

    public JSONException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public JSONException(final Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
