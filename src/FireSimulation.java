public class FireSimulation {

    private double speedMultiplier;
    private String colour;

    public FireSimulation() {
        speedMultiplier = 0.0;
        colour = "Red"; // Maybe have a secondary color to represent heat (i.e. orange, red or use RGB
                        // range)
    }

    // Mutator Methods:
    public void setSpeedMultiplier(double newMult) {
        speedMultiplier = newMult;
    }

    public void setColour(String newColour) {
        colour = newColour;
    }
}
