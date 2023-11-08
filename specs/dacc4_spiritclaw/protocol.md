# Specification of the protocol

## 1. Overview
This protocol is a client-server protocol. The client connects to the server and asks the result of a mathematical operation. The server computes the result and sends it back to the client or sends an error message if the operation is not valid.

## 2. Transport layer protocol
This protocol uses TCP as transport layer protocol. It has to known the IP address of the server. The server listens on the port `4242`. 

The server receives the data sent by the client and sends the result or the error message.

Data is sent as a UTF-8 string. The string is composed of the operation and the operands separated by a space. The server sends the result or the error message as a string.

The operands are integers.

Each line is terminated by a `;` character.

## 3. Messages
Here are the messages exchanged between the client and the server :

### Client sent :
- `ADD a b` : the server computes `a + b` and sends the result to the client
- `SUB a b` : the server computes `a - b` and sends the result to the client
- `MUL a b` : the server computes `a * b` and sends the result to the client
- `DIV a b` : the server computes `a / b` and sends the result to the client
- `MOD a b` : the server computes `a % b` and sends the result to the client
- `POW a b` : the server computes `a ^ b` and sends the result to the client

### Server sent :
- `RSLT a` : the server sends the result of an operation to the client
- `ERROR code [txt]` : the server sends an error message to the client. The error code is a number and the error text is a string. The error text is optional and is used to give more information about the error.

## 4. Specific elements
Error can be one of the following :
- INVOP : invalid operation
- INVNB : invalid number of arguments
- DIV0 : division by zero
- INVARG : invalid argument
- UNKERR : unknown error

## 5. Example dialogs
### Classic dialog :
```mermaid
sequenceDiagram
    participant Client
    participant Server
    Client->>Server: ADD 5 10;
    Server-->>Client: RSLT 15;
```

```mermaid
sequenceDiagram
    participant Client
    participant Server
    Client->>Server: MUL 5 10;
    Server-->>Client: RSLT 50;
```

### Error dialog :
```mermaid
sequenceDiagram
    participant Client
    participant Server
    Client->>Server: ADD 5;
    Server-->>Client: ERR INVNB Invalid number of arguments;
```