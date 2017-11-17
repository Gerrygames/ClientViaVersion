import eu.the5zig.mod.The5zigMod;
import de.gerrygames.the5zig.clientviaversion.gui.GuiPatcher;
import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import de.gerrygames.the5zig.clientviaversion.utils.Scheduler;
import de.gerrygames.the5zig.clientviaversion.utils.Utils;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class VersionReconnectButton extends ClientViaVersionButton {
	private ProtocolVersion protocolVersion;

	public VersionReconnectButton(int id, int x, int y, int w, int h, String s, ProtocolVersion protocolVersion) {
		super(id, x, y, w, h, s);
		this.protocolVersion = protocolVersion;
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY) {
		boolean clicked = this.isEnabled() && this.isVisible() && mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.getWidth() && mouseY < this.getY() + this.getHeight();
		if (clicked) {
			ClientViaVersion.setProtocol(protocolVersion);
			Scheduler.runTask(new Runnable() {
				@Override
				public void run() {
					The5zigMod.getVars().joinServer(GuiPatcher.prevScreen, Utils.getLastServerData());
				}
			});
		}
		return clicked;
	}
}
