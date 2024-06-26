package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TowerDefenseGameeTest {
    private TowerDefenseGame game;
    private Map map;
    private Player player;
    private Wave wave;

    @BeforeEach
    public void setUp() {
        map = Mockito.mock(Map.class);
        player = Mockito.mock(Player.class);
        wave = Mockito.mock(Wave.class);
        game = new TowerDefenseGame();
    }

    @Test
    public void testStartGame() {
        wave.start();
        game.startWave();
        verify(wave).start();
    }
}

