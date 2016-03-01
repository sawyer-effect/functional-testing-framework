package com.sawyereffect.cucumber.documentation;

public class CommentRow {
    private String description;
    private String step;
    private String methodName;

    public String getDescription() {
        return description;
    }

    public String getStep() {
        return step;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
