# Module d'alertes emails pour ORASS

Ce module est une application Spring Boot qui se connecte à la base de données Oracle d'ORASS pour envoyer des alertes par email aux gestionnaires de sinistres concernant les dossiers en attente.

## Fonctionnalités

- Connexion à la base de données Oracle d'ORASS
- Détection automatique des sinistres en attente depuis trop longtemps
- Envoi d'alertes par email personnalisées aux gestionnaires
- Envoi d'un résumé quotidien des alertes à un responsable
- API REST pour déclencher manuellement les alertes
- Journalisation des alertes envoyées pour éviter les doublons

## Prérequis

- Java 11 ou supérieur
- Maven
- Accès à la base de données Oracle d'ORASS
- Serveur SMTP pour l'envoi d'emails

## Installation

1. Cloner le dépôt
```bash
git clone https://votre-depot/orass-alert-system.git
cd orass-alert-system
```

2. Configurer l'application dans `src/main/resources/application.properties`

3. Construire l'application
```bash
mvn clean package
```

4. Lancer l'application
```bash
java -jar target/alert-system-0.0.1-SNAPSHOT.jar
```

## Configuration

### Planification des alertes

Par défaut, les alertes sont envoyées tous les jours à 9h00. Vous pouvez modifier cette planification en changeant la valeur de `alert.cron.expression` dans le fichier `application.properties`.

```properties
# Format cron : secondes minutes heures jour-du-mois mois jour-de-la-semaine
# Exemple : tous les jours à 9h00
alert.cron.expression=0 0 9 * * ?

# Exemple : tous les lundis à 8h30
# alert.cron.expression=0 30 8 * * MON
```

### Seuil d'alerte

Vous pouvez configurer le nombre de jours à partir duquel un sinistre est considéré comme en retard.

```properties
# Par défaut : 30 jours
alert.days.threshold=30
```

### Email du responsable pour les résumés

```properties
alert.summary.email=responsable@votre-entreprise.com
```

## Utilisation de l'API REST

### Déclencher manuellement les alertes

```
GET http://votre-serveur:8080/api/alertes/trigger
```

### Vérifier l'état de l'application

```
GET http://votre-serveur:8080/api/alertes/health
```


## Dépannage

### Problèmes de connexion à la base de données
- Vérifier les paramètres de connexion dans application.properties
- S'assurer que l'utilisateur a les droits d'accès nécessaires
- Vérifier que le driver Oracle JDBC est correctement configuré

### Problèmes d'envoi d'emails
- Vérifier les paramètres SMTP dans application.properties
- S'assurer que le serveur SMTP autorise l'envoi depuis votre application
- Vérifier les logs pour plus de détails sur les erreurs

## Journalisation

Les logs de l'application sont stockés dans le dossier `logs/orass-alert-system.log`. Vous pouvez configurer le niveau de log dans le fichier `application.properties`.

```properties
logging.level.root=INFO
logging.level.com.orass=DEBUG
```
