/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.resources;

/**
 *
 * @author asus
 */
import com.mycompany.smartcampus.datamodel.Room;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.smartcampus.exception.RoomNotEmptyException;

@Path("rooms")
public class SensorRoomResource {

    private static List<Room> rooms = new ArrayList<>();
    private static int idCounter = 1;

    public static List<Room> getRooms() {
        return rooms;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllRooms() {
        return rooms;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room) {

        String generatedRoomId = "ROOM-" + String.format("%03d", idCounter++);
        room.setId(generatedRoomId);

        rooms.add(room);

        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomById(@PathParam("id") String id) {

        for (Room room : rooms) {
            if (room.getId().equals(id)) {
                return Response.ok(room).build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Room not found")
                .build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("id") String id) {

        for (Room room : rooms) {

            if (room.getId().equals(id)) {

                if (!room.getSensorIds().isEmpty()) {
                    throw new RoomNotEmptyException("Room cannot be deleted because it is currently occupied by active hardware.");
                }

                rooms.remove(room);
                return Response.ok("Room deleted successfully").build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Room not found")
                .build();
    }
}


