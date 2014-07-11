Mini SNS API v0.0.1
============

Mini Social Network Services APIs By Spring MVC Restful Webservices

This is under construction.

Deployment Process:
  1. Run schema file ```/n9-mini-sns-dao/src/db/mini_sns_schema.sql``` using MySQL

  2. Copy configuration file from ```/n9-mini-sns-web/src/main/resources/n9-mini-sns.properties``` to ```/etc/n9-mini-sns/n9-mini-sns.properties```
  
  3. Edit configuration file 
  
      ```data.url = jdbc:mysql://{url_to_your_database_server}:3306/n9_mini_sns?useUnicode=true&amp;characterEncoding=UTF-8&amp;connectionCollation=utf8mb4_general_ci```


      ```data.username = your_database_username```
      
      
      ```data.password = your_database_password```
      
      
  4. Build whole project by Maven:
      ```mvn clean install ```

  5. Copy ```/n9-mini-sns-web/targetn9-mini-sns.war``` to your Tomcat webapps folder

  6. Run Tomcat server

Notes:
  1. You can check application log by ```/var/log/tomcat/n9-mini-sns.log``` 
  
  2. APIs is described in ```api_info.txt```
