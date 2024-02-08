package com.bestinslot.panel;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BestInSlotSelectPanel extends JPanel{
    public BestInSlotSelectPanel(BestInSlotPanel bestInSlotPanel, String text) {
        setLayout(new BorderLayout(3, 0));
        setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH, 23));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bestInSlotPanel.addQuest(text);
                setBackground(ColorScheme.DARK_GRAY_COLOR);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                setBackground(ColorScheme.DARK_GRAY_HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                setBackground(ColorScheme.DARK_GRAY_COLOR);
            }
        });
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.CENTER);
    }
}
