package utils;

import java.util.function.Function;

public class Grid<T> {
    private final T[][] grid;
    private final int width;
    private final int height;

    public Grid(int width, int height) {
        grid = (T[][]) new Object[width][height];
        this.width = width;
        this.height = height;
    }

    public T[][] getGrid() {
        return grid;
    }

    public T get(int x, int y) {
        return grid[y][x];
    }

    public void set(int x, int y, T v) {
        grid[y][x] = v;
    }

    public Grid<T> fill(GridProvider<T> provider) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                set(x, y, provider.provide(x, y));
            }
        }
        return this;
    }
}