I. Overview

La spécification du protocole client-serveur décrit le fonctionnement de base
d'une calculatrice permettant à un client d'envoyer des opérations arithmétiques au serveur qui renvoie les résultats.

II. 

Le protocole utilisé est TCP.  
Le serveur écoutera sur l'adresse IP X.X.X.X, port 12345.  
Le client doit initier la connexion et le serveur la clôturera après chaque transaction.

III.

- Client Initie la Connexion:  
Syntaxe: CONNECT
Direction: Client vers Serveur  
Signification: Le client établit une connexion avec le serveur.
- Envoi d'une Opération:
Syntaxe: <OPERATION> <opérande1> <opérande2>  
Direction: Client vers Serveur  
Signification: Le client envoie une opération au serveur avec les opérandes appropriées.
- Réponse du Serveur:
Syntaxe: RESULT <résultat>  
Direction: Serveur vers Client  
Signification: Le serveur renvoie le résultat de l'opération au client.

IV.

- Gestion d'Erreur:  
En cas d'opération invalide, le serveur enverra ERROR <message d'erreur> au client.
- Extensibilité:  
Le protocole peut être étendu pour prendre en charge d'autres opérations ou fonctionnalités.

V.

- Exemple 1: Addition

Client: CONNECT   
Client: ADD 10 20  
Serveur: RESULT 30  

- Exemple 2: Erreur

Client: CONNECT   
Client: SUBTRACT abc 20  
Serveur: ERROR Operande 'abc' invalide  

- Exemple 3: Erreur

Client: CONNECT   
Client: RAC 123 20  
Serveur: ERROR Operator 'RAC' invalide  