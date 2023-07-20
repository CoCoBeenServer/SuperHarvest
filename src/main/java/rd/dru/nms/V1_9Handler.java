package rd.dru.nms;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;
import org.bukkit.material.NetherWarts;

public class V1_9Handler implements NMSHandler{
	@Override
	public boolean breakBlock(Player p, Block b) {
		// TODO Auto-generated method stub
		return NMSHandler.super.breakBlock(p, b);
	}
	@Override
	public ItemStack getItemInHand(Player p) {
		// TODO Auto-generated method stub
		return p.getInventory().getItemInMainHand();
	}
	
	@Override
	public void crackBlock(Block b, Material type) {
		MaterialData d = new MaterialData(type);
		b.getWorld().spawnParticle(Particle.BLOCK_CRACK, b.getLocation().add(0.5,0.5,0.5), 25, 1, 0.1,
				0.1, 0.1, d);
	} 
	
	@Override
	public void crackCrop(Block b, Material type) {
		if(b.getState().getData() instanceof NetherWarts)
			crackBlock(b, Material.matchMaterial("NETHER_WARTS"));
		else
			crackBlock(b, Material.matchMaterial("CROPS"));
	}
	
	@Override
	public void playSound(Block b, NSound sound) {
		// TODO Auto-generated method stub
		Sound e;
		switch(sound) {
		case Farm:
			if(b.getState().getData() instanceof NetherWarts)
				e = Sound.BLOCK_STONE_BREAK;
			else
				e = Sound.BLOCK_GRASS_BREAK;
			break;
		case Ore:
			e = Sound.BLOCK_STONE_BREAK;
			break;
		case Tree:
			e = Sound.BLOCK_WOOD_BREAK;	
			break;
		default:
			e = Sound.BLOCK_GRASS_BREAK;
			break;
			
		}
		b.getWorld().playSound(b.getLocation(), e, 1, 1);	
	}
	
	@Override
	public void playSound(Block b, Material sound) {
		playSound(b, NSound.Ore);
	}
	@Override
	public boolean canCropHarvest(Block b) {
		if(!(isCrop(b)))
			return false;
		
		return b.getState().getData() instanceof Crops ? ((Crops)b.getState().getData()).getState().equals(CropState.RIPE) :
			((NetherWarts)b.getState().getData()).getState().equals(NetherWartsState.RIPE);
	}
	
	@Override
	public boolean isCrop(Block b) {
		// TODO Auto-generated method stub
		return b.getState().getData() instanceof Crops || b.getState().getData() instanceof NetherWarts;
	}
	
	@Override
	public boolean isFarmLnad(Block b) {
		// TODO Auto-generated method stub
		return b.getType().equals(Material.matchMaterial("SOIL"))||b.getType().equals(Material.SOUL_SAND);
	}
	
	@Override
	public void actionBarMes(Player p, String mes) {
		if(VersionChecker.getServerVersion()<11)
			LegacyMethod.sendActionBar(p, mes);
		else 
			new V1_13Handler().actionBarMes(p, mes);
			
	}

	@Override
	public void titleBarMes(Player p, String mes) {
		if(VersionChecker.getServerVersion()<10)
			LegacyMethod.sendTitle(p, " ", mes, 0, 20, 10);		
		else 
			new V1_13Handler().titleBarMes(p, mes);
	}
}
