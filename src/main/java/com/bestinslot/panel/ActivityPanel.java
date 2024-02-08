package com.bestinslot.panel;

import com.bestinslot.osrswiki.WikiScraper;

import javax.swing.*;
import java.awt.*;

public class ActivityPanel extends JPanel
{

    JLabel label;
    BestInSlotPanel bestInSlotPanel;

    public ActivityPanel(BestInSlotPanel bestInSlotPanel)
    {
        super();

        label = new JLabel("test");
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.CENTER);

        this.bestInSlotPanel = bestInSlotPanel;
    }

    public void updateOverviewPanel(String bossName)
    {
        label.setText(bossName);
        label.repaint();
        label.revalidate();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bestInSlotPanel.removeQuest();
                System.out.println("licked the doorknob");
                WikiScraper.GetEquipmentByBossName(bossName);
            }

        });

    }



}
