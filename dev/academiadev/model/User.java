package dev.academiadev.model;

public abstract class User {
    protected final String name;
    protected final String email;

    protected User(String name, String email) {
        this.name = name;
        this.email = email.toLowerCase();
    }

    public String getName() { return name; }
    public String getEmail() { return email; }

    public abstract boolean isAdmin();

    @Override
    public String toString() { return String.format("%s <%s>", name, email); }
}
