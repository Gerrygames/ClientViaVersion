package de.gerrygames.the5zig.clientviaversion.gui;

import de.gerrygames.the5zig.clientviaversion.classnames.ClassNames;
import eu.the5zig.mod.The5zigMod;
import eu.the5zig.mod.asm.Transformer;
import org.lwjgl.input.Mouse;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ButtonManager {
	public static ArrayList<Object> buttons = new ArrayList<>();
	private static boolean init = false;
	private static boolean wasMousePressed;

	public static void tick() {
		boolean mousePressed = Mouse.isButtonDown(0);

		Method method = null;
		if (mousePressed && !wasMousePressed) {
			try {
				method = Class.forName("Button").getDeclaredMethod("mouseClicked", int.class, int.class);
			} catch (Exception ex) {ex.printStackTrace();}
		} else if (!mousePressed && wasMousePressed) {
			try {
				method = Class.forName("Button").getDeclaredMethod("mouseReleased", int.class, int.class);
			} catch (Exception ex) {ex.printStackTrace();}
		}

		if (method!=null) {
			try {
				int width = (int) ClassNames.getGuiScreenWidthField().get(GuiPatcher.prevScreen);
				int height = (int) ClassNames.getGuiScreenHeightField().get(GuiPatcher.prevScreen);
				int displayWidth = (int) ClassNames.getMinecraftDisplayWidthField().get(Class.forName("Variables").getMethod("getMinecraft").invoke(The5zigMod.getVars()));
				int displayHeight = (int) ClassNames.getMinecraftDisplayHeightField().get(Class.forName("Variables").getMethod("getMinecraft").invoke(The5zigMod.getVars()));

				int x = Mouse.getX() * width / displayWidth;
				int y = height - Mouse.getY() * height / displayHeight;

				for (Object button : buttons) method.invoke(button, x, y);
			} catch (Exception ex) {ex.printStackTrace();}
		}

		wasMousePressed = mousePressed;
	}


	public static void init() {
		if (Transformer.FORGE || init) return;
		init = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				//noinspection InfiniteLoopStatement
				while(true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException ex) {ex.printStackTrace();}
					ButtonManager.tick();
				}
			}
		}, "ClientViaVersionButtonThread").start();
	}
}
