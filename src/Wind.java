/*
* File: Wind.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 1.1
* Created: +++++++++++ Owen insert date here +++++++
* Last edited: 18/08/2021
* Status: In progress
*/
public class Wind {
    private double direction;
    private double speed;

    //Constructor
    public Wind() {
        direction = 0.0;
        speed = 0.0;
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
      return speed;
    }

    public double getDirection(){
      return direction;
    }

}
