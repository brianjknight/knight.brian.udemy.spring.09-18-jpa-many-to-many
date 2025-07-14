package knight.brian.spring.boot.cruddemo;

import knight.brian.spring.boot.cruddemo.dao.AppDAO;
import knight.brian.spring.boot.cruddemo.entity.Course;
import knight.brian.spring.boot.cruddemo.entity.Instructor;
import knight.brian.spring.boot.cruddemo.entity.InstructorDetail;
import knight.brian.spring.boot.cruddemo.entity.Review;
import knight.brian.spring.boot.cruddemo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {

		return runner -> {
			// ManyToMany methods
//			createCourseAndStudents(appDAO);
//			findCourseAndStudents(appDAO);
//			findStudentAndCourses(appDAO);
			addMoreCoursesForStudent(appDAO);
		};
	}

	private void addMoreCoursesForStudent(AppDAO appDAO) {
		int theId = 2;
		Student tempStudent = appDAO.findStudentAndCoursesByStudentId(theId);

		// create more courses
		Course tempCourse1 = new Course("Rubik's Cube - How to Speed Cube");
		Course tempCourse2 = new Course("Atari 2600 - Game Development");

		// add courses to student
		tempStudent.addCourse(tempCourse1);
		tempStudent.addCourse(tempCourse2);

		System.out.println("Updating student: " + tempStudent);
		System.out.println("associated courses: " + tempStudent.getCourses());

		appDAO.update(tempStudent);

		System.out.println("Done!");

	}

	private void findStudentAndCourses(AppDAO appDAO) {
		int theId = 2;
		Student tempStudent = appDAO.findStudentAndCoursesByStudentId(theId);

		System.out.println("Loaded student: " + tempStudent);
		System.out.println("Courses for student: " + tempStudent.getCourses());

		System.out.println("Done!");

	}

	private void findCourseAndStudents(AppDAO appDAO) {

		int id = 100;
		Course tempCourse = appDAO.findCourseAndStudentsByCourseId(id);

		System.out.println("Loaded course: " + tempCourse);
		System.out.println("Students for course: " + tempCourse.getStudents());
		System.out.println("done");
	}

	private void createCourseAndStudents(AppDAO appDAO) {
		// create a course
		Course tempCourse = new Course("Pacman - How To Score One Million Points");

		// create the students
		Student tempStudent1 = new Student("John", "Doe", "john@luv2code.com");
		Student tempStudent2 = new Student("Mary", "Public", "mary@luv2code.com");

		// add students to the course
		tempCourse.addStudent(tempStudent1);
		tempCourse.addStudent(tempStudent2);

		// save the course and associated students
		System.out.println("Saving the course: " + tempCourse);
		System.out.println("associated students: " + tempCourse.getStudents());

		appDAO.save(tempCourse);

		System.out.println("Done!");
	}

	private void deleteCourseAndReviews(AppDAO appDAO) {
		int id = 100;

		System.out.println("Deleting Course & Reviews for id: " + id);
		appDAO.deleteCourseById(id);
		System.out.println("done deleting");
	}

	private void retrieveCourseAndReviews(AppDAO appDAO) {
		int id = 100;
		System.out.println("Retrieving course and reviews...");
		Course tempCourse = appDAO.findCourseAndReviewsByCourseId(id);
		System.out.println("tempCourse: ");
		System.out.println(tempCourse);
		System.out.println(tempCourse.getReviews());
		System.out.println("done retrieving course and reviews");
	}

	private void createCourseAndReviews(AppDAO appDAO) {
		Course tempCourse = new Course("Pacman - How To Score One Million");
		tempCourse.addReview(new Review("Cool course!"));
		tempCourse.addReview(new Review("Great job!"));
		tempCourse.addReview(new Review("Bad course!"));
		System.out.println("Saving course...");
		// CascadeType.ALL in Course saves associated Reviews
		appDAO.save(tempCourse);
		System.out.println(tempCourse);
		System.out.println(tempCourse.getReviews());
		System.out.println("done saving course");
	}


}
