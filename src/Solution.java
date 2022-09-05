import java.util.ArrayList;
import java.util.HashMap;

class Solution {

    long FAC = 10000001;
    long MAX = Long.MAX_VALUE;

    int max(int a, int b) {
        int res = 0;

        if(a > b) res = a;
        else res = b;

        return res;
    }

    int numPoints(ArrayList<ArrayList<Integer>> list) {

        int res = 2;

        int numElems = list.size();

        for(int i = 0; i < numElems; ++i) {
            boolean found = true;
            int target = list.get(i).get(1);
            int resLoc = 2;
            while(found) {
                found = false;
                for(int j = 0; j < numElems; ++j) {
                    if(i != j && list.get(j).get(0) == target) {
                        resLoc++;
                        res = max(res, resLoc);
                        target = list.get(j).get(1);
                        found = true;
                    }
                }
            }
        }

        return res;
    }

    public int maxPoints(int[][] points) {

        int res = 0;

        HashMap<Long, ArrayList<ArrayList<Integer>>> hash = new HashMap<>();

        int m = points.length;

        if(m <= 2) {
            return m;
        }

        // Store slopes of all pairs of points for hashing
        for(int i = 0; i < m; ++i) {
            int[] v1 = points[i];
            for(int j = i + 1; j < m; ++j) {
                int[] v2 = points[j];

                ArrayList<Integer> pointsPair = new ArrayList<>();
                ArrayList<Integer> vec1 = new ArrayList<>();

                pointsPair.add(i);
                pointsPair.add(j);

                long slope = 0;

                double[] v1D = new double[2];
                double[] v2D = new double[2];

                v1D[0] = (double) v1[0];
                v1D[1] = (double) v1[1];
                v2D[0] = (double) v2[0];
                v2D[1] = (double) v2[1];

                // Compute slope
                if(v2[0] == v1[0]) {
                    slope = MAX;
                }
                if(v2[0] != v1[0]) {
                    slope = (long) Math.floor(FAC * (v2D[1] - v1D[1]) / (v2D[0] - v1D[0]));
                }

                // Store all pairs of points with the same slope in map
                ArrayList<ArrayList<Integer>> listLoc = hash.get(slope);

                if(listLoc == null) {
                    listLoc = new ArrayList<>();
                    listLoc.add(pointsPair);
                    hash.put(slope, listLoc);
                }
                else {
                    listLoc.add(pointsPair);
                    hash.replace(slope, listLoc);
                }
            }
        }

        // Compute max number of points on a line
        for(int i = 0; i < m; ++i) {
            int[] v1 = points[i];
            for(int j = i + 1; j < m; ++j) {
                int[] v2 = points[j];

                double[] v1D = new double[2];
                double[] v2D = new double[2];

                v1D[0] = (double) v1[0];
                v1D[1] = (double) v1[1];
                v2D[0] = (double) v2[0];
                v2D[1] = (double) v2[1];

                long slope = 0;

                if(v2[0] == v1[0]) {
                    slope = MAX;
                }
                if(v2[0] != v1[0]) {
                    slope = (long) Math.floor(FAC * (v2D[1] - v1D[1]) / (v2D[0] - v1D[0]));
                }

                ArrayList<ArrayList<Integer>> listLoc = hash.get(slope);

                // Compute max number of points on line with the same slope
                res = max(res, numPoints(listLoc));
            }
        }

        return res;
    }
}