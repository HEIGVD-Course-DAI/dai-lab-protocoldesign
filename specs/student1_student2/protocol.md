Section 1 : Overview

    Alcp is a client-server protocol. The client connect to a server and can request different sort of calculation.
    The server send a response or a error message if the resquest is invalide(invalide input of the user)

Section 2 : transport layer protocol
    
    Alcp uses TCP, The client establishes the connection. It has to know the IP address of the server
    The server listens on TCP port 42020.
    

Section 3 : Messages 

    Client side operation :

        ADD <operand 1> <operand 2> - addition 
        SUB <operand 1> <operand 2> - substraction
        DIV <operand 1> <operand 2> - division
        MUL <operand 1> <operand 2> - multiplication
        CLOSE                       - close conenction

    Server side answer :
        
        ANSWER : <Answer> - the server response is a digit 
        ERROR OPERAND - if the operand is not in the correct format
        ERROR SYNTAX - if the command is wrong or was not found

    Both messages are UTF-8 encoded with "\n" as end-of-line character.
    The server send a welcome message when a connection is established.
