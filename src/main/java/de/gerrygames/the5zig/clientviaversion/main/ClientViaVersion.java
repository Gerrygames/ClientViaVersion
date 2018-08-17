package de.gerrygames.the5zig.clientviaversion.main;

import de.gerrygames.the5zig.clientviaversion.Version;
import de.gerrygames.the5zig.clientviaversion.asm.ButtonPatcher;
import de.gerrygames.the5zig.clientviaversion.netty.ViaTransformerOut;
import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_7_6_10.providers.GameProfileProvider;
import de.gerrygames.the5zig.clientviaversion.providers.ClientGameProfileProvider;
import de.gerrygames.the5zig.clientviaversion.providers.ClientMovementTransmitterProvider;
import de.gerrygames.the5zig.clientviaversion.providers.ClientTitleProvider;
import de.gerrygames.the5zig.clientviaversion.providers.ClientTitleProviderTitleRenderer;
import de.gerrygames.the5zig.clientviaversion.utils.Utils;
import de.gerrygames.the5zig.clientviaversion.viaversion.CustomViaInjector;
import de.gerrygames.the5zig.clientviaversion.viaversion.CustomViaRewindPlatform;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider.TitleRenderProvider;
import de.gerrygames.viarewind.utils.PacketUtil;
import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.The5zigMod;
import eu.the5zig.mod.event.*;
import eu.the5zig.mod.modules.Category;
import eu.the5zig.mod.plugin.Plugin;
import de.gerrygames.the5zig.clientviaversion.asm.EntitySelectorsPatcher;
import de.gerrygames.the5zig.clientviaversion.asm.SwordPatcher;
import de.gerrygames.the5zig.clientviaversion.gui.GuiPatcher;
import de.gerrygames.the5zig.clientviaversion.protocols.ProtocolPatcher;
import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_9to1_8.AttributeManager;
import de.gerrygames.the5zig.clientviaversion.reflection.Injector;
import de.gerrygames.the5zig.clientviaversion.utils.Scheduler;
import de.gerrygames.the5zig.clientviaversion.viaversion.CustomViaBackwardsPlatform;
import de.gerrygames.the5zig.clientviaversion.viaversion.CustomViaPlatform;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.realms.RealmsSharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Plugin(name = "ClientViaVersion", version = Version.PLUGIN_VERSION)
public class ClientViaVersion {
	public static final Logger LOGGER = LogManager.getLogger("ClientViaVersion");
	
	public static final int CLIENT_PROTOCOL_VERSION = RealmsSharedConstants.NETWORK_PROTOCOL_VERSION;
	public static int spoofedVersion = CLIENT_PROTOCOL_VERSION;
	public static List<ProtocolVersion> supportedVersion;
	public static ProtocolVersion selected;

	public static UserConnection user;
	public static Object networkManager;
	public static boolean blockingOnNewServers = false;

	private static String title = Display.getTitle();

	private static ClientViaVersion instance;

	static {
		LaunchClassLoader launchClassLoader = ((LaunchClassLoader)ClientViaVersion.class.getClassLoader().getParent());
		try {
			Field field = LaunchClassLoader.class.getDeclaredField("transformers");
			field.setAccessible(true);
			List<IClassTransformer> transformer = ((List<IClassTransformer>)field.get(launchClassLoader));
			transformer.add(new EntitySelectorsPatcher());
			transformer.add(new ButtonPatcher());
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
		Scheduler.runTask(() -> Display.setTitle(title));
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
		blockingOnNewServers = new File(The5zigMod.getVars().getMinecraftDataDirectory(), "blockingOnNewServers").exists();

		ClientViaVersion.LOGGER.info("Minecraft Version: " + The5zigAPI.getAPI().getMinecraftVersion());
		ClientViaVersion.LOGGER.info("5zig Version: " + The5zigAPI.getAPI().getModVersion());
		ClientViaVersion.LOGGER.info("Forge installed: " + The5zigAPI.getAPI().isForgeEnvironment());
		if (The5zigAPI.getAPI().isForgeEnvironment()) {
			try {
				ClientViaVersion.LOGGER.info("---Forge Mods---");
				Object loader = Class.forName("net.minecraftforge.fml.common.Loader").getDeclaredMethod("instance").invoke(null);
				List mods = (List) loader.getClass().getDeclaredMethod("getActiveModList").invoke(loader);
				Method getVersion = Class.forName("net.minecraftforge.fml.common.ModContainer").getDeclaredMethod("getVersion");
				Method getName = Class.forName("net.minecraftforge.fml.common.ModContainer").getDeclaredMethod("getName");
				for (Object mod : mods) {
					ClientViaVersion.LOGGER.info("" + getName.invoke(mod) + " " + getVersion.invoke(mod));
				}
				ClientViaVersion.LOGGER.info("---Forge Mods---");
			} catch (Exception ignored) {}
		}

		instance = this;
		Via.init(ViaManager.builder().platform(new CustomViaPlatform()).injector(new CustomViaInjector()).build());
		Via.getManager().getProviders().use(BulkChunkTranslatorProvider.class, new BulkChunkTranslatorProvider());
		new CustomViaBackwardsPlatform();
		new CustomViaRewindPlatform();
		PacketUtil.serverSender = ViaTransformerOut::sendToServer;
		Via.getManager().getProviders().use(HandItemProvider.class, new ClientHandItemProvider());
		Via.getManager().getProviders().use(MovementTransmitterProvider.class, new ClientMovementTransmitterProvider());
		Via.getManager().getProviders().use(GameProfileProvider.class, new ClientGameProfileProvider());
		Via.getManager().getProviders().use(TitleRenderProvider.class, new ClientTitleProvider());

		ProtocolVersion.register(new ProtocolVersion(5, "1.7.6-10"));
		ProtocolRegistry.registerProtocol(new Protocol1_8TO1_7_6_10(), Collections.singletonList(47), 5);
		ProtocolVersion.register(new ProtocolVersion(4, "1.7.1-5"));
		ProtocolRegistry.registerProtocol(new Protocol1_7_6_10to1_7_1_5(), Collections.singletonList(5), 4);
		ProtocolPatcher.patch();

		supportedVersion = new ArrayList<>(ProtocolVersion.getProtocols());
		supportedVersion.removeIf(pv -> ClientViaVersion.CLIENT_PROTOCOL_VERSION !=pv.getId() && ProtocolRegistry.getProtocolPath(ClientViaVersion.CLIENT_PROTOCOL_VERSION, pv.getId()) == null);

		supportedVersion.sort(Comparator.comparingInt(ProtocolVersion::getId));

		selected = ProtocolVersion.getProtocol(CLIENT_PROTOCOL_VERSION);

		if (supportedVersion.size()<=1 || selected==null) {
			ClientViaVersion.LOGGER.error("Unsupported clientversion. Disabling ClientViaVersion.");
			return;
		}

		try {
			Injector.injectListener();
		} catch (Exception ex) {
			ClientViaVersion.LOGGER.error("Could not override logger (acting as listener). Disabling ClientViaVersion");
			ex.printStackTrace();
			return;
		}

		The5zigAPI.getAPI().getPluginManager().registerListener(this, new Scheduler());

		if (CLIENT_PROTOCOL_VERSION > 47) {
			try {
				SwordPatcher.patch();
			} catch (Exception ex) {
				ClientViaVersion.LOGGER.error("Could not override Sword Items.");
				ex.printStackTrace();
			}
		}

		if (CLIENT_PROTOCOL_VERSION < 47) {
			The5zigAPI.getAPI().registerModuleItem(this, "Minecraft Title", ClientTitleProviderTitleRenderer.class, Category.GENERAL);
		}

		try {
			Injector.patchCredits();
		} catch (Exception ex) {
			ClientViaVersion.LOGGER.error("Could not edit credits. This has no effect on functionality.");
			ex.printStackTrace();
		}
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