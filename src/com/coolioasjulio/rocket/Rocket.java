package com.coolioasjulio.rocket;

public class Rocket {
    private double mass;
    private double crossSectionalArea;
    private double dragCoefficient;
    private Engine engine;
    public Rocket(double mass, double crossSectionalArea, double dragCoefficient, Engine engine) {
        this.mass = mass;
        this.crossSectionalArea = crossSectionalArea;
        this.dragCoefficient = dragCoefficient;
        this.engine = engine;
    }

    public double getDryMass() {
        return mass;
    }

    public double getMassAtTime(double time){
        return getDryMass() + engine.getMassAtTime(time);
    }

    public double getCrossSectionalArea() {
        return crossSectionalArea;
    }

    public double getDragCoefficient() {
        return dragCoefficient;
    }

    public Engine getEngine(){
        return engine;
    }
}
