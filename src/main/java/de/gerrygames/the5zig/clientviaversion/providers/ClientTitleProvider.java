package de.gerrygames.the5zig.clientviaversion.providers;

import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_7_6_10to1_8.provider.TitleRenderProvider;
import de.gerrygames.the5zig.clientviaversion.utils.Utils;
import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.event.EventHandler;
import eu.the5zig.mod.event.TickEvent;
import us.myles.ViaVersion.api.data.UserConnection;

public class ClientTitleProvider extends TitleRenderProvider {
	private int currentTick = 0;
	private int showTick = 0;
	private boolean show = false;
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
		currentTick++;
	}

	@Override
	public void hide(UserConnection user) {
		super.hide(user);
		if (user!=ClientViaVersion.user) return;
		show = false;
	}

	@Override
	public void display(UserConnection user) {
		if (user!=ClientViaVersion.user) return;
		showTick = currentTick;
		show = true;
	}

	public int getCurrentTick() {
		return currentTick;
	}

	public int getShowTick() {
		return showTick;
	}

	public boolean isShow() {
		return show;
	}

	public String getTitle() {
		String title = this.titles.get(ClientViaVersion.user);
		title = Utils.jsonToLegacy(title);
		return title;
	}

	public String getSubTitle() {
		String subTitle = this.subTitles.get(ClientViaVersion.user);
		subTitle = Utils.jsonToLegacy(subTitle);
		return subTitle;
	}

	public int getFadeIn() {
		return this.fadeIn.getOrDefault(ClientViaVersion.user, 20);
	}

	public int getFadeOut() {
		return this.fadeOut.getOrDefault(ClientViaVersion.user, 20);
	}

	public int getStay() {
		return this.stay.getOrDefault(ClientViaVersion.user, 60);
	}

	public int getTotalShowTime() {
		return getFadeIn() + getStay() + getFadeOut();
	}

	public boolean shouldRender() {
		return show && currentTick - showTick < getTotalShowTime() && getTitle()!=null;
	}
}
