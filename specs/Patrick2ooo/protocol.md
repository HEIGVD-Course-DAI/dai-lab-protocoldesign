# Labo DAI - Protocol Design

Auteur: Patrick2ooo

Date: 19.10.23

## Aperçu

Ce labo est un protocol client-serveur. le client va demander au serveur d'effectuer un certain calcul(pour l'instant addition et multiplication) et retourné le résultat

## Transport protocol utilisé

Je vais utilisée un protocol TCP/IP pour ce labo

## Concept du protocol

Afin que le protocol fonctionne le client aura besoin de connaitre l'IP et le port du serveur. Pour valider que la connection à été faites le serveur va envoyé au client les opérations de calcul disponible. le client n'aura plus qu'à envoyé le calcul souhaité et attendre la réponse du serveur. Ce sera le client qui définira s'il veut arrêter la connection.

## Message

Tout les message du client seront envoyé au format suivant `ADD N1 N2` . 
Les opération possible pour notre calculatrice seront :
- `ADD`
- `SUB`
- `MUL`
- `DIV`

## Gestion d'erreur

Si l'on a une quelconque erreur tel que mauvaise opération, pas assez de nombre, nombre invalide (char, string, etc...) ou opération illégale (div pasr 0) le serveur nous renverra juste un string d'erreur.

## Exemple

Serveur : Bonjour, voici les opération possible :

Serveur : - ADD

Serveur : - SUB

Serveur : - MUK

Serveur : - DIV

Client  : ADD 1 2

Serveur : 3

Client  : SUB a 3

Serveur : Invalid Operand

Client : ABCDEF

Serveur : Operation Impossible

Client : QUIT

