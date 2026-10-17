package combatlogx.expansion.cheat.prevention.listener;

import java.util.Locale;

import org.jetbrains.annotations.NotNull;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import com.github.sirblobman.api.language.LanguageManager;
import com.github.sirblobman.api.language.replacer.ComponentReplacer;
import com.github.sirblobman.api.language.replacer.Replacer;
import com.github.sirblobman.api.language.replacer.StringReplacer;
import com.github.sirblobman.api.shaded.adventure.text.Component;
import com.github.sirblobman.combatlogx.api.event.PlayerTagEvent;
import com.github.sirblobman.combatlogx.api.manager.ICombatManager;
import com.github.sirblobman.combatlogx.api.object.UntagReason;

import combatlogx.expansion.cheat.prevention.ICheatPreventionExpansion;
import combatlogx.expansion.cheat.prevention.configuration.IGameModeConfiguration;

public final class ListenerGameMode extends CheatPreventionListener {
    public ListenerGameMode(@NotNull ICheatPreventionExpansion expansion) {
        super(expansion);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onSwitch(PlayerGameModeChangeEvent e) {
        Player player = e.getPlayer();
        if (isInCombat(player)) {
            GameMode gameMode = e.getNewGameMode();
            GameMode forceMode = getForceSwitchMode();
            if (gameMode == forceMode) {
                return;
            }

            if (isPreventSwitching()) {
                e.setCancelled(true);
                sendMessage(player, "expansion.cheat-prevention.game-mode.no-switch");
                return;
            }

            checkUntag(player);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTag(PlayerTagEvent e) {
        if (isForceSwitch()) {
            Player player = e.getPlayer();
            GameMode gameMode = getForceSwitchMode();
            player.setGameMode(gameMode);

            Replacer replacer = getGameModeReplacer(player, gameMode);
            sendMessage(player, "expansion.cheat-prevention.game-mode.force-switch", replacer);
        }
    }

    private void checkUntag(@NotNull Player player) {
        if (isUntagOnSwitch()) {
            ICombatManager combatManager = getCombatManager();
            combatManager.untag(player, UntagReason.EXPIRE);
        }
    }

    /**
     * Language files saved before the {@code game-mode.*} keys existed keep the previous behaviour (the enum name).
     */
    private @NotNull Replacer getGameModeReplacer(@NotNull Player player, @NotNull GameMode gameMode) {
        LanguageManager languageManager = getLanguageManager();
        String key = "game-mode." + gameMode.name().toLowerCase(Locale.US);
        String missing = String.format(Locale.US, "{%s}", key);
        if (missing.equals(languageManager.getMessageRaw(player, key))) {
            return new StringReplacer("{game_mode}", gameMode.name());
        }

        Component gameModeName = languageManager.getMessage(player, key);
        return new ComponentReplacer("{game_mode}", gameModeName);
    }

    private @NotNull IGameModeConfiguration getGameModeConfiguration() {
        ICheatPreventionExpansion expansion = getCheatPrevention();
        return expansion.getGameModeConfiguration();
    }

    private boolean isPreventSwitching() {
        IGameModeConfiguration gameModeConfiguration = getGameModeConfiguration();
        return gameModeConfiguration.isPreventSwitching();
    }

    private boolean isForceSwitch() {
        IGameModeConfiguration gameModeConfiguration = getGameModeConfiguration();
        return gameModeConfiguration.isForceSwitch();
    }

    private boolean isUntagOnSwitch() {
        IGameModeConfiguration gameModeConfiguration = getGameModeConfiguration();
        return gameModeConfiguration.isUntagOnSwitch();
    }

    private @NotNull GameMode getForceSwitchMode() {
        IGameModeConfiguration gameModeConfiguration = getGameModeConfiguration();
        return gameModeConfiguration.getForceMode();
    }
}
