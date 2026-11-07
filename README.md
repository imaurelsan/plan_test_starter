# Mes tests

J'ai ajouté deux tests pour la réservation d'un livre.

Le premier test est dans `book-back/src/test/java/fr/visiplus/book/BookReservationIntegrationTest.java`.
C'est un test d'intégration. Il démarre le contexte Spring, utilise H2 et MockMvc,
puis appelle réellement la route `POST /book/reserve/{bookId}/{userId}`.
Je crée un utilisateur et un livre `AVAILABLE` dans le test. Je vérifie ensuite
que la réponse est 200 et que `bookDTO.status` vaut `BOOKED`.

Le deuxième test est dans
`book-front/src/app/components/book-reservation/book-reservation.spec.ts`.
C'est un test unitaire du composant Angular. Je remplace `BookService` par un mock
et je vérifie que `reserve(1)` appelle le service avec `(1, 1)`.
Le mock renvoie un livre avec le statut `BOOKED`.

Dans mes mots, le test d'intégration vérifie que plusieurs parties fonctionnent
ensemble, comme le contrôleur, le service et la base H2. Le test unitaire vérifie
une seule partie avec ses dépendances simulées. Il est donc plus rapide.

Pour lancer le test backend :

```powershell
cd book-back
.\mvnw test
```

Pour lancer le test frontend :

```powershell
cd book-front
npm install
npm test
```

Au début je ne savais pas quel champ JSON vérifier. J'ai trouvé le bon chemin
dans `ReservationDTO` : le livre est dans `bookDTO`, et son statut dans `status`.
J'ai aussi utilisé Jest, car c'est l'outil indiqué dans `package.json`.

J'ai appris à préparer des données avec les repositories au lieu d'écrire des IDs
en dur. J'ai aussi vu la différence entre tester un parcours Spring complet et
tester un composant Angular avec un mock.
