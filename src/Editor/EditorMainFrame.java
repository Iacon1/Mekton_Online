package Editor;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GameEngine.GameInfo;
import GameEngine.Configurables.ConfigManager;
import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.EditorPopulatingModule;
import GameEngine.Editor.ModulesPane;
import Utils.Logging;
import Utils.MiscUtils;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class EditorMainFrame extends JFrame
{

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	
	private void populateTabs()
	{
		List<JPanel> panels = new ArrayList<JPanel>();
		
		panels.add(new ModulesPane());
		
		if (ModuleManager.getHighestOfType(EditorPopulatingModule.class) != null)
			ModuleManager.getHighestOfType(EditorPopulatingModule.class).populateTabs(panels);
		
		for (int i = 0; i < panels.size(); ++i) tabbedPane.add(panels.get(i));
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					EditorMainFrame frame = new EditorMainFrame(args[0]);
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
	public EditorMainFrame(String serverPack)
	{
		setTitle(MiscUtils.getProgramName() + " Editor");
		
		GameInfo.setServerPack(serverPack);
		ModuleManager.init();
		ConfigManager.init();
		
		setResizable(true);
		
		setIconImages(MiscUtils.getIcons(MiscUtils.ExecType.editor));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
		sl_contentPane.putConstraint(SpringLayout.NORTH, tabbedPane, 0, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, tabbedPane, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, tabbedPane, 0, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, tabbedPane, 0, SpringLayout.EAST, contentPane);
		contentPane.add(tabbedPane);
		
		populateTabs();
	}

}
