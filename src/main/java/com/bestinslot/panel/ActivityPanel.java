package com.bestinslot.panel;

import com.bestinslot.osrswiki.WikiScraper;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.SwingUtil;
import com.bestinslot.tools.Icon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class ActivityPanel extends JPanel
{

    JLabel label;
    private final JLabel questNameLabel = new JLabel();
    private static final ImageIcon CLOSE_ICON = Icon.CLOSE.getIcon();
    BestInSlotPanel bestInSlotPanel;
    private final JPanel actionsContainer = new JPanel();
    private final JPanel configContainer = new JPanel();
    private final JPanel introPanel = new JPanel();
    private final JPanel questStepsContainer = new JPanel();
    private final JPanel questGeneralRequirementsListPanel = new JPanel();
    private final JPanel questGeneralRecommendedListPanel = new JPanel();

    private final JPanel questItemRequirementsListPanel = new JPanel();
    private final JPanel questItemRecommendedListPanel = new JPanel();
    private final JPanel questCombatRequirementsListPanel = new JPanel();
    private final JPanel questGeneralRequirementsHeader = new JPanel();
    private final JPanel questGeneralRecommendedHeader = new JPanel();
    private final JPanel questItemRequirementsHeader = new JPanel();
    private final JPanel questCombatRequirementHeader = new JPanel();
    private final JPanel questItemRecommendedHeader = new JPanel();
    private final JPanel questNoteHeader = new JPanel();
    private final JPanel questOverviewNotesPanel = new JPanel();
    private final JPanel questRewardPanel = new JPanel();
    private final JPanel questRewardHeader = new JPanel();
    private final JPanel externalQuestResourcesPanel = new JPanel();
    private final JPanel externalQuestResourcesHeader = new JPanel();

    public ActivityPanel(BestInSlotPanel bestInSlotPanel)
    {
        super();
        this.bestInSlotPanel = bestInSlotPanel;

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);
        setBorder(new EmptyBorder(6, 6, 6, 6));

        /* CONTROLS */
        actionsContainer.setLayout(new BorderLayout());
        actionsContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        actionsContainer.setPreferredSize(new Dimension(0, 30));
        actionsContainer.setBorder(new EmptyBorder(5, 5, 5, 10));
        actionsContainer.setVisible(true);

        final JPanel viewControls = new JPanel(new GridLayout(1, 3, 10, 0));
        viewControls.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        JButton closeBtn = new JButton();
        SwingUtil.removeButtonDecorations(closeBtn);
        closeBtn.setIcon(CLOSE_ICON);
        closeBtn.setToolTipText("Close helper");
        closeBtn.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        closeBtn.setUI(new BasicButtonUI());
        closeBtn.addActionListener(ev -> bestInSlotPanel.removeQuest());
        viewControls.add(closeBtn);

        actionsContainer.add(viewControls, BorderLayout.EAST);

        questNameLabel.setForeground(Color.WHITE);
        questNameLabel.setText("");
        final JPanel leftTitleContainer = new JPanel(new BorderLayout(5, 0));
        leftTitleContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        leftTitleContainer.add(questNameLabel, BorderLayout.CENTER);

        actionsContainer.add(leftTitleContainer, BorderLayout.WEST);

        /* Quest config panel */
        configContainer.setLayout(new BorderLayout());
        configContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        configContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(5, 0, 0, 0, ColorScheme.DARK_GRAY_COLOR),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        BoxLayout boxLayoutOverview2 = new BoxLayout(configContainer, BoxLayout.Y_AXIS);
        configContainer.setLayout(boxLayoutOverview2);
        configContainer.setVisible(false);

        JPanel configHeaderPanel = new JPanel();
        configHeaderPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        configHeaderPanel.setLayout(new BorderLayout());
        configHeaderPanel.setBorder(new EmptyBorder(5, 5, 5, 10));
        JLabel configHeaderText = new JLabel();
        configHeaderText.setForeground(Color.WHITE);
        configHeaderText.setText("Configuration:");
        configHeaderText.setMinimumSize(new Dimension(1, configHeaderPanel.getPreferredSize().height));
        configHeaderPanel.add(configHeaderText);
        configContainer.add(configHeaderPanel);

        /* Quest overview panel */
        introPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(5, 0, 0, 0, ColorScheme.DARK_GRAY_COLOR),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        introPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        introPanel.setLayout(new BorderLayout());
        introPanel.setVisible(true);

        /* Panel for all overview details */
        final JPanel overviewPanel = new JPanel();
        BoxLayout boxLayoutOverview = new BoxLayout(overviewPanel, BoxLayout.Y_AXIS);
        overviewPanel.setLayout(boxLayoutOverview);

        overviewPanel.add(generateRequirementPanel(questGeneralRequirementsListPanel,
                questGeneralRequirementsHeader, "General requirements:"));
        overviewPanel.add(generateRequirementPanel(questGeneralRecommendedListPanel,
                questGeneralRecommendedHeader, "Recommended:"));
        overviewPanel.add(generateRequirementPanel(questItemRequirementsListPanel,
                questItemRequirementsHeader, "Item requirements:"));
        overviewPanel.add(generateRequirementPanel(questItemRecommendedListPanel, questItemRecommendedHeader,
                "Recommended items:"));
        overviewPanel.add(generateRequirementPanel(questCombatRequirementsListPanel, questCombatRequirementHeader,
                "Enemies to defeat:"));
        overviewPanel.add(generateRequirementPanel(questOverviewNotesPanel, questNoteHeader, "Notes:"));
        overviewPanel.add(generateRequirementPanel(questRewardPanel, questRewardHeader,
                "Rewards:"));
        overviewPanel.add(generateRequirementPanel(externalQuestResourcesPanel, externalQuestResourcesHeader , "External Resources:"));

        introPanel.add(overviewPanel, BorderLayout.NORTH);

        /* Container for quest steps */
        questStepsContainer.setLayout(new BoxLayout(questStepsContainer, BoxLayout.Y_AXIS));

        add(actionsContainer);
        add(configContainer);
        add(introPanel);
        add(questStepsContainer);


    }

    public void updateOverviewPanel(String bossName)
    {
        questNameLabel.setText(bossName);
        questNameLabel.repaint();
        questNameLabel.revalidate();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("licked the doorknob");
                WikiScraper.GetEquipmentByBossName(bossName);
            }

        });
















    }

    private JPanel generateRequirementPanel(JPanel listPanel, JPanel headerPanel, String header)
    {
        JPanel requirementPanel = new JPanel();
        requirementPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        requirementPanel.setLayout(new BorderLayout());
        requirementPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        headerPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(5, 5, 5, 10));

        JLabel questItemReqs = new JLabel();
        questItemReqs.setForeground(Color.WHITE);
        questItemReqs.setText(header);
        questItemReqs.setMinimumSize(new Dimension(1, headerPanel.getPreferredSize().height));
        headerPanel.add(questItemReqs, BorderLayout.NORTH);

        listPanel.setLayout(new DynamicPaddedGridLayout(0, 1, 0, 1));
        listPanel.setBorder(new EmptyBorder(10, 5, 10, 5));

        requirementPanel.add(headerPanel, BorderLayout.NORTH);
        requirementPanel.add(listPanel, BorderLayout.CENTER);

        return requirementPanel;
    }

}
