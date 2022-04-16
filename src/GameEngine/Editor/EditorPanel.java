// Simple menu for editing things in the editor.

package GameEngine.Editor;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.border.BevelBorder;
import javax.swing.BoxLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import GameEngine.MenuSlate;
import GameEngine.MenuSlate.InfoFunction;


@SuppressWarnings("serial")
public class EditorPanel extends JPanel implements MenuSlate
{
	private interface UpdateTask {public void onUpdate();}

	private int cellsH;
	private int cellsV;
	private int cellWidth;
	private int cellHeight;
	private List<UpdateTask> updateTasks; // Tasks to do on update
	
	private void update() {for (UpdateTask task : updateTasks) task.onUpdate();}
	
	/**
	 * Create the panel.
	 */
	public EditorPanel()
	{
		super();
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(null);
		setSize(640, 480);
		
		updateTasks = new ArrayList<UpdateTask>();
	}
	/**
	 * Create the panel.
	 */
	public EditorPanel(int width, int height, int cellsH, int cellsV)
	{
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(null);
		
		updateTasks = new ArrayList<UpdateTask>();
		setVisible(true);
		setCells(cellsH, cellsV);
		setSize(width, height);
	}
	
	@Override
	public void setCells(int h, int v)
	{
		cellWidth = getWidth() / h;
		cellHeight = getHeight() / v;
		
		cellsH = h;
		cellsV = v;
	}
	
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		if (cellsH != 0 && cellsV != 0) setCells(cellsH, cellsV);
	}
	
	@Override
	public void addInfo(int x, int y, String label, int labelLength, int contentLength,
			InfoFunction<String> function)
	{
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		labelLabel.setBounds(x * cellWidth, y * cellHeight, labelLength * cellWidth, cellHeight);
		JLabel contentLabel = new JLabel();
		add(contentLabel);
		contentLabel.setBounds((x + labelLength) * cellWidth, y * cellHeight, contentLength * cellWidth, cellHeight);
		
		labelLabel.setText(label);
		contentLabel.setText(function.getValue());
		
		updateTasks.add(() -> {contentLabel.setText(function.getValue());});
	}

	@Override
	public void addTextbar(int x, int y, String label, int labelLength, int maxLength, int contentLength,
			DataFunction<String> function)
	{
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		labelLabel.setBounds(x * cellWidth, y * cellHeight, labelLength * cellWidth, cellHeight);
		JTextField contentField = new JTextField();
		add(contentField);
		contentField.setBounds((x + labelLength) * cellWidth, y * cellHeight, contentLength * cellWidth, cellHeight);
		
		labelLabel.setText(label);
		contentField.setText(function.getValue());
		
		updateTasks.add(() -> {contentField.setText(function.getValue());});
		contentField.getDocument().addDocumentListener(new DocumentListener()
		{
			private void onUpdate()
			{
				function.setValue(contentField.getText());
				update();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {onUpdate();}
			@Override
			public void removeUpdate(DocumentEvent e) {onUpdate();}
			@Override
			public void changedUpdate(DocumentEvent e) {onUpdate();}
		});
	}
	
	@Override
	public void addIntegerWheel(int x, int y, String label, int labelLength, int min, int max, int contentLength,
			DataFunction<Integer> function)
	{
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		labelLabel.setBounds(x * cellWidth, y * cellHeight, labelLength * cellWidth, cellHeight);
		JSpinner contentSpinner = new JSpinner();
		add(contentSpinner);
		contentSpinner.setModel(new SpinnerNumberModel(min, min, max, 1));
		contentSpinner.setBounds((x + labelLength) * cellWidth, y * cellHeight, contentLength * cellWidth, cellHeight);
		
		labelLabel.setText(label);
		contentSpinner.setValue(function.getValue());
		
		updateTasks.add(() -> {contentSpinner.setValue(function.getValue());});
		contentSpinner.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				function.setValue((Integer) contentSpinner.getValue());
				update();
			}
		});
	}
	@Override
	public void addDoubleWheel(int x, int y, String label, int labelLength, double min, double max, int digits, int contentLength,
			DataFunction<Double> function)
	{
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		labelLabel.setBounds(x * cellWidth, y * cellHeight, labelLength * cellWidth, cellHeight);
		JSpinner contentSpinner = new JSpinner();
		add(contentSpinner);
		contentSpinner.setModel(new SpinnerNumberModel(min, min, max, 1));
		contentSpinner.setBounds((x + labelLength) * cellWidth, y * cellHeight, contentLength * cellWidth, cellHeight);
		
		labelLabel.setText(label);
		contentSpinner.setValue(function.getValue());
		
		updateTasks.add(() -> {contentSpinner.setValue(function.getValue());});
		contentSpinner.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				function.setValue((Double) contentSpinner.getValue());
				update();
			}
		});
	}

	@Override
	public void addCheckbox(int x, int y, String label, int labelLength, int contentLength,
			DataFunction<Boolean> function)
	{
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		labelLabel.setBounds(x * cellWidth, y * cellHeight, labelLength * cellWidth, cellHeight);
		JCheckBox contentBox = new JCheckBox();
		add(contentBox);
		contentBox.setBounds((x + labelLength) * cellWidth, y * cellHeight, contentLength * cellWidth, cellHeight);
		
		labelLabel.setText(label);
		contentBox.setSelected(function.getValue());
		
		updateTasks.add(() -> {contentBox.setSelected(function.getValue());});
		contentBox.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				function.setValue(contentBox.isSelected());
				update();
			}
		});
	}

	@Override
	public <T> void addOptions(int x, int y, String label, int labelLength, int contentLength, String[] optionLabels, T[] options,
			DataFunction<T> function)
	{
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		labelLabel.setBounds(x * cellWidth, y * cellHeight, labelLength * cellWidth, cellHeight);
		JList<String> contentList = new JList<String>();
		add(contentList);
		contentList.setBounds((x + labelLength) * cellWidth, y * cellHeight, contentLength * cellWidth, cellHeight);
		
		
		
		labelLabel.setText(label);
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for (int i = 0; i < optionLabels.length; ++i) listModel.addElement(optionLabels[i]);
		contentList.setModel(listModel);
		contentList.setSelectedIndex(0);
		contentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentList.setSelectedIndex(listModel.indexOf(function.getValue()));
		
		updateTasks.add(() -> {contentList.setSelectedIndex(listModel.indexOf(function.getValue()));});
		contentList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				function.setValue(options[contentList.getSelectedIndex()]);
				update();
			}
		});	
	}
	@Override
	public <E extends Enum<E>> void addOptions(int x, int y, String label, int labelLength, int contentLength, E[] options,
			DataFunction<E> function)
	{
		String[] optionLabels = new String[options.length];
		for (int i = 0; i < options.length; ++i) optionLabels[i] = options[i].name();
		addOptions(x, y, label, labelLength, contentLength, optionLabels, options, function);
	}
	
	@Override
	public void addButton(int x, int y, String label, int w, int h, ButtonFunction function)
	{
		JButton contentButton = new JButton();
		contentButton.setBounds(x * cellWidth, y * cellHeight, (x + w) * cellWidth, (y + h) * cellHeight);
		
		contentButton.setText(label);
		
		contentButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				function.onClick();
				update();
			}
		});
	}
	
	@Override
	public MenuSlate addSubSlate(int x, int y, int w, int h, DataFunction<MenuSlate> function)
	{
		EditorPanel contentPanel = new EditorPanel();
		add(contentPanel);
		contentPanel.setBounds(x * cellWidth, y * cellHeight, (x + w) * cellWidth, (y + h) * cellHeight);
		contentPanel.setCells(w, h);
		// TODO allow swapping of subslates
		return contentPanel;
	}
}
