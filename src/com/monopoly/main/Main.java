package com.monopoly.main;

import com.monopoly.controller.Controller;
import com.monopoly.logic.engine.Engine;
import com.monopoly.view.View;
import java.util.function.Consumer;


public class Main
{
    public static void main(String[] args)
    {
        View view = new View();
        Engine engine = new Engine();
        Controller controller = new Controller(view, engine);
        
        controller.initGame();
        engine.getBoard().getBoardChangeNotifier().addListener(view::boardChange);
        engine.getCurrentPlayerNotifier().addListener(view::showCurrentPlayerName);
        controller.play(); //game Starts here
    }

}
