package ch.heig.dai.lab.protocoldesign;

public class Result {
    private String UUID;
    private double result;
    private boolean success;
    private String errorMessage;

    public Result(String UUID, double result, boolean success, String errorMessage) {
        this.UUID = UUID;
        this.result = result;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public String getUUID() {
        return UUID;
    }

    public double getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
