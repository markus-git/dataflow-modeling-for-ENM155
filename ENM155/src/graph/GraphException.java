package graph;

/** Runtime exception used for reporting graph specific exceptions. */
public class GraphException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GraphException(final String message) {
        super(message);
    }

    public GraphException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GraphException(final Throwable cause) {
        super(cause.getMessage(), cause);
    }
}