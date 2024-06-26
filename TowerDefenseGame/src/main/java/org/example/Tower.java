package org.example;

public class Tower {
    private char symbol;

    public Tower(char symbol) {
        // Precondición: El símbolo no debe ser un espacio en blanco
        assert symbol != ' ' : "El símbolo de la torre no puede ser un espacio en blanco";

        this.symbol = symbol;

        // Postcondición: El símbolo debe ser igual al proporcionado
        assert this.symbol == symbol : "El símbolo de la torre no se inicialioz correctamente";
    }

    public char getSymbol() {
        // Invariante: El símbolo no debe ser un espacio en blanco
        assert symbol != ' ' : "El símbolo de la torre no puede ser un espacio en blanco";
        return symbol;
    }

    public void setSymbol(char symbol) {
        // Precondición: El símbolo no debe ser un espacio en blanco
        assert symbol != ' ' : "El símbolo de la torre no puede ser un espacio en blanco";

        this.symbol = symbol;

        // Postcondición: El símbolo debe ser igual al proporcionado
        assert this.symbol == symbol : "El símbolo de la torre no se actualizó correctamente";
    }
}
