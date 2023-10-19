# Section 1: Overview #
### SAMPLE ### 
simfp is a client-server protocol. The client connects to a server and requests a file.
The server sends the file or an error message, if the file does not exit

# Section 2: Transport layer protocol #
### SAMPLE ### 
simfp uses TCP. The client establishes the connection. It has to know the IP address
of the server. The server listens on TCP port 55555.
The server closes the connection when the requested file or the error message has
been sent.

# Section 3: Messages #
### SAMPLE ### 
There are two types of messages:
• GET <filename>
The client requests a file. The filename can contain an absolute Unix path sur as:
“/docs/public/spec.txt”.
• UNKNOWN <filename>
Error response message after a GET message, if the file does not exist.
Both messages are UTF-8 encoded with “\n” as end-of-line character.
If the file exists, the server sends the file content as binary byte stream.

# Section 4: example dialogs #
### SAMPLE ### 
![Capture d'écran 2023-10-19 135536.png](\MaximeSpecDiagram.png)