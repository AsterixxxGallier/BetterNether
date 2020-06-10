package paulevs.betternether.biomes;

import java.util.Random;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.sound.MusicType;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.MixedNoisePoint;
import net.minecraft.world.biome.Biome.Settings;
import net.minecraft.world.biome.BiomeEffects.Builder;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import paulevs.betternether.MHelper;

public class BiomeDefenition
{
	private static final Random random = new Random();
	
	private String name;
	private int color;
	private SoundEvent loop;
	private BiomeMoodSound mood;
	private BiomeAdditionsSound additions;
	private MixedNoisePoint noise;
	private BiomeParticleConfig particleConfig;
	
	public BiomeDefenition(String name)
	{
		this.name = name;
		random.setSeed(name.hashCode());
		noise = new Biome.MixedNoisePoint(
				random.nextFloat(),
				random.nextFloat(),
				random.nextFloat(),
				random.nextFloat() * 2 - 1, 1.0F);
	}
	
	/**
	 * Sets biome sky color
	 * @param r - Red [0 - 255]
	 * @param g - Green [0 - 255]
	 * @param b - Blue [0 - 255]
	 * @return this BiomeDefenition
	 */
	public BiomeDefenition setColor(int r, int g, int b)
	{
		this.color = MHelper.color(r, g, b);
		return this;
	}
	
	/**
	 * Plays in never-ending loop for as long as player is in the biome
	 * @param loop - SoundEvent
	 * @return this BiomeDefenition
	 */
	public BiomeDefenition setLoop(SoundEvent loop)
	{
		this.loop = loop;
		return this;
	}
	
	/**
	 * Plays commonly while the player is in the biome
	 * @param mood - SoundEvent
	 * @return this BiomeDefenition
	 */
	public BiomeDefenition setMood(SoundEvent mood)
	{
		this.mood = new BiomeMoodSound(mood, 6000, 8, 2.0D);
		return this;
	}
	
	/**
	 * Plays once every 6000-17999 ticks while the player is in the biome
	 * @param additions - SoundEvent
	 * @return this BiomeDefenition
	 */
	public BiomeDefenition setAdditions(SoundEvent additions)
	{
		this.additions = new BiomeAdditionsSound(additions, 0.0111);
		return this;
	}
	
	public Settings buildBiomeSettings()
	{
		Builder effects = new Builder()
				.waterColor(4159204)
				.waterFogColor(329011)
				.fogColor(color);
		if (loop != null)
			effects.loopSound(loop);
		if (mood != null)
			effects.moodSound(mood);
		if (additions != null)
			effects.additionsSound(additions);
		if (particleConfig != null)
			effects.particleConfig(particleConfig);
		effects.music(MusicType.method_27283(SoundEvents.MUSIC_NETHER_WARPED_FOREST));
		
		return new Settings()
				.configureSurfaceBuilder(SurfaceBuilder.NETHER, SurfaceBuilder.NETHER_CONFIG)
				.precipitation(Biome.Precipitation.NONE)
				.category(Biome.Category.NETHER)
				.depth(0.1F)
				.scale(0.2F)
				.temperature(2.0F)
				.downfall(0.0F)
				.effects(effects.build())
				.parent((String) null)
				.noises(ImmutableList.of(noise));
	}
	
	public String getName()
	{
		return name;
	}
	
	public BiomeDefenition setParticleConfig(BiomeParticleConfig config)
	{
		this.particleConfig = config;
		return this;
	}
}