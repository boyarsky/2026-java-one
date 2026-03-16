public class Exercise2b {

    private Exercise2b() {

    }

    void main() {
        try {
            var airplane = new Airplane();
            IO.println(airplane.getFlightLength());
        } catch (IllegalArgumentException _) {
            IO.println("random number is no good");
        }
    }
}