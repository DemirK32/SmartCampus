package com.mycompany.smartcampus.resources;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getApiDiscovery() {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("apiName", "Smart Campus Sensor & Room Management API");
        response.put("version", "v1");
        response.put("contact", "admin@smartcampus.local");

        Map<String, String> resources = new LinkedHashMap<>();
        resources.put("rooms", "/api/v1/rooms");
        resources.put("sensors", "/api/v1/sensors");
        resources.put("sensorReadings", "/api/v1/sensors/{sensorId}/readings");

        response.put("resources", resources);

        return response;
    }
    
    
    
    
}