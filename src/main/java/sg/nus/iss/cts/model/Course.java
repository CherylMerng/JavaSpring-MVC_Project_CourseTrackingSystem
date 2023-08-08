package sg.nus.iss.cts.model;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Course class
 *
 * @version $Revision: 1.0
 * @author Suria
 * 
 */

@Entity
@Table(name = "course")
public class Course {

  /** Attributes for Course **/
  @Id
  @Column(name = "courseid")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int courseId;
  
  @Basic
  @Column(name = "employeeid")
  private String employeeId;
  
  @Column(name = "coursename")
  private String courseName;
  
  @Column(name = "organiser")
  private String organiser;
  
  @Column(name = "fromdate")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fromDate;
  
  @Column(name = "todate")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate toDate;
  
  @Basic
  @Column(name = "fees")
  private double fees;
  
  @Basic
  @Column(name = "gstincluded", columnDefinition = "BIT", length = 1)
  private boolean gstIncluded;
  
  @Basic
  @Column(name = "justification")
  private String justification;
  
  @Column(name = "status", columnDefinition = "ENUM('SUBMITTED', 'APPROVED', 'WITHDRAWN', 'UPDATED', 'REJECTED')")
  @Enumerated(EnumType.STRING)
  private CourseEventEnum status;

  public Course() {}
  
  public Course(int courseId) {
    this.courseId = courseId;
  }
  
  public Course(String employeeId, String courseName, String organiser, LocalDate fromDate, LocalDate toDate,
      double fees, boolean gstIncluded, String justification, CourseEventEnum status) {
    super();
    this.employeeId = employeeId;
    this.courseName = courseName;
    this.organiser = organiser;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.fees = fees;
    this.gstIncluded = gstIncluded;
    this.justification = justification;
    this.status = status;
  }

  public int getCourseId() {
    return courseId;
  }

  public void setCourseId(int courseId) {
    this.courseId = courseId;
  }

  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getOrganiser() {
    return organiser;
  }

  public void setOrganiser(String organiser) {
    this.organiser = organiser;
  }

  public LocalDate getFromDate() {
    return fromDate;
  }

  public void setFromDate(LocalDate fromDate) {
    this.fromDate = fromDate;
  }

  public LocalDate getToDate() {
    return toDate;
  }

  public void setToDate(LocalDate toDate) {
    this.toDate = toDate;
  }

  public double getFees() {
    return fees;
  }

  public void setFees(double fees) {
    this.fees = fees;
  }

  public boolean isGstIncluded() {
    return gstIncluded;
  }

  public void setGstIncluded(boolean gstIncluded) {
    this.gstIncluded = gstIncluded;
  }

  public String getJustification() {
    return justification;
  }

  public void setJustification(String justification) {
    this.justification = justification;
  }

  public CourseEventEnum getStatus() {
    return status;
  }

  public void setStatus(CourseEventEnum status) {
    this.status = status;
  }

}
