# Overview
SuperCalculator is a client-server protocol. The client connects to the server and give the user input plus indicate the operation to do. The server sends the result of it or an error if it's impossible (not an operation, divide by 0, ...).
# Transport layer protocol
SuperCalculator uses TCP. The client establishes the connection. It has to know the IP address
of the server. The server listens on TCP port 4242.
The server closes the connection after 10 calculus are made or when an error happens.
# Messages
There are two types of messages:

There are three types of messages.

- ADD \<x\> \<y\> The client requests to do an addition : x+y. The value need to be convertible to double.
- MUL \<x\> \<y\> The client requests to do a multiplication : x*y. The value need to be convertible to double.
- INVALID \<errnum\> Error response message after a client request demand, the errnum parameter give a id of the error type following the list specified further.  
  Both messages are UTF 8 encoded with “\n” as end of line character.
  If the operation is valid, the server sends the result as a text.

NB: all parameter given by the client need to have a sign.
Example :
- ADD +2 +2
- MUL +3 -4

List of errors ids :
0) not specified : various unknowns errors
1) not an operation : the operation ask by the client is not valid. For example: "ADDITION 1 2", should be "ADD 1 2".
2) Illegal amount of parameter : Too least or too many parameters given.
3) not a number : At least one of the parameter who need to be convertible to double is not convertible.
4) Illegal move : division by 0, square root of a negative number, ...
5) Internal errors : various errors that happened on the server side, as an example : the result is too big to be stock in a double.

# Example dialogs

1) \(C-\>S\) Open TCP connection.
2) \(C-\>S\) MUL +2 -4
3) \(C\<-S\) -8
4) \(C-\>S\) ADD +7 -8
5) \(C\<-S\) -1
6) \(C-\>S\) MUL -1 -a
7) \(C\<-S\) INVALID 1
8) \(C\<-S\) Closes TCP connection