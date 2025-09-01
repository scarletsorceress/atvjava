public interface SubscriptionPlan {
    boolean canEnroll(int currentActiveEnrollmentsCount);
    String getName();
}
