#Zork Clone

This is a clone of the popular game Zork. It is implemented in Java and supports the ability for users to add
their own custom stories.

The project is currently not finished.

##Running

To run the game, just use the Game class file in src/Game.java. You can also interact with the game from a webserver with the
GameInterface class. The best way to compile at the moment is to use an IDE like IntelliJ. Be sure the Maven
dependencies are satisfied.

##Custom Game

You can define a custom JSON file to define the story, there is a model file in the src folder. When you finish your
file, drop it in the src folder and run the game with a command line argument like so:

```-json ./file.json```

###Custom Behaviour

You can compile a class file and drop it in the root directory (where src/ and pom.xml) are. Compile your custom class
with the jar file in target/ after running ```mvn package```. The name of the class file is specified in each object in
your JSON file.