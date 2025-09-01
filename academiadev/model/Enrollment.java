public class Enrollment {
    private final Student student;
    private final Course course;
    private double progress; // 0.0 - 100.0
    private boolean active; // active means the enrollment is in place (not cancelled)

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.progress = 0.0;
        this.active = true;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public double getProgress() { return progress; }
    public boolean isActive() { return active; }

    public void setProgress(double progress) {
        if (progress < 0 || progress > 100) throw new IllegalArgumentException("Progress must be 0..100");
        this.progress = progress;
    }

    public void cancel() { this.active = false; }

    @Override
    public String toString() {
        return String.format("%s -> %s : %.1f%% (%s)", student.getEmail(), course.getTitle(), progress, active ? "ACTIVE" : "CANCELLED");
    }
}
