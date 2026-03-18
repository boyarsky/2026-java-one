# 2026-java-25

This repository includes the lab of my JavaOne 2026 HOL/Hack Session along with the lab solutions. The lab assignment is as follows.

Note that you are allowed to use Google or the Java 25 JavaDoc for any of these. You are not expected to have memorized the methods or syntax. Please do not have AI do the lab for you.

The answers are in the solutions directory.

You can use https://dev.java/playground/ as indicated for most of exercise 1 and all of exercise 4 as noted in those sections. All exercises can be done on a computer with Java 25+ installed

# Exercise 1

If you are using the Java playground, remove the main() method declaration and have the contents of the main method as loose code. Ignore anything about running from the command line.

1. Copy the following code into a file named exercise1.Exercise1.java
```
import java.util.Random;
import java.util.Set;

public class Exercise1 {

    public static class Airplane {
        private static final Set<String> SF_AIRPORTS = Set.of("SFO", "SJC", "OAK");
        private int flightLength;
        private String destination;
        public Airplane() {
            this(getDefaultLength());
        }
        public Airplane(int flightLength) {
            this(validateAndReturn(flightLength), "SFO");
        }
        public Airplane(int flightLength, String destination) {
            this.flightLength = validateAndReturn(flightLength);
            this.destination = destination;
        }
        private static int validateAndReturn(int flightLength) {
            if (flightLength < 0) {
                throw new IllegalArgumentException("Flight length cannot be negative");
            }
            return flightLength;
        }
        private static int getDefaultLength() {
            var random = new Random();
            return random.nextInt() % 300;
        }
        public int getFlightLength() {
            return flightLength;
        }
        /**
         * Determines whether the flight is heading to San Francisco. Returns whether <i>destination</i> in airports:
         * <ul>
         *     <li>San Francisco (SFO)</li>>
         *     <li>San Jose (SJC)</li>
         *     <li>Oakland (OAK)</li>
         * </ul>
         *
         * @return true if the destination of the flight is near San Francisco, false otherwise
         */
        public boolean isFlightToSanFrancisco() {
            return SF_AIRPORTS.contains(destination);
        }
    }

    public static void main(String[] args) {
        try {
            var airplane = new Airplane();
            System.out.println(airplane.getFlightLength);
        } catch(@SuppressWarnings("unused") IllegalArgumentException e) {
            System.out.println("random number is no good");
        }
    }
 }
```
2. Run it as java exercise1.Exercise1.java
3. Refactor to use a module import: import module java.base
4. Refactor to use a compact class: remove the exercise1.Exercise1 class declaration. Also get rid of the import entirely as java.base automatically provided.
5. Refactor to use an instance main: void main()
6. Refactor to use IO.println() instead of System.out.println()
7. Refactor to use an unnamed variable (_) instead of the suppress warnings in the catch block
8. Refactor the JavaDoc to use triple slashes instead of the traditional Javadoc. See https://spec.commonmark.org/0.30/ for how to do emphasis to replace the italics and lists.
9. Refactor the default constructor of the Airplane class to use a flexible constructor body where the call to getDefaultLength() is stored in a local variable and that local variable is passed to this()
10. Refactor the validateAndReturn() method to just be validate() and change the return type to void. Adjust the other constructors as needed.
11. Bonus: Try printing destination in the constructor before the this() call and note the error message. Now trying setting destination to a value in the same location. Why do you think one works and the other doesn't?

# Exercise 2

This exercise cannot be done in the Java playground.

1. Copy the Airplane class from your exercise 1 solution into a new file (in a new directory) called Airplane.java. Remember to get rid of the static modifier for the class and to restore the import.
2. Create an Exercise2a file with just the main method from Exercise1. Do not include a class declaration.
3. Run it as Exercise2a.java. Notice how Airplane is found and no .class files are created.
4. Create Exercise2b.java with the same contents as Exercise2a.java and add a class declartion.
5. Run it as Exercise2b.java and note that it also runs without creating any .class files
6. Bonus: Add a private default constructor to Exercise2b and run java Exercise2b again. Do you understand the behavior?
7. Bonus: Add a parameter to the constructor in Exercise2b and run java Exercise2b again. Do you understand why it fails?
8. Bonus: Finally, add static to the main method and run java Exercise2b.java. Do you understand why it now works?

# Exercise 3

This exercise cannot be done in the Java playground.

1. Create a file named Exercise3a and try running this code. How long does it take?
```
 static class Logic {
        static void waitUp() {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

void main() throws InterruptedException {

        var start = System.nanoTime();

        var platformThreads = Stream.generate(() -> Thread.ofPlatform().unstarted(Logic::waitUp)).limit(1_000).toList();
        platformThreads.forEach(Thread::start);
        for (var t : platformThreads) {
            t.join();
        }
        
        var end = System.nanoTime();
        IO.print("Done. %d seconds ".formatted((end - start) / (1_000 * 1_000)));
}
```

2. Try increasing the number of threads in the limit to a million. Does it work?
3. Try changing the above example to use a virtual thread. Now can you change the number of threads to a million? How long does it take to run?
4. Now see how many actual platform threads were used to service all these virtual threads:

```
 static class Logic {
        private static Set<String> workers = new TreeSet<>();

        static void waitUp() {
            String name = Thread.currentThread().toString();
            workers.add(name.replaceFirst("^.*/", ""));
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

 void main() throws InterruptedException {

    var start = System.nanoTime();

    var virtualThreads = Stream.generate(() -> Thread.ofVirtual().unstarted(Logic::waitUp)).limit(1_000_000).toList();
    virtualThreads.forEach(Thread::start);
    for (var t : virtualThreads) {
       t.join();
    }

    var end = System.nanoTime();
    IO.print("Done. %d seconds ".formatted((end - start) / (1_000 * 1_000)));
    Logic.workers.forEach(System.out::println);
}
```

# Exercise 4

If using the Java playground, remove the main() method declaration (like in exercise 1; copying the contents in)

1. Copy this code into Exercise4.java
```
enum DayOfWeek {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY}

record ClassStart(DayOfWeek dayOfWeek, int hour, int minute) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClassStart(DayOfWeek d, int h, int m)) {
            return d == dayOfWeek && h == hour;
        }
        return false;
    }
}

static int traditional(DayOfWeek dayOfWeek) {
    int result;
    switch (dayOfWeek) {
        case SATURDAY:
        case SUNDAY:
            result = 1;
            break;
        default:
            result = dayOfWeek.toString().length();
    }
    return result;
}

void main() {
    IO.println(traditional(DayOfWeek.FRIDAY));
    IO.println(traditional(DayOfWeek.SATURDAY));
}
```
2. Identify the variable in equals() that can be an unnamed variable and switch it to an underscore.
3. Create a method named switchExpression that does the same thing as traditional() but uses the newer arrow syntax.
4. Bonus: Write a method switchExpressionWithRecord using a switch expression (including when clause) that reutnrs true if later than hour 10 on weekends or later than hour 17 on weekdays.
