package ssm.example.start.redis.model;

public class Notes {
	private String title;
	private String message;
	private Type type;

	public Notes(String title, String message, Type type) {
		super();
		this.title = title;
		this.message = message;
		this.type = type;
	}

	public Notes() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Notes [title=" + title + ", message=" + message + ", type=" + type + "]";
	}

}
