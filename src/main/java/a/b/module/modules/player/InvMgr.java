package a.b.module.modules.player;

import a.b.module.Module;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.InvUtils;
import a.b.utils.Timer;
import a.b.utils.Utils;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class InvMgr extends Module {

    public static SliderSetting delay, blockSlot, foodSlot, pearlSlot;
    public static TickSetting invOnly, sort, clean;

    public InvMgr() {
        super("InvManager", ModuleCategory.player);
        this.registerSetting(delay = new SliderSetting("Delay", 2, 0, 50, 1));
        this.registerSetting(blockSlot = new SliderSetting("Block", 2, 1, 9, 1));
        this.registerSetting(foodSlot  = new SliderSetting("Food", 3, 1, 9, 1));
        this.registerSetting(pearlSlot = new SliderSetting("Pearl", 4, 1, 9, 1));
        this.registerSetting(invOnly  = new TickSetting("Inv Only", true));
        this.registerSetting(clean = new TickSetting("Clean", true));
        this.registerSetting(sort  = new TickSetting("Sort", false));
    }

    @SubscribeEvent
    public void s(TickEvent.PlayerTickEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;
        if(InvUtils.isChatOpen() || InvUtils.isPauseMenuOpen())
            return;

        if(invOnly.isToggled())
            if(!InvUtils.isInvOpen())
                return;

        for(int i = 9; i<mc.thePlayer.inventoryContainer.getInventory().size(); i++) {
            if(!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack())
                continue;

            ItemStack stackInSlot = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item itemInSlot = stackInSlot.getItem();

            long s = (long) (delay.getInput()*25);
            String getName = itemInSlot.getUnlocalizedName();

            if((itemInSlot instanceof ItemPotion && InvUtils.isBadPotion(stackInSlot)) ||
                    getName.startsWith("Raw")  || getName.startsWith("Gunpo") ||
                    getName.contains("snow") || getName.contains("stick") ||
                    getName.contains("seeds") || getName.contains("bottle") ||
                    getName.contains("string") || getName.contains("bucket") ||
                    getName.contains("feather") || getName.contains("piston") ||
                    getName.contains("sugar") || getName.contains("flower") ||
                    getName.contains("wheat") || getName.contains("tnt") ||
                    getName.contains("boat") || getName.contains("torch") ||
                    getName.contains("web") || itemInSlot instanceof ItemEgg
            ) {
                if(clean.isToggled())
                    if(Timer.hasTimeElapsed(s, true))
                        InvUtils.clean(i);

                if((itemInSlot instanceof ItemPotion && InvUtils.isBadPotion(stackInSlot)) ||
                        getName.startsWith("Raw")  || getName.startsWith("Gunpo") ||
                        getName.contains("snow") || getName.contains("stick") ||
                        getName.contains("seeds") || getName.contains("bottle") ||
                        getName.contains("string") || getName.contains("bucket") ||
                        getName.contains("feather") || getName.contains("piston") ||
                        getName.contains("sugar") || getName.contains("flower") ||
                        getName.contains("wheat") || getName.contains("tnt") ||
                        getName.contains("boat") || getName.contains("torch") ||
                        getName.contains("web") || itemInSlot instanceof ItemEgg
                )

                    if(clean.isToggled())
                        if(Timer.hasTimeElapsed(s+10, true))
                            InvUtils.swapShift(i);
            } else {
                if(itemInSlot instanceof ItemBlock && i != 36) {
                    if(getName.equals("Gravel") || getName.equals("Sand")) return;

                    if(sort.isToggled())
                        if(Timer.hasTimeElapsed(s, true))
                            InvUtils.sortBlock((int) blockSlot.getInput());
                }
                if(itemInSlot instanceof ItemFood && i != 36) {
                    if(getName.startsWith("Raw")) return;

                    if(sort.isToggled())
                        if(Timer.hasTimeElapsed(s, true))
                            InvUtils.sortFood((int) foodSlot.getInput());
                }
                if(itemInSlot instanceof ItemEnderPearl && i != 36) {
                    if(getName.contains("Corrupted")) return;

                    if(sort.isToggled())
                        if(Timer.hasTimeElapsed(s, true))
                            InvUtils.sortPearl((int) pearlSlot.getInput());
                }
            }
        }
    }

}