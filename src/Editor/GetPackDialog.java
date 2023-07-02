package Editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GameEngine.GameInfo;
import Utils.Logging;
import Utils.MiscUtils;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class GetPackDialog extends JDialog
{
	private final JPanel contentPanel = new JPanel();
	JComboBox<String> serverBox;
	
	private void initServerPack(String name)
	{
		String path = "Resources/Server Packs/" + name + "/";
		MiscUtils.newFolder(path);
		
		MiscUtils.newFolder(path + "Graphics/");
		
		MiscUtils.newFolder(path + "Fonts/");
		
		MiscUtils.newFolder(path + "Sounds/");
		MiscUtils.newFolder(path + "Sounds/SFX/");
		MiscUtils.newFolder(path + "Sounds/Music/");
		
		MiscUtils.newFolder(path + "Modules/");
	}
	private String[] getServers()
	{
		return MiscUtils.listFilenames("Resources/Server Packs/");
	}
	private void onClickOK() // When OK is pressed
	{
		String serverName = (String) serverBox.getSelectedItem();
		if (serverName != null)
		{
			if (serverBox.getSelectedIndex() == -1) initServerPack(serverName); // A new server was entered.
			this.setVisible(false);
			this.dispose();
			EditorMainFrame.main(new String[]{serverName});
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try
		{
			GetPackDialog dialog = new GetPackDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e)
		{
			Logging.logException(e);
		}
	}

	/**
	 * Create the dialog.
	 */
	public GetPackDialog()
	{
		setIconImages(GameInfo.getIcons(GameInfo.ExecType.editor));
		
		setTitle(GameInfo.getProgramName() + " Editor: Set Serverpack");
		setBounds(100, 100, 300, 175);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		
		serverBox = new JComboBox<String>(getServers());
		serverBox.setEditable(true);
		sl_contentPanel.putConstraint(SpringLayout.VERTICAL_CENTER, serverBox, 0, SpringLayout.VERTICAL_CENTER, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.HORIZONTAL_CENTER, serverBox, 20, SpringLayout.HORIZONTAL_CENTER, contentPanel);
		contentPanel.add(serverBox);

		JLabel serverLabel = new JLabel("Server Pack Name:");
		sl_contentPanel.putConstraint(SpringLayout.EAST, serverLabel, -10, SpringLayout.WEST, serverBox);
		sl_contentPanel.putConstraint(SpringLayout.VERTICAL_CENTER, serverLabel, 0, SpringLayout.VERTICAL_CENTER, serverBox);
		contentPanel.add(serverLabel);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(e -> {onClickOK();});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
			
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(e -> {this.setVisible(false); this.dispose();});
		buttonPane.add(cancelButton);
	}
}
