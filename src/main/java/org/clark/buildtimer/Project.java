package org.clark.buildtimer;

public enum Project {

    // Fusion projects
    FUSION("Fusion"),

    // other
    MISCELLANEOUS("Miscellaneous", false) {

        @Override
        public String getPath() {
            return super.getPath().replace(String.format("last_build_%s", getName()), "tmp-timer-file");
        }
    };

    private static final String BUILD_FILE_PATH;

    static {
        BUILD_FILE_PATH = System.getenv("TMP");
    }

    private static final BuildTimerProperties properties = BuildTimerProperties.getInstance();

    private final String projectName;
    private final boolean updateBuildTime;

    private Project(String projectName) {
        this(projectName, true);
    }

    private Project(String projectName, boolean updateBuildTime) {
        this.projectName = projectName;
        this.updateBuildTime = updateBuildTime;
    }

    public final String getName() {
        return projectName;
    }

    public final boolean isUpdateBuildTime() {
        return updateBuildTime;
    }

    public String getPath() {
        return String.format(properties.getFilenameTemplate(), BUILD_FILE_PATH, getName());
    }
}
