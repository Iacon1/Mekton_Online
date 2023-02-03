// By Iacon1
// Created 01/11/2023
//

package GameEngine.MenuStuff;

import java.util.function.Supplier;

public interface MenuSlatePopulator
{
	public void populate(MenuSlate slate, Supplier<MenuSlate> supplier);
}
