# CRP protocol specification

## Overview
CRP (Computation Request Protocol) is a client-server protocol used to perform computations on a distant server. To do so, the client connects to the server via TCP and sends computation requests that the server will process and respond to.

## Transport Layer Protocol
CRP uses TCP. The client establishes the connection and has to know the IP adress of the server. The server listens for requests on port 1234 and closes the connection when the response has been sent.

## Messages

This section contains all the different types of requests and responses that any version of CRP needs to implement.

### Request delimiter

Requests and response must end with a newline character (```\n```).

### ```CMPT``` Request

Syntax : ```CMPT [operation] [op1] [op2] [..] [opN]```

A CMPT request is sent by the client to the server to request a computation. The nature of the computation is determined by the [operation] argument. There can be any number of operands but this number has to match exactly the required number for the selected operation.

The following operations have to be supported in any implementation of CRP :
- Addition : ```ADD``` [op1] [op2]
- Multiplication : ```MPLY``` [op1] [op2]

As long as these two operations are supported, the developer is free to implement as many additional operations as he wants or needs.

In case the requested operation is not in the list of supported operations, the server returns an ```ERROR``` message with code ```2```.

### ```RSLT``` Message

Syntax : ```RSLT [result]```.

Contains the result to a specific ```CMPT``` operation.

#### Supported data types for the operands
CRP supports arbritrary size integer arithmetic. This means that the operands and the result of a computation do not have to fit in a 64 or 32 bit integer type, they can be of any size.

However, the operands have to be of integer type and CRP does not support (for now) arbritrary size floating point arithmetic.

### GETOPS Request (GET OPerationS)

Syntax : ```GETOPS```

The ```GETOPS``` request allows the client to get the list of operations that are supported by a specific server.

### OPSLST Response (OPerationS LiST)

```OPSLST``` is the standard response to the GETOPS request. It contains the list of supported operations along with the number of operands expected for each. 

Syntax : ```[operation1] [args1] [operation2] [args2] [...]```

All possible operations are separated by spaces, followed by an integer representing the number of operands or ```-1``` if there can be any number of operands.
Since addition and multiplication have to be supported by any implementation of CRP, the following is the minimal OPSLST response : ```ADD 2 MPLY 2```

### ERROR Response 

```ERROR [code] [message]```

If the server fails to understand or complete a request, it will return an ```ERROR``` response with an error code and a description.
- Error code 1 -> Unknown request. 
    - Returned if the request sent to the CRP server is not recognized.
- Error code 2 -> Unknown operation.
    - Returned when the requested ```CMPT``` operation is not supported by the server.
- Error code 3 -> Invalid operands format. 
    - Returned when the operands of a ```CMPT``` request could not be parsed by the server (letters instead of digit, for example). 
- Error code 4 -> Missing operand(s). 
    - Returned when a ```CMPT``` request contains less than the required number of operands.
- Error code 5 -> Too many operands. 
    - Returned when a ```CMPT``` request contains more than the required number of operands
- Error code 6 -> Computation error. 
    - Returned when an error occured during the completion of the requested computation.

 

