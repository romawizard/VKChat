package ru.roma.vkchart.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.roma.vkchart.presenters.CallbackPagination;

/**
 * Created by Ilan on 06.03.2018.
 */

public class Pagination<T> {

    private static int MAX_LIMIT;
    private int offset = 0;
    private boolean firstload = true;
    private boolean success = true;
    private List<T> cash;



    public Pagination(int capasityLoad) {
        MAX_LIMIT = capasityLoad;
        cash = new ArrayList<T>();

    }


    public synchronized  List<T> next(List<T> data ) {

        if (firstload) {
            cash.addAll(data);
            firstload = false;
            success = true;
            offset = (cash.size() - 1);

            return cash;
        }

        int crossing = 0;
        for (T t : data) {
            if (cash.contains(t)) {
                crossing++;
            }
        }
        if (crossing > 0 && crossing < MAX_LIMIT) {
            Iterator<T> iterator = data.iterator();
            while (iterator.hasNext()) {
                T t = iterator.next();
                if (cash.contains(t)) {
                    iterator.remove();
                }
            }
            cash.addAll(data);
            offset = cash.size() -1;

        } else {
            MyLog.log("else in pagination");
            offset = 0;
            cash.clear();
            success = false;
            firstload = true;
        }
        return cash;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getOffset() {
        return  offset;
    }

}
