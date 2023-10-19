# ASDCP - Ansermoz Saul Distributed Calculation Protocol

1. HELLO
Client sends 'HELLO' to the server

2. OPERATION string
The server responds to HELLO Requests with a string of allowed operators
Example: OPERATION "+_*/%.()"
Server allows Plus, Minus, Multiply, Division, Modulo, Float and () operations

3. CALC string
The client reads the calculation from the user and sends it in the format string to the server
The client has to send the calculation, without blank spaces

4. RESULT string - server returns result if calculation was successful
4. ERROR string - server returns error msg if calculation failed
As soon as the server receives a calculation String he does the calculations and returns the result or error
