
/*
    Write a JAVA Program to implement a segment tree with its operations In Hyderabad after
a long pandemic gap, the Telangana Youth festival Is Organized at HITEX.
In HITEX, there are a lot of programs planned. During the festival in order to maintain the rules of
Pandemic, they put a constraint that one person can only attend any one of the programs in one day
according to planned days. Now it’s your aim to implement the "Solution" class in such a way that you
need to return the maximum number of programs you can attend according to given constraints.
Explanation: You have a list of programs ‘p’ and days ’d’, where you can attend only one program on
one day. Programs [p] = [first day, last day], p is the program's first day and the last day.
Input Format:
Line-1: An integer N, number of programs.
Line-2: N comma separated pairs, each pair(f_day, l_day) is separated by space.
 */
import java.util.*;

class SegmentTree {
    int[] segTree;
    int size;

    public SegmentTree(int maxDay) {
        this.size = maxDay;
        segTree = new int[4 * size];

        buildTree(0, 1, maxDay);
    }

    private void buildTree(int i, int l, int r) {
        if (l == r) {
            segTree[i] = l;
            return;
        }
        int mid = l + (r - l) / 2;
        buildTree(2 * i + 1, l, mid);
        buildTree(2 * i + 2, mid + 1, r);

        segTree[i] = Math.min(segTree[2 * i + 1], segTree[2 * i + 2]);
    }

    public void update(int idx, int i, int l, int r) { // we should update index idx with maximum value saying used
        if (l == r) {
            segTree[i] = Integer.MAX_VALUE;
            return;
        }

        int mid = l + (r - l) / 2;

        if (idx <= mid) {
            update(idx, 2 * i + 1, l, mid);
        } else {
            update(idx, 2 * i + 2, mid + 1, r);
        }

        segTree[i] = Math.min(segTree[2 * i + 1], segTree[2 * i + 2]);

    }

    public int getMinInRange(int i, int l, int r, int start, int end) {
        if (l > end || r < start) {
            return Integer.MAX_VALUE;
        }

        else if (l >= start && r <= end) {
            return segTree[i];
        }

        int mid = l + (r - l) / 2;
        return Math.min(getMinInRange(2 * i + 1, l, mid, start, end), getMinInRange(2 * i + 2, mid + 1, r, start, end));
    }

    public int canAttend(int start, int end) {
        int day = getMinInRange(0, 1, size, start, end);
        if (day > end)
            return -1; // not possible to attend this event

        // since we are attending this event mark it as attended with a very high value
        update(day, 0, 1, size);

        return day;
    }
}

public class P4_SegmentTree {
    private static int maxEvents(int[][] events) {
        if (events == null || events.length == 0)
            return 0;

        // sort by end date
        Arrays.sort(events, (a, b) -> {
            if (a[1] == b[1]) {
                return a[0] - b[0];
            }

            return a[1] - b[1];
        });

        int maxDay = 0;
        for (int[] event : events)
            maxDay = Math.max(maxDay, event[1]);

        SegmentTree segTree = new SegmentTree(maxDay);
        int count = 0;

        for (int[] event : events) {
            if (segTree.canAttend(event[0], event[1]) != -1) {
                count++;
            }
        }

        return count;

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();
        String[] str = sc.nextLine().split(",");

        int[][] events = new int[n][2];
        for (int i = 0; i < n; i++) {
            String[] val = str[i].trim().split(" ");
            events[i][0] = Integer.parseInt(val[0]);
            events[i][1] = Integer.parseInt(val[1]);
        }

        System.out.println(maxEvents(events));

        sc.close();
    }
}