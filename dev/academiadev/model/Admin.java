package dev.academiadev.model;

public class Admin extends User {
    public Admin(String name, String email) { super(name, email); }
    @Override public boolean isAdmin() { return true; }
}
