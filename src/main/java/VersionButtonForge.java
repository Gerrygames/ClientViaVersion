import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;

public class VersionButtonForge extends GuiButton {

	public VersionButtonForge(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}

	@Override
	public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
		boolean clicked = super.func_146116_c(mc, mouseX, mouseY);
		if (clicked) {
			int id = ClientViaVersion.supportedVersion.indexOf(ClientViaVersion.selected);
			if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				id = id==0 ? ClientViaVersion.supportedVersion.size()-1 : id-1;
			} else {
				id = id==ClientViaVersion.supportedVersion.size()-1 ? 0 : id+1;
			}
			ClientViaVersion.setProtocol(ClientViaVersion.supportedVersion.get(id));
			this.field_146126_j = ClientViaVersion.selected.getName();
		}
		return clicked;
	}
}
