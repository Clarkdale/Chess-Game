# Chess-Game
Chess game developed using javafx for graphical interface. The initial board created with pieces
and background of beige and brown board squares, which can be seen in the issues section of this 
repository. Alongside that, there are also images of the game working, with pawns moved on the next
image, and example of restricted movement where a pawn was attempted to be moved to an invalid 
location three spaces in front of it. In this sort of circumstance, a message was sent to 
standard out stating, "Invalid Move." Mouse clicks on screen are fully functional, and progress
is being made towards further restricting the movements of pieces and more customization of 
the overall interface.
__________________________________________________________________________________________________________________________________________

Currently, there are still some edits that need to be made to this overall game. While Knights, Bishops, Pawns, and Rooks have their 
movement restricted, there are still some bugs, such as being able to hop over pieces with all pieces rather than just Knights. 
Kings also need their movement to be restricted, as well as the queen. Movement is currently being restricted based on what piece
the user selects, and based on which space the user then selects, where boolean logic is used to check if this space is valid.  

Consideration towards storing possible moves in a set and highlighting the board based on this is being made.
