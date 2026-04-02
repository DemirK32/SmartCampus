# SmartCampus

## Overview
SmartCampus is a JAX-RS based RESTful API for managing rooms, sensors, and sensor readings within a campus environment. The system supports room management, sensor registration, filtering, and historical data tracking using sub-resources.

The application is built using JAX-RS (Jersey) and runs on Apache Tomcat. All data is stored in memory using Java data structures such as ArrayList and HashMap.

---

## Technology Stack
- Java
- JAX-RS (Jersey)
- Apache Tomcat 9
- Maven
- JSON
- In-memory data structures (ArrayList, HashMap)

---

## API Base URL
http://localhost:8080/SmartCampus/api/v1


---

## Main Features
- Discovery endpoint for API navigation
- Room management (create, list, delete)
- Sensor registration linked to rooms
- Sensor filtering using query parameters
- Historical sensor readings using sub-resources
- Automatic update of sensor current value
- Custom error handling with JSON responses
- Request and response logging using filters

---

## How to Build and Run the Project
1. Open the project in NetBeans.
2. Make sure Apache Tomcat 9 is configured.
3. Right-click the project and select:
   - Clean and Build
4. Run the project.
5. Open Postman or browser and use:
http://localhost:8080/SmartCampus/api/v1


---

## Main Endpoints

### Discovery
- GET /api/v1

### Rooms
- GET /api/v1/rooms
- POST /api/v1/rooms
- GET /api/v1/rooms/{roomId}
- DELETE /api/v1/rooms/{roomId}

### Sensors
- GET /api/v1/sensors
- GET /api/v1/sensors?type=CO2
- POST /api/v1/sensors

### Sensor Readings
- GET /api/v1/sensors/{sensorId}/readings
- POST /api/v1/sensors/{sensorId}/readings

---

## Sample curl Commands

```bash
# Discovery
curl -X GET http://localhost:8080/SmartCampus/api/v1

# Create Room
curl -X POST http://localhost:8080/SmartCampus/api/v1/rooms \
-H "Content-Type: application/json" \
-d "{\"name\":\"Room A\",\"capacity\":70}"

# Get All Rooms
curl -X GET http://localhost:8080/SmartCampus/api/v1/rooms

# Create Sensor
curl -X POST http://localhost:8080/SmartCampus/api/v1/sensors \
-H "Content-Type: application/json" \
-d "{\"type\":\"CO2\",\"status\":\"ACTIVE\",\"currentValue\":400.5,\"roomId\":\"ROOM-001\"}"

# Filter Sensors
curl -X GET "http://localhost:8080/SmartCampus/api/v1/sensors?type=CO2"

# Add Sensor Reading
curl -X POST http://localhost:8080/SmartCampus/api/v1/sensors/CO2-001/readings \
-H "Content-Type: application/json" \
-d "{\"value\":421.7}"

# Get Sensor Readings
curl -X GET http://localhost:8080/SmartCampus/api/v1/sensors/CO2-001/readings
```
