import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Виберіть завдання для виконання:");
        System.out.println("1. Визначення випадкових подій з заданими ймовірностями");
        System.out.println("2. Моделювання випадкової величини з заданим законом розподілу");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                performEventSimulation();
                break;
            case 2:
                performRandomVariableSimulation();
                break;
            default:
                System.out.println("Невірний вибір");
        }
        scanner.close();
    }

    private static void performEventSimulation() {
        // Визначаємо події та їх ймовірності
        Map<String, Double> events = new HashMap<>();
        events.put("Event A", 0.5);
        events.put("Event B", 0.3);
        events.put("Event C", 0.2);

        // Моделювання
        int simulations = 100000;
        Map<String, Integer> results = simulateEvents(events, simulations);

        // Виведення результатів
        for (Map.Entry<String, Integer> entry : results.entrySet()) {
            System.out.printf("%s: %.2f%%%n", entry.getKey(), (entry.getValue() / (double) simulations) * 100);
        }
    }

    private static Map<String, Integer> simulateEvents(Map<String, Double> events, int simulations) {
        Map<String, Integer> results = new HashMap<>();
        double[] cumulativeProbabilities = new double[events.size()];
        String[] eventNames = new String[events.size()];

        int index = 0;
        double cumulative = 0.0;
        for (Map.Entry<String, Double> entry : events.entrySet()) {
            cumulative += entry.getValue();
            cumulativeProbabilities[index] = cumulative;
            eventNames[index] = entry.getKey();
            results.put(entry.getKey(), 0);
            index++;
        }

        for (int i = 0; i < simulations; i++) {
            double randomValue = random.nextDouble();
            for (int j = 0; j < cumulativeProbabilities.length; j++) {
                if (randomValue < cumulativeProbabilities[j]) {
                    results.put(eventNames[j], results.get(eventNames[j]) + 1);
                    break;
                }
            }
        }

        return results;
    }

    private static void performRandomVariableSimulation() {
        // Визначаємо параметри розподілу
        double mean = 0.0;
        double stdDev = 1.0;

        // Моделювання
        int simulations = 100000;
        double[] values = simulateNormalDistribution(mean, stdDev, simulations);

        // Визначення статистичних характеристик
        double calculatedMean = calculateMean(values);
        double calculatedVariance = calculateVariance(values, calculatedMean);

        // Виведення результатів
        System.out.printf("Середнє значення: %.4f%n", calculatedMean);
        System.out.printf("Дисперсія: %.4f%n", calculatedVariance);
    }

    private static double[] simulateNormalDistribution(double mean, double stdDev, int simulations) {
        double[] values = new double[simulations];
        for (int i = 0; i < simulations; i++) {
            values[i] = mean + stdDev * random.nextGaussian();
        }
        return values;
    }

    private static double calculateMean(double[] values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    private static double calculateVariance(double[] values, double mean) {
        double sum = 0.0;
        for (double value : values) {
            sum += Math.pow(value - mean, 2);
        }
        return sum / values.length;
    }
}
