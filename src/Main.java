import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    private static final int TEXT_LENGTH = 100_000;
    private static final int TEXT_COUNT = 10_000;
    private static final int QUEUE_CAPACITY = 100;

    private static final BlockingQueue<String> textQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

    private static final Map<Character, String> maxTexts = new HashMap<>();

    public static void main(String[] args) {
        maxTexts.put('a', "");
        maxTexts.put('b', "");
        maxTexts.put('c', "");

        Thread producer = new Thread(new TextGenerator(textQueue, TEXT_COUNT, TEXT_LENGTH));

        Thread analyzerA = new Thread(new TextAnalyzer(textQueue, 'a', maxTexts));
        Thread analyzerB = new Thread(new TextAnalyzer(textQueue, 'b', maxTexts));
        Thread analyzerC = new Thread(new TextAnalyzer(textQueue, 'c', maxTexts));

        producer.start();
        analyzerA.start();
        analyzerB.start();
        analyzerC.start();

        try {
            producer.join();
            analyzerA.interrupt();
            analyzerB.interrupt();
            analyzerC.interrupt();
            analyzerA.join();
            analyzerB.join();
            analyzerC.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Text with max 'a': " + maxTexts.get('a'));
        System.out.println("Text with max 'b': " + maxTexts.get('b'));
        System.out.println("Text with max 'c': " + maxTexts.get('c'));
    }
}
