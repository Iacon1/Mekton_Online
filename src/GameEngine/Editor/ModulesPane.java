package GameEngine.Editor;

import javax.swing.JPanel;

import GameEngine.Configurables.ModuleManager;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class ModulesPane extends JPanel
{

	/**
	 * Create the panel.
	 */
	public ModulesPane()
	{
		super();
		setName("Modules");
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setTabPlacement(JTabbedPane.LEFT);
		springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, tabbedPane, 0, SpringLayout.EAST, this);
		add(tabbedPane);

//		for (int i = 0; i < ModuleManager.size(); ++i)
//			tabbedPane.add(ModuleManager.getModule(i).getEditorPanel());
	}

}
