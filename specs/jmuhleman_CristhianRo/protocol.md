# Section 1: Overview

The purpose of this protocol is to process simple computations sent from a client on the server. The following operation are supported on double types.
    * Multiplication (*)
    * Division (/)
    * Addition (+)
    * Substraction(-)
    * Modulus (%)
    * Powe(pow<base>-<exposent>)


The synthax will follow the pattern: <operand1> <operation> <operaand2>
Spaces will be needed between the operands and operations.



# Section 2: Transport layer

* The server listen on the port 1234 and waits for a connexion. The client initiates the connexion.

* The server sends a welcoming message showing the supported operations.

* After the response from the server the connexion remains alive. The server waits till the next request from the client.

* Finally the client sends the by request to close connexion.

# Section 3: Messages and error handling

All the messages will be encoded with UTF-8 and \n at the end
The follings prefixes will be used for transmitting messages for calculations:
    * COMPUTE:<operand1><operation><operand2>
        To send regular computations to the server.

The following error messages will be used to handle errors:
    * ERROR:"div0" 
        for an expression containing /0

    * ERROR:"invalid expression"
        for an invalid expression such as "+/3

The following message will be sent to close the connexion:
    * BYE
        Since the server always waits new requests from the client it only closes the connexion when it receives such a message.
        
In case of a timeout (120 sec) the server will close the connection.

# Section 4: Examples





