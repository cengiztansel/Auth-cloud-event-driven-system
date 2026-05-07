import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
  url: "[http://cengiztansel.duckdns.org:8081](http://cengiztansel.duckdns.org:8081)" , // Keycloak'un çalıştığı adres
  realm: "inventory-realm",     // Oluşturduğun Realm adı
  clientId: "inventory-frontend", // Oluşturduğun Client ID
});

export default keycloak;