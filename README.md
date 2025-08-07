# CSC207-Group-Project

## Sign-Up Login System

A login/signup system is pretty straightforward, however in keeping with Clean Architecture and best practices, we should 
maintain separation of concerns here and have each class perform as few responsibilities as possible. 

The classes for our sign-Up/login system should be: 

### 1. UserData: 

We will have a file, users.json, which stores the usernames and corresponding passwords 
of our users. However, for security purposes, we will use a hashing algorithm to store passwords, so users.json
will store only the hashed password. The purpose of UserData is to read the JSON file and store the data 
so that we can easily access usernames and hashed password. 

### 2. UserAuthenticator 

Once a user enters their password, the hashed password will have to be 
compared to the input, we will use BCrypt.checkpw() for this. This class
will contain a function, say authenticate(), which if it returns true, will 
let the rest of our app know this is a valid user. 

### 3. PasswordHasher 

We can't just store the password a user enters as is, otherwise one could just open the file
and see anyone's password. That's why we'll use BCrypt's hashing algorithm to store passwords
in hashed form. 

### 4. User 

Naturally we want a user class which stores all the information 
relevant to a user. For now we're only concerned with its stored username 
and stored password. Since we don't want to store the password as is, we
should store the hashed password instead. 

 

