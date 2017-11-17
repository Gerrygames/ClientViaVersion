import de.gerrygames.the5zig.clientviaversion.gui.ButtonManager;

public class ClientViaVersionButton extends Button {
	public ClientViaVersionButton(int id, int x, int y, String label) {
		super(id, x, y, label);
		ButtonManager.buttons.add(this);
	}

	public ClientViaVersionButton(int id, int x, int y, String label, boolean enabled) {
		super(id, x, y, label, enabled);
		ButtonManager.buttons.add(this);
	}

	public ClientViaVersionButton(int id, int x, int y, int width, int height, String label) {
		super(id, x, y, width, height, label);
		ButtonManager.buttons.add(this);
	}

	public ClientViaVersionButton(int id, int x, int y, int width, int height, String label, boolean enabled) {
		super(id, x, y, width, height, label, enabled);
		ButtonManager.buttons.add(this);
	}
}