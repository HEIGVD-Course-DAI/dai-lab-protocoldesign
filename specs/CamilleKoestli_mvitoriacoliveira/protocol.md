# Protocol for Simple Calculator Application
## Section 1: Overview

    This protocol outlines the communication between a client and a server in a simple calculator application. The client sends mathematical operations to the server, which computes the result and sends it back to the client.

## Section 2: Transport Layer Protocol

    Connection Establishment: TCP
    Server Address and Port: Server's IP address is determined by the application owner. Port number: 1234
    Connection Initiation: Client initiates the connection, and the server accepts.

## Section 3: Messages

    Message Syntax:
        Client to Server: OPERATION OPERAND1 OPERAND2 or QUIT
        Server to Client: START OF CONNECTION, END OF CONNECTION, Welcome Message, ERROR: MESSAGE

    Message Flow:
        Client establishes a connection with the server.
        Server sends a welcome message with supported operations.
        Client sends a message containing a mathematical operation or QUIT.
        Server processes the message:
            If the message is a valid operation, compute the result and send it back to the client.
            If the message is QUIT, close the connection.
            If the message is invalid, send an error message.
        Steps 3-4 are repeated until the client decides to quit.

## Section 4: Specific Elements

    Error Handling:
        If the client sends an invalid message, the server responds with an error message (ERROR: MESSAGE).
        Example error messages: ERROR: BAD OPERANDS, ERROR: OPERATION NOT SUPPORTED

## Section 5: Example Dialogs

    Example 1: Successful Calculation
        Client: 2 ADD 3
        Server: RESULT 5

    Example 2: Invalid Operation
        Client: 5 SUBTRACT 2
        Server: ERROR: OPERATION NOT SUPPORTED

    Example 3: Invalid Operand
        Client: MULTIDE 10 ABC
        Server: ERROR: BAD OPERANDS

### Authors: Camille Koestli, Vitória Oliveira
