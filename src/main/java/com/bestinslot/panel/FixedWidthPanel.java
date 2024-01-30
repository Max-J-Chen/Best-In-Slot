package com.bestinslot.panel;

import net.runelite.client.ui.PluginPanel;
import java.awt.Dimension;
import javax.swing.JPanel;

public class FixedWidthPanel extends JPanel
{
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(PluginPanel.PANEL_WIDTH, super.getPreferredSize().height);
    }

}
