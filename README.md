# Cloud-Native Inventory System (Secure Version)
This branch/version represents the Enterprise-Ready state of the system, featuring a centralized Identity and Access Management (IAM) layer using Keycloak.
🛡 Security Architecture
Unlike the open prototype, this version implements OAuth2 and OpenID Connect (OIDC) protocols to secure the entire ecosystem.
•	Identity Provider: Keycloak (Open Source IAM)
•	Protocol: OAuth2 + JWT (JSON Web Tokens)
•	Authentication Flow: Authorization Code Flow with PKCE (for Frontend)
•	Role-Based Access Control (RBAC): Fine-grained permissions for API endpoints.

# Keycloak Configuration
To successfully run this version, the following IAM components must be configured:
1.	Realm: inventory-realm
2.	Client: inventory-client
o	Access Type: Confidential / Public (based on frontend needs)
o	Valid Redirect URIs: http://your-domain:5173/*
o	Web Origins: http://your-domain:5173
3.	Roles: user, admin
4.	Test Users:
o	apiuser / 123456

# Technical Implementation
Backend (Spring Boot Security)
The backend acts as an OAuth2 Resource Server. It validates the incoming JWT from the frontend against the Keycloak JWKS endpoint.
•	Issuer URI: http://keycloak:8080/auth/realms/inventory-realm
•	Validation: Signature and expiration check on every request.
Frontend (React + Keycloak.js)
The frontend is wrapped with the official keycloak-js adapter.
•	Auto-Login: Users are redirected to the Keycloak login page if not authenticated.
•	Token Management: Automatic token refresh and injection into Axios headers.

# Setup & Troubleshooting
If you are deploying this on a cloud environment (GCP/AWS) without HTTPS:
1.	Web Crypto API: Modern browsers require HTTPS or localhost for the Web Crypto API used by Keycloak. For HTTP cloud deployments, use the chrome://flags bypass for testing.
2.	Internal Network: Ensure the backend container can reach the Keycloak container via the Docker internal DNS (e.g., http://inventory-keycloak:8080).
