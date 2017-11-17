import eu.the5zig.mod.The5zigMod;
import de.gerrygames.the5zig.clientviaversion.gui.GuiPatcher;
import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import de.gerrygames.the5zig.clientviaversion.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class VersionReconnectButtonForge extends GuiButton {
	private ProtocolVersion protocolVersion;

	public VersionReconnectButtonForge(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, ProtocolVersion protocolVersion) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.protocolVersion = protocolVersion;
	}

	@Override
	public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
		boolean clicked = super.func_146116_c(mc, mouseX, mouseY);
		if (clicked) {
			ClientViaVersion.setProtocol(protocolVersion);
			The5zigMod.getVars().joinServer(GuiPatcher.prevScreen, Utils.getLastServerData());
		}
		return clicked;
	}
}
