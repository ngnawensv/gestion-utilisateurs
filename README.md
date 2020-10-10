Gestion des utilisateurs (Partie Back-end avec Java)
==
Cahier des charges
-
Il est question de développer un portail web d'inscription et de connexion en utilisant les services RESTFul (Java+Hibernate+Spring Boot) côté serveur et Angular 8 côté client. NB :  les deux applications sont développées séparément. Les besoins fonctionnels de cette application sont :

* Extraction de tous les utilisateurs 
* Extraction d'un utilisateur à base de son identifiant 
* Extraction d'un utilisateur à base de son login 
* Extraction d'un utilisateur à base de son login et mot de passe 
* Création d'un utilisateur 
* Mise à jour de l'utilisateur 
* Suppression d'un utilisateur

![page_connexion]("https://www.flickr.com/photos/190559299@N02/50443625781/in/dateposted-public/")

 Outils et technologies utilisées
-
* Spring Boot - 2.3.4.RELEASE 
* Java 11
* Framework : Maven 3.2+,Spring( Spring MVC, Spring data, ),JPA, Hibernate 5.2.17, Mockito, JSON, Boomerang, Postman.
* Base de données embarquée : MySql
* IDE : IntelliJI DEA Utimate
 
 Les Taches realisées
-
Pour créer l’architecture de base de l’application à partir de Spring Boot, plusieurs façon s’offre à nous :  A partir d’un IDE à l’instar de Spring Tools Suite ou à partir du générateur fourni par Spring Boot à l'adresse <https://start.spring.io/> (Préférée).

 Creation des couche de l'application
-
1.	**Création du modèle (couche entities)**: User et Role
2.	**Création de la base de données**  
On va initialiser la base de données par des scripts et se connecter dessus sans faire aucune déclaration ni aucune configuration de la data source. C'est un des avantages de Spring Boot. Ici aucune configuration d’Hibernate pour le mapping objet relationnel. La simple déclaration de la base de données MySQL dans le pom.xml fait que Spring Boot configure HibernateEntityManager pour moi. 
3.	**Création de la couche d’accès aux données (DAO/Repository)** : UserDao et RoleDao
4.	**Création de la couche services** : UserService et RoleService
5.	**Création de la couche contrôleur** (des services REST)  
C'est cette couche qui intercepte et filtre toutes les requêtes utilisateurs. Chaque contrôleur dispose d'un service pour traiter les requêtes : c'est le service REST. La Création d'un service REST se passe globalement dans les contrôleurs.
Dans la classe UserController je vais développer les services de lecture, de création, de mise à jour et de suppression d'un utilisateur
6. **Création du package component**  
Il faut créer la classe utilitaire CrossDomainFilter qui aura pour objectif de pallier aux problèmes de CrossDomain. En fait, comme le client et le serveur peuvent être hébergés sur deux serveurs distants, il faut penser aux problèmes réseaux qui peuvent entraver la communication. Il faut indiquer au serveur quels sont les types d'entêtes HTTP à prendre en considération.

Gestion des exceptions avec l’annotation @ControllerAdvice
-
1)	**Utilisation de @ControllerAdvice**  
L'annotation **@ControllerAdvice** est une spécialisation de l'annotation de @Component introduite depuis Spring-3.2. Elle permet de gérer de façon plus globale les exceptions dans un service REST. Une classe portant cette annotation est détectée automatiquement par Spring au chargement de l'application. Cette annotation prend en charge trois autres annotations très importantes :
* **@ExceptionHandler** : permet d’intercepter des exceptions dites globales, quelles que soient leurs origines ;
* **@InitBinder** : permet une initialisation globale ;
* **@ModelAttribute** : permet de créer un objet model global (par exemple : création et initialisation d'une vue ou d'une page de l'application).  
Pour mieux comprendre, créons un nouveau package nommé **exception** et une classe
**ExceptionHandlerControllerAdvice** de gestion globale des exceptions annotée par @ControllerAdvice, et quelques-unes de ses méthodes annotées par @ExceptionHandler, @InitBinder and @ModelAttribute.
On a deux classes utilitaires de gestion des exceptions. Une classe standard de gestion des exceptions que j'ai nommées **ResourceNotFoundException** et une classe classe POJO nommée **ExceptionResponse** qui correspond à l'objet qui va servir à stocker les messages d'erreurs.

2)	**Test des services avec des exceptions**

Gestion des test unitaires et tests d’intégration
-
1)	**Gestion des tests unitaires**  
Chaque couche de l'application peut être testée unitairement. Dans le cas des tests unitaires, l'idée est de s'affranchir de la dépendance entre la couche courante et la couche immédiatement inferieure et de tester unitairement chaque méthode cette couche. C'est ce qui explique le plus souvent l'usage des **Mocks Objects (Objets mocks = Objets factices ou objets sans existence réelle)** dans les tests unitaires.  

a)	**Tests unitaires de la couche DAO**  
La couche DAO (Couche d’Accès aux Données) est la couche la plus basse de l'application et, de ce fait, elle communique le plus souvent avec la base de données. 
Pour la tester, on ne cherche pas à savoir comment se passe l'extraction des données, mais on vérifie plutôt que la couche DAO est capable de fournir les données dont on a besoin : d'où l'utilisation d'un Mock Object.
Pour répondre à ce défi, Spring Boot a mis à notre disposition la classe TestEntityManager qui est l'équivalent Mock de JPA EntityManager.  
L’initialisationdes tests est faite par la classe ci-dessous avec des annotations :
**@RunWith(SpringRunner.class)** permet d'établir la liaison l'implémentation Spring de JUnit, donc c'est tout naturel qu'on l'utilise pour un test unitaire. Elle permet d'utiliser le lanceur Spring de tests "SpringJUnit4ClassRunner"
**@DataJpaTest** est une implémentation Spring de JPA qui fournit une configuration intégrée de la base de données H2, Hibernate, SpringData, et la DataSource. Cette annotation active également la détection des entités annotées par Entity, et intègre aussi la gestion des logs SQL.
**@TestEntityManager** permet de persister les données en base lors des tests. Toutes les méthodes seront testées. 

b)	**Tests unitaires de la couche Service**  
La couche de service a une dépendance directe avec la couche DAO. Cependant, on n'a pas besoin de savoir comment se passe la persistance au niveau de la couche DAO. Le plus important c'est que cette couche renvoie les données sollicitées. On va donc moquer cette couche en utilisant le **framework Mockito**.  
Pour créer un **Mock Object DAO**, Spring Boot Test fournit l'annotation **@MockBean**. Et pour exécuter le service, nous avons besoin d'un objet instance de service. On peut obtenir cette instance à travers une déclaration de classe statique interne en utilisant l'annotation @TestConfiguration et en annotant cette instance par **@Bean**.
L'annotation **@TestConfiguration** a pour effet de n'exposer le bean de service que lors de la phase de tests, ce qui évite sûrement les conflits. Cette annotation joue le rôle de l'annotation **@Autowired**. Le Framework Mockito a été utilisé pour moquer tout le service DAO, ce qui a permis d'initialiser les tests.

c)	**Tests unitaires de la couche Controller**
Ce contrôleur dépend de la couche de service. Il ne connait pas la couche DAO. Il faut donc créer un Mock Object de la couche de service grâce à l'annotation déjà vue précédemment **@MockBean**.
Et pour indiquer le contrôleur à tester, il faut utiliser l'annotation **@WebMvcTest** en lui passant en paramètre la classe à tester. Cette annotation apporte toutes les dépendances SpringMVC nécessaires pour le cas de test. Grâce à l'annotation @WebMvcTest, on peut injecter un **MockMvc** qui servira à construire des URL pour générer des requêtes HTTP.

#### Résumé: Nécessaire pour les tests unitaires par couche

**Couche DAO**
* **@DataJpaTest** pour configurer la base de données et la Datasource ;
* **TestEntityManager** qui est l'équivalent Mock de JPA EntityManager
* **@RunWith(SpringRunner.class)** établir le lien entre Spring et Junit

**Couche de service**
* **@TestConfiguration** permet de récupérer une implémentation du bean de service ;
* **@MockBean** pour créer un objet Mock de la couche DAO ;
* **Mockito** pour créer un Mock de la couche DAO.
* **@RunWith(SpringRunner.class)** établir le lien entre Spring et Junit

**Couche contrôleur**
* **@WebMvcTest** permet d'indiquer le contrôleur à tester
* **@MockMvc** pour injecter un mock MVC qui sert à construire des requêtes HTTP ;
* **@MockBean** pour créer un mock de la couche de service.
*	**@RunWith(SpringRunner.class)** établir le lien entre Spring et Junit

2)	**Gestion des tests d’intégration**  
En cours d'implementation....

Références
-
<https://bnguimgo.developpez.com/tutoriels/spring/services-rest-avec-springboot-et-spring-resttemplate/?page=premiere-partie-le-serveur>






