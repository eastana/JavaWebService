package controllers;

import domain.models.Group;
import filters.customAnnotations.JWTTokenNeeded;
import filters.customAnnotations.OnlyAdmin;
import services.interfaces.IGroupService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/group")
public class GroupController {
    private IGroupService groupService;

    public GroupController() {
        groupService = new GroupService();
    }

    @JWTTokenNeeded
    @GET
    @Path("/{specialization}/{group}")
    public Response getInfo(@PathParam("specialization") int spec,
                            @PathParam("group") int groupName) {
        Group group;
        try {
            group = groupService.groupInfo(spec, groupName);
        } catch (ServerErrorException e) {
            return Response
                    .serverError()
                    .build();
        } catch (BadRequestException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        if (group == null) return Response
                .status(Response.Status.NOT_FOUND)
                .build();
        else return Response
                .ok(group)
                .build();
    }

    @OnlyAdmin
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addGroup(Group group) {
        try {
            groupService.addGroup(group);
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
                .entity("Group successfully created!")
                .build();
    }
}
