// By Iacon1
// Created 04/24/2021
// Removes server

package Client;

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
import javax.swing.SwingConstants;
import javax.swing.JTextField;

/** The dialog for when you want to remove a server.
 * 
 */
@SuppressWarnings("serial")
public class RemoveServerDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private final JLabel warningLabel = new JLabel("<html>Warning: This cannot be undone.");
	private final JTextField nameBox = new JTextField();
	private final JLabel nameLabel = new JLabel("Server name:");
	private GetServerFrame caller;
	
	/** Runs the dialog.
	 * 
	 *  @param caller The frame that called this.
	 */
	public static void main(GetServerFrame caller)
	{
		try
		{
			RemoveServerDialog dialog = new RemoveServerDialog();
			dialog.caller = caller;
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e) {Logging.logException(e);}
	}

	private void onClickOK() // When OK is pressed
	{
		String serverName = nameBox.getText();
		if (serverName != null)
		{
			caller.removeServer(serverName);
			this.setVisible(false);
			this.dispose();
		}
	}
	
	/** Constructor.
	 * 
	 */
	public RemoveServerDialog()
	{
		setIconImages(GameInfo.getIcons(GameInfo.ExecType.client));
		setTitle(GameInfo.getProgramName() + " Client: Remove server...");
		
		nameBox.setColumns(10);
		setBounds(100, 100, 300, 175);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		sl_contentPanel.putConstraint(SpringLayout.VERTICAL_CENTER, nameLabel, 0, SpringLayout.VERTICAL_CENTER, nameBox);
		sl_contentPanel.putConstraint(SpringLayout.EAST, nameLabel, -10, SpringLayout.WEST, nameBox);
		
		sl_contentPanel.putConstraint(SpringLayout.NORTH, nameBox, 10, SpringLayout.SOUTH, warningLabel);
		sl_contentPanel.putConstraint(SpringLayout.HORIZONTAL_CENTER, nameBox, 50, SpringLayout.HORIZONTAL_CENTER, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, nameBox, -10, SpringLayout.EAST, contentPanel);
		
		sl_contentPanel.putConstraint(SpringLayout.NORTH, warningLabel, 10, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.HORIZONTAL_CENTER, warningLabel, 0, SpringLayout.HORIZONTAL_CENTER, contentPanel);
		
		contentPanel.setLayout(sl_contentPanel);
		warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		contentPanel.add(warningLabel);
		
		contentPanel.add(nameBox);
		
		contentPanel.add(nameLabel);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
		JButton okButton = new JButton("Remove");
		okButton.setActionCommand("Remove");
		okButton.addActionListener(e -> {onClickOK();});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(e -> {this.setVisible(false); this.dispose();});
		buttonPane.add(cancelButton);
	}
}
