package de.gerrygames.the5zig.clientviaversion.render;

import de.gerrygames.the5zig.clientviaversion.reflection.ReflectionAPI;
import eu.the5zig.mod.The5zigMod;
import eu.the5zig.mod.manager.SearchManager;

import java.util.ArrayList;
import java.util.List;

public class RendererRegistry {
	private static RendererRegistry instance;
	private List<Renderer> renderer = new ArrayList<>();

	public RendererRegistry() {
		instance = this;

		SearchManager searchManager = The5zigMod.getDataManager().getSearchManager();
		The5zigMod.getListener().unregisterListener(searchManager);

		searchManager = new SearchManager() {
			@Override
			public void draw() {
				RendererRegistry.getInstance().render();
				super.draw();
			}
		};

		ReflectionAPI.setFinalValuePrintException(The5zigMod.getDataManager(), "searchManager", searchManager);
	}

	public static RendererRegistry getInstance() {
		return instance;
	}

	public void registerRenderer(Renderer renderer) {
		this.renderer.add(renderer);
	}

	public void render() {
		renderer.forEach(Renderer::render);
	}
}
