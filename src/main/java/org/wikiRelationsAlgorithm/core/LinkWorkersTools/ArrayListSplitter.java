package org.wikiRelationsAlgorithm.core.LinkWorkersTools;

import java.util.ArrayList;
import java.util.List;

public class ArrayListSplitter {
    public static ArrayList<ArrayList<String>> split(int numberOfLists, ArrayList<String> arrayList) {
        ArrayList<ArrayList<String>> arrayLists = new ArrayList<>();
        int arrayListRemainder = arrayList.size() % numberOfLists;
        int arrayLength = arrayList.size() / numberOfLists;
        for (int i = 0; i < numberOfLists; i++) {
            int firstIndex = i * arrayLength;
            int secondIndex = (i + 1) * arrayLength;
            if (i+1 == numberOfLists) {
                secondIndex += arrayListRemainder;
            }
            List<String> list = arrayList.subList(firstIndex, secondIndex);
            ArrayList<String> stringArrayList = new ArrayList<>(list);
            arrayLists.add(stringArrayList);
        }
        return arrayLists;
    }
}