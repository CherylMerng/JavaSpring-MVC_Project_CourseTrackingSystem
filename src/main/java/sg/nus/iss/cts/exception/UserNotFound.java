package sg.nus.iss.cts.exception;

public class UserNotFound extends Exception {
  private static final long serialVersionUID = 1L;

  public UserNotFound() {
  }

  public UserNotFound(String msg) {
    super(msg);
  }
}
