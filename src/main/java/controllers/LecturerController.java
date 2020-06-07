package controllers;

import domain.models.Lecturer;
import filters.customAnnotations.JWTTokenNeeded;
import filters.customAnnotations.OnlyAdmin;
import services.LecturerService;
import services.interfaces.ILecturerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/lecturer")
public class LecturerController {
    private ILecturerService lecturerService;

    public LecturerController() {
        lecturerService = new LecturerService();
    }

    @JWTTokenNeeded
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAll")
    public Response getAll() {
        List<Lecturer> lecturers;
        try {
            lecturers = lecturerService.getAll();
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

        if (lecturers == null) return Response
                .status(Response.Status.NOT_FOUND)
                .build();
        else return Response
                .ok(lecturers)
                .build();
    }

    @JWTTokenNeeded
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getLecturerByID(@PathParam("id") int id) {
        Lecturer lecturer;
        try {
            lecturer = lecturerService.getLecturerByID(id);
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

        if (lecturer == null) return Response
                .status(Response.Status.NOT_FOUND)
                .build();
        else return Response
                .ok(lecturer)
                .build();
    }

    @JWTTokenNeeded
    @GET
    @Path("/number")
    public Response getNumber() {
        try {
            long number = lecturerService.getNumber();
            return Response
                    .ok()
                    .entity("Number of lecturers: " + number)
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
    public Response addLecturer(Lecturer lecturer) {
        try {
            lecturerService.addLecturer(lecturer);
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
                .entity("Lecturer added successfully!")
                .build();
    }

    @OnlyAdmin
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateLecturer(Lecturer lecturer) {
        try {
            lecturerService.updateLecturer(lecturer);
        } catch (ServerErrorException ex) {
            return Response.serverError()
                    .entity(ex.getMessage())
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();
        }
        return Response.ok("Lecturer successfully updated!").build();
    }

    @OnlyAdmin
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public Response deleteLecturer(Lecturer lecturer) {
        try {
            lecturerService.deleteLecturer(lecturer);
        } catch (ServerErrorException ex) {
            return Response.serverError()
                    .entity(ex.getMessage())
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();
        }
        return Response.ok("Lecturer successfully deleted!").build();
    }
}
