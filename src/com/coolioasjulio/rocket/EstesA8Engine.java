package com.coolioasjulio.rocket;

public class EstesA8Engine extends Engine {

    private static final double START_MASS = 0.0167; // kg
    private static final double PROPELLANT_MASS = 0.0033; // kg
    private static final double BURN_TIME = 0.73; // sec
    private static final double[][] THRUST_POINTS = new double[][]{ // { sec, N }
            {0.041, 0.512},
            {0.084, 2.115},
            {0.127, 4.358},
            {0.166, 6.794},
            {0.192, 8.588},
            {0.206, 9.294},
            {0.226, 9.730},
            {0.236, 8.845},
            {0.247, 7.179},
            {0.261, 5.063},
            {0.277, 3.717},
            {0.306, 3.205},
            {0.351, 2.884},
            {0.405, 2.499},
            {0.467, 2.371},
            {0.532, 2.307},
            {0.589, 2.371},
            {0.632, 2.371},
            {0.652, 2.243},
            {0.668, 1.794},
            {0.684, 1.153},
            {0.703, 0.448},
            {0.730, 0.000}
    };


    public EstesA8Engine() {
        super(THRUST_POINTS, START_MASS, START_MASS - PROPELLANT_MASS, BURN_TIME);
    }
}
