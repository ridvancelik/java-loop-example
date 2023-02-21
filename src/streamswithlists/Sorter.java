package streamswithlists;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Sorter {
    public long sort(List<Element> elements) {
        List<Long> sumList = elements.stream()
                .map(e -> {
                    e.getNumbers().sort(Integer::compareTo);
                    return e;
                }).map(e -> new ElemWithAvg(e,
                        e.getNumbers().stream().mapToInt(Integer::intValue).sum() / e.getNumbers().size())
                ).map(e -> {
                    List<Integer> list = e.getElement().getNumbers().stream().distinct().collect(Collectors.toList());
                    e.getElement().setNumbers(list);
                    return e;
                }).map(e -> {
                    List<Integer> list = e.getElement().getNumbers().stream().filter(n -> n < e.getAverage()).collect(Collectors.toList());
                    e.getElement().setNumbers(list);
                    return e;
                }).map(e -> e.getElement().getNumbers().stream().mapToLong(Integer::longValue).sum())
                .collect(Collectors.toList());

        return sumList.stream().mapToLong(Long::longValue).sum() / sumList.size();
    }

    public List<Element> makeElements(int volume, int numbersVolume) {
        List<Element> elements = new ArrayList<>();
        for (int i = 0; i < volume; i++) {
            List<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < numbersVolume; j++) {
                numbers.add(ThreadLocalRandom.current().nextInt(10000));
            }
            elements.add(new Element(numbers));
        }
        return elements;
    }

    public long runSorting(int volume, int numbersVolume, int runs) {
        long start;
        long end;
        long[] runTimes = new long[runs];
        for (int i = 0; i < runs; i++) {
            System.out.printf("Run %d\n", i + 1);
            List<Element> elements = this.makeElements(volume, numbersVolume);
            start = System.nanoTime();
            this.sort(elements);
            end = System.nanoTime();
            runTimes[i] = end - start;
            System.out.printf("Run time %d\n", runTimes[i]);
        }
        long sum = 0;
        for (int i = 0; i < runs; i++) {
            sum += runTimes[i];
        }
        return sum / runs;
    }
}