package org.clark.buildtimer;

import java.awt.Graphics2D;

import org.clark.buildtimer.LedSegment.LedOrientation;
import org.clark.buildtimer.LedSegment.LedState;

public final class Digit {

    private final LedSegment upperLeft;
    private final LedSegment lowerLeft;
    private final LedSegment upperRight;
    private final LedSegment lowerRight;
    private final LedSegment top;
    private final LedSegment mid;
    private final LedSegment bottom;

    public Digit(int x, int y, int offset) {
        upperLeft = new LedSegment(x,y,offset,LedOrientation.VERTICAL);
        lowerLeft = new LedSegment(x,y+10*offset,offset,LedOrientation.VERTICAL);
        upperRight = new LedSegment(x+8*offset,y,offset,LedOrientation.VERTICAL);
        lowerRight = new LedSegment(x+8*offset,y+10*offset,offset,LedOrientation.VERTICAL);
        top = new LedSegment(x+2*offset,y-9*offset,offset,LedOrientation.HORIZONTAL);
        mid = new LedSegment(x+2*offset,y+offset,offset,LedOrientation.HORIZONTAL);
        bottom = new LedSegment(x+2*offset,y+11*offset,offset,LedOrientation.HORIZONTAL);
    }

    private void set0() {
        upperLeft.setState(LedState.ON);
        lowerLeft.setState(LedState.ON);
        upperRight.setState(LedState.ON);
        lowerRight.setState(LedState.ON);
        top.setState(LedState.ON);
        mid.setState(LedState.OFF);
        bottom.setState(LedState.ON);
    }

    private void set1() {
        upperLeft.setState(LedState.OFF);
        lowerLeft.setState(LedState.OFF);
        upperRight.setState(LedState.ON);
        lowerRight.setState(LedState.ON);
        top.setState(LedState.OFF);
        mid.setState(LedState.OFF);
        bottom.setState(LedState.OFF);
    }

    private void set2() {
        upperLeft.setState(LedState.OFF);
        lowerLeft.setState(LedState.ON);
        upperRight.setState(LedState.ON);
        lowerRight.setState(LedState.OFF);
        top.setState(LedState.ON);
        mid.setState(LedState.ON);
        bottom.setState(LedState.ON);
    }

    private void set3() {
        upperLeft.setState(LedState.OFF);
        lowerLeft.setState(LedState.OFF);
        upperRight.setState(LedState.ON);
        lowerRight.setState(LedState.ON);
        top.setState(LedState.ON);
        mid.setState(LedState.ON);
        bottom.setState(LedState.ON);
    }

    private void set4() {
        upperLeft.setState(LedState.ON);
        lowerLeft.setState(LedState.OFF);
        upperRight.setState(LedState.ON);
        lowerRight.setState(LedState.ON);
        top.setState(LedState.OFF);
        mid.setState(LedState.ON);
        bottom.setState(LedState.OFF);
    }

    private void set5() {
        upperLeft.setState(LedState.ON);
        lowerLeft.setState(LedState.OFF);
        upperRight.setState(LedState.OFF);
        lowerRight.setState(LedState.ON);
        top.setState(LedState.ON);
        mid.setState(LedState.ON);
        bottom.setState(LedState.ON);
    }

    private void set6() {
        upperLeft.setState(LedState.ON);
        lowerLeft.setState(LedState.ON);
        upperRight.setState(LedState.OFF);
        lowerRight.setState(LedState.ON);
        top.setState(LedState.ON);
        mid.setState(LedState.ON);
        bottom.setState(LedState.ON);
    }

    private void set7() {
        upperLeft.setState(LedState.OFF);
        lowerLeft.setState(LedState.OFF);
        upperRight.setState(LedState.ON);
        lowerRight.setState(LedState.ON);
        top.setState(LedState.ON);
        mid.setState(LedState.OFF);
        bottom.setState(LedState.OFF);
    }

    private void set8() {
        upperLeft.setState(LedState.ON);
        lowerLeft.setState(LedState.ON);
        upperRight.setState(LedState.ON);
        lowerRight.setState(LedState.ON);
        top.setState(LedState.ON);
        mid.setState(LedState.ON);
        bottom.setState(LedState.ON);
    }

    private void set9() {
        upperLeft.setState(LedState.ON);
        lowerLeft.setState(LedState.OFF);
        upperRight.setState(LedState.ON);
        lowerRight.setState(LedState.ON);
        top.setState(LedState.ON);
        mid.setState(LedState.ON);
        bottom.setState(LedState.ON);
    }

    public void set(int digit) {
        switch (digit) {
        case 0: set0(); break;
        case 1: set1(); break;
        case 2: set2(); break;
        case 3: set3(); break;
        case 4: set4(); break;
        case 5: set5(); break;
        case 6: set6(); break;
        case 7: set7(); break;
        case 8: set8(); break;
        case 9: set9(); break;
        default: throw new UnsupportedOperationException(String.format("Between 0 and 9 please [%d]", digit));
        }
    }

    public void draw(Graphics2D g2) {
        upperLeft.render(g2);
        lowerLeft.render(g2);
        upperRight.render(g2);
        lowerRight.render(g2);
        top.render(g2);
        mid.render(g2);
        bottom.render(g2);
    }
}
