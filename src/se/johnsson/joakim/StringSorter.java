package se.johnsson.joakim;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StringSorter {

    private List<String> strings;

    public List<String> getStrings() {
        return strings;
    }

    void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public List<String> naturalSort() {
        Collections.sort(strings);
        return strings;
    }

    public List<String> naturalSortWithStreams() {
        return strings.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> lengthSort() {
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.length() - s2.length();
            }
        });
        return strings;
    }

    public List<String> lengthSortLambda() {
        // Same as above - but with Lambda - works like this:
        // <arguments to the compare method> -> <body of the compare method>
        Collections.sort(strings, (s1, s2) -> s1.length() - s2.length());
        return strings;
    }

    public List<String> lengthSortLambdaWithListSort() {
        strings.sort((s1, s2) -> s1.length() - s2.length());
        return strings;
    }

    public List<String> lengthSortLambdaWithListSortAndComparingInt() {
        strings.sort(Comparator.comparingInt(String::length));
        return strings;
    }

    public List<String> lengthSortWithStreams() {
        // The list (collection) is converted into a stream()
        // A stream takes each value of the collection one by one
        // And uses each to filter, or convert back into the collection if they are needed
        return strings.stream()
                // sorted() of Stream class produces another stream and compares it
                // It takes a Comparator as argument
                // This Comparator is a Lambda expression
                .sorted((s1, s2) -> Integer.compare(s1.length(), s2.length()))
        .collect(Collectors.toList());
    }
}
