package knight.brian.spring.boot.cruddemo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import knight.brian.spring.boot.cruddemo.entity.Course;
import knight.brian.spring.boot.cruddemo.entity.Instructor;
import knight.brian.spring.boot.cruddemo.entity.InstructorDetail;
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

}
