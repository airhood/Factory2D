package object;

import entity.Entity;
import item.Item;

public class ItemObject extends Entity {
	
	public Item item;
	
	public ItemObject(Item item, int x, int y) {
		this.item = item;
		worldX = x;
		worldY = y;
	}
}
