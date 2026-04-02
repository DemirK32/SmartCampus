/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.resources;

import javax.ws.rs.Path;

import com.mycompany.smartcampus.datamodel.Room;
import com.mycompany.smartcampus.datamodel.Sensor;
import com.mycompany.smartcampus.exception.LinkedResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author asus
 */
@Path("sensors")
public class SensorResource {

    private static List<Sensor> sensors = new ArrayList<>();
    private static int sensorIdCounter = 1;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getAllSensors(@QueryParam("type") String type) {
        if (type == null || type.isEmpty()) {
            return sensors;
        }
        
        List<Sensor> filteredSensors = new ArrayList<>();

        for (Sensor sensor : sensors) {
            if (sensor.getType() != null && sensor.getType().equalsIgnoreCase(type)) {
                filteredSensors.add(sensor);
            }
        }

        return filteredSensors;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {

        List<Room> rooms = SensorRoomResource.getRooms();

        Room matchedRoom = null;

        for (Room room : rooms) {
            if (room.getId().equals(sensor.getRoomId())) {
                matchedRoom = room;
                break;
            }
        }

        if (matchedRoom == null) {
            throw new LinkedResourceNotFoundException("Room does not exist for the given roomId.");
        } // bu kismi degistirdik LinkedResourceNotFoundException ekledik

        String generatedSensorId = sensor.getType() + "-" + String.format("%03d", sensorIdCounter++);
        sensor.setId(generatedSensorId);

        sensors.add(sensor);
        matchedRoom.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }

    public static List<Sensor> getSensors() {
        return sensors;
    }

    public static Sensor getSensorById(String sensorId) {
        for (Sensor sensor : sensors) {
            if (sensor.getId().equals(sensorId)) {
                return sensor;
            }
        }
        return null;
    }

    @Path("{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}
