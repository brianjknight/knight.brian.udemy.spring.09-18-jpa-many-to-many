package knight.brian.spring.boot.cruddemo;

import knight.brian.spring.boot.cruddemo.dao.AppDAO;
import knight.brian.spring.boot.cruddemo.entity.Course;
import knight.brian.spring.boot.cruddemo.entity.Instructor;
import knight.brian.spring.boot.cruddemo.entity.InstructorDetail;
import knight.brian.spring.boot.cruddemo.entity.Review;
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

			// @OneToMany Uni-directional methods
//			createCourseAndReviews(appDAO);
//			retrieveCourseAndReviews(appDAO);
			deleteCourseAndReviews(appDAO);
		};
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
