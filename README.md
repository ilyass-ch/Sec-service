# Sec-service
# Projet d'Authentification JWT avec Spring Boot

Ce projet implémente un système robuste d'authentification et d'autorisation utilisant JSON Web Tokens (JWT). Il est conçu pour démontrer l'utilisation sécurisée des tokens d'accès et de rafraîchissement pour gérer les sessions utilisateurs dans une application web moderne.

## Caractéristiques

- Authentification et autorisation par JWT.
- Gestion des rôles et des permissions utilisateur.
- Sécurisation des endpoints API avec Spring Security.
- Utilisation de deux méthodes de protection des ressources :
- Configuration déclarative dans `SecurityConfig`.
- Annotations de sécurité pour une protection granulaire des contrôleurs.

## Technologies Utilisées

- **Spring Boot** : pour l'orchestration des services.
- **Spring Security** : pour la sécurisation des endpoints.
- **H2 Database** : base de données en mémoire pour le stockage des utilisateurs et des rôles.
- **Java JWT (Auth0)** : pour la création et la vérification des tokens JWT.

## Installation

1. **Cloner le projet**
   ```bash
   git clone https://github.com/ilyass-ch/Sec-service.git
