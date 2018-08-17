package de.gerrygames.the5zig.clientviaversion.viaversion;

import us.myles.ViaVersion.api.ViaVersionConfig;

import java.util.Collections;
import java.util.List;

public class CustomViaConfig implements ViaVersionConfig {
	@Override
	public boolean isCheckForUpdates() {
		return false;
	}

	@Override
	public boolean isPreventCollision() {
		return false;
	}

	@Override
	public boolean isNewEffectIndicator() {
		return true;
	}

	@Override
	public boolean isShowNewDeathMessages() {
		return false;
	}

	@Override
	public boolean isSuppressMetadataErrors() {
		return true;
	}

	@Override
	public boolean isShieldBlocking() {return false;}

	@Override
	public boolean isHologramPatch() {
		return false;
	}

	@Override
	public boolean isPistonAnimationPatch() {
		return false;
	}

	@Override
	public boolean isBossbarPatch() {
		return true;
	}

	@Override
	public boolean isBossbarAntiflicker() {
		return false;
	}

	@Override
	public boolean isUnknownEntitiesSuppressed() {
		return true;
	}

	@Override
	public double getHologramYOffset() {
		return 0;
	}

	@Override
	public boolean isAutoTeam() {return false;}

	@Override
	public boolean isBlockBreakPatch() {
		return false;
	}

	@Override
	public int getMaxPPS() {
		return -1;
	}

	@Override
	public String getMaxPPSKickMessage() {
		return null;
	}

	@Override
	public int getTrackingPeriod() {
		return -1;
	}

	@Override
	public int getWarningPPS() {
		return -1;
	}

	@Override
	public int getMaxWarnings() {
		return -1;
	}

	@Override
	public String getMaxWarningsKickMessage() {
		return null;
	}

	@Override
	public boolean isAntiXRay() {
		return false;
	}

	@Override
	public boolean isSendSupportedVersions() {
		return false;
	}

	@Override
	public boolean isStimulatePlayerTick() {
		return true;
	}

	@Override
	public boolean isItemCache() {
		return false;
	}

	@Override
	public boolean isNMSPlayerTicking() {
		return false;
	}

	@Override
	public boolean isReplacePistons() {
		return false;
	}

	@Override
	public int getPistonReplacementId() {
		return -1;
	}

	@Override
	public boolean isForceJsonTransform() {
		return true;
	}

	@Override
	public boolean is1_12NBTArrayFix() {
		return true;
	}

	@Override
	public boolean is1_13TeamColourFix() {
		return true;
	}

	@Override
	public boolean is1_12QuickMoveActionFix() {
		return true;
	}

	@Override
	public List<Integer> getBlockedProtocols() {
		return Collections.emptyList();
	}

	@Override
	public String getBlockedDisconnectMsg() {
		return null;
	}

	@Override
	public String getReloadDisconnectMsg() {
		return null;
	}
}
