package me.erik.frontported.mixin;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin<T extends ScreenHandler> extends HandledScreen<T> {
    
    @Shadow protected boolean drawStatusEffects;
    
    public AbstractInventoryScreenMixin(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    
    /**
     * @reason Disable inventory offset from potion effects
     * @author ErikLP
     */
    @Overwrite
    public void applyStatusEffectOffset() {
        if ((this.client != null) && (this.client.player != null) && this.client.player.getStatusEffects().isEmpty()) {
            this.x = (this.width - this.backgroundWidth) / 2;
            this.drawStatusEffects = false;
        } else if (FrontPorted.config.disableInventoryOffset) {
            this.x = (this.width - this.backgroundWidth) / 2;
            this.drawStatusEffects = true;
        } else {
            this.x = 160 + ((this.width - this.backgroundWidth - 200) / 2);
            this.drawStatusEffects = true;
        }
    }
    
}
