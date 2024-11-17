import java.util.concurrent.BlockingQueue;

public class TextGenerator implements Runnable {
    private final BlockingQueue<String> queue;
    private final int textCount;
    private final int textLength;

    public TextGenerator(BlockingQueue<String> queue, int textCount, int textLength) {
        this.queue = queue;
        this.textCount = textCount;
        this.textLength = textLength;
    }

    @Override
    public void run() {
        for (int i = 0; i < textCount; i++) {
            try {
                String generatedText = TextUtils.generateText("abc", textLength);
                queue.put(generatedText);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
