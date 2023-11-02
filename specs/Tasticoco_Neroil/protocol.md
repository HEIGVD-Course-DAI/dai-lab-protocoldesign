# Specifics : 

## Overview : 

***CalculazorDX*** is a simple way to do calculation on a server with request
from a client. The client connects to a server and requests the server to do a calculation.
The server sends the result of the operation or an error message if the specified operations do not exist.


## Transport layer protocol : 

***CalculazorDX*** use TCP. The client establishes the connection. It has to know the IP address of the server. 
The server listens on TCP port 23997.

The server closes the connection when the result has been sent or the error message has been sent.

## Messages

- **ADD \<values\>**
- **SUB \<values\>**
- **MULT \<values\>**
- **DIV \<values\>**
<br> The client sends two values separated by a space
- **BADOP** 
<br> Error response if second value of div is 0 or if the operands aren't numbers.

The server sends the result as a binary stream. 

