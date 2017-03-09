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
public class TestStudent {

    private IStudent student;
    private IAdmin admin;
    private IInstructor instructor;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
        this.instructor = new Instructor();
    }

    //tests for overenrollment
    //BUG
    @Test
    public void testReister(){
        this.admin.createClass("Class", 2017,"Instructor", 1);
        this.student.registerForClass("Student", "Class", 2017);
        this.student.registerForClass("Student2", "Class", 2017);
        assertFalse(this.student.isRegisteredFor("Student2", "Class", 2017));
    }

    @Test
    //Testing if a student can drop a class he's not registered for, but no getter function to finish this off.
    public void testDropIfEnrolled(){
        this.admin.createClass("Class", 2017, "Instructor", 5);
        this.admin.createClass("Class2", 2017, "Instructor", 5);
        this.student.registerForClass("Student", "Class", 2017);
        this.student.dropClass("Student", "Class2", 2017);
       // assertTrue(this.student.isRegisteredFor("Student", ))
    }

    //Testing if students can submit homework
    //BUG
    @Test
    public void testSubmitHomework(){
        this.admin.createClass("Class", 2017,"Instructor", 1);
        //this.student.registerForClass("Student", "Class", 2017);
        this.instructor.addHomework("Instructor", "Class", 2017,
                "Homework", "First");
        this.student.submitHomework("Student", "Homework",
                                    "Answer", "Class", 2017);
        this.instructor.assignGrade("Instructor", "Class", 2017,
                                    "Homework", "Student", 50);
        assertFalse(this.instructor.getGrade("Class", 2017, "Homework", "Student") != null);
    }

}
