/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.resources;

import com.mycompany.smartcampus.datamodel.Sensor;
import com.mycompany.smartcampus.datamodel.SensorReading;
import com.mycompany.smartcampus.exception.SensorUnavailableException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author asus
 */
public class SensorReadingResource {
    private String sensorId;

    private static Map<String, List<SensorReading>> sensorReadingsMap = new HashMap<>();
    private static int readingIdCounter = 1;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadingsForSensor() {

        Sensor sensor = SensorResource.getSensorById(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        List<SensorReading> readings = sensorReadingsMap.get(sensorId);

        if (readings == null) {
            readings = new ArrayList<>();
        }

        return Response.ok(readings).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReadingToSensor(SensorReading reading) {

        Sensor sensor = SensorResource.getSensorById(sensorId);

        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor is currently unavailable for new readings.");
        }
        
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }
        
        

        if (reading.getTimestamp() == 0) {
            reading.setTimestamp(System.currentTimeMillis());
        }

        String generatedReadingId = "READ-" + String.format("%03d", readingIdCounter++);
        reading.setId(generatedReadingId);

        List<SensorReading> readings = sensorReadingsMap.get(sensorId);

        if (readings == null) {
            readings = new ArrayList<>();
            sensorReadingsMap.put(sensorId, readings);
        }

        readings.add(reading);

        sensor.setCurrentValue(reading.getValue()); // side effect part4
        // When a reading is added, the parent sensor's currentValue field should also be updated

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
}
