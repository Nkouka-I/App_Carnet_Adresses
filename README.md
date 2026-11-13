# AtoZ Carnet

Application de gestion de carnet d'adresses en Java (Swing), développée pour la **SAÉ 3.1** du BUT Informatique (IUT de Fontainebleau, 2026-2027).

**Auteurs :** Ildevert Nkouka (vue), Tiago Alves--Le Du (modèle et contrôleur)

## Fonctionnalités

- **Contacts** : nom, adresse mail et étiquettes libres ; création, modification, suppression
- **Étiquettes** : création, renommage, suppression, application à plusieurs contacts à la fois
- **Listes de diffusion** : groupes définis par des critères sur les étiquettes (« doit avoir » ou « ne doit pas avoir »), mis à jour automatiquement
- **Recherche** en direct (nom, adresse, étiquettes, sans tenir compte de la casse ni des accents), **tri** et **filtre par étiquette**
- **Copie** dans le presse-papiers de l'adresse d'un contact ou de toutes celles d'une liste
- Données conservées dans une base **MariaDB** ; les erreurs d'accès à la base sont affichées à l'utilisateur

## Architecture (MVC)

Package `fr.iutfbleau.sae31.AtoZ`, divisé en trois :

| Package | Rôle |
|---|---|
| `vue` | Interface Swing (fenêtre, panneaux gauche / central / droit, formulaires) et style (`Style`, `StyleBouton`, `StyleComposant`, `ZoneDefilante`) |
| `controleur` | `Controleur` : point d'entrée unique de la vue ; interfaces `ContactVue`, `EtiquetteVue`, `ListeDiffusionVue`, `CritereVue` |
| `model` | Classes de données, DAO (une par table), `Carnet`, `Connexion` |

La vue n'utilise jamais les classes du modèle : elle ne manipule que les interfaces fournies par le contrôleur.

## Prérequis

- Java (JDK) avec `javac` et `jar`
- `make`
- Le pilote JDBC MariaDB dans le dossier `lib/`
- Un accès au serveur MariaDB de l'IUT (adresse et identifiants dans `model/Connexion.java`)

## Compilation et lancement

```bash
make            # compile et produit atoz.jar
make run        # lance l'application
make clean      # supprime bin/ et atoz.jar
make mrproper   # nettoie puis reconstruit
```

L'application se lance avec `make run`, qui ajoute le pilote de `lib/` au classpath.

## Base de données

Cinq tables : `Contact`, `Etiquette`, `Contact_etiquette` (liaison plusieurs-à-plusieurs), `ListeDiffusion` et `Critere`. Les clés étrangères utilisent `ON DELETE CASCADE`.

## Documentation

La Javadoc se génère avec `javadoc -d doc -private -sourcepath src -subpackages fr.iutfbleau.sae31.AtoZ`. Le compte rendu du projet est fourni à part (PDF).
