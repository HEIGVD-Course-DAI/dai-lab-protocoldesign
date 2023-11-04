## Specification – Protocol

### Section 1 : Overview

MOP (Math Operation protocol) is a client-server protocol. 
The client connects to a server and waits for the user to enter his calculation containing his operation followed by two digits, then sends it to the servers.

The operations possible will be “ADD”, “SUB”, “MULT”, “DIV”, "POW" :
-	ADD -> Addition
-	SUB -> Substraction
-	MULT -> Multiplication
-	DIV -> Division
-	POW -> Power

The server sends the result back or an error message, if the operation is not covered.
Then, the clients displays the result.

### Section 2 : Transport layer protocol

MOP uses TCP. The client establishes the connection. 
It has to know the IP address of the server. 
The server listens on TCP port 42069.

The server closes the connection when the result has been sent.

### Section 3 : Messages

There are these types of messages :
-	WELCOME
     The client opens the communication and the server sends a welcome message with the possible operations.
-	CALCULATION <nb1 OPERATION nb2>
     The clients sends the operation. The operation must be in uppercase.
-	RESULT <Number>
     The server sends back the result of the calculation, then the client displays it.
-   END
     The client ask to close the connection.
-   CANNOT_DIVIDE_BY_0
     Error response message after a division by 0.
-	OPERATION_NOT_VALID <operation>
     Error response message after a CALCULATION message, if the operation is not covered.
-   NUMBER_NOT_VALID <nb1, XXXX>
     Error response message after a CALCULATION message, if one of the values is not a number.

If the operation exists, the server sends the result back.

### Section 4 : example dialogs

**Successful CALCULATION :**
1. Client opens TCP connection.
2. Server accepts and sends a WELCOME message with the possible operations -> WELCOME 12+12| <OP>. 
3. Client provides an operation followed by two numbers -> CALCULATION|4*2
4. Server responds the result --> RESULT|8
5. Server wait a new calculation or closes the TCP connection if it receives an END message

**OPERATION_NOT_VALID :**
1. Client opens TCP connection.
2. Server accepts and sends a WELCOME message with the possible operations -> WELCOME 12+12| <OP>. 
3. Client provides an operation followed by two numbers -> CALCULATION|21%2
4. Server responds that the operation doesn’t exist. --> ERROR|OPERATION_NOT_VALID
5. Server waits a new calculation.

**NUMBER_NOT_VALID :**
1. Client opens TCP connection.
2. Server accepts and sends a WELCOME message with the possible operations -> WELCOME 12+12| <OP>. 
3. Client provides an operation followed by two numbers -> CALCULATION|12.2+2
4. Server responds that the values are incorrect. --> ERROR|NUMBER_NOT_VALID
5. Server waits a new calculation.



