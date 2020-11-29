package se.johnsson.joakim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    private static final List<String> TEST_STRINGS = Arrays.asList("this", "is", "a", "list", "of", "strings");
    private static boolean runAllDemos;

    public static void main(String[] args) {

        // Set to true to run all demos
        runAllDemos = false;

        // run collections with threads demo
        collectionsWithThreadsDemo(true);
        // run lazy streams demo
        lazyStreamsDemo(false);
        // run using collect demo
        usingCollectDemo(false);
        // run sort strings demo
        sortStringsDemo(false);
        // run summarizing demo
        summarizingDemo(false);
    }

//    private static void emptyDemo(boolean runDemo) {
//        if (runAllDemos || runDemo) {
//            String demoName = "demo";
//            printNameDivider(demoName);
//            printEndDivider(demoName);
//        }
//    }

    // Demos
    private static void collectionsWithThreadsDemo(boolean runDemo) {
        if (runAllDemos || runDemo) {
            String demoName = "collections with threads demo";
            printNameDivider(demoName);
            CollectionsDemo demo = new CollectionsDemo();

            long start = System.nanoTime();
            demo.printNums(demo.doubler);
            long end = System.nanoTime();
            System.out.println("Time taken (print | doubler): " + (end - start) / 1e9);
            System.out.println();

            start = System.nanoTime();
            demo.printNums(demo.doublerWithSleep100);
            end = System.nanoTime();
            System.out.println("Time taken (print | doubler with sleep 100): " + (end - start) / 1e9);
            System.out.println();

            start = System.nanoTime();
            demo.printNumsParallel(demo.doublerWithSleep100);
            end = System.nanoTime();
            System.out.println("Time taken (print parallel | doubler with sleep 100): " + (end - start) / 1e9);
            System.out.println();

            start = System.nanoTime();
            demo.printNumsParallel(demo.doubler);
            end = System.nanoTime();
            System.out.println("Time taken (print parallel | doubler): " + (end - start) / 1e9);
            System.out.println();

            start = System.nanoTime();
            demo.printNumsParallel(demo.doublerWithSleep1);
            end = System.nanoTime();
            System.out.println("Time taken (print parallel | doubler with sleep 1): " + (end - start) / 1e9);
            printEndDivider(demoName);
        }
    }

    private static void lazyStreamsDemo(boolean runDemo) {
        if (runAllDemos || runDemo) {
            String demoName = "lazy streams demo";
            printNameDivider(demoName);
            // find first double between 200 and 400 divisible by 3
            int firstDoubleDivBy3 = IntStream.range(100, 200)
                    // 101 numbers between 100 and 200
                    // starts with 100, multiplies it by 2 and checks it
                    // then next one until it finds the first
                    // it doesnt check ALL 101 numbers!
                    .map(LazyStreams::multByTwo)
                    .filter(n -> n % 3 == 0)
                    .findFirst()
                    .orElse(999);

            System.out.printf("First even divisible by 3 is %d%n", firstDoubleDivBy3);
            printEndDivider(demoName);
        }
    }

    private static void usingCollectDemo(boolean runDemo) {
        if (runAllDemos || runDemo) {
            String demoName = "using collect demo";
            printNameDivider(demoName);
            List<String> strings = TEST_STRINGS;
            // not what we want --> has side effects
            // lambda stuff needs to be final or effectively final - can't be modified
            // evenLengths is a reference - we don't modify the reference itself
            // we add stuff to the list it points to - this is a legal workaround, but not good practice
            List<String> evenLengths = new ArrayList<>();
            strings.stream()
                    .filter(s -> s.length() % 2 == 0)
                    .forEach(evenLengths::add);
            System.out.println("evenLengths");
            System.out.println(evenLengths);
            // we want this --> no side effects
            // a new list is created and returned - this is legal and ok
            List<String> evens = strings.stream()
                    .filter(s -> s.length() % 2 == 0)
                    .collect(Collectors.toList());
            System.out.println("evens");
            System.out.println(evens);
            printEndDivider(demoName);
        }
    }

    private static void sortStringsDemo(boolean runDemo) {
        if (runAllDemos || runDemo) {
            String demoName = "sort strings demo";
            printNameDivider(demoName);
            StringSorter ss = new StringSorter();
            List<String> strings = TEST_STRINGS;
            ss.setStrings(strings);
            System.out.println(strings);
            System.out.println("Sorted");
            System.out.println(ss.lengthSortWithStreams());
            printEndDivider(demoName);
        }
    }

    private static void summarizingDemo(boolean runDemo) {
        if (runAllDemos || runDemo) {
            String demoName = "summarizing demo";
            printNameDivider(demoName);
            DoubleSummaryStatistics stats = Stream.generate(Math::random)
                    .limit(1000)
                    // :: is <Class name>::<method name>
                    .collect(Collectors.summarizingDouble(Double::doubleValue));
            System.out.println(stats);
            System.out.println("max: " + stats.getMax());
            printEndDivider(demoName);
        }
    }

    // Helpers
    private static void printNameDivider(String demoName) {
        System.out.println();
        StringBuilder sb = new StringBuilder();
        sb.append(prefix(demoName));
        sb.append(demoName);
        sb.append(suffix(demoName, false));
        System.out.println(sb);
        System.out.println();
    }

    private static void printEndDivider(String demoName) {
        System.out.println();
        StringBuilder sb = new StringBuilder();
        sb.append(prefix(demoName));
        sb.append("end");
        sb.append(suffix(demoName, true));
        System.out.println(sb);
        printDots(demoName);
        printBars(demoName);
        System.out.println();
    }

    private static String prefix(String demoName) {
        int strLength = demoName.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strLength; i++) {
            sb.append("-");
        }
        return sb.toString() + "/ ";
    }

    private static String suffix(String demoName, boolean isEndSuffix) {
        int strLength = demoName.length();
        if (isEndSuffix) {
            strLength = strLength * 2 - 3;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strLength; i++) {
            sb.append("-");
        }
        return " /" + sb.toString();
    }

    private static void printDots(String demoName) {
        int strLength = demoName.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strLength + 2; i++) {
            sb.append(".  ");
        }
        System.out.println(sb.toString());
    }

    private static void printBars(String demoName) {
        int strLength = demoName.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strLength + 2; i++) {
            sb.append("|  ");
        }
        System.out.println(sb.toString());
    }
}
