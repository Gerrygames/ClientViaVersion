import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ShieldBlockingButtonForge extends GuiButton {

	public ShieldBlockingButtonForge(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}

	@Override
	public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
		boolean clicked = super.func_146116_c(mc, mouseX, mouseY);
		if (clicked) {
			ClientViaVersion.shieldBlocking = !ClientViaVersion.shieldBlocking;
			this.field_146126_j = "ShieldBlocking: " + ClientViaVersion.shieldBlocking;
		}
		return clicked;
	}
}
