To Play the Game you need to clone the repository to get the .java files.  Then open the WorldView.java file and run it.


WELCOME to 100,000 B.C.E!

In a WORLD... Before Computer Entertainment...

You are Uhg, a pre-historic caveman on a quest to bag yourself a pre-historic cavewoman. 
Unfortunately, the object of your desire lies on the other side of a river, and swimming hasn't been invented yet.
To cross this barrier and profess your love, you must assemble 3 items from around the game world into a grappling spear
which you'll be able to throw across the river. The components for this are: a stick, a T-Rex tooth, and a vine. 
Sticks and teeth seem pretty common, but vines are strangely scarce. You're pretty sure you saw some growing in a cave
near here... now where exactly was the entrance?


Controls
-------------
Arrows: 	Move
Shift:		Run
Space:		Attack

Pause Menus (There's 2!)
-------------

To open inventory, click the inventory button in the menu bar at the bottom of the screen.

To open the minimap, click the Minimap button in the menu bar at the bottom of the screen.

Opening either the Inventory or the Minimap will pause the game, and the game will resume when the window is closed. 

Enemies
------------
T-Rex:			This guy just wants to follow you around like he's your biggest fan. You're gonna need some of his teeth.
Triceratops:	Charges at you the moment he sees you. Tough to kill, but triceratops meat is delicious and nutritious.
Pterodactyl:	I really have no idea what this guy is up to, and I honestly don't think he does either. Drops meat.

Note that the enemy drops happen on the same square as where you defeat the enemy, so if you're too slow to see what's going on, Uhg might go and pick it up! To verify what was dropped or picked up, open your inventory.


Pickups
------------
T-Rex Teeth:	These things make great spear heads. Can you guess where you get them from?
Sticks:			It's a stick. You can just find these laying around.
Vine:			This rare flora can only be found in the deep dark of the cave.
Meat:			You're the original Paleo. Chomping on this stuff will restore some health.

You can make spears once you've collected some T-Rex Teeth and some Sticks. (EDITOR'S NOTE: currently the spears do nothing except look cool.)
Once you've got yourself a vine and a spear, you can make a grappling spear to cross that pesky river to meet your true love.


Pro-tips
-------------
Mankind was never meant to fight dinosaurs. Evasion is a surer means of survival.


Test Cases
-------------
So, funny story about the test cases. It turns out we've got a nearly 100% code coverage, with the exception of some Map Generation lines that may or may not be hit because they're chosen randomly. But on average it seems to be displaying about 99%. We were able to test all the non-GUI dependent code in about 230 lines of test cases!

The test cases are located in GeneralTestCases.java, in the Testing directory. There are a few more test files, but those were used during deveolopement and worked in tandem with the GUI code, so they won't display well.

The GeneralTestCases class contains robust test cases for:
MapGenerator.java
ScreenMap.java
Tile.java
Tuple.java
WorldController.java

Any lines not tested in those are lines that are GUI-dependent.

But wait! Aren't there like two dozen other classes?

Yeah, but it turns out all of them need the GUI to work, because they're closely intertwined with images or sounds or the view.

The classes that we managed to test small parts of are:
BackgroundMusic.java (Cannot get past the line where the MediaPlayer is initiated)
SoundEffect.java (Likewise)
SpriteSheet.java (Can only instantiate the object)

The rest of the classes couldn't even be instantiated without the view being populated. To show that to be true, the final of the test cases is called the CrashAndBurn Test Suite. It's basically all testing what assertionError is thrown when an object of the class is instantiated. By showing that all the rest of the code is GUI-Dependent, and any lines that we don't hit in the classes we did test are GUI-Dependent, that shows that we have 100% path coverage for all non-GUI-Dependent code except in the MapGenerator.java class, where we sometimes have 100%, sometimes as low as 97%, due to a few paths that can only be touched if the Random Number Generator decides to let us. But even at the lowest, that leaves us at about 99% code coverage on average. Pretty darn good we did there.



MAJOR CHEATY SPOILER BELOW 
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
Do you really want to cheat?
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
Really?
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V
V

Really. Ok. Fine:

(You can disable player damage by clicking the middle 'dot' in the title on the welcome screen and entering the Konami code)


since everybody missed our slide presentation and game demo, you can check out the slide show on google slides by visiting the following link:

https://docs.google.com/presentation/d/1L3swqvPgNX7IJ3sWNz9yo-bcl-135HPdW4-76n9eXew/edit?usp=sharing

# 1000000-BC
Fun cave man game created as a final project for CSC 335 at U of A
