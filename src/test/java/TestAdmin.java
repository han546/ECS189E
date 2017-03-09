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
 * Created by Han on 3/7/17.
 */
public class TestAdmin {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
        this.instructor = new Instructor();
    }

    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    //Testing for changing capacity to be lower than currently enrolled students
    //BUG
    @Test
    public void testCapacity(){
        this.admin.createClass("Test", 2017, "Instructor", 2);
        this.student.registerForClass("Student1", "Test", 2017);
        this.student.registerForClass("Student2", "Test", 2017);
        this.admin.changeCapacity("Test", 2017, 1);
        assertFalse(this.admin.getClassCapacity("Test", 2017) < 2);
    }

    //BUG
    //tests for a negative capacity
    @Test
    public void testNegativeCapacity() {
        this.admin.createClass("Test", 2017, "Instructor", -5);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    //BUG
    //tests for a 0 capacity
    @Test
    public void testZeroCapacity() {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    //BUG
    // tests for two classes being same name same year
    @Test
    public void testSameNameYear() {
        this.admin.createClass("Test", 2017, "Bob", 5);
        String firstTeach = this.admin.getClassInstructor("Test", 2017);
        //System.out.print(this.admin.getClassInstructor("Test", 2017));
        this.admin.createClass("Test", 2017, "Joe", 5);
        String secondTeach = this.admin.getClassInstructor("Test", 2017);
        //System.out.print(this.admin.getClassInstructor("Test", 2017));
        assertFalse(firstTeach != secondTeach);
    }

    //BUG
    //tests for one instructor being in more than 2 classes
    @Test
    public void testInstructorCapacity() {
        this.admin.createClass("Test", 2017, "Instructor", 2);
        this.admin.createClass("Test2", 2017, "Instructor", 2);
        this.admin.createClass("Test3", 2017, "Instructor", 2);
        assertFalse ((this.admin.getClassInstructor("Test2", 2017) == this.admin.getClassInstructor("Test", 2017))
                && (this.admin.getClassInstructor("Test", 2017)== this.admin.getClassInstructor("Test3", 2017)));
    }

//    @Test
//    public void testInvalidClassNameNull() {
//        this.admin.createClass(null, 2017, "Instructor", 15);
//        assertFalse(this.admin.classExists(null, 2017));
//    }

}

