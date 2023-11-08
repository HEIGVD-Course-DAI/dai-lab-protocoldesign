# overview

Calcu-late is a client-server protocol, it's base on a json described later

# transport layer protocol

- Protocol : TCP
- The server accepts new clients with the port 6666
- The client use a random port.

# Message

The client initialise the connection to the server.
The server send a list of know operators with the operands number to the client.
The sever send a list of know symbols to the client.

All message are send with a json.
Clients:

```JSON
{
  "Operator": "add",
  "Operands": [
    10,
    15
  ]
}
```

Operator can be :
- add
- mul

Operands can be :
- any number


Server:

```JSON
{
  "UUID": "2516df4a-9228-4edf-8c1b-b94005c8ae16",
  "Result": 120,
  "Success": true,
  "Error_message": "Operator not valid."
}
```

Operator :

```JSON
[
  {
    "Operator": "add",
    "Operands": -1
  },
  {
    "Operator": "mul",
    "Operands": 2
  }
]
```

## Error_message

List of possible error message :

- Invalid JSON
