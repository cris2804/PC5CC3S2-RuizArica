package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TowerTest {

    @Test
    public void testTowerConstructorValidSymbol() {
        Tower tower = new Tower('T');
        assertEquals('T', tower.getSymbol());
    }



    @Test
    public void testSetSymbolValid() {
        Tower tower = new Tower('T');
        tower.setSymbol('A');
        assertEquals('A', tower.getSymbol());
    }


    @Test
    public void testGetSymbolInvariant() {
        Tower tower = new Tower('T');
        assertEquals('T', tower.getSymbol());
        // El símbolo debe ser diferente de un espacio en blanco
        assertNotEquals(' ', tower.getSymbol());
    }
}
