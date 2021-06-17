package Modules.BaseModule.ClientFrames;

import java.awt.Dimension;
import java.awt.EventQueue;

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
import GameEngine.Hexmap;
import GameEngine.Configurables.ConfigManager;
import GameEngine.PacketTypes.GameDataPacket;
import Utils.Logging;
import Utils.MiscUtils;

@SuppressWarnings("serial")
public class ClientMainGameFrame extends JFrame
{
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	private final JPanel mapViewPanel = new JPanel();
	private final JPanel inventoryPanel = new JPanel();
	private final JPanel characterPanel = new JPanel();
	private final JPanel mekPanel = new JPanel();
	private final JLayeredPane layeredPane = new JLayeredPane();
	private final JPanel GUIPanel = new JPanel();
	private final GraphicsCanvas hexmapCanvas = new GraphicsCanvas();
	private final JPanel commandPanel = new JPanel();
	private final JLabel commandLabel = new JLabel("Command:");
	private final JTextField commandBox = new JTextField();
	
	private HashMap<String, JPanel> panels_;
	private ArrayList<String> panelIDs_;
	
	private String command_;
	
	public void updateUIStuff(GameDataPacket packet) // Updates UI stuff
	{
		Hexmap<?> map = (Hexmap<?>) packet.ourView.getEntities().get(packet.currentLocationId);
		hexmapCanvas.setRenderer(map);
		hexmapCanvas.setLayout(null);
		hexmapCanvas.repaint();
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
		
		tabbedPane.setBounds(0, 0, ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight());
		getContentPane().add(tabbedPane);
		
		mapViewPanel.setLayout(null);
		mapViewPanel.setSize(tabbedPane.getWidth(), tabbedPane.getHeight() - 25); // TODO calculate size of tabs
		mapViewPanel.add(layeredPane);
		layeredPane.setBounds(0, 0, mapViewPanel.getWidth(), mapViewPanel.getHeight());
		
		layeredPane.setLayer(GUIPanel, 1);
		GUIPanel.setOpaque(false);
		
		layeredPane.add(GUIPanel);
		GUIPanel.setSize(layeredPane.getSize());
		SpringLayout sl_GUIPanel = new SpringLayout();
		
		sl_GUIPanel.putConstraint(SpringLayout.WEST, commandPanel, 0, SpringLayout.WEST, GUIPanel);
		sl_GUIPanel.putConstraint(SpringLayout.EAST, commandPanel, 0, SpringLayout.EAST, GUIPanel);
		sl_GUIPanel.putConstraint(SpringLayout.SOUTH, commandPanel, 0, SpringLayout.SOUTH, GUIPanel);
		sl_GUIPanel.putConstraint(SpringLayout.NORTH, commandPanel, -50, SpringLayout.SOUTH, GUIPanel);
		
		GUIPanel.setLayout(sl_GUIPanel);
		commandPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		GUIPanel.add(commandPanel);
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
		layeredPane.setLayer(hexmapCanvas, 0);
		hexmapCanvas.setBounds(0, 0, ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight());

		layeredPane.add(hexmapCanvas);
		
		addTab("Map", mapViewPanel);
		addTab("Inventory", inventoryPanel);
		addTab("Character", characterPanel);
		addTab("Mech", mekPanel);
		
		pack();
		
	}
}
