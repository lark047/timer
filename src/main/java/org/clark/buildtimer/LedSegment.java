package org.clark.buildtimer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public final class LedSegment {

    public static enum LedState {

        ON(Color.RED), OFF(new Color(230, 230, 230));

        private Color color;

        private LedState(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }

    public static enum LedOrientation { HORIZONTAL, VERTICAL }

//        public LedState toggle() {
//            return equals(ON) ? OFF : ON;
//        }

    private final Polygon segment;
    private LedState state;

    private final int x, y;
    private final int offset;

    public LedSegment(int x, int y, int offset, LedOrientation orientation) {

        segment = new Polygon();
        state = LedState.OFF;

        this.x = x;
        this.y = y;
        this.offset = offset;

        segment.addPoint(x, y);
        segment.addPoint(x + offset, y + offset);

        if (orientation.equals(LedOrientation.VERTICAL)) {
            segment.addPoint(C_X(2), C_Y(0));
            segment.addPoint(C_X(2), C_Y(-8));
            segment.addPoint(C_X(1), C_Y(-9));
            segment.addPoint(C_X(0), C_Y(-8));
        }
        else if (orientation.equals(LedOrientation.HORIZONTAL)) {
            segment.addPoint(C_X(5), C_Y(1));
            segment.addPoint(C_X(6), C_Y(0));
            segment.addPoint(C_X(5), C_Y(-1));
            segment.addPoint(C_X(1), C_Y(-1));
        }
    }

    // returns the X Coordinate
    private int C_X(int factor) {
        return x + factor * offset;
    }

    // returns the Y Coordinate
    private int C_Y(int factor) {
        return y + factor * offset;
    }

    public void render(Graphics2D g2) {
        g2.setColor(state.getColor());
        g2.fillPolygon(segment);
    }

    public void setState(LedState state) {
        this.state = state;
    }
}
