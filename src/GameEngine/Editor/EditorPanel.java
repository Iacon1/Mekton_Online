// Simple menu for editing things in the editor.

package GameEngine.Editor;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JList;
import java.awt.Component;

import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import GameEngine.IntPoint2D;
import GameEngine.Configurables.ConfigManager;
import GameEngine.EntityTypes.Alignable;
import GameEngine.EntityTypes.Alignable.AlignmentPoint;
import GameEngine.Graphics.ScreenCanvas;
import GameEngine.Graphics.Sprite;
import GameEngine.MenuStuff.MenuSlate;

@SuppressWarnings("serial")
public class EditorPanel extends JPanel implements MenuSlate
{
	private interface UpdateTask {public void onUpdate();}
	private interface ResizeTask {public void onResize();}
	private int cellsH;
	private int cellsV;
	private int cellWidth;
	private int cellHeight;
	private List<UpdateTask> updateTasks; // Tasks to do on update
	private List<ResizeTask> resizeTasks; // Resize components on screen resize
	private List<MenuSlate> children; // Sub-slates and tabs
	private static Timer timer = new Timer();
	
	private static Object componentLock = new Object(); // Hold while modifying components
	private TimerTask updateTask = new TimerTask()
	{
		@Override public void run() 
		{
			synchronized (componentLock) {for (UpdateTask task : updateTasks) task.onUpdate();}		
		}
	};
	private TimerTask resizeTask = new TimerTask()
	{
		@Override public void run() 
		{
			synchronized (componentLock) {for (ResizeTask task : resizeTasks) task.onResize();}
		}
	};
	
	private class EditorPanelComponentHandle implements ComponentHandle
	{
		protected Component[] components;
		public EditorPanelComponentHandle(Component... components) {this.components = components;}
		
		@Override
		public int getX()
		{
			if (components.length == 0) return 0;
			int mX = components[0].getX();
			for (int i = 1; i < components.length; ++i)
			{
				mX = Math.min(mX, components[i].getX());
			}
			
			return mX / cellWidth;
		}
		@Override
		public int getY()
		{
			if (components.length == 0) return 0;
			int mY = components[0].getY();
			for (int i = 1; i < components.length; ++i)
			{
				mY = Math.min(mY, components[i].getY());
			}
			
			return mY / cellHeight;
		}
		@Override
		public int getW()
		{
			if (components.length == 0) return 0;
			int mX = components[0].getX()  + components[0].getWidth();
			for (int i = 1; i < components.length; ++i)
			{
				mX = Math.max(mX, components[i].getX() + components[i].getWidth());
			}
			mX /= cellWidth;
			mX -= getX();
			
			return mX;
		}
		@Override
		public int getH()
		{
			if (components.length == 0) return 0;
			int mY = components[0].getY()  + components[0].getHeight();
			for (int i = 1; i < components.length; ++i)
			{
				mY = Math.max(mY, components[i].getY() + components[i].getHeight());
			}
			mY /= cellHeight;
			mY -= getY();
			
			return mY;
		}

		@Override
		public void setX(int x)
		{
			int dX = x - getX();
			for (int i = 0; i < components.length; ++i)
				components[i].setLocation(components[i].getX() + dX * cellWidth, components[i].getY());
		}

		@Override
		public void setY(int y)
		{
			int dY = y - getY();
			for (int i = 0; i < components.length; ++i)
				components[i].setLocation(components[i].getX(), components[i].getY() + dY * cellHeight);
		}

		@Override
		public void setW(int w)
		{
			int dW = w - getW();
			for (int i = 0; i < components.length; ++i)
				components[i].setSize(components[i].getWidth() + dW * cellWidth, components[i].getHeight());
		}

		@Override
		public void setH(int h)
		{
			int dH = h - getH();
			for (int i = 0; i < components.length; ++i)
				components[i].setSize(components[i].getWidth(), components[i].getHeight() + dH * cellHeight);
		}
	}
	private class EditorPanelSubHandle extends EditorPanelComponentHandle implements SubHandle
	{
		private MenuSlate handledSlate;
		
		public EditorPanelSubHandle(MenuSlate handledSlate)
		{
			super((EditorPanel) handledSlate);
			this.handledSlate = handledSlate;
		}
		
		public EditorPanel getPanel() {return (EditorPanel) handledSlate;}
		
		@Override
		public void removeSlate()
		{
			synchronized (componentLock)
			{
				children.remove(handledSlate);
				remove(getPanel());
				components = new Component[0];
				revalidate();
			}
		}

		@Override
		public void swapSlate(MenuSlate newSlate)
		{
			synchronized (componentLock)
			{
				removeSlate();
				handledSlate = newSlate;
				getPanel().setBounds(getX() * cellWidth, getY() * cellHeight, getW() * cellWidth, getY() * cellHeight);
				add(getPanel());
				children.add(handledSlate);
				components = new Component[] {getPanel()};
				revalidate();
			}
		}		
	}
	private class EditorPanelTabHandle extends EditorPanelComponentHandle implements TabHandle
	{
		private Map<String, EditorPanel> slates = new HashMap<String, EditorPanel>();
		private JTabbedPane contentPane;
		
		public EditorPanelTabHandle(JTabbedPane contentPane)
		{
			super(contentPane);
			this.contentPane = contentPane;
		}
		@Override
		public void setTab(String name, MenuSlate slate)
		{
			synchronized (componentLock)
			{
				if (contentPane.indexOfTab(name) == -1) contentPane.add(name, (EditorPanel) slate);
				else {slates.get(name).clear(); contentPane.setComponentAt(contentPane.indexOfTab(name), (EditorPanel) slate);}
				slates.put(name, (EditorPanel) slate);
				children.add(slate);
			}
		}

		@Override
		public void removeTab(String name)
		{
			synchronized (componentLock)
			{
				contentPane.remove(slates.get(name));
				children.remove(slates.get(name));
				slates.remove(name);
			}
		}
	}
	private class EditorPanelScrollHandle extends EditorPanelComponentHandle implements SubHandle
	{
		private JScrollPane pane;
		private MenuSlate handledSlate;
		
		public EditorPanelScrollHandle(JScrollPane pane, MenuSlate handledSlate)
		{
			super((EditorPanel) handledSlate);
			this.pane = pane;
			this.handledSlate = handledSlate;
		}
		
		public EditorPanel getPanel() {return (EditorPanel) handledSlate;}
		
		@Override
		public void removeSlate()
		{
			synchronized (componentLock)
			{
				children.remove(handledSlate);
				pane.remove(getPanel());
				components = new Component[0];
				revalidate();
			}
		}

		@Override
		public void swapSlate(MenuSlate newSlate)
		{
			synchronized (componentLock)
			{
				removeSlate();
				handledSlate = newSlate;
				getPanel().setBounds(getX() * cellWidth, getY() * cellHeight, getW() * cellWidth, getY() * cellHeight);
				pane.add(getPanel());
				children.add(handledSlate);
				components = new Component[] {getPanel()};
				revalidate();
			}
		}		
	}
	private class BasicResizeTask implements ResizeTask
	{
		private ComponentHandle parent;
		private Alignable.AlignmentPoint anchorPoint;
		private Component component;
		private int x, y, w, h;
		
		public BasicResizeTask(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,Component component, int x, int y, int w, int h)
		{
			this.parent = parent;
			this.anchorPoint = anchorPoint;
			this.component = component;
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}
		@Override
		public void onResize()
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			component.setBounds((anchorPos.x + x) * cellWidth, (anchorPos.y + y) * cellHeight, w * cellWidth, h * cellHeight);
			int BRW = anchorPos.x + x + w, BRH = anchorPos.y + y + h;
			if (BRW > getHCells())
			{
				double hFactor = ((double) BRW) / getHCells();
				setSize((int) (getWidth() * hFactor), getHeight());
				setCells(BRW, getVCells());
			}
			if (BRH > getVCells())
			{
				double vFactor = ((double) BRH) / getVCells();
				setSize(getWidth(), (int) (getHeight() * vFactor));
				setCells(getHCells(), BRH);
			}
		}
		
	}
	private IntPoint2D getAnchorPos(ComponentHandle comp, Alignable.AlignmentPoint anchor)
	{
		if (comp == null || anchor == null) return new IntPoint2D(0, 0);
		switch (anchor)
		{
		case northWest: return new IntPoint2D(comp.getX(), comp.getY());
		case north: return new IntPoint2D(comp.getX() + comp.getW() / 2, comp.getY());
		case northEast: return new IntPoint2D(comp.getX() + comp.getW(), comp.getY());
		
		case west: return new IntPoint2D(comp.getX(), comp.getY() + comp.getH() / 2);
		case center: return new IntPoint2D(comp.getX() + comp.getW() / 2, comp.getY()  + comp.getH() / 2);
		case east: return new IntPoint2D(comp.getX() + comp.getW(), comp.getY() + comp.getH() / 2);
		
		case southWest: return new IntPoint2D(comp.getX(), comp.getY() + comp.getH());
		case south: return new IntPoint2D(comp.getX() + comp.getW() / 2, comp.getY()  + comp.getH());
		case southEast: return new IntPoint2D(comp.getX() + comp.getW(), comp.getY() + comp.getH());
		default: return new IntPoint2D(0, 0);
		}
	}
	/**
	 * Create the panel.
	 */
	public EditorPanel()
	{
		super();
		
		updateTasks = new ArrayList<UpdateTask>();
		resizeTasks = new ArrayList<ResizeTask>();
		children = new ArrayList<MenuSlate>();
		
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(null);
		setSize(640, 480);
		
		timer.scheduleAtFixedRate(updateTask, 0, 1000 / ConfigManager.getFramerateCap());
		timer.scheduleAtFixedRate(resizeTask, 0, 1000 / ConfigManager.getFramerateCap());
	}
	/**
	 * Create the panel.
	 */
	public EditorPanel(int width, int height, int cellsH, int cellsV)
	{
		super();
		
		updateTasks = new ArrayList<UpdateTask>();
		resizeTasks = new ArrayList<ResizeTask>();
		children = new ArrayList<MenuSlate>();
		timer = new Timer();
		
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(null);
		
		setVisible(true);
		setCells(cellsH, cellsV);
		setSize(width, height);

		timer.scheduleAtFixedRate(updateTask, 0, 1000 / ConfigManager.getFramerateCap());
		timer.scheduleAtFixedRate(resizeTask, 0, 1000 / ConfigManager.getFramerateCap());
	}
	
	@Override
	public void setCells(int h, int v)
	{		
		cellWidth = getWidth() / h;
		cellHeight = getHeight() / v;
		
		cellsH = h;
		cellsV = v;
	}
	@Override public int getHCells() {return cellsH;}
	@Override public int getVCells() {return cellsV;}
	@Override
	public void clear()
	{
		updateTasks.clear();
		resizeTasks.clear();
		for (MenuSlate child : children) child.clear();
		children.clear();
		this.removeAll();
	}
	
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		if (cellsH != 0 && cellsV != 0) setCells(cellsH, cellsV);
	}
	@Override
	public void setBounds(int x, int y, int width, int height)
	{
		super.setBounds(x, y, width, height);
		if (cellsH != 0 && cellsV != 0) setCells(cellsH, cellsV);
	}
	
	@Override
	public ComponentHandle addInfo(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int contentLength, int h,
			InfoFunction<String> function)
	{
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		resizeTasks.add(new BasicResizeTask(parent, anchorPoint, labelLabel, x, y, labelLength, h));
		JLabel contentLabel = new JLabel();
		add(contentLabel);
		resizeTasks.add(new BasicResizeTask(parent, anchorPoint, contentLabel, x + labelLength, y, contentLength, h));	
		labelLabel.setText(label);
		contentLabel.setText(function.getValue());
		
		updateTasks.add(() -> {contentLabel.setText(function.getValue());});

		return new EditorPanelComponentHandle(labelLabel, contentLabel);
	}
	@Override public ComponentHandle addInfo(int x, int y, String label, int labelLength, int contentLength, int h,
			InfoFunction<String> function) {return addInfo(null, null, x, y, label, labelLength, contentLength, h, function);}
	
	@Override
	public ComponentHandle addTextbar(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint, int x, int y, String label, int labelLength, int maxLength, int contentLength, int h,
			DataFunction<String> function)
	{
		final DataFunction<String> wrappedFunction = new DataFunctionWrapper<String>(function);
		
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		resizeTasks.add(new BasicResizeTask(parent, anchorPoint, labelLabel, x, y, labelLength, h));
		JTextField contentField = new JTextField();
		add(contentField);
		resizeTasks.add(new BasicResizeTask(parent, anchorPoint, contentField, x + labelLength, y, contentLength, h));	
		labelLabel.setText(label);
		contentField.setText(function.getValue());
		
		updateTasks.add(() ->
		{if (!contentField.getText().equals(wrappedFunction.getValue())) contentField.setText(wrappedFunction.getValue());});

		contentField.getDocument().addDocumentListener(new DocumentListener()
		{
			private void onUpdate()
			{
				synchronized (componentLock) {wrappedFunction.setValue(contentField.getText());}
			}
			@Override
			public void insertUpdate(DocumentEvent e) {onUpdate();}
			@Override
			public void removeUpdate(DocumentEvent e) {onUpdate();}
			@Override
			public void changedUpdate(DocumentEvent e) {onUpdate();}
		});
		
		return new EditorPanelComponentHandle(labelLabel, contentField);
	}
	@Override
	public ComponentHandle addTextbar(int x, int y, String label, int labelLength, int maxLength, int contentLength, int h,
			DataFunction<String> function) {return addTextbar(null, null, x, y, label, labelLength, maxLength, contentLength, h, function);}

	@Override
	public ComponentHandle addIntegerWheel(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int min, int initial, int max, int contentLength, int h,
			DataFunction<Integer> function)
	{
		final DataFunction<Integer> wrappedFunction = new DataFunctionWrapper<Integer>(function);
		
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		
		resizeTasks.add(() ->
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			labelLabel.setBounds((anchorPos.x + x) * cellWidth, (anchorPos.y + y) * cellHeight, labelLength * cellWidth, h * cellHeight);
		});
		JSpinner contentSpinner = new JSpinner();
		add(contentSpinner);
		
		contentSpinner.setModel(new SpinnerNumberModel(initial, min, max, 1));
		resizeTasks.add(() -> 
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			contentSpinner.setBounds((anchorPos.x + x + labelLength) * cellWidth, (anchorPos.y + y) * cellHeight, contentLength * cellWidth, h * cellHeight);
		});
		
		labelLabel.setText(label);
		contentSpinner.setValue(wrappedFunction.getValue());
		
		updateTasks.add(() -> {contentSpinner.setValue(wrappedFunction.getValue());});
		contentSpinner.addChangeListener((ChangeEvent e) -> {synchronized (componentLock) {wrappedFunction.setValue((Integer) contentSpinner.getValue());}});
		
		return new EditorPanelComponentHandle(labelLabel, contentSpinner);
	}
	@Override
	public ComponentHandle addIntegerWheel(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int min, int max, int contentLength, int h,
			DataFunction<Integer> function)
	{
		int initial = min;
		if (function.getValue() != null && min <= function.getValue() && function.getValue() <= max) initial = function.getValue();
		return addIntegerWheel(parent, anchorPoint, x, y, label, labelLength, min, initial, max, contentLength, h, function);
	}
	@Override
	public ComponentHandle addIntegerWheel(int x, int y, String label, int labelLength, int min, int initial, int max, int contentLength, int h,
			DataFunction<Integer> function) {return addIntegerWheel(null, null, x, y, label, labelLength, min, initial, max, contentLength, h, function);}
	@Override
	public ComponentHandle addIntegerWheel(int x, int y, String label, int labelLength, int min, int max, int contentLength, int h,
			DataFunction<Integer> function) {return addIntegerWheel(null, null, x, y, label, labelLength, min, max, contentLength, h, function);}
	
	@Override
	public ComponentHandle addDoubleWheel(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, double min, double initial, double max, int digits, int contentLength, int h,
			DataFunction<Double> function)
	{
		final DataFunction<Double> wrappedFunction = new DataFunctionWrapper<Double>(function);
		
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		resizeTasks.add(() ->
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			labelLabel.setBounds((anchorPos.x + x) * cellWidth, (anchorPos.y + y) * cellHeight, labelLength * cellWidth, h * cellHeight);
		});
		JSpinner contentSpinner = new JSpinner();
		add(contentSpinner);
		contentSpinner.setModel(new SpinnerNumberModel(initial, min, max, Math.pow(10, -digits - 1)));
		resizeTasks.add(() -> 
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			contentSpinner.setBounds((anchorPos.x + x + labelLength) * cellWidth, (anchorPos.y + y) * cellHeight, contentLength * cellWidth, h * cellHeight);
		});
		labelLabel.setText(label);
		if (wrappedFunction.getValue() != null) contentSpinner.setValue(wrappedFunction.getValue());
		contentSpinner.addChangeListener((ChangeEvent e) -> {synchronized (componentLock) {wrappedFunction.setValue((Double) contentSpinner.getValue());}});
		
		return new EditorPanelComponentHandle(labelLabel, contentSpinner);
	}
	@Override
	public ComponentHandle addDoubleWheel(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, double min, double max, int digits, int contentLength, int h,
			DataFunction<Double> function)
	{
		double initial = min;
		if (function.getValue() != null) initial = function.getValue();
		return addDoubleWheel(parent, anchorPoint, x, y, label, labelLength, min, initial, max, digits, contentLength, h, function);
	}
	@Override
	public ComponentHandle addDoubleWheel(int x, int y, String label, int labelLength, double min, double initial, double max, int digits, int contentLength, int h,
			DataFunction<Double> function) {return addDoubleWheel(null, null, x, y, label, labelLength, min, initial, max, digits, contentLength, h, function);}
	@Override
	public ComponentHandle addDoubleWheel(int x, int y, String label, int labelLength, double min, double max, int digits, int contentLength, int h,
			DataFunction<Double> function) {return addDoubleWheel(null, null, x, y, label, labelLength, min, max, digits, contentLength, h, function);}

	@Override
	public ComponentHandle addCheckbox(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int contentLength, int h,
			DataFunction<Boolean> function)
	{
		final DataFunction<Boolean> wrappedFunction = new DataFunctionWrapper<Boolean>(function);

		JCheckBox contentBox = new JCheckBox();
		contentBox.setText(label);
		contentBox.setHorizontalTextPosition(2); // Left
		contentBox.setOpaque(false);
		add(contentBox);
		resizeTasks.add(() -> 
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			contentBox.setBounds((anchorPos.x + x) * cellWidth, (anchorPos.y + y) * cellHeight, (labelLength + contentLength) * cellWidth, h * cellHeight);
		});
		contentBox.setSelected(wrappedFunction.getValue());
		
		updateTasks.add(() -> {contentBox.setSelected(wrappedFunction.getValue());});
		contentBox.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				synchronized (componentLock) {wrappedFunction.setValue(contentBox.isSelected());}
			}
		});
		
		return new EditorPanelComponentHandle(contentBox);
	}
	@Override
	public ComponentHandle addCheckbox(int x, int y, String label, int labelLength, int contentLength, int h,
			DataFunction<Boolean> function) {return addCheckbox(null, null, x, y, label, labelLength, contentLength, h, function);}
	
	@Override
	public ComponentHandle addButton(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int w, int h, ButtonFunction function)
	{
		JButton contentButton = new JButton();
		add(contentButton);
		resizeTasks.add(() ->
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			contentButton.setBounds((anchorPos.x + x) * cellWidth, (anchorPos.y + y) * cellHeight, w * cellWidth, h * cellHeight);
		});
		contentButton.setText(label);
		
		contentButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				synchronized (componentLock) {function.onClick();}
			}
		});
		
		return new EditorPanelComponentHandle(contentButton);
	}
	@Override
	public ComponentHandle addButton(int x, int y, String label, int w, int h, ButtonFunction function)
	{
		return addButton(null, null, x, y, label, w, h, function);
	}
	
	@Override
	public <T> ComponentHandle addOptions(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int contentLength, int h, String[] optionLabels, T[] options,
			DataFunction<T> function)
	{
		final DataFunction<T> wrappedFunction = new DataFunctionWrapper<T>(function);
		
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		resizeTasks.add(() ->
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			labelLabel.setBounds((anchorPos.x + x) * cellWidth, (anchorPos.y + y) * cellHeight, labelLength * cellWidth, h * cellHeight);
		});
		
		JComboBox<String> contentBox = new JComboBox<String>(optionLabels);
		add(contentBox);
		resizeTasks.add(() -> 
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			contentBox.setBounds((anchorPos.x + x + labelLength) * cellWidth, (anchorPos.y + y) * cellHeight, contentLength * cellWidth, h * cellHeight);
		});
		labelLabel.setText(label);
		contentBox.setEditable(false);
		
		updateTasks.add(() -> {contentBox.setSelectedIndex(Arrays.asList(options).indexOf(wrappedFunction.getValue()));});
		contentBox.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e) {synchronized (componentLock) {wrappedFunction.setValue(options[contentBox.getSelectedIndex()]);}}
		});
		
		return new EditorPanelComponentHandle(labelLabel, contentBox);
	}
	@Override
	public <T> ComponentHandle addOptions(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int contentLength, int labelHeight, int contentHeight, String[] optionLabels, T[] options,
			DataFunction<T> function)
	{
		final DataFunction<T> wrappedFunction = new DataFunctionWrapper<T>(function);
		
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		resizeTasks.add(() ->
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			labelLabel.setBounds((anchorPos.x + x) * cellWidth, (anchorPos.y + y) * cellHeight, labelLength * cellWidth, labelHeight * cellHeight);
		});
		DefaultListModel<String> contentModel = new DefaultListModel<String>();
		for (int i = 0; i < optionLabels.length; ++i) contentModel.add(i, optionLabels[i]);
		JList<String> contentList = new JList<String>();
		contentList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentList.setModel(contentModel);
		add(contentList);
		resizeTasks.add(() -> 
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			contentList.setBounds((anchorPos.x + x + labelLength) * cellWidth, (anchorPos.y + y) * cellHeight, contentLength * cellWidth, contentHeight * cellHeight);
		});
		
		labelLabel.setText(label);
		
		updateTasks.add(() -> {contentList.setSelectedIndex(Arrays.asList(options).indexOf(wrappedFunction.getValue()));});
		contentList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) {synchronized (componentLock) {wrappedFunction.setValue(options[contentList.getSelectedIndex()]);}}
		});
		
		return new EditorPanelComponentHandle(labelLabel, contentList);
	}	
	@Override
	public <E extends Enum<E>> ComponentHandle addOptions(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int contentLength, int h,
			E[] options, DataFunction<E> function)
	{
		String[] optionLabels = new String[options.length];
		for (int i = 0; i < options.length; ++i) optionLabels[i] = options[i].name();
		return addOptions(parent, anchorPoint, x, y, label, labelLength, contentLength, h, optionLabels, options, function);
	}
	@Override
	public <E extends Enum<E>> ComponentHandle addOptions(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int contentLength, int labelHeight, int contentHeight, 
			E[] options, DataFunction<E> function)
	{
		String[] optionLabels = new String[options.length];
		for (int i = 0; i < options.length; ++i) optionLabels[i] = options[i].name();
		return addOptions(parent, anchorPoint, x, y, label, labelLength, contentLength, labelHeight, contentHeight, optionLabels, options, function);
	}
	@Override
	public <T> ComponentHandle addOptions(int x, int y, String label, int labelLength, int contentLength, int h, String[] optionLabels, T[] options,
			DataFunction<T> function)
	{
		return addOptions(null, null, x, y, label, labelLength, contentLength, h, optionLabels, options, function);
	}
	@Override
	public <T> ComponentHandle addOptions(int x, int y, String label, int labelLength, int contentLength, int labelHeight, int contentHeight, String[] optionLabels, T[] options,
			DataFunction<T> function)
	{
		return addOptions(null, null, x, y, label, labelLength, contentLength, labelHeight, contentHeight, optionLabels, options, function);
	}	
	@Override
	public <E extends Enum<E>> ComponentHandle addOptions(int x, int y, String label, int labelLength, int contentLength, int h,
			E[] options, DataFunction<E> function)
	{
		return addOptions(null, null, x, y, label, labelLength, contentLength, h, options, function);
	}
	@Override
	public <E extends Enum<E>> ComponentHandle addOptions(int x, int y, String label, int labelLength, int contentLength, int labelHeight, int contentHeight, 
			E[] options, DataFunction<E> function)
	{
		return addOptions(null, null, x, y, label, labelLength, contentLength, labelHeight, contentHeight, options, function);
	}

	@Override
	public ComponentHandle addSprite(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, int w, int h, InfoFunction<Sprite> function)
	{
		ScreenCanvas c = new ScreenCanvas();
		add(c);
		resizeTasks.add(() ->
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			c.setBounds((anchorPos.x + x) * cellWidth, (anchorPos.y + y) * cellHeight, w * cellWidth, h * cellHeight);
		});
		updateTasks.add(() -> 
		{
			IntPoint2D sSize = function.getValue().getSize();
			c.setScale(((float) w * (float) cellWidth) / (float) sSize.x, ((float) h * (float) cellHeight) / (float) sSize.y);
			function.getValue().render(c, new IntPoint2D(0, 0));
			c.repaint();
		});
		
		return new EditorPanelComponentHandle(c);
	}
	@Override
	public ComponentHandle addSprite(int x, int y, int w, int h, InfoFunction<Sprite> function)
	{
		return addSprite(null, null, x, y, w, h, function);
	}

	@Override
	public SubHandle addSubSlate(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, int w, int h, MenuSlate subSlate)
	{
		add((Component) subSlate);
		revalidate();
		children.add(subSlate);
		EditorPanelSubHandle handle = new EditorPanelSubHandle(subSlate);
		resizeTasks.add(() ->
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			int newW = w; if (newW == -1) newW = handle.getPanel().getHCells();
			int newH = h; if (newH == -1) newH = handle.getPanel().getVCells();
			handle.getPanel().setBounds((anchorPos.x + x) * cellWidth, (anchorPos.y + y) * cellHeight, newW * cellWidth, newH * cellHeight);
			int BRW = anchorPos.x + x + newW , BRH = anchorPos.y + y + newH;
			if (BRW > getHCells())
			{
				double hFactor = ((double) BRW) / getHCells();
				setSize((int) (getWidth() * hFactor), getHeight());
				setCells(BRW, getVCells());
			}
			if (BRH > getVCells())
			{
				double vFactor = ((double) BRH) / getVCells();
				setSize(getWidth(), (int) (getHeight() * vFactor));
				setCells(getHCells(), BRH);
			}
		});
		return handle;
	}
	@Override
	public SubHandle addSubSlate(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, MenuSlate subSlate) {return addSubSlate(parent, anchorPoint, x, y, -1, -1, subSlate);}
	@Override
	public SubHandle addSubSlate(int x, int y, int w, int h, MenuSlate subSlate)
	{
		return addSubSlate(null, null, x, y, w, h, subSlate);
	}
	@Override
	public SubHandle addSubSlate(int x, int y, MenuSlate subSlate)
	{
		return addSubSlate(null, null, x, y, subSlate);
	}
	@Override
	public TabHandle addTabbedSection(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, int w, int h)
	{
		JTabbedPane contentPane = new JTabbedPane();
		
		contentPane.setSize(w * cellWidth, h * cellHeight);
		add(contentPane);
		
		TabHandle tabHandle = new EditorPanelTabHandle(contentPane);
		
		resizeTasks.add(() ->
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			contentPane.setBounds((anchorPos.x + x) * cellWidth, (anchorPos.y + y) * cellHeight, w * cellWidth, h * cellHeight);
		});
		return tabHandle;
	}
	@Override
	public TabHandle addTabbedSection(int x, int y, int w, int h)
	{
		return addTabbedSection(null, null, x, y, w, h);
	}
	
	@Override
	public SubHandle addScrollSlate(ComponentHandle parent, AlignmentPoint anchorPoint, int x, int y, int w1, int h1,
			int w2, int h2, MenuSlate subSlate)
	{
		JScrollPane scrollPane = new JScrollPane((Component) subSlate, 0, 0);
		add(scrollPane);
		revalidate();
		children.add(subSlate);
		EditorPanelScrollHandle handle = new EditorPanelScrollHandle(scrollPane, subSlate);
		resizeTasks.add(() ->
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			scrollPane.setBounds((anchorPos.x + x) * cellWidth, (anchorPos.y + y) * cellHeight, w1 * cellWidth, h1 * cellHeight);
			handle.getPanel().setSize(w2 * cellWidth,  h2 * cellHeight);
		});
		return handle;
	}
	@Override
	public SubHandle addScrollSlate(ComponentHandle parent, AlignmentPoint anchorPoint, int x, int y, int w, int h,
			MenuSlate subSlate)
	{
		JScrollPane scrollPane = new JScrollPane((Component) subSlate);
		add(scrollPane);
		revalidate();
		children.add(subSlate);
		EditorPanelScrollHandle handle = new EditorPanelScrollHandle(scrollPane, subSlate);
		resizeTasks.add(() ->
		{
			IntPoint2D anchorPos = getAnchorPos(parent, anchorPoint);
			scrollPane.setBounds((anchorPos.x + x) * cellWidth, (anchorPos.y + y) * cellHeight, w * cellWidth, h * cellHeight);
//			handle.getPanel().setSize(handle.getPanel().getHCells() * cellWidth,  handle.getPanel().getVCells() * cellHeight);
		});
		return handle;
	}
	@Override
	public SubHandle addScrollSlate(int x, int y, int w1, int h1, int w2, int h2, MenuSlate subSlate) {return addScrollSlate(null, null, x, y, w1, h1, w2, h2, subSlate);}
	@Override
	public SubHandle addScrollSlate(int x, int y, int w, int h, MenuSlate subSlate) {return addScrollSlate(null, null, x, y, w, h, subSlate);}
	
	@Override
	public void finalize()
	{
		clear();
		updateTask.cancel();
		resizeTask.cancel();
		timer.purge();
	}
}
