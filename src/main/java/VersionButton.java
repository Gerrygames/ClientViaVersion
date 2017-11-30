import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import org.lwjgl.input.Keyboard;

public class VersionButton extends ClientViaVersionButton {

	public VersionButton(int id, int x, int y, int w, int h, String s) {
		super(id, x, y, w, h, s);
	}

	@Override
	public void mouseWasClicked(int mouseX, int mouseY) {
		int id = ClientViaVersion.supportedVersion.indexOf(ClientViaVersion.selected);
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			id = id == 0 ? ClientViaVersion.supportedVersion.size() - 1 : id - 1;
		} else {
			id = id == ClientViaVersion.supportedVersion.size() - 1 ? 0 : id + 1;
		}
		ClientViaVersion.setProtocol(ClientViaVersion.supportedVersion.get(id));
		this.setLabel(ClientViaVersion.selected.getName());
	}

	@Override
	protected void mouseWasReleased(int mouseX, int mouseY) { }

	@Override
	public String getToolTip() {
		return "§cUse at own risk!";
	}
}