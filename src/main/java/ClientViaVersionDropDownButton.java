import eu.the5zig.mod.gui.Gui;

import java.util.List;

public class ClientViaVersionDropDownButton extends ClientViaVersionButton {
	private boolean droppedDown  = false;
	private int elementHeight;
	private int originalHeight;
	protected List<DropDownElement> elements;
	private DropDownElement selected;

	public ClientViaVersionDropDownButton(int id, int x, int y, int width, int height, List<DropDownElement> elements, int selected) {
		super(id, x, y, width, height, elements.get(selected).getDisplayName());
		this.elementHeight = height;
		this.originalHeight = height;
		this.elements = elements;
		this.selected = elements.get(selected);
	}

	@Override
	protected void mouseWasClicked(int mouseX, int mouseY) {
		if (!droppedDown) {
			setDroppedDown(true);
		} else {
			int y = mouseY - this.getY() - originalHeight;
			setDroppedDown(false);
			if (y<=0) return;
			int index = y / elementHeight;
			selected = elements.get(index);
			setLabel(selected.getDisplayName());
			selected.run();
			onSelect(selected);
		}
	}

	protected void onSelect(DropDownElement element) {

	}

	@Override
	protected void mouseWasReleased(int mouseX, int mouseY) {

	}

	private void setDroppedDown(boolean droppedDown) {
		this.droppedDown = droppedDown;
	}

	public boolean isDroppedDown() {
		return droppedDown;
	}

	@Override
	protected boolean onPreDraw(int mouseX, int mouseY) {
		setLabel(selected.getDisplayName());
		this.setHeight(originalHeight);
		return true;
	}

	@Override
	protected void onPostDraw(int mouseX, int mouseY) {
		this.setHeight(originalHeight + (droppedDown ? elementHeight * elements.size() : 0));

		int y = mouseY - this.getY() - originalHeight;
		int index = y<=0 || mouseX<this.getX() || mouseX>this.getX()+this.getWidth() ? -1 : y / elementHeight;
		float scale = 1.0f;

		if (this.droppedDown) {
			for (int i = 0; i < elements.size(); i++) {
				DropDownElement element = elements.get(i);
				int top = this.getY() + originalHeight + elementHeight*i;

				int outlineColor = abgr(255, 255, 255, 255);
				int color = i==index ? abgr(150, 79, 79, 79) : abgr(120, 255, 255, 255);

				Gui.drawRect(this.getX(), top, this.getX()+this.getWidth(), top+elementHeight+1, color);
				Gui.drawRectInline(this.getX(), top, this.getX()+this.getWidth(), top+elementHeight+1, outlineColor);
				Gui.drawScaledCenteredString(element.getDisplayName(), this.getX() + this.getWidth() / 2, top + elementHeight / 2 - (int)(scale * 4), 1.0f);
			}
		}
		scale = 3.0f;
		Gui.drawScaledCenteredString(droppedDown ? "⇑" : "⇓", this.getX()+this.getWidth()-8, this.getY() + originalHeight / 2 - (int)(scale * 4), scale);
	}

	private int abgr(int a, int r, int g, int b) {
		return (a << 24) | (r << 16) | (g & 0xFF) << 8 | (b & 0xFF);
	}

	public static class DropDownElement {
		private String displayName;
		private Runnable callback;

		public DropDownElement(String displayName, Runnable callback) {
			this.displayName = displayName;
			this.callback = callback;
		}

		public String getDisplayName() {
			return displayName;
		}

		public void run() {
			if (callback!=null) callback.run();
		}
	}
}
