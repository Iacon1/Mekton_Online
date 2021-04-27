// By Iacon1
// Created 04/26/2021
// Server overview GUI

package Server.Frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GameEngine.ConfigManager;
import GameEngine.GameInstance;
import Server.Server;
import Server.ServerLogger;
import Utils.Logging;
import Utils.MiscUtils;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SpringLayout;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class ServerMainFrame extends JFrame
{	
	private Server server_;
	private JPanel contentPane;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JTabbedPane logPanel = new JTabbedPane(JTabbedPane.LEFT);
	private final JScrollPane allLogsPane = new JScrollPane();
	private final JScrollPane noticeLogsPane = new JScrollPane();
	private final JScrollPane errorLogsPane = new JScrollPane();
	private final JScrollPane exceptionLogsPane = new JScrollPane();
	private final JPanel overviewPanel = new JPanel();
	private final Timer timer = new Timer(1000 / 60, e -> {updateUI();});
	private final JLabel allLabel = new JLabel("New label");
	private final JLabel noteLabel = new JLabel("New label");
	private final JLabel errorLabel = new JLabel("New label");
	private final JLabel exceptionLabel = new JLabel("New label");
	private final JSplitPane splitPane = new JSplitPane();
	private final JTree objectTree = new JTree();
	private final JLabel playersLabel = new JLabel("New label");
	
	private void updateLogs() // Updates logs
	{
		final ServerLogger logger = (ServerLogger) Logging.getLogger();
		allLabel.setText(logger.buildLabelText(null));
		noteLabel.setText(logger.buildLabelText("note"));
		errorLabel.setText(logger.buildLabelText("error"));
		exceptionLabel.setText(logger.buildLabelText("exception"));
	}
	private DefaultMutableTreeNode recursivelyTree(GameInstance instance) // Recursively loads a game instance into tree nodes
	{
		ArrayList<GameInstance> children = instance.getChildren();
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(instance.getName());
		
		for (int i = 0; i < children.size(); ++i)
		{
			node.add(recursivelyTree(children.get(i)));
		}
		
		return node;
	}
	private void updateObjectsList() // Updates objectTree
	{
		DefaultMutableTreeNode node = recursivelyTree(server_.getWorld());
		DefaultTreeModel model = new DefaultTreeModel(node);
		
		objectTree.setModel(model);
	}
	private void updateUI() // Updates the display
	{
		updateLogs();
		updateObjectsList();
	}
	
	public static void main(Server server)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ServerMainFrame frame = new ServerMainFrame(server);
					frame.setVisible(true);
				} catch (Exception e)
				{Logging.logException(e);}
			}
		});
	}

	public ServerMainFrame(Server server)
	{
		setTitle(MiscUtils.getProgramName() + " Server: Main Window");

		server_ = server;
		
		setIconImages(MiscUtils.getIcons(false));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight());
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(ConfigManager.getScreenWidth(), ConfigManager.getScreenHeight()));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		tabbedPane.setBounds(0, 0, 640, 480);
		
		
		contentPane.add(tabbedPane);
		
		tabbedPane.addTab("Overview", null, overviewPanel, null);
		SpringLayout sl_overviewPanel = new SpringLayout();
		sl_overviewPanel.putConstraint(SpringLayout.NORTH, playersLabel, 10, SpringLayout.NORTH, overviewPanel);
		sl_overviewPanel.putConstraint(SpringLayout.WEST, playersLabel, 10, SpringLayout.WEST, overviewPanel);
		overviewPanel.setLayout(sl_overviewPanel);
		playersLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		overviewPanel.add(playersLabel);
		
		tabbedPane.addTab("Logs", null, logPanel, null);
		
		logPanel.addTab("All", null, allLogsPane, null);
		allLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		allLabel.setVerticalAlignment(SwingConstants.TOP);
		
		allLogsPane.setViewportView(allLabel);
		
		logPanel.addTab("Notices", null, noticeLogsPane, null);
		noteLabel.setVerticalAlignment(SwingConstants.TOP);
		noteLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		noticeLogsPane.setViewportView(noteLabel);
		
		logPanel.addTab("Errors", null, errorLogsPane, null);
		errorLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		errorLabel.setVerticalAlignment(SwingConstants.TOP);
		
		errorLogsPane.setViewportView(errorLabel);
		
		logPanel.addTab("Exceptions", null, exceptionLogsPane, null);
		exceptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		exceptionLabel.setVerticalAlignment(SwingConstants.TOP);
		
		exceptionLogsPane.setViewportView(exceptionLabel);
		splitPane.setResizeWeight(0.5);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		tabbedPane.addTab("Objects", null, splitPane, null);
		objectTree.setFont(new Font("Tahoma", Font.PLAIN, 15));
		objectTree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("JTree") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("colors");
						node_1.add(new DefaultMutableTreeNode("blue"));
						node_1.add(new DefaultMutableTreeNode("violet"));
						node_1.add(new DefaultMutableTreeNode("red"));
						node_1.add(new DefaultMutableTreeNode("yellow"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("sports");
						node_1.add(new DefaultMutableTreeNode("basketball"));
						node_1.add(new DefaultMutableTreeNode("soccer"));
						node_1.add(new DefaultMutableTreeNode("football"));
						node_1.add(new DefaultMutableTreeNode("hockey"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("food");
						node_1.add(new DefaultMutableTreeNode("hot dogs"));
						node_1.add(new DefaultMutableTreeNode("pizza"));
						node_1.add(new DefaultMutableTreeNode("ravioli"));
						node_1.add(new DefaultMutableTreeNode("bananas"));
					add(node_1);
				}
			}
		));
		
		splitPane.setLeftComponent(objectTree);
		
		timer.start();
		
		pack();
	}
}
