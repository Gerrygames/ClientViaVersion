package de.gerrygames.the5zig.clientviaversion.providers;

import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.modules.AbstractModuleItem;
import eu.the5zig.mod.render.RenderLocation;

public class ClientTitleProviderTitleRenderer extends AbstractModuleItem {
	private static final String dummyTitle = "Titles in 1.7 whooo!";
	private static final String dummySubTitle = "SubTitle!!";
	private static final float scale = 5.0f;
	private static final float subScale = 2.0f;

	@Override
	public void render(int x, int y, RenderLocation renderLocation, boolean dummy) {
		x += getWidth(dummy) / 2;
		if (dummy) {
			The5zigAPI.getAPI().getRenderHelper().drawScaledCenteredString(dummyTitle, x, y, scale);
			The5zigAPI.getAPI().getRenderHelper().drawScaledCenteredString(dummySubTitle, x, (int) (y + scale * 10), subScale);
		} else {
			The5zigAPI.getAPI().getRenderHelper().drawScaledCenteredString(ClientTitleProvider.getInstance().getTitle(), x, y, scale);
			String subTitle = ClientTitleProvider.getInstance().getSubTitle();
			if (subTitle!=null) {
				The5zigAPI.getAPI().getRenderHelper().drawScaledCenteredString(subTitle, x, (int) (y + scale * 10), subScale);
			}
		}
	}

	@Override
	public boolean shouldRender(boolean dummy) {
		return dummy || ClientTitleProvider.getInstance().shouldRender();
	}

	@Override
	public int getWidth(boolean dummy) {
		if (dummy) {
			return Math.max(The5zigAPI.getAPI().getRenderHelper().getStringWidth(dummyTitle), The5zigAPI.getAPI().getRenderHelper().getStringWidth(dummySubTitle));
		} else {
			String title = ClientTitleProvider.getInstance().getTitle();
			if (title==null) return 0;
			String subTitle = ClientTitleProvider.getInstance().getSubTitle();
			return Math.max(The5zigAPI.getAPI().getRenderHelper().getStringWidth(title), The5zigAPI.getAPI().getRenderHelper().getStringWidth(subTitle));
		}
	}

	@Override
	public int getHeight(boolean dummy) {
		return (int) (scale * 10 + subScale * 10);
	}
}
