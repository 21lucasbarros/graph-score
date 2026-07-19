package com.lucasbarros.model;

public class CastMember {
    private String actorName;
    private RoleType role;

    public CastMember(String actorName, RoleType role) {
        this.actorName = actorName;
        this.role = role;
    }

    public String getActorName() {
        return actorName;
    }

    public RoleType getRole() {
        return role;
    }
}
