package com.bestinslot;

import com.bestinslot.tools.Icon;
import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.Nullable;
import javax.inject.Named;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicButtonUI;
import net.runelite.api.Client;
import net.runelite.client.RuneLiteProperties;
import net.runelite.client.account.SessionManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.SessionClose;
import net.runelite.client.events.SessionOpen;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;
import net.runelite.client.util.SwingUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.JPanel;

public class BestInSlotPanel extends PluginPanel
{

    //private static final ImageIcon DISCORD_ICON;
    private static final ImageIcon GITHUB_ICON;
    //private static final ImageIcon PATREON_ICON;

    private final IconTextField searchBar = new IconTextField();

    private JPanel searchBISPanel;

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

        searchBISPanel = new JPanel();
        searchBISPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        searchBISPanel.setLayout(new BorderLayout(0, BORDER_OFFSET));
        searchBISPanel.add(searchBar, BorderLayout.CENTER);


        JPanel introDetailsPanel = new JPanel();
        introDetailsPanel.setLayout(new BorderLayout());
        introDetailsPanel.add(titlePanel, BorderLayout.NORTH);
        introDetailsPanel.add(searchBISPanel, BorderLayout.CENTER);

        add(introDetailsPanel, BorderLayout.NORTH);
    }

    private void onSearchBarChanged() {
    }


}
