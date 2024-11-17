import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class TextAnalyzer implements Runnable {
    private final BlockingQueue<String> queue;
    private final char targetChar;
    private final Map<Character, String> maxTexts;

    public TextAnalyzer(BlockingQueue<String> queue, char targetChar, Map<Character, String> maxTexts) {
        this.queue = queue;
        this.targetChar = targetChar;
        this.maxTexts = maxTexts;
    }

    @Override
    public void run() {
        String localMaxText = "";
        int localMaxCount = 0;

        try {
            while (true) {
                if (Thread.interrupted()) break;

                String text = queue.take();
                int count = TextUtils.countCharOccurrences(text, targetChar);

                if (count > localMaxCount) {
                    localMaxCount = count;
                    localMaxText = text;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            synchronized (maxTexts) {
                maxTexts.put(targetChar, localMaxText);
            }
        }
    }
}
