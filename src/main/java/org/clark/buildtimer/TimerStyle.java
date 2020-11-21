package org.clark.buildtimer;

public enum TimerStyle {

    TEXT,
    DIGITAL;

    public boolean isText() {
        return equals(TEXT);
    }

    public boolean isDigital() {
        return equals(DIGITAL);
    }
}
