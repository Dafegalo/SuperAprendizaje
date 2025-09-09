package config;

public class ErrorProbabilityConfig {
    private double errorRate;

    public ErrorProbabilityConfig(double errorRate) {
        this.errorRate = errorRate;
    }

    public double getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(double errorRate) {
        if (errorRate < 0 || errorRate > 1) {
            throw new IllegalArgumentException("Error rate must be between 0 and 1.");
        }
        this.errorRate = errorRate;
    }
}