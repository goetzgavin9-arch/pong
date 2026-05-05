# pong


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
Up and down arrow keys move the paddle a's  y coordinate.
Swing timer ticks every 100 ms.
On tick: ball and paddles move, check for collisions and check for points gain.
Pressing R resists after game over.
Done 
The ball can collide with the top and bottom walls along with both paddles
When the ball go past either of the paddles the other person gets a point
Win and lose screen appear and pressing R resets the game.

