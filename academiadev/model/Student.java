import java.util.*;

public class Student extends User {
    private SubscriptionPlan subscriptionPlan;
    // We'll keep set of enrollments elsewhere in service, but helpful to have ID or convenience equals/hashCode by email
    public Student(String name, String email, SubscriptionPlan subscriptionPlan) {
        super(name, email);
        this.subscriptionPlan = subscriptionPlan;
    }

    public SubscriptionPlan getSubscriptionPlan() { return subscriptionPlan; }
    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) { this.subscriptionPlan = subscriptionPlan; }

    @Override
    public String toString() {
        return super.toString() + " [" + subscriptionPlan.getName() + "]";
    }
}
