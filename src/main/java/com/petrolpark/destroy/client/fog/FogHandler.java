package com.petrolpark.destroy.client.fog;

import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import com.simibubi.create.foundation.utility.animation.LerpedFloat.Chaser;

public class FogHandler {

    protected Color targetColor = Color.BLACK;
    protected Color lastColor = Color.TRANSPARENT_BLACK;
    protected LerpedFloat colorMix = LerpedFloat.linear();

    public void tick() {
        colorMix.tickChaser();
        if (colorMix.getValue() >= 1d) lastColor = targetColor;
    };

    public void setTargetColor(Color color, float partialTicks) {
        if (color.equals(targetColor)) return;
        if (lastColor.equals(Color.TRANSPARENT_BLACK)) {
            lastColor = color;
        } else {
            lastColor = getColor(partialTicks);
        }
        //lastColor = Color.mixColors(lastColor, targetColor, colorMix.getValue());
        targetColor = color;
        colorMix.setValue(0d);
        colorMix.chase(1d, 0.2d, Chaser.EXP);
    };

    public Color getColor(float partialTicks) {
        return Color.mixColors(lastColor, targetColor, colorMix.getValue(partialTicks));
    };
};
