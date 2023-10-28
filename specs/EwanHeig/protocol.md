Author: Bloechle Ewan

## Overview
We're making a calculation with a client-server application over TCP.
The client establish a connection, reads the user inputs, send them to the server and diplay
the result of the calculation. The server keep the connection open until the client send him
a command to close it and he perform the operations.

## Transport layers protocol
The protocol is TCP
The client will be in charge to open the connection, the server wait for a client
then wait for the client to send him a command, he proceed it and return it then wait for another
command until the client send him a command to close the connection

## Message
The client send a mathematical operations to calculate with the format: Operation leftValue rightValue
Example: ADD 10 8

The server can do the following operation: ADD, SUB, MUlT, DIV.

If the serve can calculate it, he return the result of the operations, else he
return "ERROR: Calculation impossible"

The command QUIT stop the connection

## example of dialogue
Server: Welcome! Here are the available operations: ADD, SUB,  MULT, DIV

Client: ADD 4 10

Server: 14

Client: DIV 8 0

Server: ERROR: Calculation impossible

Client: SUB 1 6

Server: -5

Client: MULT 4 asfrt

Server: ERROR: Calculation impossible

Client: QUIT

Connection is now closed.

