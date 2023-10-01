package com.TrainerCRUD.model;

public enum Team {
    VALOR(1),
    MYSTIC(2),
    INSTINCT(3);

    private final int id;

    Team(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
