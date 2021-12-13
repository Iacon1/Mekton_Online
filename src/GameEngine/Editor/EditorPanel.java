// Simple menu for editing things in the editor.

package GameEngine.Editor;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.border.BevelBorder;
import javax.swing.BoxLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;


@SuppressWarnings("serial")
public class EditorPanel extends JPanel
{
	private JLabel titleLabel;
	private JPanel controlsPanel;
	
	public interface InfoFunction {public String update();}
	private List<JLabel> labels;
	private List<InfoFunction> infoFunctions;
	private JPanel infoPanel;
	
	private void updateInfo()
	{
		for (int i = 0; i < infoFunctions.size(); ++i) labels.get(i).setText(infoFunctions.get(i).update());
	}
	public <T> void addInfo(InfoFunction function)
	{
		JLabel label = new JLabel();
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
	public interface ClickFunction {public void onClick();}
	private void addControlRow(JComponent a, JComponent b)
	{
		a.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		b.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
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
		editFunction.onEdit(optionValues[initialValueIndex]);
		updateInfo();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
		list.setLayoutOrientation(JList.VERTICAL);
		addControlRow(new JLabel(label), scrollPane);
	}
	public <T extends Enum> void addOptionList(String label, T[] optionValues, T initialValue, EditFunction<T> editFunction)
	{
		String[] optionLabels = new String[optionValues.length];
		for (int i = 0; i < optionValues.length; ++i) optionLabels[i] = optionValues[i].name();
		addOptionList(label, optionLabels, optionValues, initialValue.ordinal(), editFunction);	
	}
	public void addSpinner(String label, int minimumValue, int defaultValue, int maximumValue, EditFunction<Integer> editFunction)
	{
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(defaultValue, minimumValue, maximumValue, 1));
		spinner.addChangeListener(e -> {
			editFunction.onEdit((Integer) spinner.getModel().getValue());
			updateInfo();
		});
		editFunction.onEdit((Integer) spinner.getModel().getValue());
		updateInfo();
		
		addControlRow(new JLabel(label), spinner);
	}
	public void addButton(String label, ClickFunction function)
	{
		JButton button = new JButton(label);
		
		button.addActionListener(e -> {function.onClick(); updateInfo();});
		
		addControlRow(new JLabel(label), button);
	}
	public void addButtonPair(String label1, String label2, ClickFunction function1, ClickFunction function2)
	{
		JButton button1 = new JButton(label1);
		button1.addActionListener(e -> {function1.onClick(); updateInfo();});
		JButton button2 = new JButton(label2);
		button2.addActionListener(e -> {function2.onClick(); updateInfo();});
		
		addControlRow(button1, button2);
	}
	
	
	private static class EditableNode extends DefaultMutableTreeNode
	{
		private Editable editable;
		
		public EditableNode(String label, Editable editable)
		{
			super(label);
			this.editable = editable;
		}
		
		public EditorPanel editorPanel()
		{
			return editable.editorPanel();
		}
		
	}
	private MutableTreeNode getNode(String label, Object[] objects)
	{
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(label);
		for (int i = 0; i < objects.length; ++i)
		{
			if (objects[i] == null) continue;
			else if (Object[].class.isAssignableFrom(objects[i].getClass())) // Itself an array
			{
				node.add(getNode(objects[i].getClass().getName(), (Object[]) objects[i]));
			}
			else if (Editable.class.isAssignableFrom(objects[i].getClass())) // An editable
			{
				Editable editable = (Editable) objects[i];
				node.add(getNode(editable.getName(), editable));
			}
			else continue;
		}
		
		return node;
	}
	private MutableTreeNode getNode(String label, Editable editable)
	{
		EditableNode node = new EditableNode(label, editable);
		return node;
	}
	public static interface HierarchicalListUpdateHandle {public void update(Object... objects);}
	
	/** Adds a hierarchical list.
	 * 
	 *  @return An update handle.
	 */
	public HierarchicalListUpdateHandle addHierarchicalList(String label, Object... objects)
	{
		JTree tree = new JTree();
		MutableTreeNode rootNode = getNode(label, objects);
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		tree.setModel(model);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(tree);
		splitPane.setDividerLocation(0.5);
		
		tree.addTreeSelectionListener(e -> {
			Object node = tree.getLastSelectedPathComponent();
			if (node == null) return;
			if (node.getClass() == EditableNode.class)
			{
				EditorPanel editorPanel = ((EditableNode) node).editorPanel();
				splitPane.setRightComponent(editorPanel);
				splitPane.setDividerLocation(0.5);
				splitPane.setResizeWeight(0.5);
			}
			else if (node == rootNode)
			{
				splitPane.setRightComponent(null);
				splitPane.setDividerLocation(1.0);
				splitPane.setResizeWeight(1.0);
			}
			updateInfo();
		});
		addControlRow(new JLabel(label), splitPane);
		
		return o -> 
		{
			MutableTreeNode rN = getNode(label, o);
			DefaultTreeModel m = new DefaultTreeModel(rN);
			tree.setModel(m);
			tree.updateUI();
		};
	}
	
	public void setTitle(String title) {titleLabel.setText(title);}
	
	/**
	 * Create the panel.
	 */
	public EditorPanel(String title)
	{
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BorderLayout(0, 0));
		
		titleLabel = new JLabel(title);
		add(titleLabel, BorderLayout.NORTH);
		
		controlsPanel = new JPanel();
		controlsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridLayout controlsLayout = new GridLayout(0, 2);
		add(controlsPanel, BorderLayout.CENTER);
		controlsPanel.setLayout(controlsLayout);
		
		infoPanel = new JPanel();
		add(infoPanel, BorderLayout.WEST);
		infoPanel.setLayout(new GridLayout(0, 1));
		
		labels = new ArrayList<JLabel>();
		infoFunctions = new ArrayList<InfoFunction>();
	}

}
