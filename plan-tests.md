# Plan de tests — Application de réservation de livres

## 1. Informations générales

### Objectif du plan de tests
Définir le cadre et l’organisation des tests afin de vérifier le bon fonctionnement de l’application de réservation de livres.

### Périmètre fonctionnel du projet
Application web permettant :
- la consultation des livres de la bibliothèque
- la consultation des livres réservés par un utilisateur
- la consultation des livres réservés non récupérés après 21 jours
- la réservation d’un livre
- l’inscription et la connexion des utilisateurs

---

## 2. Objectifs et stratégie de tests

Les tests ont pour objectifs de :
- vérifier le bon fonctionnement des fonctionnalités principales
- garantir la stabilité du backend et du frontend
- détecter les régressions lors des évolutions
- assurer un niveau de qualité minimum avant déploiement

Les différents types de tests de la pyramide des tests seront mis à profit.

Les tests sont automatisés via une pipeline GitHub Actions.

### Critères d’entrée
- Code compilable
- Fonctionnalités implémentées

### Critères de sortie
- Tous les tests automatisés sont passants
- Aucune anomalie critique ou bloquante détectée
---

## 3. Périmètre des tests

### Fonctionnalités testées
- Inscription d’un utilisateur
- Connexion d’un utilisateur
- Consultation de la liste des livres
- Réservation d’un livre
- Consultation des livres réservés par l’utilisateur
- Consultation des livres réservés depuis plus de 21 jours

### Fonctionnalités non testées
- Tests de performance et de charge
- Tests de sécurité avancés
- Compatibilité multi-navigateurs approfondie

---

## 4. Organisation des tests

### Méthodologie : 
- Behavior Driven Development
- Test Driven Development

### Tests unitaires backend
- Technologie : JUnit, Spring Boot Test
- Cible : services, contrôleurs, règles métier
- Objectif : vérifier la logique métier et les traitements internes

### Tests unitaires frontend
- Technologie : Jest
- Cible : composants et services Angular
- Objectif : vérifier le comportement des composants et la logique côté client

### Tests end-to-end (E2E)
- Technologie : Cypress
- Cible : parcours utilisateurs complets
- Objectif : vérifier le fonctionnement global de l’application du point de vue utilisateur

### Automatisation 
Les tests sont automatisés via une pipeline GitHub Actions :
- exécution des tests unitaires backend
- exécution des tests unitaires frontend
- exécution des tests end-to-end
- arrêt de la pipeline en cas d’échec d’un test

---

## 5. Environnements de test

- Environnement local de développement
- Environnement d’intégration utilisé par la pipeline GitHub Actions
- Base de données de test (ex : H2 ou configuration équivalente)

---

## 6. Parties prenantes et Planning

### Parties prenantes
- Développeurs :
  - écriture des tests unitaires backend et frontend
  - définition et maintenance des tests E2E
- Equipe métier : 
  - Contrôle via tests manuels

### Planification
- Durant le cycle de développement, tests unitaires en amont (cf TDD).
- A la fin de l'implémentation d'une fonctionnalité dans un composant : test d'intégration
- A la fin de l'implémentation d'une fonctionnalité full stack  : test e2e

---

## 7. Livrables de test

- Résultats des tests unitaires backend : rapport JUnit
- Résultats des tests unitaires frontend : rapport Jest
- Résultats des tests end-to-end : rapport Cypress
- Statut et logs de la pipeline GitHub Actions

---