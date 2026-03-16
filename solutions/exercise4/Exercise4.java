enum DayOfWeek {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY}

record ClassStart(DayOfWeek dayOfWeek, int hour, int minute) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClassStart(DayOfWeek d, int h, int _)) {
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

static int switchExpressions(DayOfWeek dayOfWeek) {
    return switch (dayOfWeek) {
        case SATURDAY, SUNDAY -> 1;
        default -> dayOfWeek.toString().length();
    };
}

static boolean switchExpressionWithRecord(Object object) {
    return switch(object) {
        case ClassStart(DayOfWeek day, int hour, int _)
                when hour > 10 && (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)
                -> true;
        case ClassStart(DayOfWeek day, int hour, int _)
                when hour > 17
                -> true;
        default -> false;
    };

}

void main() {
    IO.println(traditional(DayOfWeek.FRIDAY));
    IO.println(traditional(DayOfWeek.SATURDAY));
    IO.println(switchExpressions(DayOfWeek.FRIDAY));
    IO.println(switchExpressions(DayOfWeek.SATURDAY));
    IO.println(switchExpressionWithRecord(new ClassStart(DayOfWeek.MONDAY, 11, 0)));
    IO.println(switchExpressionWithRecord(new ClassStart(DayOfWeek.SUNDAY, 11, 0)));

}