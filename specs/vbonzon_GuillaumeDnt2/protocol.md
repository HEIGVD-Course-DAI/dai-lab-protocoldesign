## Description
Ceci est un protocol client-serveur qui permet au client d'envoyer une opération mathématique au serveur et à ce dernier d'envoyer le résultat

## Transport 
Le protocol fonctionne sur TCP. Le serveur écoute sur le port 31415 et le client doit connaître l'adresse IP du serveur.
Le client ouvre la connection et le serveur ferme la connection après avoir reçu le message CLOSE du client.

## Message
Le client envoie une opération sur plusieurs arguments au serveur puis attend une réponse.
Quand le serveur reçoit le message, il vérifie la syntaxe de l'opération et la validité des arguments.
Le serveur renvoit le résultat ou un message d'erreur puis ferme la connection.

### Messages client :
- ADD <args>  : le serveur ajoute les entiers séparés par un espace et renvoit le résultat
- MULT <args> : le serveur multiplie les entiers séparés par un espace et renvoit le résultat
- SUB <args>  : le serveur soustrait les arguments dans leur ordre d'apparition et renvoit le résultat
- DIV <args>  : le serveur divise les arguments dans leur ordre d'apparition et renvoit le résultat 
- POW <a> <b> : le serveur met l'argument a à la puissance b et renvoit le résultat
- MEAN <args> : le serveur renvoit la moyenne de tous les arguments
- CLOSE       : le serveur ferme la connexion proprement et envoit un message de confirmation au client

### Messages serveur :
- RESULT <x> : résultat d'une opération demandée par le client
- ERROR <e>  : le serveur envoie un message d'erreur avec le type d'erreur spécifique : Bad operation, Bad value, Missing argument
- Closing connection : Notifie le client que le serveur va fermer la connexion

### BONUS:
- Gestion des nombres réels

## Exemples
Client : *ouvre la connection
Client : SUB 64 32 16 
Serveur : RESULT 16
Client : CLOSE
Serveur : Closing connection...
Serveur : *ferme la connection

Client : *ouvre la connection
Client : SUB 64 32 16 
Serveur : RESULT 16
CLient : ADD 4 12 3
Serveur : RESULT 29
Client : CLOSE
Serveur : Closing connection...
Serveur : *ferme la connection

Client : *ouvre la connection
Client : POW 5
Serveur : ERROR Missing argument
...suite des commandes

Client : *ouvre la connection
Client : ADD 8 abc
Serveur : ERROR Bad value
...suite des commandes

Client : *ouvre la connection
Client : MULTIPLY 3 7 
Serveur : ERROR Bad operation
...suites des commandes
