# findBoxes

## Description and Life-cycle

This game is called Find Boxes. It is a simplified variation of the game Battleships on 10x10 boards. Find Boxes is a 2 player game which is played on ruled grids. Each player hides some specific number of boxes (the players get to decide the number of boxes they want  to hide) on the boards which the other player has to find by guessing the location. The player that finds all the boxes first on their respective board wins the game. 


Stage 1: Each player hides a fixed number of boxes in their opponent’s board
Stage 2: This is when the game starts and the players have to guess the positions of hidden boxes in their board. Whoever finds all the boxes first, wins.

-Each player has a board with 10x10 squares. The top board belongs to Player 1, the bottom board belongs to Player 2.

-At the start of the game, players enter the common number of boxes they are going to hide and then their respective names.

-Ideally Player 1 should look away when Player 2 is hiding boxes in Player 1’s board and vice versa.

-A player hides a box by clicking on a square in their opponent’s board. The status in the screen below would reflect the count. It’s not possible to hide more than the number entered at the beginning of the game.

-The game can only start when the decided number of boxes have been selected on each board and one of the players has to click “Start Game” to start the guessing.

-The counts in the screen below reflects the number of boxes in their respective boards.

-Player finds boxes, by clicking on squares to open them. When a player finds a hidden box, the square turns red and their count decreases. If a player clicks on an empty square, it’s opened and is light gray. It’s not possible to change already revealed squares.

-The first player to find all the hidden boxes (i.e. when their count reaches 0 in the status bar) wins.

-The name of the winning player is added to history and then stored in a file and the next time the game is played, it will show the names of the players in the order they had won.

### Screen Components

Player 1’s board on top and Player 2’s board below that
History of who won in the past on the right side
Status bar at the very bottom indicating count of current hidden boxes for each player and also mentions whose turn it is
The player with current turn will have “(current turn)” specified
