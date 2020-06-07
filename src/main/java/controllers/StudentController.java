package controllers;

import domain.models.Student;
import filters.customAnnotations.JWTTokenNeeded;
import filters.customAnnotations.OnlyAdmin;
import services.StudentService;
import services.interfaces.IStudentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/student")
public class StudentController {
    private IStudentService studentService;

    public StudentController() {
        studentService = new StudentService();
    }

    @JWTTokenNeeded
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAll")
    public Response getAll() {
        List<Student> students;
        try {
            students = studentService.getAll();
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .entity(ex.getMessage())
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        }

        if (students == null) return Response
                .status(Response.Status.NOT_FOUND)
                .build();
        else return Response
                .ok(students)
                .build();
    }

    @JWTTokenNeeded
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getStudentByID(@PathParam("id") int id) {
        Student student;
        try {
            student = studentService.getStudentByID(id);
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .entity(ex.getMessage())
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        }

        if (student == null) return Response
                .status(Response.Status.NOT_FOUND)
                .build();
        else return Response
                .ok(student)
                .build();
    }

    @JWTTokenNeeded
    @GET
    @Path("/number")
    public Response getNumber() {
        try {
            long number = studentService.getNumber();
            return Response
                    .ok()
                    .entity("Number of students: " + number)
                    .build();
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .entity(ex.getMessage())
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        }
    }

    @OnlyAdmin
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addStudent(Student student) {
        try {
            studentService.addStudent(student);
        } catch (ServerErrorException ex) {
            return Response.serverError()
                    .entity(ex.getMessage())
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();
        }
        return Response
                .status(Response.Status.CREATED)
                .entity("Student added successfully!")
                .build();
    }


    @OnlyAdmin
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateStudent(Student student) {
        try {
            studentService.updateStudent(student);
        } catch (ServerErrorException ex) {
            return Response.serverError()
                    .entity(ex.getMessage())
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();
        }
        return Response.ok("Student successfully updated!").build();
    }

    @OnlyAdmin
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public Response deleteStudent(Student student) {
        try {
            studentService.deleteStudent(student);
        } catch (ServerErrorException ex) {
            return Response.serverError()
                    .entity(ex.getMessage())
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();
        }
        return Response.ok("Student successfully deleted!").build();
    }
}
