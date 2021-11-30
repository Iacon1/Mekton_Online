// By Iacon1
// Created 11/29/2021
// Module for populating the editor.

package GameEngine.Configurables.ModuleTypes;

import java.util.List;

import javax.swing.JPanel;

import GameEngine.EntityTypes.GameEntity;

public interface EditorPopulatingModule extends Module
{
	/** Populates the list of entity types that can be added in the editor.
	 *  Does not neccessarily overwrite the defaults.
	 * 
	 */
	public void populateEntityTypes(List<Class<? extends GameEntity>> typeList);
	
	/** Populates the list of editor panels.
	 *  Does not neccessarily overwrite the defaults.
	 * 
	 */
	public void populateTabs(List<JPanel> panels);
}
