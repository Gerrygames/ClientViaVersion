package de.gerrygames.the5zig.clientviaversion.providers;

import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import de.gerrygames.the5zig.clientviaversion.utils.Utils;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider.TitleRenderProvider;
import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.event.EventHandler;
import eu.the5zig.mod.event.TickEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class ClientTitleProvider extends TitleRenderProvider {
	private static ClientTitleProvider instance;

	public ClientTitleProvider() {
		instance = this;
		The5zigAPI.getAPI().getPluginManager().registerListener(ClientViaVersion.getInstance(), this);
	}

	public static ClientTitleProvider getInstance() {
		return instance;
	}

	@EventHandler
	public void onTick(TickEvent event) {
		AtomicInteger time = getTime(ClientViaVersion.user);
		if (time.get()>0 && time.decrementAndGet() <= 0) {
			clear(ClientViaVersion.user);
		}
	}

	public String getTitle() {
		String title = this.titles.get(ClientViaVersion.user);
		if (title==null) return null;
		title = Utils.jsonToLegacy(title);
		return title;
	}

	public String getSubTitle() {
		String subTitle = this.subTitles.get(ClientViaVersion.user);
		if (subTitles==null) return null;
		subTitle = Utils.jsonToLegacy(subTitle);
		return subTitle;
	}

	public boolean shouldRender() {
		return getTime(ClientViaVersion.user).get() > 0 && this.titles.containsKey(ClientViaVersion.user);
	}
}
