// Simple menu for editing things in the editor.

package GameEngine.Editor;

import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Label;
import java.util.List;
import java.util.ArrayList;

import javax.swing.border.BevelBorder;
import javax.swing.BoxLayout;


@SuppressWarnings("serial")
public class EditorPanel extends JPanel
{
	private JLabel titleLabel;
	private JPanel controlsPanel;
	
	public interface InfoFunction {public String update();}
	private List<Label> labels;
	private List<InfoFunction> infoFunctions;
	private JPanel infoPanel;
	
	private void updateInfo()
	{
		for (int i = 0; i < infoFunctions.size(); ++i) labels.get(i).setText(infoFunctions.get(i).update());
	}
	public <T> void addInfo(InfoFunction function)
	{
		Label label = new Label();
		infoPanel.add(label);
		labels.add(label);
		infoFunctions.add(function);
		
		updateInfo();
		
/*		int labelsW = 0;
		int labelsH = 0;
		
		for (int i = 0; i < labels.size(); ++i)
		{
			FontMetrics metrics = labels.get(i).getFontMetrics(labels.get(i).getFont());
			labelsW = Math.max(labelsW, metrics.stringWidth(labels.get(i).getText()) + metrics.charWidth('a'));
			labelsH = labelsH + metrics.getHeight();
		}
		infoPanel.setSize(new Dimension(labelsW, labelsH));
		infoPanel.updateUI();
*/	}
	
	public interface EditFunction<T> {public void onEdit(T newValue);}
	private void addControlRow(Component a, Component b)
	{
		controlsPanel.add(a);
		controlsPanel.add(b);
		
	}
	/** Adds a selectable list to the panel
	 * 
	 */
	public <T> void addOptionList(String label, String[] optionLabels, T[] optionValues, int initialValueIndex, EditFunction<T> editFunction)
	{
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for (int i = 0; i < optionLabels.length; ++i) listModel.addElement(optionLabels[i]);
		
		JList<String> list = new JList<String>();
		list.setModel(listModel);
		list.setSelectedIndex(initialValueIndex);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(e ->
		{
			editFunction.onEdit(optionValues[list.getSelectedIndex()]);
			updateInfo();
		});

		addControlRow(new Label(label), list);
	}
	
	public void setTitle(String title) {titleLabel.setText(title);}
	
	/**
	 * Create the panel.
	 */
	public EditorPanel(String title)
	{
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setLayout(new BorderLayout(0, 0));
		
		titleLabel = new JLabel(title);
		add(titleLabel, BorderLayout.NORTH);
		
		controlsPanel = new JPanel();
		GridLayout controlsLayout = new GridLayout(0, 2);
		add(controlsPanel, BorderLayout.CENTER);
		controlsPanel.setLayout(controlsLayout);
		
		infoPanel = new JPanel();
		add(infoPanel, BorderLayout.WEST);
		infoPanel.setLayout(new GridLayout(0, 1));
		
		labels = new ArrayList<Label>();
		infoFunctions = new ArrayList<InfoFunction>();
	}

}
