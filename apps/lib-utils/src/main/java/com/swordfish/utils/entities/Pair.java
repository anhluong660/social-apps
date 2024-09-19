package com.swordfish.utils.entities;

import lombok.Getter;

@Getter
public class Pair<E1, E2> {

    private E1 first;

    private E2 second;

    public Pair() {
    }

    public Pair(E1 first, E2 second) {
        this.first = first;
        this.second = second;
    }
}
