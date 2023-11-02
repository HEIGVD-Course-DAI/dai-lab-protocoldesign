## Section 1: Overview

**Purpose and General Behavior**

mop _(MathOpsProtocol)_ is a client-server protocol. It allows clients to send mathematical operations to the server and
receive the computed result. The server performs the requested calculations and sends back the result to the client or
an error message if the operation is not correct.

## Section 2: Transport Layer Protocol

**Protocol Type**

mop uses TCP for reliable and connection-oriented communication.

**Connection Establishment**

The client is responsible for establishing the connection to the server.
The client must know the IP address of the server to initiate the connection.
The server listens for incoming connections on TCP port 54321.
The server is responsible for closing the connection after sending the response to the client.

## Section 3: Messages
**Message Types**

There are two types of messages in the client-server protocol:

`<operation> <operand1> <operand2>`

The client sends this message to the server to request a computation.
<operation> can be "ADD", "MULTIPLY" or other operations that the server supports.
<operand1> and <operand2> are numerical values on which the operation should be performed.

`<result>`

The server responds with this message after processing a client's request.
<result> is the numerical result of the requested operation or an error message.

**Message Encoding**

All messages are encoded using UTF-8.
The newline character ('\n') is used as an end-of-line character.

## Section 4: Example Dialogs
**Example Dialog 1 - Addition Operation:**

Client: ADD 10 20

Server: 30

**Example Dialog 2 - Multiplication Operation:**

Client: MULTIPLY 5 6

Server: 30

**Example Dialog 3 - Error Handling:**

Client: ADD x y

Server: Error: Invalid operands