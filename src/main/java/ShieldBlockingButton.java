import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;

public class ShieldBlockingButton extends ClientViaVersionButton {
	public ShieldBlockingButton(int id, int x, int y, int w, int h, String s) {
		super(id, x, y, w, h, s);
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY) {
		boolean clicked = this.isEnabled() && this.isVisible() && mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.getWidth() && mouseY < this.getY() + this.getHeight();
		if (clicked) {
			ClientViaVersion.shieldBlocking = !ClientViaVersion.shieldBlocking;
			this.setLabel("ShieldBlocking: " + ClientViaVersion.shieldBlocking);
		}
		return clicked;
	}
}