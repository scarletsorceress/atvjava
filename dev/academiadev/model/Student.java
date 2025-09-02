package dev.academiadev.model;

public class Student extends User {
    private SubscriptionPlan subscriptionPlan;

    public Student(String name, String email, SubscriptionPlan plan) {
        super(name, email);
        this.subscriptionPlan = plan;
    }

    public SubscriptionPlan getSubscriptionPlan() { return subscriptionPlan; }
    public void setSubscriptionPlan(SubscriptionPlan plan) { this.subscriptionPlan = plan; }

    @Override public boolean isAdmin() { return false; }

    @Override public String toString() {
        return String.format("%s <%s> [%s]", name, email, subscriptionPlan.getPlanName());
    }
}
