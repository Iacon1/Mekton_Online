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
import GameEngine.GraphicsCanvas;
import GameEngine.Hexmap;
import Utils.Logging;
import Utils.MiscUtils;

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
	private final GraphicsCanvas HexmapCanvas = new GraphicsCanvas();
	
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
				} catch (Exception e)
				{
					Logging.logException(e);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientGameFrame()
	{
		setTitle(MiscUtils.getProgramName() + " Client: Game Window");
		
		setIconImages(MiscUtils.getIcons(true));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		getContentPane().setLayout(null);
		
		getContentPane().setPreferredSize(new Dimension(ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight()));
		
		tabbedPane.setBounds(0, 0, ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight());
		getContentPane().add(tabbedPane);
		
		tabbedPane.addTab("Map", null, mapViewPanel, null);
		mapViewPanel.setLayout(null);
		layeredPane.setBounds(0, -10, ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight());
		
		mapViewPanel.add(layeredPane);
		layeredPane.setLayer(GUIPanel, 0);
		GUIPanel.setBounds(0, 0, ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight());
		
		layeredPane.add(GUIPanel);
		GUIPanel.setLayout(new SpringLayout());
		layeredPane.setLayer(HexmapCanvas, 1);
		HexmapCanvas.setBounds(0, 0, 640, 480);

		layeredPane.add(HexmapCanvas);
		
		tabbedPane.addTab("Inventory", null, inventoryPanel, null);
		
		tabbedPane.addTab("Character", null, characterPanel, null);
		
		tabbedPane.addTab("Mech", null, mekPanel, null);
		
		Hexmap map = new Hexmap();
		map.setDimensions(18, 9, 1);
		HexmapCanvas.setRenderer(map);
		
		pack();
		
	}

}
