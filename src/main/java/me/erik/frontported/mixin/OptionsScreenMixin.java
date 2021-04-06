package me.erik.frontported.mixin;

import me.erik.frontported.FrontPortedOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
 * @author Toshimichi0915
 */
@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {
    
    @Final
    @Shadow
    private GameOptions settings;
    
    protected OptionsScreenMixin(Text title) {
        super(title);
    }
    
    @Inject(at = @At("TAIL"), method = "init()V", require = 1)
    public void init(CallbackInfo info) {
        if (this.client == null)
            return;
        addButton(new ButtonWidget(width / 2 - 155, height / 6 + 144 - 6, 150, 20,
                new TranslatableText("frontported.options.title"), (w) -> this.client.openScreen(new FrontPortedOptionsScreen(this, settings))));
    }
    
}
