# JavaFX-MVC-database-controller
A small JavaFX app I made while studying Java at uni. It's used to add uni entities to an H2 database or search existing data


This small app uses JavaFX for the UI and H2 as a database, everything else is vanilla Java.
It can be used to insert new entities into an H2 DB or search the existing ones.
Some things may be fixed or changed in the future but due to a lack of time new features like editing and deleting data will probably not be added.

How to use: 
Download H2 and run it, create a ne Generic H2 embedded database and change the URL, username and password in the "baza.properties" file located in the "dat" folder.
You can run a premade script in the browser, just copy the text from "DBScript.txt" also located in "dat" and paste it in the H2 console texfield for some initial data or you can just add some yourself.
Entities can also be added, deleted or edited using the H2 console.
