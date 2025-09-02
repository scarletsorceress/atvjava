package dev.academiadev.model;

public class PremiumPlan implements SubscriptionPlan {
    @Override public String getPlanName() { return "PREMIUM"; }
    @Override public boolean canEnrollMore(int currentActiveEnrollments) { return true; }
    @Override public String toString(){ return getPlanName(); }
}
