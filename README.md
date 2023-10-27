Install:
Download and install Eclipse IDE (https://www.eclipse.org/downloads/packages/release/2021-12/r/eclipse-ide-enterprise-java-and-web-developers).
Download MySQL Server (https://dev.mysql.com/downloads/mysql/).
Download Apache Tomcat 9.0 (https://tomcat.apache.org/download-90.cgi).
Download Java 21.
Hit "<> Code" in the top right and select the .zip download.
Open Eclipse and set your workspace folder.
Take the .zip and extract it into your Eclipse Workspace.
In Eclipse, go to File > Import > Import from existing project and import the extracted zip in your Eclipse Workspace.

Configure:
Install Java 21
Ensure that that Java 21 is being used.
	If not, go to your environment variables and create a new variable called JAVA_HOME and set the value to your Java SDK location. Mine is located in “C:\Program Files\Java\jdk-[version number]”
	Then set put your JDK’s location in the PATH environment variable at the top.
	Use a terminal and type java -version to check the currently used Java version.
Install MySQL server
	Set the default user to the database as root and the password for that user as pass1234.
Name the windows service MySQL80.
Extract the Apache download (.zip) to a known location.
Open Eclipse.
Go to the Servers tab at the bottom.
Add a new Apache Tomcat 9.0 server and point to the location you extracted the Apache server to.
Right click the project in the Project Explorer.
Go to “Properties”
Go to the “Java Compiler” tab.
Ensure that Java 21 is being used by checking the first dropdown.
	If not, uncheck the “Use compliance from execution…” checkbox and then change the “Compiler compliance level” by hitting the dropdown and selecting the correct Java version.
Go to the “Project Facets” tab. 
Ensure that the Java configuration is set to 21.
Hit “Apply and Close.”
You should now be able to run the project.

Ashwin Raam Sethuram - 15 hours
Edited the insert statement for the users.
Created the checkUser() method.
Wrote the isClient() method.

Matthew Meyer - 15 hours
Created ER.
Created SQL.
Wrote check for username.
