# team9Project1

Software Modelling project 1 notes:
Question 1:
Edit roll method in navigation pane to loop the animation and store overall dice roll in an attribute
May also need to edit the Die class

Question 2:
Use connection between puppet and navigation pane to track whether nbsteps = num of dice
In act modify if statement for checking for connection

Solution 2:
(-created variable in puppet 'turnSteps' stores the total steps to be moved in current turn. Set to 'nbSteps' in Puppet.go(int nbSteps).
 -changed condition statement in Puppet.act() to check if 'turnSteps' == 'numberOfDice' then connection must go up, sets 'currentCon' to null otherwise.
 -modified varaible 'numberOfDice' in NavigationPane as it could not be accessed normally, declared it outisde of constructor and added NavigationPane.getnumberOfDice().)

Question 3:
Create new methods in puppet that access other players information through navigation pane

Solution 3:
(-implemented Puppet.collisionDetect() which checks if any other player share a square and moves them back one square.
 -collisionDetect() calls Puppet.go(nbSteps = -1), in Puppet.act() now checks if nbSteps == -1 and calls Puppet.moveToPrevCell()
 -had to modify conditional structure in Puppet.act() by moving if(nbSteps == 0) to outside the if(nbSteps > 0) statement.)

Question 4:
Logic for simulated players will be written in SimulatedPlayer class
The Boolean for toggle is isToggle which is on line 208 in NavigationPane
Puppet class will handle how to react to connections based on toggle

Solution 4:
(-implemented GamePane.swapConnections() that swaps the behaviour of snakes and ladders. 
 -Is called when 'isToggle' changes within NavigationPane.buttonChecked(...) for isToggle.
 -AI decision making is in SimulatedPlayer.shouldToggle(), true when there a more up connections than down in next roll for opppenents.)

Question 5:
Also could improve cohesion to do it in a different class
