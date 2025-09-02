package dev.academiadev.model;

public class BasicPlan implements SubscriptionPlan {
    private final int maxActive = 3;
    @Override public String getPlanName() { return "BASIC"; }
    @Override public boolean canEnrollMore(int currentActiveEnrollments) { return currentActiveEnrollments < maxActive; }
    @Override public String toString(){ return getPlanName(); }
}
