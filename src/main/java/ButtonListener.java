public class ButtonListener {
	public static void onMouseClicked(Object button, int mouseX, int mouseY) {
		if (button instanceof ClientViaVersionButton) ((ClientViaVersionButton) button).mouseClicked(mouseX, mouseY);
	}
}