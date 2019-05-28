package com.coolioasjulio.rocket;

import java.util.function.DoubleUnaryOperator;

public class Engine {

    private static double clamp(double d, double low, double high) {
        return Math.max(Math.min(d, high), low);
    }

    private static DoubleUnaryOperator createThrustCurve(double[][] thrustPoints) {
        return t -> {
            for (int i = 0; i < thrustPoints.length - 1; i++) {
                double t1 = thrustPoints[i][0];
                double t2 = thrustPoints[i+1][0];
                if (t >= t1 && t <= t2) {
                    double dt = t2 - t1;
                    double percentage = (t - t1) / dt;
                    return interpolate(thrustPoints[i][1], thrustPoints[i+1][1], percentage);
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
        this(createThrustCurve(thrustPoints), startMass, endMass, burnTime);
    }

    public Engine(DoubleUnaryOperator thrustCurve, double startMass, double endMass, double burnTime) {
        this(thrustCurve, t -> interpolate(startMass, endMass, clamp(t, 0, burnTime) / burnTime));
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
