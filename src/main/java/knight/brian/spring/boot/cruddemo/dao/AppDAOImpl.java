package knight.brian.spring.boot.cruddemo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import knight.brian.spring.boot.cruddemo.entity.Course;
import knight.brian.spring.boot.cruddemo.entity.Instructor;
import knight.brian.spring.boot.cruddemo.entity.InstructorDetail;
import knight.brian.spring.boot.cruddemo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO {

    // define field for entity manager
    private EntityManager entityManager;

    // inject the entity manager
    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Instructor instructor) {

    }

    @Override
    public Instructor findInstructorById(int id) {
        return null;
    }

    @Override
    public void deleteInstructorById(int id) {

    }

    @Override
    public InstructorDetail findInstructorDetailById(int id) {
        return null;
    }

    @Override
    public void deleteInstructorDetailById(int id) {

    }

    @Override
    public List<Course> findCoursesByInstructorId(int id) {
        return List.of();
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int id) {
        return null;
    }

    @Override
    public void update(Instructor instructor) {

    }

    @Override
    @Transactional
    public void update(Course course) {
        entityManager.merge(course);
    }

    @Override
    public Course findCourseById(int id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    @Transactional
    public void deleteCourseById(int id) {
        Course tempCourse = entityManager.find(Course.class, id);

        entityManager.remove(tempCourse);
    }

    @Override
    @Transactional
    public void save(Course course) {
        // CascadeType.ALL in Course saves associated Reviews
        entityManager.persist(course);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int id) {

        // Using query with JOIN FETCH to eagerly get results due to FetchType.LAZY
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c " +
                   "JOIN FETCH c.reviews " +
                   "where c.id = :data",
                Course.class);

        query.setParameter("data", id);

        Course course = query.getSingleResult();

        return course;
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int id) {

        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c " +
                   "JOIN FETCH c.students " +
                   "where c.id = :data",
                Course.class);

        query.setParameter("data", id);

        return query.getSingleResult();
    }

    @Override
    public Student findStudentAndCoursesByStudentId(int id) {

        TypedQuery<Student> query = entityManager.createQuery(
                "select s from Student s " +
                   "JOIN FETCH s.courses " +
                   "where s.id = :data",
                Student.class);

        query.setParameter("data", id);

        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void update(Student student) {
        // update a Student with new courses which persists to associated Courses
        entityManager.merge(student);
    }

    @Override
    @Transactional
    public void deleteStudentById(int id) {

        Student tempStudent = entityManager.find(Student.class, id);

        if (tempStudent != null) {

            // get the courses
            List<Course> courses = tempStudent.getCourses();

            // break association of all courses for the student
            for (Course tempCourse : courses) {
                tempCourse.getStudents().remove(tempStudent);
            }

            // Now delete the student
            entityManager.remove(tempStudent);
        }
    }

}
