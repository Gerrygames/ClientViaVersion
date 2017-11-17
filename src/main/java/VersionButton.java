import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import org.lwjgl.input.Keyboard;

public class VersionButton extends ClientViaVersionButton {

	public VersionButton(int id, int x, int y, int w, int h, String s) {
		super(id, x, y, w, h, s);
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY) {
		boolean clicked = this.isEnabled() && this.isVisible() && mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.getWidth() && mouseY < this.getY() + this.getHeight();
		if (clicked) {
			int id = ClientViaVersion.supportedVersion.indexOf(ClientViaVersion.selected);
			if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				id = id==0 ? ClientViaVersion.supportedVersion.size()-1 : id-1;
			} else {
				id = id==ClientViaVersion.supportedVersion.size()-1 ? 0 : id+1;
			}
			ClientViaVersion.setProtocol(ClientViaVersion.supportedVersion.get(id));
			this.setLabel(ClientViaVersion.selected.getName());
		}
		return clicked;
	}
}