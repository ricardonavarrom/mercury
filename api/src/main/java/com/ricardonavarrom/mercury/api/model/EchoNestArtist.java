package com.ricardonavarrom.mercury.api.model;

public class EchoNestArtist {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
