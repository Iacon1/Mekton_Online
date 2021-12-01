package GameEngine.Editor;

import javax.swing.JPanel;

import GameEngine.Configurables.ModuleTypes.Module;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
@SuppressWarnings("serial")
public class ModulePane extends JPanel
{

	/**
	 * Create the panel.
	 */
	public ModulePane(Module.ModuleConfig config)
	{
		setName(config.moduleName);
		
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JLabel nameLabel = new JLabel("Name: " + config.moduleName);
		springLayout.putConstraint(SpringLayout.NORTH, nameLabel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, this);
		add(nameLabel);
		
		JLabel versionLabel = new JLabel("Version: " + config.moduleVersion);
		springLayout.putConstraint(SpringLayout.NORTH, versionLabel, 10, SpringLayout.SOUTH, nameLabel);
		springLayout.putConstraint(SpringLayout.WEST, versionLabel, 0, SpringLayout.WEST, nameLabel);
		add(versionLabel);
		
		JLabel descriptionLabel = new JLabel("Description: \n" + config.moduleDescription);
		springLayout.putConstraint(SpringLayout.NORTH, descriptionLabel, 10, SpringLayout.SOUTH, versionLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionLabel, 0, SpringLayout.WEST, nameLabel);
		add(descriptionLabel);
		
	}
}
