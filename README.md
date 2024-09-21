Source Link : https://medium.com/@alyragab70/authentication-types-and-techniques-eb8232eebcfb

Authentication Types and Techniques

API Security is very important to prevent or mitigate attacks on Exposed or Internal APIs.

In this regard i intend to write a series of high level articles on API and Microservice Security principals , And to start with, It is good to start with the different Strategies, Techniques and Types of Authentication Mechanisms

Choosing a matured Authentication Strategy helps to implement the Confidentiality aspect of the CIA Triad which is a goal in itself

We will go in this writing to Define the Technique and How does it work ?

What are the common Authentication Techniques ?
Basic Authentication
Token Based Authentication
Json Web Token (JWT)
Session Based Authentication
Open authorization (OAuth) ( Authorization )
Single Sign On (SSO)
1- Basic Authentication :
A. What ?

The Basic Authentication is a method for an HTTP user agent (e.g. a web browser) to provide a username and password when making a request.
A Request contains a header field in the form of Authorization: Basic <credentials> , where credentials is the Base64 encoding of username and password
So the Credentials are sent in the HTTP request header in Base64 Encoding and If someone can intercept the transmission, the username and password can easily be Decoded
It can be Secured if the transmission between Server and Client is based on a valid TLS Encryption
B. How it works ?

Client sends an HTTP request to a Server
HTTP Server checks if the Request Header contains an Authorization Header which contains a Username and Password or no, If there is a valid Credentails then the Server will send an HTTP response 200 Ok if not then the Response will be 401 Unauthorized in the WWW-authenticate Header by this form : WWW-Authenticate: Basic realm=”Web App”
Noting that the WWW-authenticate Header has something called realm attribute which identifies the set of resources to which the user and password will apply, The Client caches the valid credentials for that Realm for reuse in the future requests.
2- Token Based Authentication:
A. What ?

Token-based Authentication is different from traditional Basic Authentication techniques. Tokens offer a additional layer of security
Token-based authentication is a mechanism which ensures that any HTTP Request to an HTTP Server is accompanied by a Signed Token which the Server verifies for authenticity
That Token is actually a Generated String by the HTTP Server for a Client to be used in any Request going forward
The User/Client retains access as long as the Token remains valid. Once the user logs out, the token is invalidated
So Why using Token ?

Tokens are Stateless, The token contains all the information it needs for authentication. This is great for scalability as it frees your server from having to store session state
Fine-Grained Access Control, Within the token payload we can specify user roles and permissions as well as resources that the user can access.
It is signed by a secret and verified by the server only
It has a Lease duration or Expiry time after this duration it is not valid
B. How it works ?

Client Sends a request to a Server to generate a token by authenticating using a Username and Password at the beginning
If the Server validates the credentials and it is ok then the 200 Ok is sent back to the header of the response with the generated token , If not then Un-processable Entity 422 response code will be sent
Client will cache or store that token in a local storage / cookie to be reused in future requests
Any Request sent by the client will be based on that Token and the same request process again which is , Server validates that token to send 200 Ok in the response , if token is not valid then the response will be 401 invalid token in the response header
Token-based Auth types :
JWT (JSON Web Token)

OAuth (Open Authorization)

SAML (Security Assertions Markup Language)

OpenID Connect

3- JSON Web Token (JWT)
A. What ?

JWT is an Open Standard protocol used to securely share information between Client and Server
It contains an Encoded JSON Objects including a set of claims and Signed using a cryptographic algorithm to ensure that the claims cannot be altered after the token is issued.
JWT has three main components with a “.” as a separation :
Header : to Define the token type and its signing algorithm

Payload : to Defines the token issuer, Expiration of the token …etc “Token Metadata”

Signature : to Verify that the claims have not been changed in transit between Client and Server like (HMACSHA256)

HMACSHA256(header + “.” + Payload Data + Secret)

B. How it works ?

The request sequence is like the normal token based auth like the above one.
4- Session Based Authentication :
A. What ?

Session is actually a file stored in the Server’s memory or local storage or remote datastore , which has all user’s metadata information like (Session ID, User ID, Login time , Login expiry time …etc)
Client will send the Session ID to the server to be used for verification
So as it indicates it is not Stateless type of Auth, It is Stateful which makes things not flexible for scalability and even the revoke unlike the token based.
Server stores this file locally to be able to keep track of the user’s requests and access validation.
B. How it works ?

Client will send a login request including username and password
Server will validate the credentials and respond back with the Session ID to the client, and create that file locally or in its memory
Client will store that Session ID as a Cookie or in a local storage if Cookie is not enabled
Client will send another request with that Session ID
Server responds 200 if it is validated , otherwise responds with 401 Un-authorized
If the Client logged out then the session is killed from the Server and cookie removed from the Client
5- Open authorization (OAuth) :

A. What ?

It is an Open Standard Protocol for Secured Access Delegation , It is Authorization Protocol ( not Authentication ) which allows users to share their private resources to a third party , Like login to a Web Application using Github/Twitter …etc
It works only over HTTPS and Authorizes an API, Applications, Device …etc with Access Tokens rather than credentials
OAuth 2.0 (The active version now) doesn’t define a specific format for Access Tokens. However, the JSON Web Token (JWT) format can be used, This enables token issuers to include data in the token itself. Also, for security reasons, Access Tokens may have an expiration date and a refresh token to refresh the token when expired …etc
B. How it works ?

OAuth Uses 4 types of AuthZ flows (or Grant Types) to generate access tokens as below :
1- client_id: It is the username

2- client_secret: It is like password

3- authorization server: The main engine of OAuth

4- resource server: The API which stores data the application wants to access (That Server that exposes protected resources)

The Access Flow as below :

1- Client App sends a token request to Authorization Server (ex: Github, Twitter)

2- Authorization Server to prompt a login form to the user

3- User to login using username and password

4- Authorization Server Asks the user to define the scope (read email, read repositories …etc)

5- User to Allow the access scope

6- Client App Sends the Authorization Code from the Authorization Server

7- Client App sends get token request from the Authorization Server

8- Client App Gets the Access Token and Accesses the protected resource

9- Finally , Client App sends the token to the Resource Server to validate which will responds back with the access response

More Details can be found HERE

6- Single-Sign On (SSO) :

A. What ?

SSO allows the User to login with single set of Credentials (username and password) to many servers from a centralized location.
SSO is only one aspect of managing user access to many other systems.
Ex: Using OKTA to get access to AWS,Github,JIRA …etc with the same login mechanism of OKTA itself , So without SSO we have to login to each system separately.
SSO given access is done by expiry time, can have MFA , Can define Roles and Permissions ...etc
The protocol between the authentication server and the client applications will, typically, be SAML 2.0, OpenID Connect, Kerberos or other authentication protocol that supports SSO
B. How it works ?

Using Security Assertion Markup Language (SAML) then there will be three main components :
1- User: Typically the End User

2- Identity Provider (IdP): Can be Auth0 or OKTA for example

3- Service Provider (SP): the Client like (AWS, Github …etc)

So user logs into IdP and generates SAML assertion (Which is a signed XML document contains authorization info)
IdP sends that generated SAML Assertion to connected SPs
User sends SAML assertion to the SP
SP Validates the SAML and then create the session for the user
