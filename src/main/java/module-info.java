module com.example.breakout_game.lab12_breakout_game {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.breakout_game.lab12_breakout_game to javafx.fxml;
    exports com.example.breakout_game.lab12_breakout_game;
}