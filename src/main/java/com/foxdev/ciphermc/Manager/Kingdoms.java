package com.foxdev.ciphermc.Manager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Kingdoms {

    private String name;
    private Set<UUID> members;

    public Kingdoms(String name){
        this.name = name;
        this.members = new HashSet<>();
    }
    public String getName() {
        return name;
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public void addMember(UUID uuid) {
        members.add(uuid);
    }

    public void removeMember(UUID uuid) {
        members.remove(uuid);
    }
}