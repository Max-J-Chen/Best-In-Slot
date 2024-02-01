package com.bestinslot.panel;

import com.bestinslot.osrswiki.WikiScraper;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.SwingUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class QuestOverviewPanel extends JPanel
{
    public QuestOverviewPanel(BestInSlotPanel bestInSlotPanel)
    {
        super();
        JLabel label = new JLabel("test");
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.CENTER);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                bestInSlotPanel.removeQuest();
                System.out.println("licked the doorknob");
                WikiScraper.GetEquipmentByBossName("Skotizo");

            }

        });


    }


}
