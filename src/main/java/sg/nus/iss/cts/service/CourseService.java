package sg.nus.iss.cts.service;

import java.util.List;

import sg.nus.iss.cts.model.Course;

public interface CourseService {

  List<Course> findAllCourses();

  Course findCourse(Integer ceid);

  Course createCourse(Course course);

  Course changeCourse(Course course);

  void removeCourse(Course course);

  List<Course> findCoursesByEID(String eid);

  List<Course> findPendingCoursesByEID(String eid);

}