// import module java.base;

    public static class Airplane {
        private static final Set<String> SF_AIRPORTS = Set.of("SFO", "SJC", "OAK");
        private int flightLength;
        private String destination;
        public Airplane() {
            var defaultLength = getDefaultLength();
            this(defaultLength);
        }
        public Airplane(int flightLength) {
            validate(flightLength);
            this(flightLength, "SFO");
        }
        public Airplane(int flightLength, String destination) {
            validate(flightLength);
            this.flightLength = flightLength;
            this.destination = destination;
        }
        private static void validate(int flightLength) {
            if (flightLength < 0) {
                throw new IllegalArgumentException("Flight length cannot be negative");
            }
        }
        private static int getDefaultLength() {
            var random = new Random();
            return Math.abs(random.nextInt()) % 500;
        }
        public int getFlightLength() {
            return flightLength;
        }

        /// Determines whether the flight is heading to San Francisco. Returns whether *destination* in airports:
        ///
        /// - San Francisco (SFO)
        /// - San Jose (SJC)
        /// - Oakland (OAK)
        ///
        /// @return true if the destination of the flight is near San Francisco, false otherwise
        public boolean isFlightToSanFrancisco() {
            return SF_AIRPORTS.contains(destination);
        }
    }

    void main() {
        try {
            var airplane = new Airplane();
            IO.println(airplane.getFlightLength());
        } catch(IllegalArgumentException _) {
            IO.println("random number is no good");
        }
    }