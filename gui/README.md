# gui
User interface for schedule planner

This repo should contain the html files for each page, a servlet Java class to manage the html pages, and a Java class for connecting to the databse and pulling information from there (the servlet class and this class may possibly be combined into one). For the servlet and html pages, we need Apache Tomchat running. For reading information from the SQL databse into our GUI, we need mysql-connector-java-8.0.22.jar.

The webpages use PHP, thus they have a .php extension. This means that they cannot be run locally - they either need to be hosted on a website or you will need to use a MAMP server to see them. 

Remember to put the MySQL Connector JAR file into the Tomcat program file.
On Jaehyung's computer, the file location was This PC > OS (C:) > Program Files > Apache Software Foundation > Tomcat 9.0 > lib
Once I copied my mysql-connector-java-8.0.22.jar into this folder, I was able to extract data from the MySQL database and use my servlet to display that data on a Web browser.