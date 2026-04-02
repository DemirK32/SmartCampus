/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.mapper;

import com.mycompany.smartcampus.exception.LinkedResourceNotFoundException;
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
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {
    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) {

        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                422,
                "The request body is valid JSON, but it refers to a roomId that does not exist."
        );

        return Response.status(422)
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    //Burada Response.status(422) yazıyoruz çünkü 
    //Response.Status içinde standart enum olarak 422 yok. Bu normal
}
