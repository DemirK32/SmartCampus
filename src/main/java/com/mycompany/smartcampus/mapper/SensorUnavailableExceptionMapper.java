/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.mapper;

import com.mycompany.smartcampus.exception.SensorUnavailableException;
import com.mycompany.smartcampus.model.ErrorMessage;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author asus
 */
@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException>{
    @Override
    public Response toResponse(SensorUnavailableException exception) {

        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                403,
                "Sensor is currently in MAINTENANCE mode and cannot accept readings."
        );

        return Response.status(Response.Status.FORBIDDEN)
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
