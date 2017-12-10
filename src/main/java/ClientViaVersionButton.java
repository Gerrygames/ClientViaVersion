public abstract class ClientViaVersionButton extends Button {
	public ClientViaVersionButton(int id, int x, int y, String label) {
		super(id, x, y, label);
	}

	public ClientViaVersionButton(int id, int x, int y, String label, boolean enabled) {
		super(id, x, y, label, enabled);
	}

	public ClientViaVersionButton(int id, int x, int y, int width, int height, String label) {
		super(id, x, y, width, height, label);
	}

	public ClientViaVersionButton(int id, int x, int y, int width, int height, String label, boolean enabled) {
		super(id, x, y, width, height, label, enabled);
	}

	/*public void a(bib mc, int mouseX, int mouseY, float partialTicks) {
		if (onPreDraw(mouseX, mouseY)) {
			super.a(mc, mouseX, mouseY, partialTicks);
		}
		onPostDraw(mouseX, mouseY);
	}

	public void a(bib mc, int mouseX, int mouseY) {
		mouseWasReleased(mouseX, mouseY);
	}

	public boolean b(bib mc, int mouseX, int mouseY) {
		if (isHovered(mouseX, mouseY)) {
			mouseWasClicked(mouseX, mouseY);
			return true;
		}
		return false;
	}*/

	public boolean isHovered(int mouseX, int mouseY) {
		return this.isEnabled() && this.isVisible() && mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.getWidth() && mouseY < this.getY() + this.getHeight();
	}

	protected boolean onPreDraw(int mouseX, int mouseY) {
		return true;
	}

	protected void onPostDraw(int mouseX, int mouseY) {
	}

	protected abstract void mouseWasClicked(int mouseX, int mouseY);

	protected abstract void mouseWasReleased(int mouseX, int mouseY);
}