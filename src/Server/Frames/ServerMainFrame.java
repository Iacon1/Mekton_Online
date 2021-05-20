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
import GameEngine.GameEntity;
import GameEngine.GameWorld;
import Server.Account;
import Server.Server;
import Server.ServerLogger;
import Utils.Logging;
import Utils.MiscUtils;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.SpringLayout;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class ServerMainFrame extends JFrame
{	
	private static Server server_;
	private DefaultTreeModel model;
	private final Timer timer = new Timer();
	private JPanel contentPane;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JTabbedPane logPanel = new JTabbedPane(JTabbedPane.LEFT);
	private final JScrollPane allLogsPane = new JScrollPane();
	private final JScrollPane noticeLogsPane = new JScrollPane();
	private final JScrollPane errorLogsPane = new JScrollPane();
	private final JScrollPane exceptionLogsPane = new JScrollPane();
	private final JPanel overviewPanel = new JPanel();
	
	private final JLabel allLabel = new JLabel("New label");
	private final JLabel noteLabel = new JLabel("New label");
	private final JLabel errorLabel = new JLabel("New label");
	private final JLabel exceptionLabel = new JLabel("New label");
	private final JSplitPane splitPane = new JSplitPane();
	private final JTree objectTree = new JTree();
	private final JPanel playersPanel = new JPanel();
	private final JLabel playersLabel = new JLabel("New label");
	
	private class InstanceNode extends DefaultMutableTreeNode
	{
		private GameEntity instance_;
		
		public InstanceNode(GameEntity instance)
		{
			super(instance.getName());
			instance_ = instance;
		}	
	}
	
	private void updateLogs() // Updates logs
	{
		final ServerLogger logger = (ServerLogger) Logging.getLogger();
		allLabel.setText(logger.buildLabelText(null));
		noteLabel.setText(logger.buildLabelText("note"));
		errorLabel.setText(logger.buildLabelText("error"));
		exceptionLabel.setText(logger.buildLabelText("exception"));
	}
	private void initObjectsTree()
	{
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("Game World");
		model = new DefaultTreeModel(node);
		objectTree.setModel(model);
	}	
	private void updateRecursively(GameEntity rootInstance, InstanceNode rootNode)
	{
		for (int i = 0; i < Math.max(rootNode.getChildCount(), rootInstance.getChildren().size()); ++i)
		{
			GameEntity instance;
			InstanceNode node;
			
			try {instance = rootInstance.getChildren().get(i);}
			catch (Exception e) {instance = null;}
			try {node = (InstanceNode) rootNode.getChildAt(i);}
			catch (Exception e) {node = null;}
			
			if (node == null && instance != null) // Should be an instance here but there isn't
			{
				node = new InstanceNode(instance);
				updateRecursively(instance, node);
				rootNode.add(node);
				int[] addedIndices = {rootNode.getChildCount() - 1};
				model.nodesWereInserted(rootNode, addedIndices);
			}
			else if (node != null && instance == null) // Shouldn't be here...
			{
				rootNode.remove(i);
				int[] removedIndices = {i};
				Object[] removedObjects = {node};
				model.nodesWereRemoved(rootNode, removedIndices, removedObjects);
				//i--; // Really evil, gets around the index shifting
			}
			else if (node != null && instance != null && instance == node.instance_) // We're on the same page here
				updateRecursively(instance, node);
			else if (node != null && instance != null) // Swap out old node for a new one
			{
				node.instance_ = instance;
				updateRecursively(instance, node);
				model.nodeChanged(node);
			}
		}
	}	
	private void updateObjectsTree() // Updates objectTree
	{
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) model.getRoot();
		for (int i = 0; i < Math.max(rootNode.getChildCount(), GameWorld.getWorld().getRootEntities().size()); ++i)
		{
			GameEntity instance;
			InstanceNode node;
			
			try {instance = GameWorld.getWorld().getRootEntities().get(i);}
			catch (Exception e) {instance = null;}
			try {node = (InstanceNode) rootNode.getChildAt(i);}
			catch (Exception e) {node = null;}
			
			if (node == null && instance != null) // Should be an instance here but there isn't
			{
				node = new InstanceNode(instance);
				updateRecursively(instance, node);
				rootNode.add(node);
				int[] addedIndices = {rootNode.getChildCount() - 1};
				model.nodesWereInserted(rootNode, addedIndices);
			}
			else if (node != null && instance == null) // Shouldn't be here...
			{
				rootNode.remove(i);
				int[] removedIndices = {i};
				Object[] removedObjects = {node};
				model.nodesWereRemoved(rootNode, removedIndices, removedObjects);
				//i--; // Really evil, gets around the index shifting
			}
			else if (node != null && instance != null && instance == node.instance_) // We're on the same page here
				updateRecursively(instance, node);
			else if (node != null && instance != null) // Swap out old node for a new one
			{
				node.instance_ = instance;
				updateRecursively(instance, node);
				model.nodeChanged(node);
			}
		}
	}
	private void updatePlayersList()
	{
		String text = "<html>";
		ArrayList<Account> accounts = server_.getAccounts();
		
		for (int i = 0; i < accounts.size(); ++i)
		{
			text = text + accounts.get(i).username + "<br><br>";
		}
		
		playersLabel.setText(text);
	}
	private class UpdateTask extends TimerTask
	{
		public void run() // Updates the display
		{
			updateLogs();
			updateObjectsTree();
			updatePlayersList();
		}
	}
	
	public static void main(Server server)
	{
		server_ = server;
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ServerMainFrame frame = new ServerMainFrame(null);
					frame.setVisible(true);
				} catch (Exception e)
				{Logging.logException(e);}
			}
		});
	}

	public ServerMainFrame(Server server)
	{
		if (server != null) server_ = server;
		setTitle(MiscUtils.getProgramName() + " Server: Main Window");

		setResizable(false);
		
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
		
		splitPane.setLeftComponent(objectTree);
		
		tabbedPane.addTab("Players", null, playersPanel, null);
		SpringLayout sl_playersPanel = new SpringLayout();
		sl_playersPanel.putConstraint(SpringLayout.NORTH, playersLabel, 10, SpringLayout.NORTH, playersPanel);
		sl_playersPanel.putConstraint(SpringLayout.WEST, playersLabel, 10, SpringLayout.WEST, playersPanel);
		playersPanel.setLayout(sl_playersPanel);
		
		playersPanel.add(playersLabel);
		
		tabbedPane.addTab("Overview", null, overviewPanel, null);
		SpringLayout sl_overviewPanel = new SpringLayout();
		overviewPanel.setLayout(sl_overviewPanel);
		initObjectsTree();
		
		timer.schedule(new UpdateTask(), ConfigManager.getFramerate(), ConfigManager.getFramerate());
		
		pack();
	}
}
