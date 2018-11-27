/**
 * Created by Classdumper, User Peter Heusch
 * Creation Date: 06.11.2018 22:24:25
 */

package matchplanner;

import java.util.ArrayList;

public class League {

    private int lower[];
    private int upper[];
    private int head;
    private int tail;
    private int teams;
    private int day;

    public League(int i) {
        int[] all = new int[i]; //1 2 3 4

        for (int j = 0; j < i; j++) {
            all[j] = j;
        }

        head = all[i - 1];
        tail = (i / 2) - 1;

        upper = new int[(i / 2) - 1];
        for (int j = 0; j < ((i / 2) - 1); j++) {
            upper[j] = all[j];
        }

        ArrayList<Integer> list = new ArrayList<>();

        for (int j = i - 2; j >= i / 2; j--) {
            list.add(all[j]);
        }
        lower = list.stream().mapToInt(x -> x).toArray();

        teams = i;
        day = 0;

    }

    public void shift() {
        day++;

        if (upper[upper.length - 1] < this.tail && lower[lower.length - 1] > this.tail) {
            int tail_old = this.tail;
            this.tail = upper[0];
            for (int i = 0; i < upper.length - 1; i++) {
                upper[i] = upper[i + 1];
            }
            upper[upper.length - 1] = tail_old;

        } else {
            int tail_old = this.tail;
            this.tail = lower[lower.length - 1];
            int[] temp = lower.clone();
            for (int i = 0; i < lower.length - 1; i++) {
                temp[i+1] = lower[i];
            }
            temp[0] = tail_old;
            
            lower = temp;
        }

    }

    public int getLower(int t) {
        return lower[t];
    }

    public int[] getLower() {
        return lower.clone();
    }

    public int getUpper(int t) {
        return upper[t];
    }

    public int[] getUpper() {
        return upper.clone();
    }

    public int getDay() {
        return day;
    }

    public Match getMatch(int match) {
        return Match.getMatch(this, match);
    }

    public int getHead() {
        return head;
    }

    public int getTeams() {
        return teams;
    }

    public int getTail() {
        return tail;
    }
}
