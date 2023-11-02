## Intro
We want to implement a simple protocol to make a numeric operation:
1. The client requests a calculation on two operators (double).
2. The server sends the answer and close the connection.

## Overview
OPON (operations on the network) is a client-server protocol. The client connects to a server and requests a calculation (addition or multiplication) on two operators of type double. The server sends the result with a status code (or an empty result, if the status code corresponds to an exception) and then close the connection.

## Transport layer protocol
OPON uses TCP. The client establishes the connection. It has to know the IP address of the server. The server listens on TCP port 1234.
The server closes the connection when the answer message (result + status code) has been sent.

## Messages
The client message:
1. For addition: ADD <num1> <num2>
1. For multiplication: MUL <num1> <num2>
The client requests the calculation. 
The server send the answer as a string that contains a status code (0 or other code for errors) and an optional result (in case the operation can be made).
1. Response message: 0 5.5
2. Error response message: 1

## Specific elements
Status code for sucess: 0.
Error response message if:
1. Unknown operation -> 1
2. Numbers in wrong format (for example, letters instead of digits) -> 2
3. Wrong number of arguments (for example, 5 arguments given instead of 3) -> 3
If one or both numbers sent by the client are not specified, 0 will be taken as the missing number/s.
Messages are UTF-8 encoded, with “\n” as end-of-line character.

## Exemple dialogs
