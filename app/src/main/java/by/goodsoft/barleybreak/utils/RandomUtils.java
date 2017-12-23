package by.goodsoft.barleybreak.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Aleksandr Shvets
 * on 02.12.2017.
 */

public class RandomUtils {

    public static List<Integer> getRandomList(int length) {
        List<Integer> randomList = new ArrayList<>();
        for (int i = 0; i < length - 1; i++) {
            randomList.add(i);
        }
        Collections.shuffle(randomList);
        randomList.add(length - 1);
        if (!isPossibleToSolve(randomList)) {
            int temp = randomList.get(length - 2);
            randomList.set(length - 2, randomList.get(length - 3));
            randomList.set(length - 3, temp);
        }
        return randomList;
    }

    private static boolean isPossibleToSolve(List<Integer> randomList) {
        int inversions = 0;
        for (int i = 0; i < randomList.size(); i++) {
            for (int j = i; j < randomList.size(); j++) {
                if (randomList.get(i) > randomList.get(j)) {
                    inversions++;
                }
            }
        }
        // если количество инверсий четное, то решение существует
        return inversions % 2 == 0;
    }
}
