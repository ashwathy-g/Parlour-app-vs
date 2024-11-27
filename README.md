Parlour Management System 

Welcome to the Parlour Management System, a Spring Boot-based application designed to streamline operations for beauty parlours. This application provides a user-friendly platform for parlour registration, booking services, managing categories, employee availability, and secure access through JWT, OTP-based authentication, and email services.


Admin Module - Parlour App


The Admin Module in the Parlour App is responsible for managing administrative functionalities such as registering admins, authenticating login credentials, managing parlour registration approvals, and handling deletion requests. It integrates seamlessly with JWT-based authentication, email notification services, and Spring Security for role-based access control.

Key Features:



 1.  Admin Login:

Validates admin credentials (email and password) using CustomerUserDetailsService.
Generates and returns a JWT token for authenticated sessions.
Parlour Management:

Retrieve a list of all registered parlours.
Approve or reject parlour registration requests based on status codes:

1 - Approved.

2 - Rejected.

3 - Pending review.

Email notifications are sent to parlour owners to inform them of the status update.
Parlour Deletion:

View all deletion requests made by parlour owners.
Approve or reject deletion requests.
Sends email notifications to parlour owners when deletion requests are approved.
Admins can also directly delete parlours.
Security:

JWT authentication is used for secure access to admin endpoints.
Role-based access control ensures that only admins can access certain operations (e.g., approving parlours).


API for ADMIN:

POST /admin/AdminLogin - Login an admin.

GET /admin/allRegisteredParlour - Fetch all registered parlours.

PUT /admin/approve/{id} - Approve/reject parlour registration.

DELETE /admin/parlour/delete/{id} - Delete a parlour directly.

GET /admin/parlour/deletion-requests - Fetch deletion requests.

PATCH /admin/approve-deletion/{id} - Approve deletion requests.

Category,Subcategory,SubSubCategory are added by admin .

Parlour Module


The Parlour Module is a core component of the Beauty Parlour Management System. It enables parlours to register, manage their profiles, offer services, and interact with the platform. The module also provides functionality for parlour authentication, status management, and deletion requests.


Key Features

  1.  Parlour Registration:

Parlours can register by providing essential details such as name, email, phone number, license information, and location.
Images like profile pictures, cover images, and license images are also supported.

  2.  Authentication:

Parlours can log in using email and password.
Secure login with JWT token generation.

3.  Profile Management:

  Update parlour details such as name, email, password, location, images, and status.

4. Password Management:

Request and validate OTP for password recovery.
Reset password functionality.

5. Status Retrieval:

Fetch the current status of the parlour (e.g., active, inactive).

6. Parlour Deletion:

 Request for account deletion by notifying the admin via email.

7. Offers and Details:

 Fetch all offers associated with a parlour.
Retrieve parlour details and a list of all registered parlours.


API Endpoints


Registration and Authentication


POST /parlour/ParlourReg
Registers a new parlour with details like name, location, and images.

POST /parlour/ParlourLogin
Logs in a parlour and generates a JWT token.

POST /parlour/generate_otp
Generates and sends an OTP to the parlour's email for password recovery.

POST /parlour/forgot_password
Resets the password using a valid OTP.

Parlour Management

GET /parlour/ParlourStatus/{parlourId}
Retrieves the current status of a parlour.

PUT /parlour/update/{id}
Updates the details of an existing parlour, including images.

GET /parlour/name/{parlourName}
Fetches details of a parlour by its name.

GET /parlour/{id}
Retrieves details of a parlour by its ID.

GET /parlour/getAllParlours
Fetches a list of all registered parlours.

Offers and Additional Data


GET /parlour/{parlourId}/offers
Retrieves all offers associated with a parlour.

Deletion and Request Management

DELETE /parlour/delete/{id}
Deletes a parlour account after verifying the provided password.

PATCH /parlour/request-deletion/{id}
Sends a deletion request email to the admin for further review.


User Module

The User Module is designed to manage end-users of the Beauty Parlour Management System. It provides features for user registration, authentication, location-based parlour search, and password recovery using OTP. This module ensures that users have a seamless experience while interacting with the platform.

Key Features

User Registration:

Users can register by providing their name, gender, phone number, email, and password.
Role-based access management is supported.

Authentication:

Secure login using phone number and password.
JWT token generation for authorized API access.


Location-based Search:

Search for nearby parlours using latitude and longitude.

Password Management:

Generate OTP for password recovery.

Validate OTP and reset the password securely.

API Endpoints


User Registration and Authentication

POST /user/UserReg

Registers a new user with details such as name, gender, email, phone number, and password.

POST /user/UserLogin

Authenticates a user and generates a JWT token for further access.

Password Recovery

POST /user/generate-Otp-for-User

Generates and sends an OTP to the user's email for password recovery.

POST /user/forgotPasswordUser

Resets the password using the OTP.

Location-Based Parlour Search


GET /user/userLocation

Retrieves parlours near a specified latitude and longitude.

Technologies Used for this project ,

Spring Boot: Framework for managing the backend API and services.

JWT: Token-based authentication for secure user sessions.

Password Encoder: Ensures secure storage of passwords.

OTP Service: Generates and validates OTPs for password recovery.

Email Service: Sends OTP emails to users.




