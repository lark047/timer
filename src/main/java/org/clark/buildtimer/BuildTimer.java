package org.clark.buildtimer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public final class BuildTimer extends JFrame implements ActionListener {

    // adapted from http://www.dreamincode.net/forums/topic/324497-create-stop-watch-using-thread-class-in-java/

    private static final long serialVersionUID = -8220437617379789411L;

    private static final int DELAY = 16; //ms
    private static final int SIZE = 7;

    private static final Color BACKGROUND_COLOR = new Color(0xffeeeeee);

    private final java.util.Timer timer = new java.util.Timer();

    private final JLabel buildLbl;
    private long buildTime = 0;
    private long buildStart = 0;
    private final TimerStyle style;

    private final Collection<BuildTimerTask> tasks = new ArrayList<>();

    private boolean isRendered = false;

    private final List<Digit> digits = new ArrayList<>();

    public static void main(String[] args) {
        final BuildTimer timer = new BuildTimer();
        timer.start();
    }

    // TODO create one window per task
    public BuildTimer() {

        createTasks();

        setAlwaysOnTop(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(false);
        setResizable(false);
        setOpacity(1.0f);

        style = BuildTimerProperties.getInstance().getTimerStyle();

        if (style.isDigital()) {
            createDigits(4);
            buildLbl = null;
            setSize(400, 180);
        }
        else {
            buildLbl = new JLabel("", SwingConstants.CENTER);
            add(buildLbl);
            setSize(50, 60);
        }

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowActivated(WindowEvent e) {
                BuildTimer.this.setBackground(BACKGROUND_COLOR);
            }
        });
    }

    private void createTasks() {
        for (Project project : Project.values()) {
            BuildTimerTask task = BuildTimerTask.getInstance(project);
            tasks.add(task);
            timer.schedule(task, 0, DELAY);
        }
    }

    private void createDigits(int size) {
        digits.add(new Digit(20 * size / SIZE, 100, size));
        digits.add(new Digit(100 * size / SIZE, 100, size));
        digits.add(new Digit(200 * size / SIZE, 100, size));
        digits.add(new Digit(280 * size / SIZE, 100, size));
        digits.add(new Digit(380 * size / SIZE, 100, size));
        digits.add(new Digit(460 * size / SIZE, 100, size));
        digits.add(new Digit(540 * size / SIZE, 60, size / 2));
        digits.add(new Digit(580 * size / SIZE, 60, size / 2));
    }

    private BuildTimerTask getRunningTask() {
        for (BuildTimerTask task : tasks) {
            if (task.isRunning()) {
                return task;
            }
        }

        return null;
    }

    public void start() {
        new javax.swing.Timer(DELAY, this).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        final String titleFmt = "%s build time";
        final BuildTimerTask task = getRunningTask();

        if (task == null) {
            // nothing to do
            reset();
        }
        else {
            if (buildStart == 0) {
                buildStart = task.getBuildStartDate();
                setTitle(String.format(titleFmt, task.getProjectName()));
            }

            updateTime();
            updateUI();

            isRendered = true;
        }

        setVisible(isRendered);
    }

    private static int getDigit(long time, int place, int base) {
        return ((int) time / place) % base;
    }

    private void updateTime() {

        buildTime = System.currentTimeMillis() - buildStart;

        switch (style) {
        case TEXT:
            break;
        case DIGITAL:

            final long ms = buildTime % 1000;
            final long sc = TimeUnit.MILLISECONDS.toSeconds(buildTime);
            final long mn = TimeUnit.MILLISECONDS.toMinutes(buildTime);
            final long hr = TimeUnit.MILLISECONDS.toHours(buildTime);

            digits.get(7).set(getDigit(ms, 10, 10));
            digits.get(6).set(getDigit(ms, 100, 10));
            digits.get(5).set(getDigit(sc, 1, 10));
            digits.get(4).set(getDigit(sc, 10, 6));
            digits.get(3).set(getDigit(mn, 1, 10));
            digits.get(2).set(getDigit(mn, 10, 6));
            digits.get(1).set(getDigit(hr, 1, 10));
            digits.get(0).set(0);
            // TODO need more?

            break;
        }
    }

    private void updateUI() {
        switch (style) {
        case TEXT:
            buildLbl.setText(getDisplayString(buildTime));
            break;
        case DIGITAL:
            repaint();
            break;
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (Digit digit : digits) {
            digit.draw(g2);
        }
    }

    private void reset() {
        buildStart = 0;
        isRendered = false;
    }

    private static String getDisplayString(long millis) {
        long m = millis % 1000;
        long s = TimeUnit.MILLISECONDS.toSeconds(millis - m) % 60;
        long i = TimeUnit.MILLISECONDS.toMinutes(millis - s * 1000 - m) % 60;
        long h = TimeUnit.MILLISECONDS.toHours(millis - i * 60000 - s * 1000 - m);
        return String.format("%02d:%02d:%02d.%03d", h, i, s, m);
    }
}
