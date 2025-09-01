public class Course {
    public enum Difficulty { BEGINNER, INTERMEDIATE, ADVANCED }
    public enum Status { ACTIVE, INACTIVE }

    private final String title;
    private String description;
    private String instructorName;
    private double durationInHours;
    private Difficulty difficultyLevel;
    private Status status;

    public Course(String title, String description, String instructorName,
                  double durationInHours, Difficulty difficultyLevel, Status status) {
        this.title = title;
        this.description = description;
        this.instructorName = instructorName;
        this.durationInHours = durationInHours;
        this.difficultyLevel = difficultyLevel;
        this.status = status;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getInstructorName() { return instructorName; }
    public double getDurationInHours() { return durationInHours; }
    public Difficulty getDifficultyLevel() { return difficultyLevel; }
    public Status getStatus() { return status; }

    public void setDescription(String description) { this.description = description; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
    public void setDurationInHours(double durationInHours) { this.durationInHours = durationInHours; }
    public void setDifficultyLevel(Difficulty difficultyLevel) { this.difficultyLevel = difficultyLevel; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s - %sh - %s", title, difficultyLevel, instructorName, durationInHours, status);
    }
}
