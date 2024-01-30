package com.bestinslot.panel;

import java.awt.*;
import java.util.function.Function;

/**
 * Grid layout implementation with support for cells with unequal size.
 * Optionally supports padding inbetween cells. This padding is only applied to visible cells, just like
 * padding in an HTML table would.
 */
public class DynamicPaddedGridLayout extends GridLayout
{
    public DynamicPaddedGridLayout(int rows, int cols, int horizontalPadding, int verticalPadding)
    {
        super(rows, cols, horizontalPadding, verticalPadding);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent)
    {
        synchronized (parent.getTreeLock())
        {
            return calculateSize(parent, Component::getPreferredSize);
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent)
    {
        synchronized (parent.getTreeLock())
        {
            return calculateSize(parent, Component::getMinimumSize);
        }
    }

    @Override
    public void layoutContainer(Container parent)
    {
        synchronized (parent.getTreeLock())
        {
            final Insets insets = parent.getInsets();
            final int numComponents = parent.getComponentCount();
            int numRows = getRows();
            int numColumns = getColumns();

            if (numComponents == 0)
            {
                return;
            }

            if (numRows > 0)
            {
                numColumns = (numComponents + numRows - 1) / numRows;
            }
            else
            {
                numRows = (numComponents + numColumns - 1) / numColumns;
            }

            final int horizontalPadding = getHgap();
            final int verticalPadding = getVgap();

            // scaling factors
            final Dimension pd = preferredLayoutSize(parent);
            final Insets parentInsets = parent.getInsets();
            int horizontalBorder = parentInsets.left + parentInsets.right;
            int verticalBorder = parentInsets.top + parentInsets.bottom;
            final double sw = (1.0 * parent.getWidth() - horizontalBorder) / (pd.width - horizontalBorder);
            final double sh = (1.0 * parent.getHeight() - verticalBorder) / (pd.height - verticalBorder);

            final int[] w = new int[numColumns];
            final int[] h = new int[numRows];

            // calculate dimensions for all components + apply scaling
            for (int i = 0; i < numComponents; i++)
            {
                final int r = i / numColumns;
                final int c = i % numColumns;
                final Component comp = parent.getComponent(i);
                final Dimension d = comp.getPreferredSize();
                d.width = (int) (sw * d.width);
                d.height = (int) (sh * d.height);

                if (w[c] < d.width)
                {
                    w[c] = d.width;
                }

                if (h[r] < d.height)
                {
                    h[r] = d.height;
                }
            }

            // Apply new bounds to all child components
            for (int c = 0, x = insets.left; c < numColumns; c++)
            {
                int componentWidth = w[c];

                for (int r = 0, y = insets.top; r < numRows; r++)
                {
                    int i = r * numColumns + c;

                    int componentHeight = h[r];

                    if (i < numComponents)
                    {
                        parent.getComponent(i).setBounds(x, y, componentWidth, componentHeight);
                    }

                    if (componentHeight > 0)
                    {
                        y += componentHeight + verticalPadding;
                    }

                }

                if (componentWidth > 0)
                {
                    x += componentWidth + horizontalPadding;
                }
            }
        }
    }

    /**
     * Calculate outer size of the layout based on it's children and sizer
     *
     * @param parent parent component
     * @param sizer  functioning returning dimension of the child component
     * @return outer size
     */
    private Dimension calculateSize(final Container parent, final Function<Component, Dimension> sizer)
    {
        final int numComponents = parent.getComponentCount();
        int numRows = getRows();
        int numColumns = getColumns();
        int numVisibleRows = 0;
        int numVisibleColumns = 0;

        if (numRows > 0)
        {
            numColumns = (numComponents + numRows - 1) / numRows;
        }
        else
        {
            numRows = (numComponents + numColumns - 1) / numColumns;
        }

        final int[] w = new int[numColumns];
        final int[] h = new int[numRows];

        // Calculate dimensions for all components
        for (int i = 0; i < numComponents; i++)
        {
            final int r = i / numColumns;
            final int c = i % numColumns;
            final Component comp = parent.getComponent(i);
            final Dimension d = sizer.apply(comp);

            if (w[c] < d.width)
            {
                w[c] = d.width;
            }

            if (h[r] < d.height)
            {
                h[r] = d.height;
            }
        }

        // Calculate total width and height of the layout
        int totalWidth = 0;
        int totalHeight = 0;

        for (int j = 0; j < numColumns; j++)
        {
            int columnWidth = w[j];
            if (columnWidth > 0)
            {
                totalWidth += columnWidth;
                numVisibleColumns += 1;
            }
        }


        for (int i = 0; i < numRows; i++)
        {
            int rowHeight = h[i];
            if (rowHeight > 0)
            {
                totalHeight += rowHeight;
                numVisibleRows += 1;
            }
        }

        final Insets insets = parent.getInsets();

        // Apply insets and horizontal and vertical padding to layout, accounting only for the visible columns & rows
        return new Dimension(insets.left + insets.right + totalWidth + (numVisibleColumns - 1) * getHgap(), insets.top + insets.bottom + totalHeight + (numVisibleRows - 1) * getVgap());
    }

}