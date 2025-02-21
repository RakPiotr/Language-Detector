import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CheckLanguage {

    public static String fromFile(String filePath) {
        // Analyze known language profiles
        Map<Character, Double> polishProfile = analyzeProfile("PL.txt");
        Map<Character, Double> spanishProfile = analyzeProfile("ES.txt");
        Map<Character, Double> englishProfile = analyzeProfile("EN.txt");

        // Calculate the profile of the given file
        Map<Character, Double> fileProfile = analyzeProfile(filePath);

        // Calculate distances
        double distancePolish = calculateDistance(polishProfile, fileProfile);
        double distanceSpanish = calculateDistance(spanishProfile, fileProfile);
        double distanceEnglish = calculateDistance(englishProfile, fileProfile);

        // Determine the closest language
        if (distancePolish <= distanceSpanish && distancePolish <= distanceEnglish) {
            return "Polish";
        } else if (distanceSpanish <= distancePolish && distanceSpanish <= distanceEnglish) {
            return "Spanish";
        } else {
            return "English";
        }
    }

    /**
     * Reads the content of a file and returns it as a string.
     *
     * @param filePath The path to the file as a string.
     * @return The content of the file, or an error message if an exception occurs.
     */
    public static String readFile(String filePath) {
        try {
            Path path = Path.of(filePath);
            return Files.readString(path);
        } catch (Exception e) {
            return "Error reading the file: " + e.getMessage();
        }
    }

    /**
     * Analyzes a file to calculate the normalized letter frequency profile.
     *
     * @param filePath The path to the file.
     * @return A map of character frequencies.
     */
    private static Map<Character, Double> analyzeProfile(String filePath) {
        String content = readFile(filePath).toLowerCase();
        Map<Character, Integer> counts = new HashMap<>();
        int totalLetters = 0;

        // Count letter occurrences
        for (char c : content.toCharArray()) {
            if (Character.isLetter(c)) {
                counts.put(c, counts.getOrDefault(c, 0) + 1);
                totalLetters++;
            }
        }

        // Normalize frequencies
        Map<Character, Double> frequencies = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            frequencies.put(entry.getKey(), entry.getValue() / (double) totalLetters);
        }

        return frequencies;
    }

    /**
     * Calculates the distance between two letter frequency profiles using a modified Pythagorean equation.
     *
     * @param profile1 The first profile.
     * @param profile2 The second profile.
     * @return The distance.
     */
    private static double calculateDistance(Map<Character, Double> profile1, Map<Character, Double> profile2) {
        double sum = 0.0;

        // Iterate over all letters (a-z)
        for (char c = 'a'; c <= 'z'; c++) {
            double freq1 = profile1.getOrDefault(c, 0.0);
            double freq2 = profile2.getOrDefault(c, 0.0);
            sum += Math.pow(freq1 - freq2, 2);
        }

        return Math.sqrt(sum);
    }

}