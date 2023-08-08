package sg.nus.iss.cts.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.cts.model.Course;
import sg.nus.iss.cts.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {

  @Resource
  private CourseRepository courseRepository;

  @Override
  public List<Course> findAllCourses() {
    return courseRepository.findAll();
  }

  @Override
  @Transactional
  public Course findCourse(Integer ceid) {
    return courseRepository.findById(ceid).orElse(null);
  }

  @Override
  @Transactional
  public Course createCourse(Course course) {
    return courseRepository.saveAndFlush(course);
  }

  @Override
  @Transactional
  public Course changeCourse(Course course) {
    return courseRepository.saveAndFlush(course);
  }

  @Override
  @Transactional
  public void removeCourse(Course course) {
    courseRepository.delete(course);
  }

  @Override
  @Transactional
  public List<Course> findCoursesByEID(String eid) {
    return courseRepository.findCoursesByEID(eid);
  }

  @Override
  @Transactional
  public List<Course> findPendingCoursesByEID(String eid) {
    return courseRepository.findPendingCoursesByEID(eid);
  }
}
