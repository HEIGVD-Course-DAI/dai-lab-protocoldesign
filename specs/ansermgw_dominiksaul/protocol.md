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
  * if the computation is mal formed, the server will respond with an error message
* then the client can either 
  * send another computation request message
  * or close the connection

### Message format

* Message will be delimited by a `\n` character, each message part will be separated by `|` and the used encoding will be UTF-8
* Message will start with a type, that can be:
  * WELCOME
  * CALCULATION
  * RESULT
  * ERROR
* After the type, there will be the payload
  * for WELCOME: the list of character for operation separated by ' '. `(` is considered to be an operation character
  * for CALCULATION: the mathematical operation like `2*(4+5)`. In this payload, space will be ignored.
  * for RESULT: the operation response, like `18`
  * for ERROR: the payload will be a string describing the error in a human-readable format, the protocol don't specify the error messages

### Communication flow example
 
1. Client establish connection
2. Server send `WELCOME|+ - * /\n`
3. Client then send `CALCULATION|2 *(4+5)\n`
4. Server respond `RESULT|18\n` 
5. Client then send `CALCULATION|2%3\n`
6. Server respond `ERROR|% Operatior is not supported\n`
7. Client then close the connection

### footnotes

* if the client send multiple request before the server has the chance to respond to one, the server will give the response to each request in 
the same order it received request
* the server is free to support more or less operation, the specification don't enforce any notation for an operation or a limited set of supported operation
* the server will use port 4242 