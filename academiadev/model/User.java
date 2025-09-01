public abstract class User {
    private final String name;
    private final String email;

    protected User(String name, String email) {
        this.name = name;
        this.email = email.toLowerCase();
    }

    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("%s <%s>", name, email);
    }
}
