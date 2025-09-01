

public class PremiumPlan implements SubscriptionPlan {
    @Override
    public boolean canEnroll(int currentActiveEnrollmentsCount) { return true; }
    @Override
    public String getName() { return "PremiumPlan"; }
}
