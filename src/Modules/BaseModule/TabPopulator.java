// By Iacon1
// Created 06/17/2021
// Populates the tabs of ClientMainGameFrame

package Modules.BaseModule;

import javax.swing.JTabbedPane;

import GameEngine.GameFrame;

public interface TabPopulator
{
	public void populateTabs(GameFrame frame, JTabbedPane tabbedPane);
}
