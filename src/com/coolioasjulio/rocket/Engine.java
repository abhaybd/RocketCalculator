package com.coolioasjulio.rocket;

import java.util.function.DoubleUnaryOperator;

public class Engine {

    private static double clamp(double d, double low, double high) {
        return Math.max(Math.min(d, high), low);
    }

    private static DoubleUnaryOperator interpolatedCurve(double[][] points) {
        return t -> {
            t = clamp(t, 0, points[points.length - 1][0]);
            for (int i = 0; i < points.length - 1; i++) {
                double t1 = points[i][0];
                double t2 = points[i + 1][0];
                if (t >= t1 && t <= t2) {
                    double dt = t2 - t1;
                    double percentage = (t - t1) / dt;
                    return interpolate(points[i][1], points[i + 1][1], percentage);
                }
            }
            return 0;
        };
    }

    private static double interpolate(double start, double end, double t) {
        return start + t * (end - start);
    }

    private DoubleUnaryOperator thrustCurve;
    private DoubleUnaryOperator massCurve;

    public Engine(double[][] thrustPoints, double startMass, double endMass, double burnTime) {
        this(thrustPoints, new double[][]{{0, startMass}, {burnTime, endMass}});
    }

    public Engine(double[][] thrustPoints, double[][] massPoints) {
        this(interpolatedCurve(thrustPoints), interpolatedCurve(massPoints));
    }

    public Engine(DoubleUnaryOperator thrustCurve, DoubleUnaryOperator massCurve) {
        this.thrustCurve = thrustCurve;
        this.massCurve = massCurve;
    }

    public double getMassAtTime(double time) {
        return massCurve.applyAsDouble(time);
    }

    public double getThrustAtTime(double time) {
        return thrustCurve.applyAsDouble(time);
    }
}
