package net.throckmorpheus.incursion;

import net.fabricmc.api.ModInitializer;
import net.throckmorpheus.incursion.block.ModBlocks;
import net.throckmorpheus.incursion.block.entity.ModBlockEntities;
import net.throckmorpheus.incursion.screen.ModScreenHandlers;
import net.throckmorpheus.incursion.structure.ModStructures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Incursion implements ModInitializer {
	public static final String MOD_ID = "incursion";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initialised "+MOD_ID);
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerAllBlockEntities();
		ModStructures.registerStructureFeatures();
	}
}
