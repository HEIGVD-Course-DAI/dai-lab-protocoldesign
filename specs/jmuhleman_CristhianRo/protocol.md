# Section 1: Overview

The purpose of this protocol is to process simple computations sent from a client on the server. The following operation are supported:
    * Multiplication (*)
    * Division (/)
    * Addition (+)
    * Substraction(-)
    * Modulus (%)

The synthax will follow the pattern: <operand1> <operation> <operaand2>
Spaces will be needed between the operands and operations.



# Section 2: Transport layer

The server listen on the port 1234 and waits for a connexion. The client initiates the connexion.
After the response from the server it does not close the connexion. It waits till the next request from the client.


# Section 3: Messages and error handling

All the messages will be encoded with UTF-8 and \n at the 
The follings prefixes will be used for transmitting messages for calculations:
    * COMPUTE:<operand1><operation><operand2>
        To send regular computations to the server.

The following error messages will be used to handle errors:
    * ERROR:"div0" 
        for an expression containing /0

    * ERROR:"invalid expression"
        for an invalid expression such as "+/3

The following message will be sent to close the connexion:
    * ENDOF
        Since the server always waits new requests from the client it only closes the connexion when it receives such a message.


# Section 4: Examples





