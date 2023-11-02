# Section 1: Overview #
**MYOptimizedCalculatorProtocol** is a client-server protocol.

The client connects to a server and sends a request for a mathematical calculation.\
The server sends the answer or an error message if the requested calculation is not well-formed
or if any other error occurred.

# Section 2: Transport layer protocol #
The client establishes the connection. It has to know the IP address of the server.
The server listens on TCP port 52222.
The server closes the connection when the requested result or the error message has
been sent.

For now, MYOCP can only handle additions, substractions and multiplications.

# Section 3: Messages #
There are three types of messages in MYOCP:\
• 1 - **CALC <calculation to perform>**
The client request a calculation. <calculation to perform> can contain any two integers and
a keyword to describe the operation to perform between these 2 numbers, in any order.
The integers must be separated by a blank space.\
• 2 - **TRASH INPUT <calculation to perform>**
Error message if the input is not well-formed as described above.\
• 3 - **WTF <calculation to perform>**
Error response message after a CALC message, if the file does not exist.\
All of these messages are UTF-8 encoded with “\n” as end-of-line character.

If the answer is valid, it is returned to the client using a byte stream.

# Section 4: example dialogs #
### SAMPLE (TODO FOR MYOCP) ### 
![Capture d'écran 2023-10-19 135536.png](\MaximeSpecDiagram.png)