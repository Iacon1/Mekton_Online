package Client.Frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import GameEngine.ConfigManager;
import GameEngine.GameWorld;
import GameEngine.GraphicsCanvas;
import GameEngine.Hexmap;
import GameEngine.PacketTypes.GameDataPacket;
import Utils.JSONManager;
import Utils.Logging;
import Utils.MiscUtils;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class ClientGameFrame extends JFrame
{

	private JPanel contentPane;
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
	
	private String command_;
	
	public void updateUIStuff(GameDataPacket packet) // Updates UI stuff
	{
		Hexmap map = (Hexmap) GameWorld.getWorld().getInstances().get(packet.currentLocationId);
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
	
	// Don't run this!
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ClientGameFrame frame = new ClientGameFrame();
					frame.setVisible(true);
				}
				catch (Exception e) {Logging.logException(e);}
			}
		});
	}

	public ClientGameFrame()
	{
		commandBox.setColumns(10);
		setTitle(MiscUtils.getProgramName() + " Client: Game Window");
		
		setIconImages(MiscUtils.getIcons(true));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		getContentPane().setLayout(null);
		
		getContentPane().setPreferredSize(new Dimension(ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight()));
		
		tabbedPane.setBounds(0, 0, ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight());
		getContentPane().add(tabbedPane);
		
		tabbedPane.addTab("Map", null, mapViewPanel, null);
		tabbedPane.setEnabledAt(0, true);
		
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
		
		tabbedPane.addTab("Inventory", null, inventoryPanel, null);
		tabbedPane.setEnabledAt(1, false);
		
		tabbedPane.addTab("Character", null, characterPanel, null);
		tabbedPane.setEnabledAt(2, false);
		
		tabbedPane.addTab("Mech", null, mekPanel, null);
		tabbedPane.setEnabledAt(3, false);
		
		pack();
		
	}
}
