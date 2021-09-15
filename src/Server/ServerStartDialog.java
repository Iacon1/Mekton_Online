package Server;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GameEngine.GameServer;
import GameEngine.Configurables.ModuleManager;
import GameEngine.Configurables.ModuleTypes.ServerMakingModule;
import Utils.Logging;
import Utils.MiscUtils;

import javax.swing.SpringLayout;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class ServerStartDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private final JSpinner portSpinner = new JSpinner();
	private final JLabel portLabel = new JLabel("Choose a port to open on:");
	
	public static void main(String[] args)
	{
		try
		{
			ServerStartDialog dialog = new ServerStartDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e) {Logging.logException(e);}
	}

	private void onClickOK() // When OK is pressed
	{
		Integer value = (Integer) portSpinner.getValue();
		if (value != null)
		{
			GameServer server = ModuleManager.getHighestOfType(ServerMakingModule.class).makeServer();
			server.start(value); // TODO changeable

			ServerWindow.main(server);
			
			this.setVisible(false);
			this.dispose();
		}
	}
	
	public ServerStartDialog()
	{
		setIconImages(MiscUtils.getIcons(false));
		
		setTitle(MiscUtils.getProgramName() + " Server: Start server...");
		setBounds(100, 100, 300, 175);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		
		sl_contentPanel.putConstraint(SpringLayout.VERTICAL_CENTER, portLabel, 0, SpringLayout.VERTICAL_CENTER, portSpinner);
		sl_contentPanel.putConstraint(SpringLayout.EAST, portLabel, -20, SpringLayout.WEST, portSpinner);
		
		sl_contentPanel.putConstraint(SpringLayout.HORIZONTAL_CENTER, portSpinner, 50, SpringLayout.HORIZONTAL_CENTER, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.VERTICAL_CENTER, portSpinner, 0, SpringLayout.VERTICAL_CENTER, contentPanel);
		
		contentPanel.setLayout(sl_contentPanel);
		portSpinner.setModel(new SpinnerNumberModel(6666, 0, 65535, 1));
		
		contentPanel.add(portSpinner);
		portLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		contentPanel.add(portLabel);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
		JButton okButton = new JButton("Start");
		okButton.setActionCommand("Start");
		buttonPane.add(okButton);
		okButton.addActionListener(e -> {onClickOK();});
		getRootPane().setDefaultButton(okButton);
			
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(e -> {this.setVisible(false); this.dispose();});
		buttonPane.add(cancelButton);
	}
}
