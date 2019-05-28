package com.coolioasjulio.rocket;

public class RocketOptimizer {
    public static void main(String[] args) {
        RocketOptimizer ro = new RocketOptimizer(0.0000001, 1000, 0.0, 0.05, 0.00025, 0.75);
        System.out.println(ro.getOptimalRocketMass());
    }

    private double massLow, massHigh;
    private double crossSectionalArea;
    private double dragCoefficient;
    private double learningRate;
    private int epochs;

    public RocketOptimizer(double learningRate, int epochs, double massLow, double massHigh, double crossSectionalArea, double dragCoefficient) {
        this.learningRate = learningRate;
        this.epochs = epochs;
        this.massLow = massLow;
        this.massHigh = massHigh;
        this.crossSectionalArea = crossSectionalArea;
        this.dragCoefficient = dragCoefficient;
    }

    public double getOptimalRocketMass() {
        double mass = Math.random() * (massHigh - massLow) + massLow;
        for (int epoch = 0; epoch < epochs; epoch++) {
            mass = Math.min(Math.max(mass, massLow), massHigh);
            double dx = 1e-5;
            double height = getApogee(mass);
            double height2 = getApogee(mass + dx);
            double derivative = (height2 - height) / dx;
            double massDelta = derivative * learningRate;
            System.out.printf("epoch=%d, mass=%.3f, apogee=%.3f, derivative=%.3f\n", epoch, mass, height, derivative);
            mass += massDelta;
        }
        return mass;
    }

    private double getApogee(double mass) {
        return new RocketSimulator(new Rocket(mass, crossSectionalArea, dragCoefficient, new EstesA8Engine()), 0.001).simulate().getApogee();
    }
}
