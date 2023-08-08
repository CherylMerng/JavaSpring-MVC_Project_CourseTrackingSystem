package sg.nus.iss.cts.exception;

public class UnauthorizedException extends Exception {
  private static final long serialVersionUID = 1L;

  public UnauthorizedException() {
  }

  public UnauthorizedException(String msg) {
    super(msg);
  }
}
