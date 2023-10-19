# Protocol Specification

## 1. Overview

This document specifies the protocol to be used for a simple client-server application over TCP. The server can perform computations such as addition and multiplication. The client reads user inputs from stdin, sends them to the server, and displays the result.

## 2. Transport Layer Protocol
The protocol is TCP, as specified in the instructions.
The port to communicate will be the 42069. This has been chosen because one of us was born on 20th April and the other's father was born in 1969.

The client will be in charge of starting and closing the communication.

## 3. Message Format

Messages exchanged between the client and server are in plain text and are terminated by a newline character ("\n"). Each message consists of an operation followed by one or more operands. Operands are separated by a space character (" ").

Client will send his request and server will answer back with the calculation done.

### Example

#### Client
```
ADD 10 20\n
```

#### Server
```
30\n
```

## 4. Supported Operations

The protocol supports the following operations:

### Addition

- Command: `ADD`
- Operands: Two or more integers.
- Description: Adds the operands.
- Example: `ADD 10 20 30\n`
- Response: The result of the addition, followed by a newline character. Example: `60\n`

### Multiplication

- Command: `MUL`
- Operands: Two or more integers.
- Description: Multiplies the operands.
- Example: `MUL 10 20 30\n`
- Response: The result of the multiplication, followed by a newline character. Example: `6000\n`

## 5. Error Handling

If the server receives an invalid command or operands, it should return an error message in the following format:

```
ERROR: <error description>\n
```

Example:

```
ERROR: Invalid command\n
```

## 6. Opening the Connection

The client can open the connection by sending the `HELLO` command.

```
HELLO\n
```

The server should respond with a confirmation message and open the connection.

```
OPENED\n
```

## 7. Closing the Connection

The client can close the connection by sending the `QUIT` command.

```
QUIT\n
```

The server should respond with a confirmation message and close the connection.

```
CLOSED\n
```

## 8. Extensibility
The protocol is designed to be extensible. New operations can be added by defining new commands and their associated behavior.
