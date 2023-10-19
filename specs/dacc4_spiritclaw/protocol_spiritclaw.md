# Specifications 

## 1. Overview
This protocol is a client-server protocol. 
The client connects to the server and submits a calculation request. 
The server computes it and sends the result back to the client. 
If the request is invalid, it sends back an error.

## 2. Transport layer protocol
The protocol uses TCP. The client establishes the connection. 
It has to know the IP address of the server. 
The server listens on TCP port 4242. 
The server closes the connection once the result has been sent or if an error occurred. 
The messages are sent in text format.

## 3. Messages
The client sends a request to the server. There are several types of requests possible :
- ADD a b
- SUB a b
- MUL a b
- DIV a b
- POW a b
- MOD a b
The server tries to compute the result and sends it back to the client. 
- RSLT a
If the request is invalid, the server sends an error message.
The errors are detailed below. It starts with ERR and the error code.
## 4. Specific elements
There a different errors possible :
- INVOP : invalid operation
- INVNB : invalid number of arguments
- DIV0 : division by zero
- INVARG : invalid argument
- UNKERR : unknown error

## 5. Example dialogs
