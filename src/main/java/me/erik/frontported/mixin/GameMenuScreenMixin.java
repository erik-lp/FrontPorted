package me.erik.frontported.mixin;

import me.erik.frontported.config.FrontPortedMainScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
 */
@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
    
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }
    
    @Inject(at = @At("TAIL"), method = "init()V", require = 1)
    public void init(CallbackInfo info) {
        if (this.client == null)
            return;
        addButton(new ButtonWidget(5, height - 25, 150, 20,
                new TranslatableText("frontported.main.title"), (w) -> this.client.openScreen(new FrontPortedMainScreen(this))));
    }
    
}
