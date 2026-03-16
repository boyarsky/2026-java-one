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