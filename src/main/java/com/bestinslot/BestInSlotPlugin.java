package com.bestinslot;

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

	@Override
	protected void startUp() throws Exception
	{
		log.info("Best In Slot started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Best In Slot stopped!");
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
