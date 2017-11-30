package de.gerrygames.the5zig.clientviaversion.gui;

import de.gerrygames.the5zig.clientviaversion.classnames.ClassNames;
import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import eu.the5zig.mod.The5zigMod;
import org.lwjgl.input.Mouse;

public class ButtonClickListener {
	private boolean wasMousePressed;

	public ButtonClickListener() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				//noinspection InfiniteLoopStatement
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					ButtonClickListener.this.tick();
				}
			}
		}, "ClientViaVersionButtonThread").start();
	}

	public void tick() {
		boolean mousePressed = Mouse.isButtonDown(0);

		boolean clicked;
		if (mousePressed && !wasMousePressed) {
			clicked = true;
		} else if (!mousePressed && wasMousePressed) {
			clicked = false;
		} else {
			wasMousePressed = mousePressed;
			return;
		}
		wasMousePressed = mousePressed;

		try {
			final int x = getMouseX();
			final int y = getMouseY();

			ButtonRegistry.getInstance().forEachButton(button -> {
				if (clicked) {
					button.mouseClicked(x, y);
				} else {
					button.mouseReleased(x, y);
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static int getMouseY() {
		try {
			int height = (int) ClassNames.getGuiScreenHeightField().get(GuiPatcher.prevScreen);
			int displayHeight = (int) ClassNames.getMinecraftDisplayHeightField().get(Class.forName("Variables").getMethod("getMinecraft").invoke(The5zigMod.getVars()));

			return height - Mouse.getY() * height / displayHeight;
		} catch (Exception ex) {ex.printStackTrace();}

		return 0;
	}

	public static int getMouseX() {
		try {
			int width = (int) ClassNames.getGuiScreenWidthField().get(GuiPatcher.prevScreen);
			int displayWidth = (int) ClassNames.getMinecraftDisplayWidthField().get(Class.forName("Variables").getMethod("getMinecraft").invoke(The5zigMod.getVars()));

			return Mouse.getX() * width / displayWidth;
		} catch (Exception ex) {ex.printStackTrace();}

		return 0;
	}
}
