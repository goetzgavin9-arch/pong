I am building pong in Java with Swing using MVC. Here is my spec:
Model
Score (int)
gameState: playing / won / lost
Ball x,y (grid coords)
Paddle a (y no horizontal movement)
Paddle b (y no horizontal movement)
View - pongView.java
Draws ball as a white circle
Draws paddle a as a green rectangle.
Draws paddle b as a red rectangle.
Draws the scores in the middle.
Draws "game over" and "you win" in the centered.
Controller - pongController.java
Up and down arrow keys move the paddle a's y coordinate.
Swing timer ticks every 100 ms.
On tick: ball and paddles move, check for collisions and check for points gain.
Pressing R resists after game over.
Done
The ball can collide with the top and bottom walls along with both paddles
When the ball go past either of the paddles the other person gets a point
Win and lose screen appear and pressing R resets the game.
Generate three class shells — GameModel.java, GameView.java, GameController.java — with method stubs based on this design. GameModel must not import any Swing classes. The program should compile and open a blank window.
the ai made the base for the game of pong

Make a opponate who controls the red paddle who has reaction delay, imperfect tracking and makes mistakes
the AI made the opponate AI for the game
it made the AI opponate too strong so I had to go in and manully nerf him by making the reaction time and speed of the opponate slower 

Make it so the your score ticks up by one when the ball goes past paddle b and the opponates score ticks up by one when the ball goes past paddle a
The AI made it so that when the ball goes past the paddle the other players score goes up by one

Make a new file called pongTester.java and include tests which are test that when the ball "scores" the score goes up test when the ball hits a paddle or the top and bottom walls it bounces test that when one persons score hits 10 the game ends and test that when the up or down arrow key are pressed the paddle moves up and down include any tests additional tests that you think are necessary
The AI made it tests so i could check to make sure that the main functionallity of the game works

make a main menu screen where it says pong in big font in the center of the screen with two buttons one that first to 10 and a second button that says endless when the first button is pushed it should put the player into the current game but when the second button is pushed it should put the player into a version of the game that doesnt end when the score hits 10
the AI made a main menu screen that has two options 

add a button at the top left that says quit that when pressed brings you back to the main menu screen
The AI made a quit button that brings you back to the main menu screen

Add a high score for the endless mode that tracks your highest score ever obtained that is displayed below the score while playing endless and below the endless option on the main menu
the AI added a highscore that was tracked only for endless
the AI didnt make it be tracked between game sesions so i had to ask it to make it be saved between sesions.
Make it so that the highscore is saved between sesions of the game being played
the AI made a highscore.txt file where teh highscore is stored between sesions of the game being played.

make the paddles have a brick wall like design where they have muliple "bricks" making up the entire paddle
the AI made the paddles be made out of "bricks" to improve the visuals of the game

Make it that when playing endless mode in the top right there is a small button that says debug controls that when pressed makes more buttons appear that do the following the first button says add ball when press the button adds a addional ball to the field the next button says speed up when pressed the button increases the speed of the ball the next button says slow down when pressed make the ball move slower make sure all of these changes are reverted upon hitting the quit button
the AI added this debug controls button
the ai also added the debug controls for the first to 10 mode so i had to tell the AI to remove it

make it so the debug controls only appear when in endless mode
the AI removed the button from the first to 10 mode
the AI also removed the ablilty to play the first to 10 game mode

when i press on the first to 10 button nothing happens and i am sent back to the main menu
the ai fixed the first to 10 gamemode so it could be played again.

When i press the button for the debug controls the paddle will no longer move even when the movment keys are pressed
the AI fixed it so that when you use the debug controls you can move the paddle

make it so that when the debug controls button is pressed while playing endless make it so that the highscore will no longer be tracked for that run of endless mode
the ai made a flag so the when you use the debug controls the highscore wont be saved.