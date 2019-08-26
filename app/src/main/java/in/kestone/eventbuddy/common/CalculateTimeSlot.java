package in.kestone.eventbuddy.common;

import java.util.ArrayList;

public class CalculateTimeSlot {
    public static ArrayList<String> timeSlot(int to, int from) {
        String time = "", hr = "", min = "";
        ArrayList<String> listTime = new ArrayList<>();
        ArrayList<String> listTime2 = new ArrayList<>();
        int slot = 30, tmp;
        for (int i = to; i <= from; i++) {
            for (int j = 0; j < 60 / slot; j++) {
                hr = i < 10 ? "0" + i : String.valueOf(i);
                min = j * slot < 10 ? "0" + j * slot : String.valueOf((j * slot));
                time = hr + ":" + min;
                listTime.add(time);
            }
        }
        tmp = 0;
        for (int n = 0; n < listTime.size() - 2; n++) {
            if (tmp == n) {
                listTime2.add(listTime.get(n)+" - " + listTime.get(n + 1));
                tmp = n + 1;
            }
        }
        return listTime2;
    }

    public static ArrayList<String> timeSlotAmPm(int to, int from) {
        String time = "", hr = "", min = "";
        ArrayList<String> listTime = new ArrayList<>();
        ArrayList<String> listTime2 = new ArrayList<>();
        int slot = 30, tmp;
        for (int i = to; i <= from; i++) {
            for (int j = 0; j < 60 / slot; j++) {
                if (i >= 12) {
                    if (i == 12) {
                        hr = String.valueOf(i);
                    } else {
                        hr = i - 12 < 10 ? "0".concat(String.valueOf(i - 12)) : String.valueOf(i);
                    }
                    min =
                            j * slot < 10 ? "0".concat(String.valueOf(j * slot)) : String.valueOf((j * slot));
                    time = hr.concat(":").concat(min).concat(" PM");
                } else {
                    hr = i < 10 ? "0".concat(String.valueOf(i)) : String.valueOf(i);
                    min =
                            j * slot < 10 ? "0".concat(String.valueOf(j * slot)) : String.valueOf((j * slot));
                    time = hr.concat(":").concat(min).concat(" AM");
                }
                listTime.add(time);
            }
        }
        tmp = 0;
        for (int n = 0; n < listTime.size() - 2; n++) {
            if (tmp == n) {
                listTime2.add(listTime.get(n));
                tmp = n + 1;
            }
        }
        return listTime2;
    }
}
