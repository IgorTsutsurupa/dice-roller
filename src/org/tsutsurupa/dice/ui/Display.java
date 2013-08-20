package org.tsutsurupa.dice.ui;

import org.tsutsurupa.obs.*;
import org.tsutsurupa.dice.model.Dice;

import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.UIManager;
import javax.swing.BorderFactory;

public class Display extends JPanel implements Observer {

	private static final String PERCENTAGE_OF = "Percentage of ";
	private static final String TOTAL_COUNT = "Total count: ";
	private static final String POINT = " point: ";

	private Dice _subject;
	private int _total;
	private float[] _percents;
	
	private JLabel _totalDisplay;
	private JLabel[] _statsDisplay;
	
	public Display(Dice subject) {
		setLookAndFeel();
		setLayout(null);
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Statistics"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		_subject = subject;
		subject.attachObs(this);
		
		_percents = new float[Dice.DEFAULT_NUMBER_OF_STATES];
		
		initDisplayInfo();
		
		initMenu();
	}
	
	public void update() {
		if (_subject instanceof Dice) {
			_total = ((Dice) _subject).getTotal();
			_percents = ((Dice) _subject).getPointsPercentage();
			updateTotalInfo();
			updateStatsInfo();
		}
	}
	
	private void updateTotalInfo() {
		_totalDisplay.setText(TOTAL_COUNT + _total);
	}
	
	private void updateStatsInfo() {
		for (int i = 0; i < _statsDisplay.length; i++) {
			_statsDisplay[i].setText(PERCENTAGE_OF + (i + 1) + POINT + _percents[i] + " %");
		}
	}
	
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
	}
	
	private void initDisplayInfo() {
		_totalDisplay = new JLabel(TOTAL_COUNT + _total);
		_totalDisplay.setBounds(10, 20, 130, 14);
		add(_totalDisplay);
		
		_statsDisplay = new JLabel[Dice.DEFAULT_NUMBER_OF_STATES];
		for (int i = 0; i < _statsDisplay.length; i++) {
			_statsDisplay[i] = new JLabel(PERCENTAGE_OF + (i + 1) + POINT + _percents[i] + " %");
			_statsDisplay[i].setBounds(10, 20 * (i + 2), 175, 14);
			add(_statsDisplay[i]);
		}
	}
	
	private void initMenu() {
		JLabel rollLabel = new JLabel("Roll teh dice");
		rollLabel.setBounds(10, 165, 58, 14);
		add(rollLabel);
		
		JLabel timesLabel = new JLabel("times");
		timesLabel.setBounds(150, 165, 31, 14);
		add(timesLabel);
		
		final JSpinner spinner = new JSpinner();
		spinner.setBounds(73, 160, 67, 20);
		add(spinner);
		
		JButton rollButton = new JButton("Roll");
		rollButton.setBounds(10, 182, 190, 23);
		rollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_subject.roll(((Integer) spinner.getValue()).intValue());
			}
		});
		add(rollButton);
		
		JButton resetButton = new JButton("Reset stats");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_subject.reset();
			}
		});
		resetButton.setBounds(10, 205, 190, 23);
		add(resetButton);
	}

}
