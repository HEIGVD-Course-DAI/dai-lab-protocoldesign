# Protocol specification

## Goal

The goal is to create a protocol to allow a client to request mathematical computation from a server.

## Protocol

The protocol will be based on TCP and will use a textual representation for transmitted messages.

Message format will be specified at the end of this chapter. 

### Communication flow

* the client will establish a connection with the server.
* the server will then send a welcome message listing the supported operation
* the client will the send a message to request a computation 
  * if the computation is well-formed the server will respond with the response message
  * if the computation is mal formed, the servier will respond with an error message
* then the client can either 
  * send another computation request message
  * or close the connection

### Message format

* Message will be delimited by a `\n` character and each message part will be separated by '|'
* Message will start with a type, that can be:
  * WELCOME
  * COMPUTATION_REQUEST
  * COMPUTATION_RESPONSE
  * COMPUTATION_ERROR
  * EXIT  
* After the type, there will be the payload
  * for WELCOME: the list of supported operation separated by ' '
  * for COMPUTATION_REQUEST: the mathematical operation like `2*(4+5)`. In this payload, space will be ignored.
  * for COMPUTATION_RESPONSE: the operation response, like `18`
  * for COMPUTATION_ERROR: there won't be any payload

### Communication flow example
 
1. Client establish connection
2. Server send `WELCOME|+ - * /\n`
3. Client then send `COMPUTATION_REQUEST|2 *(4+5)\n`
4. Server respond `COMPUTATION_RESPONSE|18\n`
5. Client then send `COMPUTATION_REQUEST|2%3\n`
6. Server respond `COMPUTATION_ERROR\n`
7. Client then close the connection

### Special scenario

* if the client send multiple request before the server has the chance to respond to one, the server will give the response to each request in 
the same order it received request