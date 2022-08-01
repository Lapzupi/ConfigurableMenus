package com.lapzupi.dev.configurablemenus.menu.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author sarhatabaot
 */
public record Duplicate(int row, int rangeMin, int rangeMax) {
    public static @NotNull Duplicate fromString(final @NotNull String duplicate) {
        final String[] array = duplicate.split(":");
        final int row = Integer.parseInt(array[0]);
        final int min = Integer.parseInt(array[1].split("-")[0]);
        final int max = Integer.parseInt(array[1].split("-")[1]);
        return new Duplicate(row,min,max);
    }

    public static boolean isDuplicateString(final @NotNull String duplicate) {
        if(duplicate.isEmpty())
            return false;
        return duplicate.contains(":") && duplicate.contains("-");
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "Duplicate{" +
                "row=" + row +
                ", rangeMin=" + rangeMin +
                ", rangeMax=" + rangeMax +
                '}';
    }
}
