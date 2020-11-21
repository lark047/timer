package org.clark.buildtimer;

import java.io.File;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public final class BuildTimerTask extends TimerTask {

    // Fusion + one misc
    private static final int MAX_TASKS = 1 + 1;
    private static int RUNNING_TASKS;

    private static long totalRunningTime = 0;

    private final Project project;
    private long buildStart = 0;
    private boolean isBuilding;

    private BuildTimerTask(Project project) {
        this.project = project;
    }

    @Override
    public void run() {

        final File timerFile = new File(project.getPath());

        if (timerFile.exists()) {
            if (buildStart == 0) {
                buildStart = timerFile.lastModified();
                System.out.println(String.format("%s build started at %s", project.getName(), new Date(buildStart).toString()));
            }
        }
        else {
            if (isBuilding) {
                System.out.println(String.format("%s build finished at %s", project.getName(), new Date().toString()));
                if (project.isUpdateBuildTime()) {
                    updateBuildTime(buildStart);
                    if (BuildTimerProperties.getInstance().isDisplayTotalTime()) {
                        System.out.println("Total running time: " + formatElapsedTime());
                    }
                }
            }
            buildStart = 0;
        }

        isBuilding = (buildStart != 0);
    }

    private static synchronized void updateBuildTime(long buildStart) {
        totalRunningTime += System.currentTimeMillis() - buildStart;
    }

    private static String formatElapsedTime() {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(totalRunningTime),
                TimeUnit.MILLISECONDS.toMinutes(totalRunningTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalRunningTime)),
                TimeUnit.MILLISECONDS.toSeconds(totalRunningTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalRunningTime)));
    }

    public String getProjectName() {
        return project.getName();
    }

    public long getBuildStartDate() {
        return buildStart;
    }

    public boolean isRunning() {
        return buildStart > 0;
    }

    public static BuildTimerTask getInstance(Project project) {
        if (RUNNING_TASKS == MAX_TASKS) {
            System.err.println("Cannot create additional tasks; limit reached");
            return null;
        }

        RUNNING_TASKS++;
        return new BuildTimerTask(project);
    }
}
