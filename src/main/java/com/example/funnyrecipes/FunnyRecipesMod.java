package com.example.funnyrecipes;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunnyRecipesMod implements ModInitializer {
    public static final String MOD_ID = "funnyrecipes";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("FunnyRecipes loaded! Craft diamonds from dirt. You deserve it.");
    }
}
