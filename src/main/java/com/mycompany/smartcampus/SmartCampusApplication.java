package com.mycompany.smartcampus;

import com.mycompany.smartcampus.mapper.RoomNotEmptyExceptionMapper;
import com.mycompany.smartcampus.resources.DiscoveryResource;
import com.mycompany.smartcampus.resources.SensorReadingResource;
import com.mycompany.smartcampus.resources.SensorResource;
import com.mycompany.smartcampus.resources.SensorRoomResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.mycompany.smartcampus.mapper.LinkedResourceNotFoundExceptionMapper;
import com.mycompany.smartcampus.mapper.SensorUnavailableExceptionMapper;

import com.mycompany.smartcampus.mapper.GeneralExceptionMapper;

import com.mycompany.smartcampus.filter.LoggingFilter;
/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("api/v1")
public class SmartCampusApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        classes.add(DiscoveryResource.class);
        classes.add(SensorRoomResource.class);
        classes.add(SensorResource.class);
        classes.add(SensorReadingResource.class);
        classes.add(RoomNotEmptyExceptionMapper.class);
        classes.add(LinkedResourceNotFoundExceptionMapper.class);
        classes.add(SensorUnavailableExceptionMapper.class);
        classes.add(GeneralExceptionMapper.class);
        classes.add(LoggingFilter.class);

        return classes;
    }
}
