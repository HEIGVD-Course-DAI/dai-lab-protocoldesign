# Spécification du Protocole de Communication Client-Serveur

## Objectif
Le protocole définit les règles de communication entre un client et un serveur pour effectuer des opérations mathématiques simples telles que l'addition et la multiplication.

## Fonctionnalités de Base
- Le client peut demander au serveur de réaliser une addition ou une multiplication.
- Le serveur répond avec le résultat de l'opération.

## Protocole

### Connexion
- Le serveur écoute sur un port TCP spécifié.
- Le client se connecte au serveur via ce port.

### Format des Messages
- Les messages sont envoyés sous forme de chaînes de caractères avec le format suivant : `OPERATION;NUM1;NUM2` (par exemple, `ADD;10;20` pour une addition ou `MULTIPLY;10;20` pour une multiplication).
- Le séparateur est le caractère `;`.

### Déroulement d'une Session
1. À la connexion, le serveur envoie un message de bienvenue indiquant les opérations prises en charge.
2. Le client envoie une demande d'opération.
3. Le serveur calcule le résultat et renvoie la réponse sous la forme d'une chaîne de caractères représentant le résultat.
4. Si une opération inconnue est demandée, le serveur répond avec un message d'erreur approprié.

### Fin de la Communication
- La session est terminée lorsque le client envoie un message `EXIT` ou déconnecte.

## Gestion des Erreurs
- Les erreurs, comme les entrées non numériques ou les opérations non valides, doivent être gérées en renvoyant un message d'erreur descriptif au client.


## Extensions Possibles (Optionnel)
- Implémentation d'autres opérations mathématiques (soustraction, division, etc.).
- Ajout d'un système d'authentification pour les clients.
- Prise en charge de plusieurs clients simultanément (multi-threading).
- Journalisation des demandes et des réponses.
