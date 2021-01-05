# Assignment_DataSec
Assignment 2 for Data Security.

The purpose of this project exercise is to provide hands on experience with the development of a user authentication mechanism.
This Authentication project addresses the design and implemention of  a simple authentication mechanism for a client/server application.

## First step for running the project
Open the application server by locating the registry on your port (in this case port 5099)

## Second step
Run the Client protocol in order to get a login prompt from the server to login. The interface for accessing the server will be prompted and authentication requirements will need to be fulfilled.
In this project, a password storage in a DBMS has been opted. The password are stored in a MariaDB and are encrypted by the PBKDF2 with Hmac SHA512 Hash function. The access control mechanism imlemented in the DBMS is there to provide confidentiality and integrity of the stored passwords.
