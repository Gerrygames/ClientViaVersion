import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import eu.the5zig.mod.gui.Gui;

import java.util.stream.Collectors;

public class VersionButton extends ClientViaVersionDropDownButton {

	public VersionButton(int id, int x, int y, int width, int height) {
		// :^)
		super(id,
				x,
				y,
				width,
				height,
				ClientViaVersion.supportedVersion
						.stream()
				        .map(protocolVersion -> new DropDownElement(protocolVersion.getName(),
							() -> ClientViaVersion.setProtocol(protocolVersion)))
						.collect(Collectors.toList()),
				ClientViaVersion.supportedVersion.indexOf(ClientViaVersion.selected)
		);
	}

	private float lastX = 0;
	private float lastY = 0;
	private static final float drag = 0.5f;
	private static final String toolTip = "§cUse at own risk!";
	@Override
	protected boolean onPreDraw(int mouseX, int mouseY) {
		boolean shouldDraw = super.onPreDraw(mouseX, mouseY);
		lastX = lastX + (((float)mouseX) - lastX) * drag;
		lastY = lastY + (((float)mouseY) - lastY) * drag;

		if (isHovered(mouseX, mouseY)) {
			Gui.drawScaledString(toolTip, lastX+5, lastY, 1.1f);
		}
		return shouldDraw;
	}
}