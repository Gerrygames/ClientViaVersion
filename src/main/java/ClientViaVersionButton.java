import de.gerrygames.the5zig.clientviaversion.gui.ButtonRegistry;
import de.gerrygames.the5zig.clientviaversion.gui.IClientViaVersionButton;

public abstract class ClientViaVersionButton extends Button implements IClientViaVersionButton {
	public ClientViaVersionButton(int id, int x, int y, String label) {
		super(id, x, y, label);
		ButtonRegistry.getInstance().registerButton(this);
	}

	public ClientViaVersionButton(int id, int x, int y, String label, boolean enabled) {
		super(id, x, y, label, enabled);
		ButtonRegistry.getInstance().registerButton(this);
	}

	public ClientViaVersionButton(int id, int x, int y, int width, int height, String label) {
		super(id, x, y, width, height, label);
		ButtonRegistry.getInstance().registerButton(this);
	}

	public ClientViaVersionButton(int id, int x, int y, int width, int height, String label, boolean enabled) {
		super(id, x, y, width, height, label, enabled);
		ButtonRegistry.getInstance().registerButton(this);
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY) {
		boolean clicked = isHovered(mouseX, mouseY);
		if (clicked) {
			this.mouseWasClicked(mouseX, mouseY);
		}
		return clicked;
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		if (isHovered(mouseX, mouseY)) {
			this.mouseWasReleased(mouseX, mouseY);
		}
	}

	public boolean isHovered(int mouseX, int mouseY) {
		return this.isEnabled() && this.isVisible() && mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.getWidth() && mouseY < this.getY() + this.getHeight();
	}

	protected abstract void mouseWasClicked(int mouseX, int mouseY);

	protected abstract void mouseWasReleased(int mouseX, int mouseY);

	public String getToolTip() {
		return null;
	}
}