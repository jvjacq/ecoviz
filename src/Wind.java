/*
* File: Wind.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 1.2
* Created: +++++++++++ Owen insert date here +++++++
* Last edited: 25/08/2021
* Status: In progress
*/
public class Wind {
    private final double CONVERT = 0.621371;
    private double direction;
    private double speed;
    private boolean metric; //1 = kph, 0 = mph

    //Constructor
    public Wind() {
        direction = 0.0;
        speed = 0.0;
        metric = true;
    }

    // Mutator methods:
    public void setDirection(double newD) {
        direction = newD;
    }

    public void setSpeed(double newS) {
        speed = newS;
    }

    //Accessor methods:
    public double getSpeed(){
      return (metric ? speedKPH() : speedMPH());
    }

    public double getDirection(){
      return direction;
    }

    public void toggleMetric(){
      metric = !metric;
    }

    public double speedKPH(){
      //Assuming speed from 0.0 - 100.0
      return speed/2;
    }

    public double speedMPH(){
      return (speed/2) * CONVERT;
    }

}
