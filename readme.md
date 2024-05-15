# Customer Onboarding API for XYZ Bank

This project is designed to improve customer onboarding for XYZ bank by enabling remote registration and account management using RESTful APIs. Customers can register, log in, and view account details, all from the comfort of their home using any electronic device.

## Features

- **Customer Registration**: Register with basic details and receive a unique username and auto-generated password.
- **Authentication**: Secure login and token retrieval.
- **Account Overview**: View details such as account balance and type.
- **Supports**: Only customers from the Netherlands and Belgium can register.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java JDK 11 or newer
- Maven
- Postgres database

### Installing

1. **Clone the repository**
   ```bash
   git clone https://github.com//customer-onboarding.git
   cd customer-onboarding
   
## Check token is valid
By below command, you can check if the token is valid or not.
```bash
curl -X POST http://localhost:8080/auth/logon -H "Authorization: Bearer <TOKEN_FROM_ENDPOINT>
