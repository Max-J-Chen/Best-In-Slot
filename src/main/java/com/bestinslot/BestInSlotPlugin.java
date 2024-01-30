package com.bestinslot;

import com.bestinslot.panel.BestInSlotPanel;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
	name = "Best In Slot"
)
public class BestInSlotPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private BestInSlotConfig config;

	@Inject
	private ClientToolbar clientToolbar;

	private BestInSlotPanel panel;

	private NavigationButton navButton;

	@Override
	protected void startUp() throws Exception
	{
		this.panel = new BestInSlotPanel();
		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/bestinslot_icon.png");

		navButton = NavigationButton.builder()
				.tooltip("Best In Slot")
				.icon(icon)
				.priority(8)
				.panel(panel)
				.build();

		clientToolbar.addNavigation(navButton);

	}

	@Override
	protected void shutDown() throws Exception
	{

	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Best In Slot says " + config.greeting(), null);
		}
	}

	@Provides
	BestInSlotConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BestInSlotConfig.class);
	}
}
