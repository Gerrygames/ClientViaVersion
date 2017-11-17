package de.gerrygames.the5zig.clientviaversion.main;

import de.gerrygames.the5zig.clientviaversion.Version;
import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_7_6_10.providers.GameProfileProvider;
import de.gerrygames.the5zig.clientviaversion.providers.ClientGameProfileProvider;
import de.gerrygames.the5zig.clientviaversion.providers.ClientMovementTransmitterProvider;
import de.gerrygames.the5zig.clientviaversion.utils.Utils;
import de.gerrygames.the5zig.clientviaversion.viaversion.CustomViaInjector;
import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.The5zigMod;
import eu.the5zig.mod.event.*;
import eu.the5zig.mod.plugin.Plugin;
import de.gerrygames.the5zig.clientviaversion.asm.EntitySelectorsPatcher;
import de.gerrygames.the5zig.clientviaversion.asm.SwordPatcher;
import de.gerrygames.the5zig.clientviaversion.gui.ButtonManager;
import de.gerrygames.the5zig.clientviaversion.gui.GuiPatcher;
import de.gerrygames.the5zig.clientviaversion.protocols.ProtocolPatcher;
import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_9to1_8.AttributeManager;
import de.gerrygames.the5zig.clientviaversion.reflection.Injector;
import de.gerrygames.the5zig.clientviaversion.utils.Scheduler;
import de.gerrygames.the5zig.clientviaversion.viaversion.CustomViaBackwardsPlatform;
import de.gerrygames.the5zig.clientviaversion.viaversion.CustomViaPlatform;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.realms.RealmsSharedConstants;
import org.lwjgl.opengl.Display;
import us.myles.ViaVersion.ViaManager;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;
import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_7_6_10to1_7_1_5.Protocol1_7_6_10to1_7_1_5;
import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_7_6_10.Protocol1_8TO1_7_6_10;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.BulkChunkTranslatorProvider;
import de.gerrygames.the5zig.clientviaversion.providers.ClientHandItemProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.HandItemProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;

import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.util.*;

@Plugin(name = "ClientViaVersion", version = Version.PLUGIN_VERSION)
public class ClientViaVersion {
	public static final int CLIENT_PROTOCOL_VERSION = RealmsSharedConstants.NETWORK_PROTOCOL_VERSION;
	public static int spoofedVersion = CLIENT_PROTOCOL_VERSION;
	public static List<ProtocolVersion> supportedVersion;
	public static ProtocolVersion selected;
	public static boolean shieldBlocking = true;

	public static UserConnection user;
	public static Object networkManager;

	private static String title = Display.getTitle();

	private static ClientViaVersion instance;

	static {
		LaunchClassLoader launchClassLoader = ((LaunchClassLoader)ClientViaVersion.class.getClassLoader().getParent());
		URLClassLoader classLoader = ((URLClassLoader)ClientViaVersion.class.getClassLoader());
		try {
			Field field = LaunchClassLoader.class.getDeclaredField("transformers");
			field.setAccessible(true);
			List<IClassTransformer> transformer = ((List<IClassTransformer>)field.get(launchClassLoader));
			transformer.add(new EntitySelectorsPatcher());
		} catch (Exception ex) {ex.printStackTrace();}
	}

	public static ClientViaVersion getInstance() {
		return instance;
	}

	public static void setProtocol(ProtocolVersion protocolVersion) {
		selected = protocolVersion;
		spoofedVersion = selected.getId();
		if (spoofedVersion== CLIENT_PROTOCOL_VERSION) {
			setTitle(title);
		} else {
			setTitle(title + " | " + selected.getName() + " Protocol");
		}
	}

	private static void setTitle(String title) {
		Scheduler.runTask(new Runnable() {
			@Override
			public void run() {
				Display.setTitle(title);
			}
		});
	}

	@EventHandler
	public void onUnload(UnloadEvent e) {
		Scheduler.clearTasks();
	}

	@EventHandler
	public void onServerQuit(ServerQuitEvent e) {
		if (user!=null) Via.getManager().removePortedClient(user.get(ProtocolInfo.class).getUuid());
		Scheduler.clearTasks();
		Utils.currentState = null;
	}

	@EventHandler
	public void onTick(TickEvent e) {
		GuiPatcher.tick();
	}

	@EventHandler
	public void onLoad(LoadEvent e) {
		instance = this;
		Via.init(ViaManager.builder().platform(new CustomViaPlatform()).injector(new CustomViaInjector()).build());
		Via.getManager().getProviders().use(BulkChunkTranslatorProvider.class, new BulkChunkTranslatorProvider());
		new CustomViaBackwardsPlatform();
		Via.getManager().getProviders().use(HandItemProvider.class, new ClientHandItemProvider());
		Via.getManager().getProviders().use(MovementTransmitterProvider.class, new ClientMovementTransmitterProvider());
		Via.getManager().getProviders().use(GameProfileProvider.class, new ClientGameProfileProvider());

		ProtocolVersion.register(new ProtocolVersion(5, "1.7.6-10"));
		ProtocolRegistry.registerProtocol(new Protocol1_8TO1_7_6_10(), Collections.singletonList(47), 5);
		ProtocolVersion.register(new ProtocolVersion(4, "1.7.1-5"));
		ProtocolRegistry.registerProtocol(new Protocol1_7_6_10to1_7_1_5(), Collections.singletonList(5), 4);
		ProtocolRegistry.registerProtocol(new Protocol1_8TO1_9(), Collections.singletonList(47), 107);
		ProtocolPatcher.patch();

		supportedVersion = new ArrayList<>(ProtocolVersion.getProtocols());
		supportedVersion.removeIf(pv -> ClientViaVersion.CLIENT_PROTOCOL_VERSION !=pv.getId() && ProtocolRegistry.getProtocolPath(ClientViaVersion.CLIENT_PROTOCOL_VERSION, pv.getId()) == null);

		supportedVersion.sort(new Comparator<ProtocolVersion>() {
			@Override
			public int compare(ProtocolVersion v1, ProtocolVersion v2) {
				return Integer.compare(v1.getId(), v2.getId());
			}
		});

		selected = ProtocolVersion.getProtocol(CLIENT_PROTOCOL_VERSION);

		if (supportedVersion.size()<=1 || selected==null) {
			The5zigMod.logger.error("[ClientViaVersion] Unsupported clientversion. Disabling ClientViaVersion.");
			return;
		}

		try {
			Injector.injectListener();
		} catch (Exception ex) {
			The5zigMod.logger.error("[ClientViaVersion] Could not override logger (acting as listener). Disabling ClientViaVersion");
			ex.printStackTrace();
			return;
		}

		The5zigAPI.getAPI().getPluginManager().registerListener(this, new Scheduler());

		if (CLIENT_PROTOCOL_VERSION > 47) {
			try {
				SwordPatcher.patch();
			} catch (Exception ex) {
				The5zigMod.logger.error("[ClientViaVersion] Could not override Sword Items.");
				ex.printStackTrace();
			}
		}

		try {
			Injector.patchCredits();
		} catch (Exception ex) {
			The5zigMod.logger.error("[ClientViaVersion] Could not edit credits. This has no effect on functionality.");
			ex.printStackTrace();
		}

		ButtonManager.init();
	}

	public static void switchState(State oldState, State newState) {}

	@EventHandler
	public void onServerJoin(ServerJoinEvent e) {
		if (spoofedVersion<=47 && CLIENT_PROTOCOL_VERSION > 47) {
			Scheduler.runTaskTimerLater(AttributeManager::sendAttackSpeed, 40L, 40L);
			Scheduler.runTaskTimerLater(AttributeManager::sendArmorUpdate, 10L, 10L);
		}
	}
}