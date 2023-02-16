# Drone-System
## Drone Delivery API

Welcome to the drone-delivery-api service. This is used to manage the delivery of small items.

An API has been made available for such servces and functions as follows.

- You can run this project on the local system.
- You can also consume this api using json format only.
- Having setup the project we can use Postman to run our end point requests.
- You can access the API after successfully running the project on following browser url http://localhost:8099/api/drone.

## The Api End-points

the following are the available end-points:

### **Register** Drone
http://localhost:8099/api/drone/register

### Available Drone for **Loading**
http://localhost:8099/api/drone/available

### Drone currently in the **Loading State**
http://localhost:8099/api/drone/loading

### Check the **items loaded in a specific Drone**
http://localhost:8099/api/drone/loaded/{id}

### Check the **battery level for a specific Drone**
http://localhost:8099/api/drone/battery-check/{id}
