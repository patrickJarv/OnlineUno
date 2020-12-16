# Desktop-Uno
CS201 Group Project - Desktop Uno  
Original repository for the project: https://github.com/lydiagd/Online-Uno
 - Chloe Kuo, Coco Zhang, John Andersen, Lydia DiBlasio, Nicholas Smythia, Patrick Jarvis
 
## Game Overview
A turn-based card game hosted via the terminal and a graphical user interface. Players can connect to a server, and when there are 4 available players a new game will start. The deck of cards include four color attributes, numbered 0-9, along with some wildcards that can change to any color.

## Multithreading
 - The server creates a socket thread which simultaneously manages any socket connections while the server is running the games
 - The socket thread thread will pass the connection to a login thread, which manages  all the client networking while the socket thread continues to search for connections at the same time
 - We create a thread of each game of uno our server is overseeing
 - A game is created when a designated number of clients connect to the server
 
 ## Networking
 - The server runs in an infinite loop until shut down and one step in that loop is to listen for clients on the designated port
 - On the client side, the ip address and port number must be known to send a request
 - When the request is sent by the client and heard by the server, we create a Player object and put it in the queue for a game
 - When there are enough players waiting in the queue, we connect a number of them in a GameTable thread
 - Players can only affect the GameTable they are connected to
 
 ## Authenticated-User Functionality
 - Key difference between a guest vs an authenticated user is in the win-loss ratio
   - Stored and displayed for auth user, but not guest
   - Guest also gets a username of "Player"+(int)(Math.random() * (5000) +1), so no choice in username
 - Upon starting a PlayerClient, after getting the server, we prompt the player to either proceed as guest, log in, or create new user
   - Each username can only be used once
 - Once the game has started:
   - Authenticated users have their win-loss ratio next to their username in the GUI
   - Guests do not, and have no way to access previous stats


## Data Structures Utilized
 - ArrayList/List: used to keep track of current players in the game, and cards in each player’s hand
 - Queue/LinkedList: used to store cards still in the draw pile
 - Array: used to store our colors, usernames, number of cards in players’ hands
 - Stack: used to store the discard pile
 - HashMap: used to store the player’s hand sizes
 
**Enjoy!**
