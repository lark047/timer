package org.clark.buildtimer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class BuildTimerProperties extends Properties {

    private static final long serialVersionUID = 5732196821059339321L;
    private static final BuildTimerProperties SELF = new BuildTimerProperties();

    private static final String TIMER_PROPERTIES = "timer.properties";

    private static final String FILENAME_TEMPLATE_KEY = "filename.template";
    private static final String TIMER_STYLE_KEY = "timer.style";
    private static final String DISPLAY_TOTAL_TIME_KEY = "display.totalTime";

    private BuildTimerProperties() {
        super();
        try (final InputStream is = getClass().getResourceAsStream("/" + TIMER_PROPERTIES)) {
            load(is);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BuildTimerProperties getInstance() {
        return SELF;
    }

    public String getFilenameTemplate() {
        return getProperty(FILENAME_TEMPLATE_KEY);
    }

    public TimerStyle getTimerStyle() {
        String style = getProperty(TIMER_STYLE_KEY);
        return TimerStyle.valueOf(style.toUpperCase());
    }

    public boolean isDisplayTotalTime() {
        String display = getProperty(DISPLAY_TOTAL_TIME_KEY);
        return Boolean.valueOf(display);
    }
}
