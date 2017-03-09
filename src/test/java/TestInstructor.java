import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Han on 3/8/17.
 */
public class TestInstructor {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.instructor = new Instructor();
        this.admin = new Admin();
        this.student = new Student();
    }

    //BUG
    //tests for instructors being able to assign homework to not their class.
    @Test
    public void rightAssignHomeworkInstructor(){
        this.admin.createClass("Class", 2017, "Instructor", 5);
        this.instructor.addHomework("Instructor2", "Class", 2017,
                                    "Homework", "First");
        assertFalse(this.instructor.homeworkExists("Class", 2017, "Homework"));
    }
    //NOT A BUG, tests for grades below 0
    @Test
    public void testValidGrades() {
        this.admin.createClass("Class", 2017, "Instructor", 5);
        this.instructor.addHomework("Instructor", "Class", 2017,
                                    "Homework", "First");
        this.student.registerForClass("Student", "Class", 2017);
        this.student.submitHomework("Student", "Homework",
                                    "Answer", "Class", 2017);
        this.instructor.assignGrade("Instructor", "Class", 2017,
                                    "Homework", "Student", -5);
        assertTrue(this.instructor.getGrade("Class", 2017,
                "Homework", "Student") >= 0);
    }

    //BUG
    // //tests for instructors being able to assign grades to not their class.
    @Test
    public void testValidGrader(){
        this.admin.createClass("Class", 2017, "Instructor", 5);
        this.instructor.addHomework("Instructor", "Class", 2017,
                                    "Homework", "First");
        this.student.registerForClass("Student", "Class", 2017);
        this.student.submitHomework("Student", "Homework",
                                    "Answer", "Class", 2017);
        this.instructor.assignGrade("Instructor2", "Class", 2017,
                                    "Homework", "Student", 50);
        assertFalse(this.instructor.getGrade("Class", 2017,
                                "Homework", "Student") == null);
    }

    //Testing for grading homework when it's not been submitted
    //BUG!
    @Test
    public void testNotSubmitted(){
        this.admin.createClass("Class", 2017, "Instructor", 5);
        this.instructor.addHomework("Instructor", "Class", 2017,
                "Homework", "First");
        this.student.registerForClass("Student", "Class", 2017);
        this.instructor.assignGrade("Instructor", "Class", 2016, "Homework", "Student", 50);
        assertFalse(this.instructor.getGrade("Class", 2017, "Homework", "Student") != null);
    }
}
