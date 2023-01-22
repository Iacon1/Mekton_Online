// Simple menu for editing things in the editor.

package GameEngine.Editor;

import javax.swing.JPanel;
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
import java.awt.Image;

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
import GameEngine.MenuSlate;
import GameEngine.Configurables.ConfigManager;
import GameEngine.Graphics.ScreenCanvas;
import GameEngine.Graphics.Sprite;
import GameEngine.Graphics.UtilCanvas;
import Utils.Logging;
import Utils.MiscUtils;

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
	private static Timer timer = new Timer();
	private TimerTask updateTask = new TimerTask()
	{
		@Override public void run() 
		{
			for (UpdateTask task : updateTasks) task.onUpdate();
			
		}
	};
	private TimerTask resizeTask = new TimerTask()
	{
		@Override public void run() 
		{
			for (ResizeTask task : resizeTasks) task.onResize();
		}
	};
	
	/**
	 * Create the panel.
	 */
	public EditorPanel()
	{
		super();
		
		updateTasks = new ArrayList<UpdateTask>();
		resizeTasks = new ArrayList<ResizeTask>();

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
	public void addInfo(int x, int y, String label, int labelLength, int contentLength, int h,
			InfoFunction<String> function)
	{
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		resizeTasks.add(() -> {labelLabel.setBounds(x * cellWidth, y * cellHeight, labelLength * cellWidth, h * cellHeight);});
		JLabel contentLabel = new JLabel();
		add(contentLabel);
		resizeTasks.add(() -> {contentLabel.setBounds((x + labelLength) * cellWidth, y * cellHeight, contentLength * cellWidth, h * cellHeight);});
		
		labelLabel.setText(label);
		contentLabel.setText(function.getValue());
		
		updateTasks.add(() -> {contentLabel.setText(function.getValue());});
	}

	@Override
	public void addTextbar(int x, int y, String label, int labelLength, int maxLength, int contentLength, int h,
			DataFunction<String> function)
	{
		final DataFunction<String> wrappedFunction = new DataFunctionWrapper<String>(function);
		
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		resizeTasks.add(() -> {labelLabel.setBounds(x * cellWidth, y * cellHeight, labelLength * cellWidth, h * cellHeight);});
		JTextField contentField = new JTextField();
		add(contentField);
		resizeTasks.add(() -> {contentField.setBounds((x + labelLength) * cellWidth, y * cellHeight, contentLength * cellWidth, h * cellHeight);});
		
		labelLabel.setText(label);
		contentField.setText(function.getValue());
		
		updateTasks.add(() ->
		{if (!contentField.getText().equals(wrappedFunction.getValue())) contentField.setText(wrappedFunction.getValue());});

		contentField.getDocument().addDocumentListener(new DocumentListener()
		{
			private void onUpdate()
			{
				wrappedFunction.setValue(contentField.getText());
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
	public void addIntegerWheel(int x, int y, String label, int labelLength, int min, int max, int contentLength, int h,
			DataFunction<Integer> function)
	{
		final DataFunction<Integer> wrappedFunction = new DataFunctionWrapper<Integer>(function);
		
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		
		resizeTasks.add(() -> {labelLabel.setBounds(x * cellWidth, y * cellHeight, labelLength * cellWidth, h * cellHeight);});
		JSpinner contentSpinner = new JSpinner();
		add(contentSpinner);
		
		contentSpinner.setModel(new SpinnerNumberModel(min, min, max, 1));
		resizeTasks.add(() -> {contentSpinner.setBounds((x + labelLength) * cellWidth, y * cellHeight, contentLength * cellWidth, h * cellHeight);});
		
		labelLabel.setText(label);
		contentSpinner.setValue(wrappedFunction.getValue());
		
		updateTasks.add(() -> {contentSpinner.setValue(wrappedFunction.getValue());});
		contentSpinner.addChangeListener((ChangeEvent e) -> {wrappedFunction.setValue((Integer) contentSpinner.getValue());});
	}
	@Override
	public void addDoubleWheel(int x, int y, String label, int labelLength, double min, double initial, double max, int digits, int contentLength, int h,
			DataFunction<Double> function)
	{
		final DataFunction<Double> wrappedFunction = new DataFunctionWrapper<Double>(function);
		
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		resizeTasks.add(() -> {labelLabel.setBounds(x * cellWidth, y * cellHeight, labelLength * cellWidth, h * cellHeight);});
		
		JSpinner contentSpinner = new JSpinner();
		add(contentSpinner);
		contentSpinner.setModel(new SpinnerNumberModel(initial, min, max, Math.pow(10, -digits - 1)));
		resizeTasks.add(() -> {contentSpinner.setBounds((x + labelLength) * cellWidth, y * cellHeight, contentLength * cellWidth, h * cellHeight);});
		
		labelLabel.setText(label);
		if (wrappedFunction.getValue() != null) contentSpinner.setValue(wrappedFunction.getValue());
		
//		updateTasks.add(() -> {if (wrappedFunction.getValue() != null) contentSpinner.setValue(wrappedFunction.getValue());});
		contentSpinner.addChangeListener((ChangeEvent e) -> {wrappedFunction.setValue((Double) contentSpinner.getValue());});
	}
	@Override
	public void addDoubleWheel(int x, int y, String label, int labelLength, double min, double max, int digits, int contentLength, int h,
			DataFunction<Double> function)
	{
		double initial = min;
		if (function.getValue() != null) initial = function.getValue();
		addDoubleWheel(x, y, label, labelLength, min, initial, max, digits, contentLength, h, function);
	}
	@Override
	public void addCheckbox(int x, int y, String label, int labelLength, int contentLength, int h,
			DataFunction<Boolean> function)
	{
		final DataFunction<Boolean> wrappedFunction = new DataFunctionWrapper<Boolean>(function);

		JCheckBox contentBox = new JCheckBox();
		contentBox.setText(label);
		contentBox.setHorizontalTextPosition(2); // Left
		contentBox.setOpaque(false);
		add(contentBox);
		resizeTasks.add(() -> {contentBox.setBounds(x * cellWidth, y * cellHeight, (labelLength + contentLength) * cellWidth, h * cellHeight);});
		
		contentBox.setSelected(wrappedFunction.getValue());
		
		updateTasks.add(() -> {contentBox.setSelected(wrappedFunction.getValue());});
		contentBox.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				wrappedFunction.setValue(contentBox.isSelected());
			}
		});
	}

	@Override
	public <T> void addOptions(int x, int y, String label, int labelLength, int contentLength, int h, String[] optionLabels, T[] options,
			DataFunction<T> function)
	{
		final DataFunction<T> wrappedFunction = new DataFunctionWrapper<T>(function);
		
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		resizeTasks.add(() -> {labelLabel.setBounds(x * cellWidth, y * cellHeight, labelLength * cellWidth, h * cellHeight);});
		
		JComboBox<String> contentBox = new JComboBox<String>(optionLabels);
		add(contentBox);
		resizeTasks.add(() -> {contentBox.setBounds((x + labelLength) * cellWidth, y * cellHeight, contentLength * cellWidth, h * cellHeight);});
		
		labelLabel.setText(label);
		contentBox.setEditable(false);
		
		updateTasks.add(() -> {contentBox.setSelectedIndex(Arrays.asList(options).indexOf(wrappedFunction.getValue()));});
		contentBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				wrappedFunction.setValue(options[contentBox.getSelectedIndex()]);
			}
		});
	}
	@Override
	public <T> void addOptions(int x, int y, String label, int labelLength, int contentLength, int labelHeight, int contentHeight, String[] optionLabels, T[] options,
			DataFunction<T> function)
	{
		final DataFunction<T> wrappedFunction = new DataFunctionWrapper<T>(function);
		
		JLabel labelLabel = new JLabel();
		add(labelLabel);
		resizeTasks.add(() -> {labelLabel.setBounds(x * cellWidth, y * cellHeight, labelLength * cellWidth, labelHeight * cellHeight);});
		
		DefaultListModel<String> contentModel = new DefaultListModel<String>();
		for (int i = 0; i < optionLabels.length; ++i) contentModel.add(i, optionLabels[i]);
		JList<String> contentList = new JList<String>();
		contentList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentList.setModel(contentModel);
		add(contentList);
		resizeTasks.add(() -> {contentList.setBounds((x + labelLength) * cellWidth, y * cellHeight, contentLength * cellWidth, contentHeight * cellHeight);});
		
		labelLabel.setText(label);
		
		updateTasks.add(() -> {contentList.setSelectedIndex(Arrays.asList(options).indexOf(wrappedFunction.getValue()));});
		contentList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				wrappedFunction.setValue(options[contentList.getSelectedIndex()]);
			}
		});
	}
	
	@Override
	public <E extends Enum<E>> void addOptions(int x, int y, String label, int labelLength, int contentLength, int h,
			E[] options, DataFunction<E> function)
	{
		String[] optionLabels = new String[options.length];
		for (int i = 0; i < options.length; ++i) optionLabels[i] = options[i].name();
		addOptions(x, y, label, labelLength, contentLength, h, optionLabels, options, function);
	}
	@Override
	public <E extends Enum<E>> void addOptions(int x, int y, String label, int labelLength, int contentLength, int labelHeight, int contentHeight, 
			E[] options, DataFunction<E> function)
	{
		String[] optionLabels = new String[options.length];
		for (int i = 0; i < options.length; ++i) optionLabels[i] = options[i].name();
		addOptions(x, y, label, labelLength, contentLength, labelHeight, contentHeight, optionLabels, options, function);
	}
	
	@Override
	public void addButton(int x, int y, String label, int w, int h, ButtonFunction function)
	{
		JButton contentButton = new JButton();
		add(contentButton);
		resizeTasks.add(() -> {contentButton.setBounds(x * cellWidth, y * cellHeight, w * cellWidth, h * cellHeight);});
		
		contentButton.setText(label);
		
		contentButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				function.onClick();
			}
		});
	}
	
	@Override
	public SubHandle addSubSlate(int x, int y, int w, int h, MenuSlate subSlate)
	{
		((EditorPanel) subSlate).setBounds(x * cellWidth, y * cellHeight, w * cellWidth, h * cellHeight);
		add((Component) subSlate);
		revalidate();
		resizeTasks.add(() -> {((EditorPanel) subSlate).setBounds(x * cellWidth, y * cellHeight, w * cellWidth, h * cellHeight);});
//		subSlate.setCells(w, h);
		return new SubHandle()
		{
			private MenuSlate handledSlate = subSlate;
			
			@Override
			public void removeSlate()
			{
				remove((Component) handledSlate);
				revalidate();
			}

			@Override
			public void swapSlate(MenuSlate newSlate)
			{
				removeSlate();
				handledSlate = newSlate;
				((EditorPanel) handledSlate).setBounds(x * cellWidth, y * cellHeight, w * cellWidth, h * cellHeight);
				add((Component) handledSlate);
				revalidate();
			}
		};
	}
	
	@Override
	public TabHandle addTabbedSection(int x, int y, int w, int h)
	{
		JTabbedPane contentPane = new JTabbedPane();
		
		contentPane.setSize(w * cellWidth, h * cellHeight);
		add(contentPane);
		
		TabHandle tabHandle = new TabHandle()
		{
			private Map<String, EditorPanel> slates = new HashMap<String, EditorPanel>();
//			private Map<String, Integer> slateResizeIDs = new HashMap<String, Integer>();
			
			@Override
			public void setTab(String name, MenuSlate slate)
			{
				if (contentPane.indexOfTab(name) == -1) contentPane.add(name, (EditorPanel) slate);
				else contentPane.setComponentAt(contentPane.indexOfTab(name), (EditorPanel) slate);
				slates.put(name, (EditorPanel) slate);
//				slateResizeIDs.put(name, i++);
//				resizeTasks.add(() -> {slates.get(name).setSize(w * cellWidth, h * cellHeight);});
//				slate.setCells(w, h);
			}

			@Override
			public void removeTab(String name)
			{
				contentPane.remove(slates.get(name));
//				resizeTasks.remove((int) slateResizeIDs.get(name));
				slates.remove(name);
//				slateResizeIDs.remove(name);
			}
		};
		
		resizeTasks.add(() -> {contentPane.setBounds(x * cellWidth, y * cellHeight, w * cellWidth, h * cellHeight);});
		return tabHandle;
	}
	@Override
	public void addSprite(int x, int y, int w, int h, InfoFunction<Sprite> function)
	{
		ScreenCanvas c = new ScreenCanvas();
		add(c);
		c.setBounds(x * cellWidth, y * cellHeight, w * cellWidth, h * cellHeight);
		
		updateTasks.add(() -> 
		{
			IntPoint2D sSize = function.getValue().getSize();
			c.setScale(((float) w * (float) cellWidth) / (float) sSize.x, ((float) h * (float) cellHeight) / (float) sSize.y);
			function.getValue().render(c, new IntPoint2D(0, 0));
			c.repaint();
		});
	}
	
	@Override
	public void clear()
	{
		updateTasks.clear();
		resizeTasks.clear();
		this.removeAll();
	}
	
	@Override
	public void finalize()
	{
		updateTask.cancel();
		resizeTask.cancel();
		timer.purge();
	}
}
