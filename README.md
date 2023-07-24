# tinyLink

# Title
TinyURL is a URL shortening web service, which provides short aliases for redirection of long URLs.

# Context
The most important part of this system is how to create and store short links.
Storing the link as a string is not efficient and it is not a good idea.

# Decision
A better way is to store links numerically.
A URL character can be a lower case alphabet ,an upper case alphabet and a digit.There are 62 possible characters.
So the task is to convert a decimal number to base 62 number.Because short links must be unique, 
the best solution is to use their ID as the private key of the database.
To get the short URL, we need to get URL id in the database and encode to Base62.


You can find the database structure , insert sql and postman collection in this repo.
