package Modules.BaseModule.ClientFrames;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;

import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import GameEngine.GraphicsCanvas;
import GameEngine.Configurables.ConfigManager;
import GameEngine.Configurables.ModuleManager;
import GameEngine.PacketTypes.GameDataPacket;
import Modules.BaseModule.TabPopulator;
import Modules.MektonCore.Hexmap;
import Utils.Logging;
import Utils.MiscUtils;

@SuppressWarnings("serial")
public class ClientMainGameFrame extends JFrame
{
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	private final JLayeredPane layeredPane = new JLayeredPane();
	private final JPanel BaseGUIPanel = new JPanel();
	private final JPanel commandPanel = new JPanel();
	private final JLabel commandLabel = new JLabel("Command:");
	private final JTextField commandBox = new JTextField();
	
	private HashMap<String, JPanel> panels_;
	private ArrayList<String> panelIDs_;
	
	private String command_;
	
	public void updateUIStuff(GameDataPacket packet) // Updates UI stuff
	{
		Hexmap<?> map = (Hexmap<?>) packet.ourView.getEntities().get(packet.currentLocationId);
		GraphicsCanvas canvas = (GraphicsCanvas) getTab("Map").getComponent(0);
		canvas.setRenderer(map);
		canvas.setLayout(null);
		canvas.repaint();
	}
	
	private void resetInputs() // Resets all input elements
	{
		command_ = null;
		commandBox.setText(null);
	}
	public String getCommand() // Gets input, resets if not empty, returns null if empty
	{
		if (command_ == null)
			return null;
		else
		{
			String command = command_;
			resetInputs();
			
			return command;
		}
	}
	
	public void addTab(String name, JPanel panel)
	{
		int id = panelIDs_.size();
		
		panels_.put(name, panel);
		panelIDs_.add(name);
		
		tabbedPane.addTab(name, null, panel, null);
		tabbedPane.setEnabledAt(id, true);
	}
	public void removeTab(String name)
	{
		int id = panelIDs_.indexOf(name);
		tabbedPane.removeTabAt(id);
		panels_.remove(name);
		panelIDs_.remove(id);
	}
	public JPanel getTab(String name)
	{
		return panels_.get(name);
	}
	
	public ClientMainGameFrame()
	{
		panels_ = new HashMap<String, JPanel>();
		panelIDs_ = new ArrayList<String>();
		
		commandBox.setColumns(10);
		
		setTitle(MiscUtils.getProgramName() + " Client: Game Window");
		
		setIconImages(MiscUtils.getIcons(true));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		getContentPane().setLayout(null);
		
		getContentPane().setPreferredSize(new Dimension(ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight()));
		
		//Set up layered pane
		layeredPane.setBounds(0, 0, ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight());
		add(layeredPane);
		layeredPane.setLayer(BaseGUIPanel, 1);
		layeredPane.setLayer(tabbedPane, 0);
		
		layeredPane.add(BaseGUIPanel);
		layeredPane.add(tabbedPane);
		
		//Set up Base GUI
		BaseGUIPanel.setOpaque(false);
		BaseGUIPanel.setBounds(layeredPane.getBounds());
		SpringLayout sl_BaseGUIPanel = new SpringLayout();
		sl_BaseGUIPanel.putConstraint(SpringLayout.WEST, commandPanel, 0, SpringLayout.WEST, BaseGUIPanel);
		sl_BaseGUIPanel.putConstraint(SpringLayout.EAST, commandPanel, 0, SpringLayout.EAST, BaseGUIPanel);
		sl_BaseGUIPanel.putConstraint(SpringLayout.SOUTH, commandPanel, 0, SpringLayout.SOUTH, BaseGUIPanel);
		sl_BaseGUIPanel.putConstraint(SpringLayout.NORTH, commandPanel, -50, SpringLayout.SOUTH, BaseGUIPanel);
		BaseGUIPanel.setLayout(sl_BaseGUIPanel);
		
		// Set up command panel
		BaseGUIPanel.add(commandPanel);
		SpringLayout sl_commandPanel = new SpringLayout();
		sl_commandPanel.putConstraint(SpringLayout.SOUTH, commandLabel, -5, SpringLayout.SOUTH, commandPanel);
		sl_commandPanel.putConstraint(SpringLayout.WEST, commandBox, 10, SpringLayout.EAST, commandLabel);
		sl_commandPanel.putConstraint(SpringLayout.WEST, commandLabel, 10, SpringLayout.WEST, commandPanel);
		sl_commandPanel.putConstraint(SpringLayout.SOUTH, commandBox, -5, SpringLayout.SOUTH, commandPanel);
		sl_commandPanel.putConstraint(SpringLayout.EAST, commandBox, -10, SpringLayout.EAST, commandPanel);
		commandPanel.setLayout(sl_commandPanel);
		commandLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		commandPanel.add(commandLabel);
		commandPanel.add(commandBox);
		commandBox.addActionListener(e -> {command_ = commandBox.getText();});
		commandPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		// Set up tabbed pane
		tabbedPane.setBounds(layeredPane.getBounds());
		
		TabPopulator populator = (TabPopulator) ModuleManager.getHighestImplementer("populateTabs");
		populator.populateTabs(this, tabbedPane);
		
		pack();
		
	}
}
