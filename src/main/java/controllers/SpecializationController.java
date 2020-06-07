package controllers;

import domain.models.Specialization;
import filters.customAnnotations.OnlyAdmin;
import services.SpecializationService;
import services.interfaces.ISpecializationService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/specialization")
public class SpecializationController {
    private ISpecializationService specService;

    public SpecializationController() {
        specService = new SpecializationService();
    }

    @OnlyAdmin
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addSepcialization(Specialization specialization) {
        try {
            specService.addSpecialization(specialization);
        } catch (ServerErrorException e) {
            return Response.serverError().build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity("Specialization successfully created!").build();
    }
}
