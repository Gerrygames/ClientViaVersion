package de.gerrygames.the5zig.clientviaversion.gui;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ButtonRegistry {
	private ArrayList<IClientViaVersionButton> buttons = new ArrayList<>();

	private static ButtonRegistry instance;

	public ButtonRegistry() {
		instance = this;
	}

	public static ButtonRegistry getInstance() {
		return instance;
	}

	public void registerButton(IClientViaVersionButton button) {
		buttons.add(button);
	}

	public void unregisterButton(IClientViaVersionButton button) {
		buttons.remove(button);
	}

	public void forEachButton(Consumer<IClientViaVersionButton> action) {
		buttons.forEach(action);
	}

	public void clear() {
		buttons.clear();
	}
}
