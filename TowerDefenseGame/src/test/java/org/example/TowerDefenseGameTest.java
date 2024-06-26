package org.example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class TowerDefenseGameTest {
    @Mock
    private Map mockMap;
    @Mock
    private Player mockPlayer;
    @InjectMocks
    private TowerDefenseGame game;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testPlaceTower() {
        Tower mockTower = mock(Tower.class);
        game.placeTower(mockTower, 2, 2);
        verify(mockMap).placeTower(mockTower, 2, 2);
    }

    @Test
    public void testGameState() {
        when(mockPlayer.getScore()).thenReturn(10);
        when(mockPlayer.getBaseHealth()).thenReturn(90);
        when(mockMap.toString()).thenReturn("[ ][ ][ ][ ][ ]\n" +
                "[ ][ ][ ][ ][ ]\n" +
                "[ ][ ][ ][ ][ ]\n" +
                "[ ][ ][ ][ ][ ]\n" +
                "[ ][ ][ ][ ][ ]\n");

        game.gameState();

        verify(mockMap).toString();
        verify(mockPlayer).getScore();
        verify(mockPlayer).getBaseHealth();
        verifyNoMoreInteractions(mockMap, mockPlayer);
    }
}
