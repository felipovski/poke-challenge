# Poke Service

The current project is a challenge accepted from Looqbox. It entails accessing PokéAPI, retrieving data regarding pokemon names and exposing two endpoints (GET methods).

## Architecture

We decided to create a Gateway between clients and services. You can see bellow the basic model:

![](/src/main/resources/images/arquitetura.jpg)

As part of the challenge, we chose to build the service with the Boundary-Control-Entity - BCE architecture (Adam Bien, a well-known Java Champion, is usually the one who propagated this design).

![](/src/main/resources/images/bce.png)

### Boundary
The interface with the external world, where we can find the Controllers exposing some endpoints.

### Control
Where all the logic happens.

### Entity
Originally, it would contain the domain objects. In our case, we decided to keep the DTOs inside this package (ideally, we would keep them in the ancillary or control package).

### Ancillary
Not part of the BCE design. It is just a package with auxiliary classes, such as Utils.


## How it works

You just need to run the command (as it will build and deploy the services):

```docker-compose up --build```

Two endpoints will be available:
- /pokemons?query=&sort=
- /pokemons/highlight?query=&sort=

in the local URI:
- http://localhost:8082/api/v1 **

** Note: Since we are not exposing the Poke Service, all requests should be done to gateway service

Next steps:
- Collect metrics via Prometheus and present them in a Grafana dashboard
- User and permissions management with Keycloak
