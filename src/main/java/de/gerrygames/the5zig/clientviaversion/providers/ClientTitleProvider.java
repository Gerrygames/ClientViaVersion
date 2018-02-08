package de.gerrygames.the5zig.clientviaversion.providers;

import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider.TitleRenderProvider;
import de.gerrygames.viarewind.utils.ChatUtil;
import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.event.EventHandler;
import eu.the5zig.mod.event.WorldTickEvent;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientTitleProvider extends TitleRenderProvider {
	private static ClientTitleProvider instance;
	private UUID uuid = The5zigAPI.getAPI().getGameProfile().getId();

	public ClientTitleProvider() {
		instance = this;
		The5zigAPI.getAPI().getPluginManager().registerListener(ClientViaVersion.getInstance(), this);
	}

	public static ClientTitleProvider getInstance() {
		return instance;
	}

	@EventHandler
	public void onTick(WorldTickEvent event) {
		AtomicInteger time = getTime(uuid);
		if (time.get() > 0 && time.decrementAndGet() <= 0) {
			this.titles.remove(uuid);
			this.subTitles.remove(uuid);
		}
	}

	public String getTitle() {
		String title = this.titles.get(uuid);
		if (title==null) return null;
		title = ChatUtil.jsonToLegacy(title);
		return title;
	}

	public String getSubTitle() {
		String subTitle = this.subTitles.get(uuid);
		if (subTitles==null) return null;
		subTitle = ChatUtil.jsonToLegacy(subTitle);
		return subTitle;
	}
	public boolean shouldRender() {
		return getTime(uuid).get() > 0 && this.titles.containsKey(uuid);
	}
}
