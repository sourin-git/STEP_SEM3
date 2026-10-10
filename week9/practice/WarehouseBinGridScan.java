public class WarehouseBinGridScan {
    static class Summary {
        int total;
        int row;
        int col;

        Summary(int total, int row, int col) {
            this.total = total;
            this.row = row;
            this.col = col;
        }
    }

    public static Summary warehouseSummary(int[][] grid) {
        int total = 0;
        int maxValue = Integer.MIN_VALUE;
        int maxRow = 0;
        int maxCol = 0;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                int value = grid[row][col];
                total += value;

                if (value > maxValue) {
                    maxValue = value;
                    maxRow = row;
                    maxCol = col;
                }
            }
        }

        return new Summary(total, maxRow, maxCol);
    }

    public static void main(String[] args) {
        int[][] grid = {
            {4, 9, 2},
            {7, 1, 6},
            {3, 12, 5}
        };

        Summary result = warehouseSummary(grid);
        System.out.println("total = " + result.total + ", maxCoordinate = (" + result.row + ", " + result.col + ")");
    }
}
