package com.bestinslot.panel;

import com.bestinslot.osrswiki.WikiScraper;
import com.bestinslot.tools.Icon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicButtonUI;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;
import net.runelite.client.util.SwingUtil;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;


public class BestInSlotPanel extends PluginPanel
{

    //private static final ImageIcon DISCORD_ICON;
    private static final ImageIcon GITHUB_ICON;
    //private static final ImageIcon PATREON_ICON;

    private final IconTextField searchBar = new IconTextField();

    private final FixedWidthPanel selectableListPanel = new FixedWidthPanel();
    private final FixedWidthPanel listPanelWrapper = new FixedWidthPanel();

    private JPanel searchPanel;
    private final JScrollPane scrollPane;
    private final FixedWidthPanel loadoutOverviewWrapper = new FixedWidthPanel();
    private final ActivityPanel activityPanel;

    static
    {
        //DISCORD_ICON = Icon.DISCORD.getIcon(img -> ImageUtil.resizeImage(img, 16, 16));
        GITHUB_ICON = Icon.GITHUB.getIcon(img -> ImageUtil.resizeImage(img, 16, 16));
        //PATREON_ICON = Icon.PATREON.getIcon(img -> ImageUtil.resizeImage(img, 16, 16));
    }

    public BestInSlotPanel() {

        super(false);

        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new BorderLayout());

        /* Setup overview panel */
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.setLayout(new BorderLayout());

        JLabel title = new JLabel();
        title.setText("Best In Slot");
        title.setForeground(Color.WHITE);
        titlePanel.add(title, BorderLayout.WEST);

        // Options
        final JPanel viewControls = new JPanel(new GridLayout(1, 1, 10, 0));
        viewControls.setBackground(ColorScheme.DARK_GRAY_COLOR);

        // GitHub button
        JButton githubBtn = new JButton();
        SwingUtil.removeButtonDecorations(githubBtn);
        githubBtn.setIcon(GITHUB_ICON);
        githubBtn.setToolTipText("Report issues or contribute on GitHub");
        githubBtn.setBackground(ColorScheme.DARK_GRAY_COLOR);
        githubBtn.setUI(new BasicButtonUI());
        githubBtn.addActionListener((ev) -> LinkBrowser.browse("https://github.com/Max-J-Chen/Best-In-Slot"));
        githubBtn.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                githubBtn.setBackground(ColorScheme.DARK_GRAY_HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                githubBtn.setBackground(ColorScheme.DARK_GRAY_COLOR);
            }
        });
        viewControls.add(githubBtn);

        titlePanel.add(viewControls, BorderLayout.EAST);

        /* Search bar */
        searchBar.setIcon(IconTextField.Icon.SEARCH);
        searchBar.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 30));
        searchBar.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        searchBar.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);
        searchBar.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                onSearchBarChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                onSearchBarChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                onSearchBarChanged();
            }
        });

        searchPanel = new JPanel();
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        searchPanel.setLayout(new BorderLayout(0, BORDER_OFFSET));
        searchPanel.add(searchBar, BorderLayout.CENTER);

        /* Selectable Panels Container*/
        selectableListPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        selectableListPanel.setLayout(new DynamicPaddedGridLayout(0, 1, 0, 5));
        selectableListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        showMatchingQuests("");

        listPanelWrapper.setLayout(new BorderLayout());
        listPanelWrapper.add(selectableListPanel, BorderLayout.NORTH);

        scrollPane = new JScrollPane(listPanelWrapper);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        /* Adding Selectable Panels */
        List<String> bossNames = WikiScraper.ScrapeWikiForBosses();

        bossNames.forEach(name ->
                selectableListPanel.add(new BestInSlotSelectPanel(this, name, Collections.emptyList())));

        /* Boss tabs */
//        bossListPanel.setBorder(new EmptyBorder(8, 10, 0, 10));
//        bossListPanel.setLayout(new DynamicPaddedGridLayout(0, 1, 0, 5));
//        bossListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        showMatchingQuests("");


        JPanel introDetailsPanel = new JPanel();
        introDetailsPanel.setLayout(new BorderLayout());
        introDetailsPanel.add(titlePanel, BorderLayout.NORTH);
        introDetailsPanel.add(searchPanel, BorderLayout.CENTER);

        add(introDetailsPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);


        /* Layout */
        activityPanel = new ActivityPanel(this);

        loadoutOverviewWrapper.setLayout(new BorderLayout());
        loadoutOverviewWrapper.add(activityPanel, BorderLayout.NORTH);


    }

    private void onSearchBarChanged() {
    }

    public void emptyBar()
    {
        searchBar.setText("");
    }

    public void addQuest(String bossName)
    {
//        allDropdownSections.setVisible(false);
        WikiScraper.GetEquipmentByBossName(bossName);
        activityPanel.updateOverviewPanel(bossName);
        scrollPane.setViewportView(loadoutOverviewWrapper);
        searchPanel.setVisible(false);

        repaint();
        revalidate();
    }

    public void removeQuest()
    {
        searchPanel.setVisible(true);
        scrollPane.setViewportView(listPanelWrapper);
        repaint();
        revalidate();
    }


}
