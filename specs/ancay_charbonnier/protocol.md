# CRP protocol specification

## Overview
Computation Request Protocol is a client-server protocol used to perform computations on a different machine. To do so, the client connects to the server to send computation requests and receive the result in the response from the server.

## Transport Layer Protocol
CRP uses TCP. The client establishes the connection and has to know the IP adress of the server. The server listens for requests on port 1234.
The server closes the connection when the response has been sent to the client.

## Messages

This section contains all the different types of requests and responses that any version of CRP needs to implement.

### COMPUTE Request

```COMPUTE [operation] [op1] [op2] [opN]```

The COMPUTE message is used to request a computation. The nature of the computation is determined by the [operation] argument. [op1] and [op2] are the operands of the operation. There can be any number of operands but it has to match the specified operation's required number of operands.

The specification of the COMPUTE request is voluntarily very vague to allow for a lot of flexibility in the implementation. The developer is free to add as many operations as he wants but the following need to be implemented :
- ADD [op1] [op2]
- MPLY [op1] [op2]

If the requested operation is not in the list of supported operations, the server returns an ERROR message.

The COMPUTE request is very flexible on purpose to allow for easy implementation of new operations if needed.

#### Supported data types
The COMPUTE message operands have to be of integer type. However, the operands can be of arbitrary size. This means that CRP has to support arbitrary size integer computations.

### GETOPS Request (GET OPerationS)

 ```GETOPS```

The ```GETOPS``` request allows the client to get the list of supported operations and their requirements from the server.

### OPSLST Response (OPerationS LiST)

```OPSLST``` is the standard response to the GETOPS request. It contains the list of supported operations along with the number of operands expected for each. All possible operations are separated by spaces, followed by an integer representing the number of operands or ```-1``` if there can be any number of operands.
Here is the minimal OPSLST response : ```ADD 2 MPLY 2```

### ERROR [code] [message]

If the request is not understood or cannot be accomplished by the server, it will return an error code with a description of the error.
Here are the possible errors encountered by the server and their code :
- Error code 1 -> Unknown request. 
    - Returned if request sent to the CRP server is not recognized or not supported.
- Error code 2 -> Unknown operation.
    - Returned when the requested operation is not supported by the server.
- Error code 3 -> Invalid operation. 
    - Returned when the operands are not valid (text instead of numbers, for example). 
- Error code 4 -> Missing operand. 
    - Returned when the operation requires more operands than the server received. Example : ADD 1
- Error code 5 -> Too many operands. 
    - Returned when too many operands were specified in the request. Example : ADD 4 2 5 1 5 6 7 2 4 3
- Error code 6 -> Computation error. 
    - Returned when the requested operation was valid but there was an error during the computation (division by zero, for example).

 

