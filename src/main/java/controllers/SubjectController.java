package controllers;

import domain.models.Subject;
import filters.customAnnotations.OnlyAdmin;
import services.SubjectService;
import services.interfaces.ISubjectService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/subject")
public class SubjectController {
    private ISubjectService subjectService;

    public SubjectController() {
        subjectService = new SubjectService();
    }

    @OnlyAdmin
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addSubject(Subject subject) {
        try {
            subjectService.addSubject(subject);
        } catch (ServerErrorException e) {
            return Response
                    .serverError()
                    .build();
        } catch (BadRequestException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        return Response
                .status(Response.Status.CREATED)
                .entity("Subject successfully created!")
                .build();
    }
}
