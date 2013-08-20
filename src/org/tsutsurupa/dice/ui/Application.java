package org.tsutsurupa.dice.ui;

import org.tsutsurupa.dice.model.Dice;
import org.tsutsurupa.dice.ui.DiceView;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.*;

public class Application {

	public static final String NAME = "Dice Roller";

	private static final int DEFAULT_DICE_HEIGHT = 240;
	private static final int DEFAULT_DICE_WIDTH = 200;
	private static final int DEFAULT_FRAME_HEIGHT = DEFAULT_DICE_HEIGHT + 28;
	private static final int DEFAULT_FRAME_WIDTH = DEFAULT_DICE_WIDTH + 6;
	private static final int EXTENDED_FRAME_WIDTH = 416;
	private static final int EXTENDED_FRAME_HEIGHT = 268;
	private static final String SHOW_STATISTICS = "Show statistics >>";
	private static final String HIDE_STATISTICS = "Hide statistics <<";

	private final Dice dice = new Dice();
	private final DiceView view = new DiceView(dice);
	private final Display display = new Display(dice);
	
	private JFrame frame;
	private JButton statisticsButton;
	private boolean extended = false;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application app = new Application();
					app.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	public Application() {
		setLookAndFeel();
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		setFrameProperties(frame);

		JPanel panel = new JPanel();
		initPanel(panel);
		frame.getContentPane().add(panel);

		panel.add(view);

		JButton rollButton = new JButton();
		initRollButton(rollButton);
		panel.add(rollButton);

		statisticsButton = new JButton(SHOW_STATISTICS);
		initStatisticsButton(statisticsButton);
		panel.add(statisticsButton);

		display.setBounds(200, 0, 210, 240);
		frame.getContentPane().add(display);
	}
	
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
	}
	
	private void setFrameProperties(JFrame frame) {
		frame.setTitle(NAME);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				Application.class.getResource("/images/favicon.png")));
		frame.setResizable(false);
		frame.setBounds(100, 100, DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	private void initPanel(JPanel panel) {
		panel.setBounds(0, 0, DEFAULT_DICE_WIDTH, DEFAULT_DICE_HEIGHT);
		panel.setLayout(null);
	}
	
	private void initRollButton(JButton button) {
		button.setText("Roll teh dice!");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dice.roll();
			}
		});
		button.setBounds(0, 200, 200, 20);
	}
	
	private void initStatisticsButton(JButton button) {
		button.setBounds(0, 220, 200, 20);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				extend();
			}
		});
	}
	
	private void show() {
		frame.setVisible(true);
	}

	public void extend() {
		if (!extended) {
			frame.setBounds(frame.getX(), frame.getY(), EXTENDED_FRAME_WIDTH,
					EXTENDED_FRAME_HEIGHT);
			statisticsButton.setText(HIDE_STATISTICS);
			extended = true;
		} else {
			frame.setBounds(frame.getX(), frame.getY(), DEFAULT_FRAME_WIDTH,
					DEFAULT_FRAME_HEIGHT);
			statisticsButton.setText(SHOW_STATISTICS);
			extended = false;
		}
	}
	
}
