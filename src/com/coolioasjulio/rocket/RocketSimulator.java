package com.coolioasjulio.rocket;

import java.util.Arrays;

public class RocketSimulator {
    private static final double AIR_DENSITY = 1.225; // kg/m^3
    private static final double G = 9.81; // m/s^2

    public static void main(String[] args) {
        Rocket rocket = new Rocket(0.010, 0.0013, 0.82, new EstesA8Engine());
        System.out.println(new RocketSimulator(rocket, 0.001).simulate().getApogee());
    }

    private Rocket rocket;
    private double dt;
    private int timestep;
    private double velocity;
    private double lastAcceleration;
    private double height;

    public RocketSimulator(Rocket rocket, double dt) {
        this.rocket = rocket;
        this.dt = dt;
    }

    private void reset() {
        timestep = 0;
        velocity = 0;
        lastAcceleration = 0;
        height = 0;
    }

    public RocketSimulationResults simulate() {
        reset();
        while (!advanceTimestep()) {
        }
        return new RocketSimulationResults(height, timestep * dt);
    }

    private boolean advanceTimestep() {
        double time = ++timestep * dt;
        double mass = rocket.getMassAtTime(time);
        double dragForce = 0.5 * AIR_DENSITY * rocket.getCrossSectionalArea() * rocket.getDragCoefficient() * Math.pow(velocity, 2);
        double gravityForce = mass * G;
        double engineForce = rocket.getEngine().getThrustAtTime(time);
        double netForce = engineForce - gravityForce - dragForce;
        double acceleration = netForce / mass;
        double newVelocity = velocity + average(acceleration, lastAcceleration) * dt;
        height += average(newVelocity, velocity) * dt;
        lastAcceleration = acceleration;
        double oldVelocity = velocity;
        velocity = newVelocity;
        System.out.printf("%d - time=%.3f, velocity=%.2f, height=%.2f\n", timestep, time, velocity, height);
        return velocity <= 0.0 && oldVelocity > 0.0;
    }

    private double average(double... arr) {
        return Arrays.stream(arr).average().orElse(0.0);
    }

    public class RocketSimulationResults {
        private double apogee;
        private double timeToApogee;

        private RocketSimulationResults(double apogee, double timeToApogee) {
            this.apogee = apogee;
            this.timeToApogee = timeToApogee;
        }

        public double getApogee() {
            return apogee;
        }

        public double getTimeToApogee() {
            return timeToApogee;
        }
    }
}
