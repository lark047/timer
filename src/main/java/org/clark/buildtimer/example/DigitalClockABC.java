package org.clark.buildtimer.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.clark.buildtimer.Digit;

public class DigitalClockABC extends JPanel implements Runnable{

    private static final long serialVersionUID = 4793486438224198612L;
    private static final int SIZE = 7;

    private long elapsed;
    private final Digit h1, h2, m1, m2, s1, s2, ms1, ms2, ms3;
    private Thread th;

    private final long START_TIME = System.currentTimeMillis();

    public DigitalClockABC(int size) {

        /* H <-- 80 --> H <-- 100 --> M <-- 80 --> M <-- 100 --> S <-- 80 --> S */
        h1 = new Digit(20 * size / SIZE, 100, size);
        h2 = new Digit(100 * size / SIZE, 100, size);
        m1 = new Digit(200 * size / SIZE, 100, size);
        m2 = new Digit(280 * size / SIZE, 100, size);
        s1 = new Digit(380 * size / SIZE, 100, size);
        s2 = new Digit(460 * size / SIZE, 100, size);
        ms1 = new Digit(540 * size / SIZE, 60, size / 2);
        ms2 = new Digit(580 * size / SIZE, 60, size / 2);
        ms3 = new Digit(620 * size / SIZE, 60, size / 2);

        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        start();
    }

    public void start() {
        if (th == null){
            th = new Thread(this);
            th.start();
        }
    }

    @Override
    public void run() {
        while (th != null) {
            elapsed = System.currentTimeMillis() - START_TIME;
            showTime();
            repaint();
        }
    }

    private void showTime() {

        long remaining = elapsed;

        int _ms3 = (int) (remaining % 10);
        remaining = (remaining - _ms3) / 10;

        int _ms2 = (int) (remaining % 10);
        remaining = (remaining - _ms2) / 10;

        int _ms1 = (int) (remaining % 10);
        remaining = (remaining - _ms1) / 10;

        int _s2  = (int) (remaining % 10);
        remaining = (remaining - _s2) / 10;

        int _s1  = (int) (remaining % 10);
        remaining = (remaining - _s1) / 10;

        int _m2  = (int) (remaining % 10);
        remaining = (remaining - _m2) / 10;

        // carry over if necessary
        if (_s1 > 5) {
            ++_m2;
            _s1 %= 6;
        }

        int _m1  = (int) (remaining % 10);
        remaining = (remaining - _m1) / 10;

        if (_m2 > 9) {
            ++_m1;
            _m2 %= 10;
        }

        int _h2 = (int) (remaining % 10);
        remaining = (remaining - _h2) / 10;

        if (_m1 > 5) {
            ++_h2;
            _m1 %= 6;
        }

        // TODO need more?

        ms3.set(_ms3);
        ms2.set(_ms2);
        ms1.set(_ms1);
        s2.set(_s2);
        s1.set(_s1);
        m2.set(_m2);
        m1.set(_m1);
        h2.set(_h2);
        h1.set(0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        h1.draw(g2);
        h2.draw(g2);
        m1.draw(g2);
        m2.draw(g2);
        s1.draw(g2);
        s2.draw(g2);
        ms1.draw(g2);
        ms2.draw(g2);
//        ms3.drawNumber(g2);
    }

    public static void main(String[] a) {
        JFrame f = new JFrame("Digital Clock");
        f.setSize(700, 260);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.getContentPane().add(new DigitalClockABC(4));
    }
}

