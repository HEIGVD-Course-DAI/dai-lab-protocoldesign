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

The specification of the COMPUTE request is voluntarily very vague to allow for a lot of flexibility in the implementation. The developer is free to add as many operations as he wants but the following 2 need to be implemented :
- ADD [op1] [op2]
- MULTIPLY [op1] [op2]

If the requested operation is not in the list of supported operations, the server returns an ERROR message.

The COMPUTE request is very flexible on purpose to allow for easy implementation of new operations if needed.

#### Supported data types
todo


#### GETOPS Request (GET OPerationS)

 ```GETOPS```

The ```GETOPS``` request allows the client to get the list of supported operations by the server.


### OPSLST Response (OPerationS LiST)

```OPSLST```

The ```OPSLST``` response contains the list of supported operations. All possible operations are separated by spaces. Example : ADD SUB MULTIPLY DIV ...

### ERROR [code] [message]

If the computation request cannot be accomplished by the server, it will return an error code with a description of the error.
Here are the possible errors encountered by the server and their code :
- Error code 1 -> Unknown operation. This code is returned when the requested operation is not supported by the server.
- Error code 2 -> Invalid operation. This code is returned when the operands are not valid (not numbers, for example). 
- Error code 3 -> Missing operand. This code is returned when the operation requires more operands than the server received. Example : ADD 1
- Error code 4 -> Too many operands. This code is returned when too many operands were specified in the request. Example : ADD 1 2 3
- Error code 5 -> Computation error. This code is returned when the requested operation is not computable. This error code will be returned in case of a division by 0 or if the result is too big.

 

