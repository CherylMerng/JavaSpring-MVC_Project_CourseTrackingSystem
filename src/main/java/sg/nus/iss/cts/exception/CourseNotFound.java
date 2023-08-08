package sg.nus.iss.cts.exception;

public class CourseNotFound extends Exception {
  private static final long serialVersionUID = 1L;

  public CourseNotFound() {
  }

  public CourseNotFound(String msg) {
    super(msg);
  }
}
