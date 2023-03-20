package com.innowise;

import java.util.HashMap;
import java.util.Map;

public class Intervals {

    private static final Map<String, int[]> intervals = intervalMap();

    private static final Map<String, Integer> noteToOrder = noteOrderMap();

    private static final Map<Integer, String> orderToNote = orderNoteMap();

    private static final Map<Integer, String> semitoneToInterval = semitoneIntervalMap();

    private static final int F_NOTE_ORDER = noteToOrder.get("F");

    private static final int C_NOTE_ORDER = noteToOrder.get("C");

    private static final int NOTES = 7;


    public static String intervalConstruction(String[] args) {
        int semitone = 0;
        int[] intervalInfo = intervals.get(args[0]);
        var naturalOrder = isNaturalOrder(args);

        semitone += smoothStartNote(args[1], naturalOrder);

        var startNote = getNote(args[1]);
        var endNote = getNote(startNote, intervalInfo, naturalOrder);

        semitone += semitonesInDistance(intervalInfo);
        semitone -= absentAccidentals(startNote, endNote, naturalOrder);

        return unsmoothEndNote(endNote, semitone, intervalInfo, naturalOrder);
    }


    public static String intervalIdentification(String[] args) {
        int semitone = 0;
        var naturalOrder = isNaturalOrder(args);

        semitone += smoothStartNote(args[0], naturalOrder);
        semitone += smoothEndNote(args[1], naturalOrder);

        var startNote = getNote(args[0]);
        var endNote = getNote(args[1]);

        semitone += semitonesInDistance(distanceBetweenNotes(startNote, endNote, naturalOrder));
        semitone -= absentAccidentals(startNote, endNote, naturalOrder);

        return interval(semitone);
    }


    private static int distanceBetweenNotes(String startNote, String endNote, boolean naturalOrder) {
        int distance = 1;

        int headInd = noteToOrder.get(startNote);
        int tailInd = noteToOrder.get(endNote);

        if (!naturalOrder) {
            int tmp = headInd;
            headInd = tailInd;
            tailInd = tmp;
        }

        distance += headInd < tailInd ? tailInd - headInd : NOTES + tailInd - headInd;

        return distance;
    }


    private static String interval(int semitone) {
        return semitoneToInterval.get(semitone);
    }


    private static String getNote(String note) {
        return note.substring(0, 1);
    }


    private static String unsmoothEndNote(String note, int semitone, int[] intervalInfo, boolean naturalOrder) {
        int diff = intervalInfo[0] - semitone;

        if (naturalOrder) {
            return diff < 0 ? note.concat("b".repeat(Math.abs(diff))) : note.concat("#".repeat(diff));
        }

        return diff < 0 ? note.concat("#".repeat(Math.abs(diff))) : note.concat("b".repeat(diff));
    }


    private static int absentAccidentals(String startNote, String endNote, boolean naturalOrder) {
        int absent = 0;

        int headInd = noteToOrder.get(startNote);
        int tailInd = noteToOrder.get(endNote);

        if (!naturalOrder) {
            int tmp = headInd;
            headInd = tailInd;
            tailInd = tmp;
        }

        if ((headInd >= tailInd && tailInd >= F_NOTE_ORDER) ||
                (headInd < F_NOTE_ORDER && F_NOTE_ORDER <= tailInd)) {
            absent += 1;
        }

        if ((headInd >= C_NOTE_ORDER && headInd >= tailInd)) {
            absent += 1;
        }

        return absent;
    }


    private static int semitonesInDistance(int[] intervalInfo) {
        return (intervalInfo[1] - 1) * 2;
    }


    private static int semitonesInDistance(int distance) {
        return (distance - 1) * 2;
    }


    private static String getNote(String startNote, int[] intervalInfo, boolean naturalOrder) {
        int idx = noteToOrder.get(startNote) + intervalInfo[1] - 1;
        int ridx = noteToOrder.get(startNote) - intervalInfo[1] + 1;

        return orderToNote.get(naturalOrder ? (idx) % NOTES : ridx > 0 ? ridx : NOTES + ridx);
    }


    private static int smoothStartNote(String note, boolean order) {
        if (note.length() == 1) {
            return 0;
        }

        if (note.length() > 2) {
            if (order) {
                return note.contains("b") ? 2 : -2;
            }

            return note.contains("b") ? -2 : 2;
        }

        if (order) {
            return note.contains("b") ? 1 : -1;
        }

        return note.contains("b") ? -1 : 1;
    }


    private static int smoothEndNote(String note, boolean order) {
        if (note.length() == 1) {
            return 0;
        }

        if (note.length() > 2) {
            if (order) {
                return note.contains("b") ? -2 : 2;
            }

            return note.contains("b") ? 2 : -2;
        }

        if (order) {
            return note.contains("b") ? -1 : 1;
        }

        return note.contains("b") ? 1 : -1;
    }


    private static boolean isNaturalOrder(String[] arr) {
        return (arr.length == 3 && arr[2].equals("asc")) || arr.length < 3;
    }


    private static Map<String, int[]> intervalMap() {
        Map<String, int[]> map = new HashMap<>();

        map.put("m2", new int[]{1, 2});
        map.put("M2", new int[]{2, 2});
        map.put("m3", new int[]{3, 3});
        map.put("M3", new int[]{4, 3});
        map.put("P4", new int[]{5, 4});
        map.put("P5", new int[]{7, 5});
        map.put("m6", new int[]{8, 6});
        map.put("M6", new int[]{9, 6});
        map.put("m7", new int[]{10, 7});
        map.put("M7", new int[]{11, 7});
        map.put("P8", new int[]{12, 8});

        return map;
    }


    private static Map<String, Integer> noteOrderMap() {
        Map<String, Integer> map = new HashMap<>();

        map.put("C", 0);
        map.put("D", 1);
        map.put("E", 2);
        map.put("F", 3);
        map.put("G", 4);
        map.put("A", 5);
        map.put("B", 6);

        return map;
    }


    private static Map<Integer, String> semitoneIntervalMap() {
        Map<Integer, String> some = new HashMap<>();


        some.put(1, "m2");
        some.put(2, "M2");
        some.put(3, "m3");
        some.put(4, "M3");
        some.put(5, "P4");
        some.put(7, "P5");
        some.put(8, "m6");
        some.put(9, "M6");
        some.put(10, "m7");
        some.put(11, "M7");
        some.put(12, "P8");

        return some;
    }


    private static Map<Integer, String> orderNoteMap() {
        Map<Integer, String> map = new HashMap<>();

        map.put(0, "C");
        map.put(1, "D");
        map.put(2, "E");
        map.put(3, "F");
        map.put(4, "G");
        map.put(5, "A");
        map.put(6, "B");

        return map;
    }
}
