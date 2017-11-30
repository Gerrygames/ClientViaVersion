package de.gerrygames.the5zig.clientviaversion.gui;

public interface IClientViaVersionButton {
	String getToolTip();

	boolean mouseClicked(int mouseX, int mouseY);

	void mouseReleased(int mouseX, int mouseY);

	boolean isHovered(int mouseX, int mouseY);
}
