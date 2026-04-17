# Property administration

## Description

# A RESTful API application must be developed for managing hotels with the following endpoints.


1. GET /property-view/hotels — retrieves a list of all hotels with brief information.

Example response:
[
{
"id": 1,
"name": "DoubleTree by Hilton Minsk",
"description": "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belarusian capital and stunning views of Minsk city from the hotel's 20th floor...",
"address": "9 Pobediteley Avenue, Minsk, 220004, Belarus",
"phone": "+375 17 309-80-00"
}
]


2. GET /property-view/hotels/{id} — retrieves detailed information about a specific hotel.

Example response:
{
"id": 1,
"name": "DoubleTree by Hilton Minsk",
"description": "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belarusian capital and stunning views of Minsk city from the hotel's 20th floor...",
"brand": "Hilton",
"address": {
"houseNumber": 9,
"street": "Pobediteley Avenue",
"city": "Minsk",
"country": "Belarus",
"postCode": "220004"
},
"contacts": {
"phone": "+375 17 309-80-00",
"email": "doubletreeminsk.info@hilton.com"
},
"arrivalTime": {
"checkIn": "14:00",
"checkOut": "12:00"
},
"amenities": [
"Free parking",
"Free WiFi",
"Non-smoking rooms",
"Concierge",
"On-site restaurant",
"Fitness center",
"Pet-friendly rooms",
"Room service",
"Business center",
"Meeting rooms"
]
}


3. GET /property-view/search — returns a list of hotels with brief information filtered by parameters: name, brand, city, country, amenities.

Example request:
GET /property-view/search?city=minsk

Response format is the same as GET /property-view/hotels.


4. POST /property-view/hotels — creates a new hotel.

Example request:
{
"name": "DoubleTree by Hilton Minsk",
"description": "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belarusian capital...",
"brand": "Hilton",
"address": {
"houseNumber": 9,
"street": "Pobediteley Avenue",
"city": "Minsk",
"country": "Belarus",
"postCode": "220004"
},
"contacts": {
"phone": "+375 17 309-80-00",
"email": "doubletreeminsk.info@hilton.com"
},
"arrivalTime": {
"checkIn": "14:00",
"checkOut": "12:00"
}
}

Example response:
{
"id": 1,
"name": "DoubleTree by Hilton Minsk",
"description": "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belarusian capital...",
"address": "9 Pobediteley Avenue, Minsk, 220004, Belarus",
"phone": "+375 17 309-80-00"
}


5. POST /property-view/hotels/{id}/amenities — adds a list of amenities to a specific hotel.

Example request:
[
"Free parking",
"Free WiFi",
"Non-smoking rooms",
"Concierge",
"On-site restaurant",
"Fitness center",
"Pet-friendly rooms",
"Room service",
"Business center",
"Meeting rooms"
]


6. GET /property-view/histogram/{param} — returns the number of hotels grouped by a specified parameter (brand, city, country, amenities).

Example request:
GET /property-view/histogram/city

Example response:
{
"Minsk": 1,
"Moscow": 2,
"Mogilev": 1
}

Example response (amenities):
{
"Free parking": 1,
"Free WiFi": 20,
"Non-smoking rooms": 5,
"Fitness center": 1
}

The application must be runnable from the console using:
mvn spring-boot:run

The application must run on port:
8092

All endpoints must use the common prefix:
/property-view

Example:
GET /property-view/hotels
GET /property-view/search

Required technologies:
Maven
Java 21+
Spring Boot
Spring Data JPA
Liquibase

Database:
H2

Final submission must be a GitHub repository link.

IMPORTANT: The solution must be implemented using Java 21. 
The application will be validated in an automated environment and must strictly meet all requirements.