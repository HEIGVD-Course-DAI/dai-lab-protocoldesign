# Client-Server Application Protocol Specification

## Protocol Objectives

The protocol aims to facilitate communication between a client and a server for simple arithmetic operations (addition and multiplication). The client can send an operation to the server, and the server will perform the computation and return the result.

## Overall Behavior

- **Transport Protocol**: TCP (Transmission Control Protocol) will be used for reliable data transmission.

- **Finding the Server**:
    - The client needs to be provided with the server's IP address and port number.

- **Connection Initiation**:
    - The client initiates the connection to the server.
    - The server responds with a welcome message indicating supported operations.

- **Connection Closure**:
    - The client can initiate the connection closure once the communication is complete.

## Messages

- **Syntax**:
    - Messages will be formatted as follows: `OPERATION ARG1 ARG2`, e.g., `ADD 10 20`.

- **Message Exchange Sequence (Flow)**:
    1. Client establishes a connection with the server.
    2. Server sends a welcome message indicating supported operations.
    3. Client sends the operation message.
    4. Server processes the message and responds with the result.
    5. Client displays the result.

- **Message Semantics**:
  
  _Upon receiving a message:_
  - Server parses the message, performs the specified operation, and sends the result to the client.
  - Client displays the result or handles errors if any.

  _Multiline messages:_
  - The server can send multiline messages to the client.
  - To mark the end of a multiline message, the server message will end with an empty line (`""`).

## Specific Elements

- **Supported Operations**:
    - Addition (`ADD`)
    - Subtraction (`SUBTRACT`)
    - Multiplication (`MULTIPLY`)
    - Division (`DIVIDE`)

- **Error Handling**:
    - If the message format is incorrect, the server will respond with an error message, and the client will display the error.

- **Extensibility**:
    - The protocol is designed to allow the addition of more operations in the future. Clients and servers should be able to gracefully handle unknown or unsupported operations.

## Examples

- **Dialog Example 1**:
    - Client sends: `ADD 10 20`
    - Server responds: `RESULT 30`

- **Dialog Example 2**:
    - Client sends: `MULTIPLY 5 8`
    - Server responds: `RESULT 40`

- **Dialog Example 3 (Unknown command)**:
    - Client sends: `INVALID_FORMAT`
    - Server responds: `Unknown command: INVALID_FORMAT`

- **Dialog Example 4 (Missing operands)**:
  - Client sends: `ADD`
  - Server responds: `No operand found. Usage: ADD <lhs> <rhs>`