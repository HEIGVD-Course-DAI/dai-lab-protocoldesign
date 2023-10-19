# Specification for sctp

## Overview
Simple Calculation Transfer Protocol (SCTP) is a text-based client-server protocol. 
The client connects to a server and provides one mathematical operation to be calculated.
The server sends the result of the operation or an error message if the operation doesn't make sense (i.e. a division by zero)

## Transport layer protocol
SCTP uses TCP. The client establishes the connection. It has to know the IP address of the server.
The server listens on port 52273.

The server closes the connection when the result of the operation or the error message has been sent.

## Messages

The client sends a list of mathematical operations to be calculated.
Here's a list of the operations that the server can do: ADD, SUB, MLT, DIV and MOD.
The server can perform computations such as "ADD 10 20".

If the server cannot calculate the result because of an error, this message is returned:
ERROR `operation` cannot be calculated!

Both messages are UTF-8 encoded with "\n" as end of line character.
The client reads user inputs from stdin, sends them to the server and displays the result.

## Example dialogs
```text
Successful calculation:
Client                  Server
    Open TCP connection    →
    ADD 10 20              →
    ←    result in text form
    ←    Close TCP connection
```

```text
Error with calculation:
Client                  Server
    Open TCP connection    →
    DIV 10 0               →
    ←    ERROR DIV 10 0 cannot be calculated!
    ←    Close TCP connection
```
