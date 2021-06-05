package xyz.n7mn.dev.gunwar.util;

public class PermissionInfo {

    private final String required;
    private final String current;
    private final boolean passed;

    public PermissionInfo(String required, String current, boolean passed) {
        this.required = required;
        this.current = current;
        this.passed = passed;
    }

    public String getRequired() {
        return required;
    }

    public String getCurrent() {
        return current;
    }

    public boolean isPassed() {
        return passed;
    }
}
