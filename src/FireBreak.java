/*
* File: FireBreak.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 1.0
* Created: +++++++++++ Owen insert date here +++++++
* Last edited: 18/08/2021
* Status: In progress
*/
public class FireBreak {

  private int id;
  private int[][] prevLayer;

  //Custom constructor
  FireBreak(int id, int[][] currentLayer){
    this.id = id;
    prevLayer = currentLayer;
  }

  //Accessor methods
  public getID(){
    return id;
  }

  public getPrevLayer(){
    return prevLayer;
  }

}
