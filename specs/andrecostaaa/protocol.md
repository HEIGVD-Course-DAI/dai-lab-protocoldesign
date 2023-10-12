# Connection

- Server address will depend on machine since we won't deploy it to a static ip address server

- Client connects to server on port 64000

# Message protocol

A message is a list of tokens. Each token is separated by a whitespace ' ' and a '\n' indicates the end of the message.

# Multiple line messages

In case a message should take multiple lines a pipe '|' will indicate the new line character

### Multiple line messages are only allowed by the server

# Commands

The following commands are allowed:

| command |           meaning           |     example      |          result           |
| :-----: | :-------------------------: | :--------------: | :-----------------------: |
|   add   |    Add a list of numbers    | `add 10 20 30\n` |         `OK 60\n`         |
|   mul   | Multiply a list of numbers  | `mul 10 20 30\n` |        `OK 6000\n`        |
|   sub   | Substract a list of numbers | `sub 10 20 30\n` |        `OK -40\n`         |
|   div   |  Divide a list of numbers   |  `div 10 20\n`   |        `OK 0.5\n`         |
|   pow   | Elevate a number to another |   `pow 2 5\n`    |         `OK 32\n`         |
|   inv   |      inverses a number      |    `inv 10\n`    |        `OK 0.1\n`         |
|  help   |  List of allowed commands   |     `help\n`     | `OK <List of commands>\n` |
|  quit   |      Close connection       |     `quit\n`     |          `OK\n`           |

# Connecting / Disconnecting

After connecting, the server sends a list of commands

When disconnecting, the client sends a message informing the server that he would like to close the connection. The server will the close the connection.
