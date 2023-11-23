# Section 1: Overview #
**MYOptimizedCalculatorProtocol** is a client-server protocol.

The client connects to a server and sends a request for a mathematical calculation.\
The server sends the answer or an error message if the requested calculation is not well-formed
or if any other error occurred.

# Section 2: Transport layer protocol #
The client establishes the connection. The server listens on TCP port 1234.
The server closes the connection when the requested result or the error message has
been sent.

For now, MYOCP can only handle additions, substractions and multiplications.

# Section 3: Messages #
There are three types of messages in MYOCP:\
• 1 - **HEY**
The client wants to open an exchange. The server should answer "hey ( ◣‿◢')"\
• 2 - **CALC <calculation to perform>**
The client requests a calculation. <calculation to perform> can contain any two integers and
a keyword (ADD, SUB, MUL) to describe the operation to perform between these 2 numbers, in any order.
The integers must be separated by a blank space.\
• 3 - **INVALIDINPUT**
Error message if the input is not well-formed as described above.\
All of these messages are UTF-8 encoded with “\n” as end-of-line character.

If the request is valid, the answer is returned to the client using a byte stream.

# Section 4: example dialogs #
Client: HEY\
Server: Hey (◣‿◢)\
Client: CALC ADD 12 23\
Server: 35\
Client: CALC SUB 10 12\
Server: -2\
Client:  CLOWN\
Server: INVALIDINPUT\