package de.gerrygames.the5zig.clientviaversion.gui;

import de.gerrygames.the5zig.clientviaversion.render.Renderer;
import eu.the5zig.mod.gui.Gui;

public class ButtonToolTipRenderer implements Renderer {
	private float lastX = 0;
	private float lastY = 0;
	private static final float drag = 0.5f;

	@Override
	public void render() {
		final int x = ButtonClickListener.getMouseX();
		final int y = ButtonClickListener.getMouseY();

		lastX = lastX + (((float)x) - lastX) * drag;
		lastY = lastY + (((float)y) - lastY) * drag;

		ButtonRegistry.getInstance().forEachButton(button -> {
			boolean hovered = button.isHovered(x, y);
			if (hovered) {
				String toolTip = button.getToolTip();
				if (toolTip!=null) {
					Gui.drawScaledString(toolTip, lastX+5, lastY, 1.1f);
				}
			}
		});
	}
}
