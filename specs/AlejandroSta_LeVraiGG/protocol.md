# Overview

SuperCalculator is a simple client-server protocol allowing users to compute simple calculus.
The client connects to a server and asks for a calculation to be made, if the parameters are valid, the server returns
the value, otherwise an error message.

# Transport layer protocol

SuperCalculator uses TCP. The client establishes the connection. It has to know the IP address
of the server. The server listens on TCP port 4242.  

The server closes the connection after 10 calculus are made or when a wrong parameter is given.

# Messages

There are three types of messages.

- ADD \<value1\> \<value2\>
- MUL \<value1\> \<value2\>
- INVALID \<error_num\>

All messages are UTF-8 encoded8 encoded with “\n” as end of line character.  

If the arithmetic expression is valid, the server returns its returns as text.

# Example dialogs

1) \(C-\>S\) Open TCP connection.
2) \(C-\>S\) MUL +2 -4
3) \(C\<-S\) -8
4) \(C-\>S\) ADD +7 -8
5) \(C\<-S\) -1
6) \(C-\>S\) MUL -1 -a
7) \(C\<-S\) INVALID 1
8) \(C\<-S\) Closes TCP connection