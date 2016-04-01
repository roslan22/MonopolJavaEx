package com.monopoly.controller;

import com.monopoly.GameEvent;
import com.monopoly.logic.engine.Engine;
import com.monopoly.logic.model.player.Player;
import com.monopoly.view.PlayerBuyAssetDecision;
import com.monopoly.view.View;
import com.monopoly.view.PlayerBuyHouseDecision;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class Controller
{
    private static final String PATH = "";

    private View view;
    private Engine engine;
    private Map<Player, Integer> lastReceivedEventIds = new HashMap<Player, Integer>();
    private List<GameEvent> events;
    
    public Controller(View view, Engine engine)
    {
        this.view = view;
        this.engine = engine;
        view.setPlayerBuyHouseDecision((int eventID, boolean answer) -> {
            buy(1, eventID, answer); //TODO: IN EXERCISE 3 CHANGE TO  PLAYERID
        });
        view.setPlayerBuyAssetDecision((int eventID, boolean answer) -> {
            buy(1, eventID, answer); //TODO: IN EXERCISE 3 CHANGE TO PLAYERID
        });
    }

    public void play()
    {
        engine.startGame();
        while (engine.isStillPlaying())
        {
            Player player = engine.getCurrentPlayer(); //TODO: need to decide what player's events to pull
            events = engine.getEvents(player.getPlayerID(), lastReceivedEventIds.get(player));
            view.showEvents(events);
        }   engine.initializeBoard(new XmlMonopolyInitReader(PATH)); //
        engine.putPlayersAtFirstCell();
    }
    
    public void initGame()
    {
        createPlayers();
        engine.initializeBoard(new XmlMonopolyInitReader(PATH)); //
        engine.putPlayersAtFirstCell();
    }
    
    private void createPlayers()
    {
        int humanPlayersNumber = view.getHumanPlayersNumber();
        int computerPlayersNumber = view.getComputerPlayersNumber();
        // TODO: Check that the sum of the players is 6
        List<String> humanPlayersNames = view.getHumanPlayerNames(humanPlayersNumber);
        engine.createPlayers(humanPlayersNames, computerPlayersNumber);
        addPlayersToLastEventId();
    }

    private void addPlayersToLastEventId() 
    {
         List<Player> players =  engine.getAllPlayers();
         for(Player player : players)
         {
            lastReceivedEventIds.put(player, 0);
         }
    }
    
    private void buy(int playerID, int eventID, boolean answer) 
    {
        engine.buy(playerID, eventID, answer);
    }
    
}
