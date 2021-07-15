Please note that when you run this program, you should only run it with the .java files that I put in the updated Git folder. I've re-organized the code in some parts to make it more readable (notably moving some member variables in the Game class out of start()) so I can't guarantee that the files would work if you tried to run them with your copies. 

I've done a lot of the logic, but a lot still needs to be implemented. I highly encourage you to go through each of the files and CTRL-F "TODO" to find where the holes are. Many are small and GUI-related that I don't know how to do, and if either of y'all could take care of that I would greatly appreciate it!

The most important TODOs left are figuring out how to handle blank tiles, how to remove tiles from the board when a move is cancelled/invalid, and how to determine game ending logic. (It's possible that no moves may be possible while the letterbag isn't empty yet.) I can do the game ending logic, but the other two TODOS are GUI-dependent. idk what the best route is to ask a user for the letter they want to replace the blank tile with. 

Also, in Game.java, the reason why you see three copies of manageButtons() and hidePlayerRacks() is because I couldn't figure out how to put those functions outside of their handle(). It'd be nice to only have one copy of the functions, but idk if that's doable. 


ERRORS:
You may notice that a player's rack is not being re-filled (that's a TODO) 
I still need to fix why the player's score isn't working.

lmk your thoughts and if you have any questions!
