#Summary: protocol specification
1. Overview purpose and general behavior of the application
   The application is a calculator. The client sends a request to the server with an operation and two operands. The server computes the result and sends it back to the client.
2. Transport layer protocol TCP or UDP, IP address, port who creates and who closes the connection
   TCP, localhost, 55555?, the server creates and closes the connection(TCP)

3. Messages who speaks first, syntax of all messages, direction for each message, meaning of each message, required sequence of messages
    The client speaks first, the client sends a request to the server with an operation and two operands. The server computes the result and sends it back to the client.
    
4. Specific elements (if needed) e.g., error handling, extensibility
    The server sends an error message if the operation is not supported (send numbers incorrect, formatting, ...). The client sends an error message if the operands are not numbers.

   5. Example dialogs examples of one or several dialogs to clarify the scenarios
      Dialog example:
       Client: ADD 10 20
       Server: 10 + 20 = 30 
      Error dialog:
         Client: ADDS 10 20
         Server: Error: ADDS is not a supported operation
   