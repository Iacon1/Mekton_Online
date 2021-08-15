// By Iacon1
// Created 04/23/2021
// Gets server data from user

package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.reflect.TypeToken;

import Net.Client.Client;
import Utils.Logging;
import Utils.MiscUtils;
import Utils.JSONManager;

import javax.swing.JLabel;
import javax.swing.SpringLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class GetServerFrame extends JFrame
{
	private static class SavedServer
	{
		public String name_;
		public String ip_;
		public int port_;
	}
	
	private JPanel contentPane;
	private final JLabel headerLabel = new JLabel("Mekton Online Client");
	private final JLabel portLabel = new JLabel("Server Port");
	private final JSpinner portSpinner = new JSpinner();
	private final JLabel serverAddressLabel = new JLabel("Server Address");
	private final JTextField serverIPBox = new JTextField();
	private final JList<String> savedServerList = new JList<String>();
	private final JLabel savedServerLabel = new JLabel("Saved Servers");
	private final JButton saveServerButton = new JButton("Save server as...");
	private final JButton removeServerButton = new JButton("Remove server...");
	private final JButton connectServerButton = new JButton("Connect to server");
	private ArrayList<SavedServer> savedServerArray_;
	// Functionality
	
	@SuppressWarnings("unchecked")
	private void populateList() // Populates savedServerList from file
	{
		savedServerArray_ = (ArrayList<SavedServer>) JSONManager.deserializeCollectionJSONList(MiscUtils.readText("Local Data/Client/SavedServers.JSON"), ArrayList.class, SavedServer.class);
		if (savedServerArray_ == null)
		{
			savedServerArray_ = new ArrayList<SavedServer>();
			return;
		}
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		for (int i = 0; i < savedServerArray_.size(); ++i)
		{
			model.addElement(savedServerArray_.get(i).name_);
		}
		
		savedServerList.setModel(model);
	}
	
	public void saveServer(String name) // Saves current server w/ provided name
	{
		SavedServer server = new SavedServer();
		server.name_ = name;
		server.ip_ = serverIPBox.getText();
		server.port_ = (Integer) portSpinner.getValue();
		
		boolean foundServer = false;
		for (int i = 0; i < savedServerArray_.size(); ++i)
		{
			if (savedServerArray_.get(i).name_.equals(server.name_))
			{
				savedServerArray_.set(i, server);
				foundServer = true;
			}
		}
		if (!foundServer) savedServerArray_.add(server);
		
		String serialized = JSONManager.serializeJSON(savedServerArray_);
		MiscUtils.saveText("Local Data/Client/SavedServers.JSON", serialized);
		populateList();
	}
	public void removeServer(String name) // Removes selected server
	{
		for (int i = 0; i < savedServerArray_.size(); ++i)
		{
			if (savedServerArray_.get(i).name_.equals(name))
			{
				savedServerArray_.remove(i);
				MiscUtils.saveText("Local Data/Client/SavedServers.JSON", JSONManager.serializeJSON(savedServerArray_));
				populateList();
				return;
			}
		}
	}
	private void onSave() // When save button is pressed
	{
		SaveServerDialog.main(this); // Hacky, but works
	}
	private void onRemove() // When remove button is pressed
	{
		RemoveServerDialog.main(this);
	}
	private void onConnect() // When connect button is pressed
	{
		Client<GameClientThread> client = new Client<GameClientThread>(serverIPBox.getText(), (Integer) portSpinner.getValue(), new GameClientThread());
		client.run();
		this.setVisible(false);
		this.dispose();
	}
	private void onSelect() // When a saved server is selected
	{
		for (int i = 0; i < savedServerArray_.size(); ++i)
		{
			if (savedServerArray_.get(i).name_.equals(savedServerList.getSelectedValue()))
			{
				serverIPBox.setText(savedServerArray_.get(i).ip_);
				portSpinner.setValue(savedServerArray_.get(i).port_);
			}
		}
	}
	
	// Launch as app
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					GetServerFrame frame = new GetServerFrame();
					frame.setVisible(true);
				}
				catch (Exception e) {Logging.logException(e);}
			}
		});
	}

	// Create frame
	public GetServerFrame()
	{
		setIconImages(MiscUtils.getIcons(true));
		
		serverIPBox.setColumns(10);
		setTitle(MiscUtils.getProgramName() + " Client: Join Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout(); // Set up layout
		sl_contentPane.putConstraint(SpringLayout.NORTH, connectServerButton, 10, SpringLayout.SOUTH, saveServerButton);
		//sl_contentPane.putConstraint(SpringLayout.WEST, connectServerButton, 0, SpringLayout.WEST, saveServerButton);
		sl_contentPane.putConstraint(SpringLayout.EAST, connectServerButton, 0, SpringLayout.EAST, removeServerButton);
		
		sl_contentPane.putConstraint(SpringLayout.VERTICAL_CENTER, saveServerButton, 0, SpringLayout.VERTICAL_CENTER, removeServerButton);
		sl_contentPane.putConstraint(SpringLayout.WEST, saveServerButton, 0, SpringLayout.WEST, serverIPBox);
	
		sl_contentPane.putConstraint(SpringLayout.SOUTH, savedServerLabel, -10, SpringLayout.NORTH, savedServerList);
		sl_contentPane.putConstraint(SpringLayout.HORIZONTAL_CENTER, savedServerLabel, 0, SpringLayout.HORIZONTAL_CENTER, savedServerList);
		
		sl_contentPane.putConstraint(SpringLayout.NORTH, savedServerList, -75, SpringLayout.SOUTH, portSpinner);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, savedServerList, 90, SpringLayout.SOUTH, portSpinner);
		sl_contentPane.putConstraint(SpringLayout.WEST, savedServerList, -100, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, savedServerList, -10, SpringLayout.EAST, contentPane);
		
		sl_contentPane.putConstraint(SpringLayout.NORTH, serverAddressLabel, 0, SpringLayout.NORTH, portLabel);
		sl_contentPane.putConstraint(SpringLayout.WEST, serverAddressLabel, 0, SpringLayout.WEST, serverIPBox);
		
		sl_contentPane.putConstraint(SpringLayout.NORTH, serverIPBox, 0, SpringLayout.NORTH, portSpinner);
		sl_contentPane.putConstraint(SpringLayout.WEST, serverIPBox, -140, SpringLayout.EAST, serverIPBox);
		sl_contentPane.putConstraint(SpringLayout.EAST, serverIPBox, -10, SpringLayout.WEST, portSpinner);
		
		sl_contentPane.putConstraint(SpringLayout.SOUTH, removeServerButton, -50, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, removeServerButton, 0, SpringLayout.WEST, portSpinner);
		
		sl_contentPane.putConstraint(SpringLayout.NORTH, portSpinner, 10, SpringLayout.SOUTH, portLabel);
		sl_contentPane.putConstraint(SpringLayout.WEST, portSpinner, 0, SpringLayout.WEST, portLabel);
		
		sl_contentPane.putConstraint(SpringLayout.HORIZONTAL_CENTER, portLabel, -30, SpringLayout.HORIZONTAL_CENTER, contentPane);
		sl_contentPane.putConstraint(SpringLayout.VERTICAL_CENTER, portLabel, 0, SpringLayout.VERTICAL_CENTER, contentPane);
		
		sl_contentPane.putConstraint(SpringLayout.NORTH, headerLabel, 25, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.HORIZONTAL_CENTER, headerLabel, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		
		contentPane.setLayout(sl_contentPane);
		headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(headerLabel);
		portLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		contentPane.add(portLabel);
		portSpinner.setModel(new SpinnerNumberModel(0, 0, 65535, 1));
		portSpinner.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		contentPane.add(portSpinner);
		
		contentPane.add(serverAddressLabel);
		
		contentPane.add(serverIPBox);
		savedServerList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		savedServerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		populateList();
		
		saveServerButton.addActionListener(e -> {onSave();});
		removeServerButton.addActionListener(e -> {onRemove();});
		connectServerButton.addActionListener(e -> {onConnect();});
		savedServerList.addListSelectionListener(e -> {onSelect();});
		contentPane.add(savedServerList);
		
		contentPane.add(savedServerLabel);
		
		contentPane.add(saveServerButton);
		
		contentPane.add(removeServerButton);
		
		contentPane.add(connectServerButton);
	}
}
