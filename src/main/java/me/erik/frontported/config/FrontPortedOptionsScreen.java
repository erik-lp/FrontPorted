package me.erik.frontported.config;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class FrontPortedOptionsScreen extends GameOptionsScreen {
    
    protected final List<Option> options;
    
    public FrontPortedOptionsScreen(Screen parent, Text title, Config.Category category) {
        super(parent, null, title);
        this.options = generateOptions(category);
    }
    
    private List<Option> generateOptions(Config.Category category) {
        Config inst = FrontPorted.config;
        List<Option> options = new ArrayList<>();
        for (Field field : inst.get(category)) {
            assert field.isAnnotationPresent(me.erik.frontported.config.Config.Option.class) : "... how? HOW did this happen???";
            if (field.getType().isAssignableFrom(boolean.class)) {
                options.add(new BooleanOption(
                        "frontported.options." + category.toString() + "." + field.getName(),
                        b -> {
                            try {
                                return field.getBoolean(inst);
                            } catch (IllegalAccessException ex) {
                                System.out.println("Warning: Field annotated with @Option is not accessible!");
                                return false;
                            }
                        },
                        (s, b) -> {
                            try {
                                field.setBoolean(inst, b);
                            } catch (IllegalAccessException ex) {
                                System.out.println("Warning: Field annotated with @Option is not accessible!");
                            }
                        }
                ));
            } else if (field.getType().isAssignableFrom(double.class)) {
                if (!field.isAnnotationPresent(Config.DoubleOption.class)) {
                    System.out.println("Warning: Field of type double is annotated with @Option but not with @DoubleOption!");
                    continue;
                }
                options.add(new DoubleOption(
                        "frontported.options." + category.toString() + "." + field.getName(),
                        field.getAnnotation(Config.DoubleOption.class).min(),
                        field.getAnnotation(Config.DoubleOption.class).max(),
                        field.getAnnotation(Config.DoubleOption.class).step(),
                        p -> {
                            try {
                                return field.getDouble(inst);
                            } catch (IllegalAccessException ex) {
                                System.out.println("Warning: Field annotated with @Option is not accessible!");
                                return 0D;
                            }
                        },
                        (s, d) -> {
                            try {
                                field.setDouble(inst, d);
                            } catch (IllegalAccessException ex) {
                                System.out.println("Warning: Field annotated with @Option is not accessible!");
                            }
                        },
                        (s, t) -> {
                            try {
                                return new TranslatableText("frontported.options." + category + "." + field.getName(), String.format("%.0f", field.getDouble(inst)));
                            } catch (IllegalAccessException ex) {
                                System.out.println("Warning: Field annotated with @Option is not accessible!");
                                return Text.of("");
                            }
                        }
                ));
            } else {
                throw new IllegalStateException("Warning: Found field of type " + field.getType() + " annotated with @Option! Only boolean and double are allowed!");
            }
        }
        return new ArrayList<>(options);
    }
    
    @Override
    protected final void init() {
        
        if (this.client == null)
            return;
        
        final boolean twoSided = this.options.size() > 10;
        
        for (int i = 0; i < this.options.size(); i++) {
            final Option option = this.options.get(i);
            final int x = twoSided ? (((this.width / 2) - 155) + ((i % 2) * 160)) : ((this.width / 2) - 75);
            final int y = twoSided ? (((this.height / 6) - 12) + (24 * (i >> 1))) : ((this.height / 6) + (24 * i));
            final AbstractButtonWidget button = option.createButton(this.client.options, x, y, 150);
            this.addButton(button);
        }
        
        this.addAdditionalButtons();
        
        this.addButton(new ButtonWidget(
                (this.width / 2) - 100,
                (this.height / 6) + ((twoSided ? 12 : 24) * (this.buttons.size() + 1)),
                200, 20,
                ScreenTexts.DONE,
                (b) -> this.onClose()
        ));
        
    }
    
    protected void addAdditionalButtons() {
    }
    
    @Override
    public final void onClose() {
        FrontPorted.saveConfig();
        super.onClose();
    }
    
    @Override
    public final void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
    
}
