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

### 1. Discovery
```bash
curl -X GET http://localhost:8080/SmartCampus/api/v1

curl -X POST http://localhost:8080/SmartCampus/api/v1/rooms \
-H "Content-Type: application/json" \
-d "{\"name\":\"Room A\",\"capacity\":70}"

curl -X GET http://localhost:8080/SmartCampus/api/v1/rooms

curl -X POST http://localhost:8080/SmartCampus/api/v1/sensors \
-H "Content-Type: application/json" \
-d "{\"type\":\"CO2\",\"status\":\"ACTIVE\",\"currentValue\":400.5,\"roomId\":\"ROOM-001\"}"

curl -X GET "http://localhost:8080/SmartCampus/api/v1/sensors?type=CO2"

curl -X POST http://localhost:8080/SmartCampus/api/v1/sensors/CO2-001/readings \
-H "Content-Type: application/json" \
-d "{\"value\":421.7}"

curl -X GET http://localhost:8080/SmartCampus/api/v1/sensors/CO2-001/readings

