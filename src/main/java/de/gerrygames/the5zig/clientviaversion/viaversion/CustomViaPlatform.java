package de.gerrygames.the5zig.clientviaversion.viaversion;

import de.gerrygames.the5zig.clientviaversion.Version;
import de.gerrygames.the5zig.clientviaversion.utils.Scheduler;
import us.myles.ViaVersion.api.ViaAPI;
import us.myles.ViaVersion.api.ViaVersionConfig;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.ViaVersion.api.configuration.ConfigurationProvider;
import us.myles.ViaVersion.api.platform.TaskId;
import us.myles.ViaVersion.api.platform.ViaPlatform;
import us.myles.viaversion.libs.gson.JsonObject;

import java.util.UUID;
import java.util.logging.Logger;

public class CustomViaPlatform implements ViaPlatform {
	private CustomViaConfig config = new CustomViaConfig();
	private CustomViaAPI api  = new CustomViaAPI();

	@Override
	public Logger getLogger() {
		return Logger.getGlobal();
	}

	@Override
	public String getPlatformName() {
		return "ClientViaVersion";
	}

	@Override
	public String getPlatformVersion() {
		return Version.PLUGIN_VERSION;
	}

	@Override
	public String getPluginVersion() {
		return "unknown";
	}

	@Override
	public TaskId runAsync(Runnable runnable) {
		return null;
	}

	@Override
	public TaskId runSync(Runnable runnable) {
		final int taskId = Scheduler.runTask(runnable);
		return new TaskId() {
			@Override
			public Object getObject() {
				return taskId;
			}
		};
	}

	@Override
	public TaskId runSync(Runnable runnable, Long delay) {
		final int taskId = Scheduler.runTaskLater(runnable, delay);
		return new TaskId() {
			@Override
			public Object getObject() {
				return taskId;
			}
		};
	}

	@Override
	public TaskId runRepeatingSync(Runnable runnable, Long delay) {
		final int taskId = Scheduler.runTaskTimer(runnable, delay);
		return new TaskId() {
			@Override
			public Object getObject() {
				return taskId;
			}
		};
	}

	@Override
	public void cancelTask(TaskId taskId) {
		Scheduler.cancel((Integer) taskId.getObject());
	}

	@Override
	public ViaCommandSender[] getOnlinePlayers() {
		return new ViaCommandSender[0];
	}

	@Override
	public void sendMessage(UUID uuid, String msg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean kickPlayer(UUID uuid, String s) {
		return false;
	}

	@Override
	public boolean isPluginEnabled() {
		return true;
	}

	@Override
	public ViaAPI getApi() {
		return api;
	}

	@Override
	public ViaVersionConfig getConf() {
		return config;
	}

	@Override
	public ConfigurationProvider getConfigurationProvider() {
		return null;
	}

	@Override
	public void onReload() {}

	@Override
	public JsonObject getDump() {
		return null;
	}

	@Override
	public boolean isOldClientsAllowed() {
		return true;
	}
}