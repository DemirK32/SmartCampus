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
---

## Report Answers

### Part 1 Question 1
In JAX RS, resource classes are usually created per request. This means that for every 
incoming HTTP request, a new instance of the resource class is created instead of using 
a single shared instance. I think this design is important because it keeps the resource 
classes stateless and avoids unwanted data sharing between different requests. 
However, in this project, some data structures like lists and maps are defined as static. 
Because of this, they are shared across all requests. While this helps keep the data 
during runtime, it can also cause problems. For example, if multiple requests try to 
change the same data at the same time, it may lead to race conditions, inconsistent 
data, or even data loss. 
To prevent these issues, shared data needs to be handled carefully. In my opinion, 
developers should control how the data is accessed and updated and avoid unsafe 
operations. In real systems, using thread safe collections like ConcurrentHashMap 
would be a better solution. So even though JAX RS creates a new resource instance for 
each request, shared static data still needs proper synchronization to keep everything 
consistent. 

### Part 1 Question 2
Hypermedia, also known as HATEOAS, is an important part of advanced RESTful design 
because it allows clients to navigate the API using links provided in the responses. 
Instead of hardcoding endpoints, the client can follow the links given by the server to 
understand what actions are available. 
I think this approach is very useful for client developers. First, it reduces the need for 
external documentation because the API explains itself through responses. Second, it 
lowers the dependency between client and server, so if the API changes, the client is 
less likely to break. Third, it makes the system more flexible, since the client can adjust 
dynamically based on the links it receives. 
Compared to static documentation, which can become outdated, hypermedia always 
provides up to date information about how to use the API. Because of this, the system 
becomes easier to maintain, more scalable, and more user friendly.

### Part 2 Question 1
When returning a list of rooms, there are two main options. One option is to return only 
the room IDs, and the other is to return full room objects. Returning only IDs makes the 
response smaller, so it uses less network bandwidth and improves performance, 
especially in large systems. However, in this case, the client has to send extra requests 
to get the full details of each room, which can increase both complexity and latency. 
On the other hand, returning full room objects gives all the necessary information in a 
single response. I think this makes things easier for the client because it does not need 
to make additional requests. It also simplifies the overall usage of the API. The downside 
is that the response size becomes larger. 
In this project, returning full room objects is more suitable. The data is stored in memory 
and the dataset is small, so the larger response size does not create a serious 
performance problem. It also makes the client side simpler and easier to manage. In 
bigger systems, returning only IDs or using pagination would be a better and more 
scalable approach.

### Part 2 Question 2
Yes, the DELETE operation in this implementation is idempotent. An operation is 
idempotent if repeating it multiple times produces the same result as performing it 
once. 
In this project, when a DELETE request is sent for a room, the system removes the room 
if it exists and meets the required conditions. If the same request is sent again, the room 
will already be deleted, so the system returns a not found response. I think the important 
point here is that the second request does not change the system any further. 
Also, if the room has active sensors, the deletion is not allowed and the system returns a 
409 Conflict response. If the same request is repeated, it will return the same response 
again without changing anything in the system. 
Because of this, the DELETE operation behaves in an idempotent way in all situations, 
since repeating the request does not create any additional changes after the first 
execution.

### Part 3 Question 1
The @Consumes(MediaType.APPLICATION_JSON) annotation means that the POST 
method only accepts requests with a JSON body. If a client sends data in a different 
format like text or XML, the JAX RS runtime cannot process it because it does not match 
the expected format. 
In this case, the server will automatically return an HTTP 415 Unsupported Media Type 
response. I think this is useful because it clearly tells the client that the request format is 
not supported. Also, this behaviour is handled directly by JAX RS, so there is no need to 
add extra validation in the code. 
This approach helps keep the API consistent by making sure that only valid data formats 
are accepted. It also improves reliability and makes the communication between client 
and server more predictable, since both sides agree on using JSON.

### Part 3 Question 2
Using @QueryParam for filtering, like in GET /api/v1/sensors?type=CO2, is generally a 
better approach than putting the filter value inside the URL path. In my opinion, this is 
because query parameters are designed for filtering and optional conditions, while path 
parameters are mainly used to identify a specific resource. 
One advantage of query parameters is flexibility. For example, it is easy to add more 
f
ilters like type and status without changing the structure of the API. This makes the API 
more scalable and easier to extend. It also keeps the URL cleaner and easier to 
understand. 
On the other hand, using path parameters for filtering can make the API structure more 
complicated and less flexible, especially when multiple filters are needed. Because of 
this, query parameters are usually the preferred way to handle filtering and search 
operations in RESTful APIs.

### Part 4 Question 1
The Sub Resource Locator pattern allows a resource class to pass the handling of 
nested paths to another dedicated resource class. Instead of putting all endpoints 
inside one big class, the main resource like SensorResource forwards requests such as 
/sensors/{id}/readings to another class like SensorReadingResource. 
I think this approach is useful because it improves separation of concerns. Each class 
focuses on a specific part of the API, for example one handles sensors and another 
handles sensor readings. This makes the code easier to read and understand. It also 
improves maintainability, since changes can be made in one place without affecting 
everything else. 
Another important advantage is that it reduces complexity. Without this pattern, one 
class would become very large and difficult to manage. In bigger systems, this could 
easily lead to errors. By using sub resources, the application becomes more modular, 
easier to scale, and simpler to manage.

### Part 5 Question 1 (422)
HTTP 422 Unprocessable Entity is more appropriate than 404 in this situation because 
the request itself is valid, but the data inside the request is wrong. The client sends a 
correct JSON body, but the roomId provided does not exist in the system. 
If we return 404, it would suggest that the endpoint is wrong or not found, which is not 
the case here. In my opinion, 422 is more accurate because it shows that the request is 
understood but cannot be processed due to invalid data. This gives clearer feedback to 
the client and helps them understand what needs to be fixed.

### Part 5 Question 2 (500)
Exposing internal Java stack traces to API users is a security risk because it can reveal 
sensitive details about the system. For example, it may show class names, package 
structure, file paths, and parts of the internal logic. I think this information can be very 
useful for attackers, since they can use it to understand how the system works and find 
possible weaknesses. 
Instead of exposing these details, the system should return a generic 500 Internal Server 
Error response. This way, the client is informed that something went wrong, but no 
internal information is leaked. This improves security and protects the system from 
potential attacks.

### Part 5 Question 3 (Logging)
Using JAX RS filters for logging is useful because it allows logging to be handled in a 
centralized way. Instead of writing logging code inside every resource method, filters 
automatically apply logging to all incoming requests and outgoing responses. 
I think this approach reduces code duplication and makes the application easier to 
maintain. It also ensures that logging is consistent across the whole system. Another 
advantage is that it makes debugging easier, since all logs are handled in one place 
instead of being spread across different parts of the code.
