# Projet JavaEE AmisEscalade - Content manager

Projet 6 du Parcours DA Java sur OpenClassroom (en cours...)

Application web javaEE destinée à publier les contenus de l'association "Les Amis de l'Escalade".
L'objectif est mettre en place :
- Un système de gestion de fiches de sites d'escalades. 
- Un outils de réservation de topos.
- Un site sécurisé et responsive.

Les scripts de création de la base sont fournis dans database_pg.zip
Un des script (jcm_demo_data.sql) n'est à utiilser que pour tester avec un jeu de demo. Et le fichier media.zip vient complèter ce jeu de démo.

#### Instruction d'installation et d'utilisation

##### Pre-requis
 - serveur web avec Tomcat v8.5 (java 1.8)
 - serveur de base de données Postgresql
 - à minima d'une environnement de developpement avec maven et java8 (Eclipse peut également être utilisé pour la compilation et le packaging avec maven)


##### Deploiement 1 - la base de données

   Vous devez disposer d'un serveur postgresql fonctionnel et d'un user admin (postgres par exemple) pouvant créer les bases et les roles. 

   - Connectez vous à postgres. Les commandes suivantes permettent la création de la base et d'un utilisateur "amiesca":

		> CREATE USER 'amiesca' WITH ENCRYPTED PASSWORD 'amiesca';
		> CREATE DATABASE jcm_demo WITH OWNER amiesca ENCODING 'UTF8' LC_COLLATE='fr_FR.UTF-8' LC_CTYPE='fr_FR.UTF-8';

   - Déconnecter vous de postgres. Décompresser le zip contenant les fichiers sql sur votre environnement. 
   Ouvrez un terminal sur votre système en vous plaçant dans le répertoire ou sont les sql. 
   Et lancer les commandes suivantes :

		$ psql -h localhost -p 5432 -U amiesca -W -f jcm_schema.sql jcm_demo     
		$ psql -h localhost -p 5432 -U amiesca -W -f jcm_referentiel.sql jcm_demo   

  Entrez le mot de passe (amiesca) puis validez pour chacune des commandes
  
  
##### Deploiement 2 - compilation et installation sur le serveur tomcat

Dans un environnement qui devrait être différent de votre serveur, créer un repertoire (AmisEscalade, par exemple).  
Ouvrez un terminal et placez vous dans ce répertoire. Puis lancer les commandes suivantes :

	$ git clone https://github.com/hervep530/AmisEscalade.git
	
Verifier que la variable d'environnement JAVA_HOME et le PATH renvoie bien le chemin de java8, puis faites

	$ mvn install

Connecter vous en ftp au serveur web.  
Avec la commande lcd, positionner dans le repertoire où vous avez fait "git clone", puis lancez :

	> put target/AmisEscalade-1.0-SNAPSHOT.war

Connecter vous sur le serveur web et, de preférence, renommez le fichier AmisEscalade-1.0-SNAPSHOT.war en AmisEscalade.war. Puis copier le dans le repertoire webapps de tomcat.  
Si votre configuration, tomcat installera l'application tout seul et démarrera l'application. Une trentaine de secondes est nécessaire.

Si tomcat n'a pas décompressé l'archive, désarchiver manuellement dans le répertoire webapps de tomcat et redémarrer le serveur. 

En cas d'échec de l'application, vous trouverez une trace dans la log dont le chemin est :  
${catalin.base}/logs/AmisEscalade/jcm_error.log

Sinon consultez les logs du serveur.


##### Deploiement 3 - utilisation du jeu de données de démo

A ce stade de l'installation, l'application est vide. Elle affiche seulement la page d'acceuil et la liste vide de site.

Pour utiliser le jeu de données, exécutez le script jcm_demo_data.ql sur la base jcm_demo. Et decompressez l'archive media.zip sur le serveur web, dans le répertoire de tomcat : .../webapps/AmisEscalade/medias/.  
Pour le script, il suffit de lancer dans un terminal, à partir du dossier où est placer le script :  

	$ psql -h localhost -p 5432 -U amiesca -W -f jcm_demo_data.sql jcm_demo   

Entrez le mot de passe (amiesca) et validez.

Pour complétez et affichez les images dans les différentes pages du site, décompressez le fichier medias.zip sur le serveur tomcat dans le répertoire :  
.../webapps/Amiescalade/medias/


##### Deploiement 4 - Personnalisation de votre environnement.

Bien entendu, il vous faudra adapter les précédentes instructions à votre environnement.  
Si vous utilisez une base avec nom, user, password ou port différent, il suffit de modifier les infos de la base de données dans le fichier persitence.xml.  
Sur le serveur web, vous le trouverez à partir du répertoire webapps de tomcat sous :  
.../webapps/AmiEscalade/WEB-INF/classes/META-INF/persistence.xml


##### Utilisation du jeu de données de démo.

Connecter vous avec : amisimple@amiescalade.fr / paSS13word   
Vous aurez accès à l'application en simple utilisateur.

Connecter vous avec : amimembre@amiescalade.fr / paSS13word   
Vous aurez accès au fonctionnalités reservé aux membres de l'association
