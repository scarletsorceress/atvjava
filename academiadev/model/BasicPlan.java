
public class BasicPlan implements SubscriptionPlan {
    private final int maxActiveEnrollments;

    public BasicPlan() { this.maxActiveEnrollments = 3; }

    @Override
    public boolean canEnroll(int currentActiveEnrollmentsCount) {
        return currentActiveEnrollmentsCount < maxActiveEnrollments;
    }

    @Override
    public String getName() { return "BasicPlan"; }
}
