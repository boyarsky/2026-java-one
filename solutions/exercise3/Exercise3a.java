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