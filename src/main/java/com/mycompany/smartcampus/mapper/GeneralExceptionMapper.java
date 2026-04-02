/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.mapper;

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
public class GeneralExceptionMapper implements ExceptionMapper<Throwable>{
    @Override
    public Response toResponse(Throwable exception) {

        ErrorMessage errorMessage = new ErrorMessage(
                "An unexpected internal server error occurred.",
                500,
                "Please contact the administrator or try again later."
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
