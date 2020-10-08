# BlackJack
Multithreaded client server blackjack game.

Server is initialised through the GameServer class. 

Clients are intialised through the Client class.

Game Play:
• As many players as you want can connect and play. If you join in the middle of a game, you will have to wait until the start of the next hand when bets are being placed to join in.

• In every game there must be at least one player up to as many as you want. There will be a dealer who is played by the computer.

• Each player is given a balance of 200 to play with. If you lose all your money you will be removed from the game but if you are the last remaining player and lose all your money the game will be reset. If you have lost and want to re-join a game, then just open up a new window.

• To leave a game, you must wait until you have finished your round and can then just close the window

• When a user joins the game wait until all the players you want have joined, the number of players currently connected is displayed in the top left.

• A round is initiated by placing a bet. Once all the payers that are connected have placed a bet then the cards will be dealt out.

• Each player gets 2 cards, these are dealt face up and the dealers first card is dealt face down and the second face up.

• If any player gets a natural 21 (a 21 straight from the 2 cards dealt) then they will receive 2.5 times their bet back. And the game will move to a new round.

• Once all the players have their cards, it will move round each player asking them to either hit(receive another card) or stick. The player can hit as many times as they want until they are bust (a score over 21) or they choose to stick. Once they are either bust or have chosen to stick it will move on to the next player.

• Once each user has finished there go then the dealer will go. At the end of the dealers round the winners will be decided and bets will be paid out.

• If no one has 21, then the winner will be decided by the closest to 21. This can’t be higher than 21.

• If a player wins, they will receive 2 times their bet back, If there is a draw, they will receive their original bet back, and if they lose they receive nothing back.
 
