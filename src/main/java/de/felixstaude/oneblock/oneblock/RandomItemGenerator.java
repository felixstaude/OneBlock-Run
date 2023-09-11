package de.felixstaude.oneblock.oneblock;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomItemGenerator {
    private static final List<Material> ITEM_MATERIALS = new ArrayList<>();
    private static final Random RANDOM = new Random();

    static {
        for (Material material : Material.values()) {
            // remove unwanted items
            if (material.isItem() && !material.getKey().equals(Material.AIR.getKey()) &&
                    !material.getKey().equals(Material.LIGHT.getKey()) &&
                    !material.getKey().equals(Material.BARRIER.getKey()) &&
                    !material.getKey().equals(Material.STRUCTURE_VOID.getKey()) &&
                    !material.getKey().equals(Material.STRUCTURE_BLOCK.getKey()) &&
                    !material.getKey().equals(Material.COMMAND_BLOCK.getKey()) &&
                    !material.getKey().equals(Material.COMMAND_BLOCK_MINECART.getKey()) &&
                    !material.getKey().equals(Material.CHAIN_COMMAND_BLOCK.getKey()) &&
                    !material.getKey().equals(Material.REPEATING_COMMAND_BLOCK.getKey()) &&
                    !material.getKey().equals(Material.JIGSAW.getKey())
            ){
                ITEM_MATERIALS.add(material);
            }
        }
    }

    public static ItemStack getRandomItemStack() {
        Material randomMaterial = ITEM_MATERIALS.get(RANDOM.nextInt(ITEM_MATERIALS.size()));
        System.out.println(randomMaterial.toString());
        return new ItemStack(randomMaterial);

    }
}
