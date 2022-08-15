package com.lapzupi.dev.configurablemenus.menu.model;

import java.util.List;

/**
 * @author sarhatabaot
 */
public class PaginatedMenuItem extends MenuItem{
    private PaginatedItemType paginatedItemType;

    public PaginatedMenuItem(final int row, final int column, final ItemSettings settings, final List<Duplicate> duplicate, final List<String> onLeftClick, final List<String> onShiftClick, final List<String> onRightClick) {
        super(row, column, settings, duplicate, onLeftClick, onShiftClick, onRightClick);
    }

    public enum PaginatedItemType {
        NORMAL,
        PREVIOUS,
        NEXT
    }
}
