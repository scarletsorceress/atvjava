package dev.academiadev.model;

public interface SubscriptionPlan {
    String getPlanName();
    boolean canEnrollMore(int currentActiveEnrollments);
}
