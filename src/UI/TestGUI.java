//By Iacon1
//Created 4/18/2021
//Testbed for JFrame

package UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestGUI
{

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel emptyLabel = new JLabel("Bob");
		emptyLabel.setPreferredSize(new Dimension(400, 400));
		frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

}
