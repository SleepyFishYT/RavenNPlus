package a.b.module.modules.movement;

import a.b.module.Module;
import a.b.module.setting.impl.SliderSetting;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Step extends Module {

    public static SliderSetting hGt;

    public Step() {
        super("Step", ModuleCategory.movement);
        this.registerSetting(hGt = new SliderSetting("Height", 3, 1, 50, 1));
    }

    @SubscribeEvent
    public void s(TickEvent.PlayerTickEvent e) {
        if (!GameSettings.isKeyDown(mc.gameSettings.keyBindJump) && mc.thePlayer.fallDistance < 0.1f) {
            mc.thePlayer.stepHeight = (float) hGt.getInput();
        } else {
            mc.thePlayer.stepHeight = 0.5f;
        }
    }

}