public class TextUtils {
    public static String generateText(String alphabet, int length) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(alphabet.charAt((int) (Math.random() * alphabet.length())));
        }
        return text.toString();
    }

    public static int countCharOccurrences(String text, char targetChar) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (c == targetChar) {
                count++;
            }
        }
        return count;
    }
}
