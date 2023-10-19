# 1. overview:
ctp (calcule transfert protocol). The client send a file with a request like "ADD 10 20 " if a want addtion 10 + 20 
and the server send back the result in a file.

# 2. transport layer protocol:

ctp uses TCP. The client establishes the connection. It has to know the IP address
of the server. The server listens on TCP port 55555.
The server closes the connection when the requested file or the error message has
been sent. 

# 3. messages :
get<filename> 

UNKNOWN <filename>
Error response message after a GET message, if the file does not exist.
Both messages are UTF-8 encoded with “\n” as end-of-line character.
If the file exists, the server sends the file content as binary byte stream.

# 4. example dialogs :

