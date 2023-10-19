# Protocol Design CTP-Calcul transfer protocol
Authors: Andrea Graf, Justin Ferrara

## Overview
The client-server application is a calculator. The client sends an operation to the server and the server returns the
result of the operation. The client can send multiple operations with one connection. The client establishes a connection
and the server keeps the connection open until the client sends a command to quit the connection. The server then closes the connection.

## Transport layer protocol
We will use tcp as transport layer protocol on port 8888 with utf8. The client create and close the connection. The server waits for
a connection until a client connects. The server then waits for a command from the client. The server then sends the result
to the client. The server then waits for the next command from the client and so on. The server closes the connection when
he receives the command to quit the connection. If the server receives a command that is not supported, he sends an
error message. If the connection is open since to long without any command, the server inform the client and then closes the connection.

## Messages
The messages are sent as text. The messages are separated by a new line. The messages are not encrypted.

### Initiation of the connection
- "init" : the client sends this message to the server to start the connection. The server sends the supported operations
- "info" : the server sends this message after the beginning of a connection.

### calculation
- "add a b" : the client sends this message to the server to add a and b. The server sends the result
- "mul a b" : the client sends this message to the server to multiply a and b. The server sends the result
- "result a" : the server sends this message to the client to send the result of the operation. a can be a number or an error message
  - "a" : the result of the operation
  - "error" : the operation is not supported

### End of the connection
- "quit" : the client or the server sends this message to the other to close the connection

## Example dialogs
