import java.util.*;
import java.util.concurrent.ExecutionException;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new Hashtable<>();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final int countThreads = 1000;
        for (int i = 0; i < countThreads; i++) {
            Runnable task = () -> {
                String str = generateRoute("RLRFR", 100);
                char[] characters = str.toCharArray();
                int countR = 0;
                for (char character : characters) {
                    if (character == 'R') {
                        countR++;
                    }
                }
                if (sizeToFreq.containsKey(countR)) {
                    int freq = sizeToFreq.get(countR);
                    int freqNew = freq + 1;
                    sizeToFreq.put(countR, freqNew);
                } else {
                    sizeToFreq.put(countR, 1);
                }
            };
            Thread thread = new Thread(task);
            thread.start();
            thread.join();

        }
        printResult(sizeToFreq);
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    /**
     * Метод возвращает максимальную частоту повторений и количесвто повторений при максимальной частоте повторений :)
     *
     * @param sizeToFreq - набор значений: key - количество повторений, value - частота повторений
     * @return возвращает массив, содержащий два значения:
     * - array[0]-количество повторений при максимальной частоте;
     * - array[1]-максимальная частота повторений.
     */
    private static int[] findMaxFreq(Map<Integer, Integer> sizeToFreq) {

        int[] array = new int[2];
        Set<Map.Entry<Integer, Integer>> entrySet = sizeToFreq.entrySet();
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            if (entry.getValue() > array[1]) {
                array[0] = entry.getKey();
                array[1] = entry.getValue();
            }
        }
        return array;
    }

    /**
     * Выводит в консоль результат
     *
     * @param sizeToFreq - карта с частотой и количеством повторений символа 'R'
     */
    private static void printResult(Map<Integer, Integer> sizeToFreq) {
        int[] array = findMaxFreq(sizeToFreq);
        Set<Map.Entry<Integer, Integer>> entrySet = sizeToFreq.entrySet();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Самое частое количество повторений ")
                .append(array[0])
                .append(" (встретилось ")
                .append(array[1] + " раз)")
                .append("\nДругие размеры:\n");
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            if (!entry.getKey().equals(array[0])) {
                stringBuffer.append("- ").append(entry.getKey()).append(" (").append(entry.getValue()).append(" раз)\n");
            }
        }
        System.out.println(stringBuffer);
    }


}
