package org.tsutsurupa.dice.ui;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import org.tsutsurupa.obs.*;
import org.tsutsurupa.dice.model.Dice;

public final class DiceView extends JLabel implements Observer {

	public static final int VIEW_HEIGHT = 200;
	public static final int VIEW_WIDHT = 200;

	private static final String DEFAULT_RESOURCE_FOLDER = "/images/";
	private static final String HELLO_MESSAGE = "Thanks for clicking \"Roll teh dice!\" button!<br>" +
												"It makes teh dice roll!..<br>" +
												"We... we like the rolling";
	private static final ImageIcon[] STATES = getIcons();
	
	private Dice _subject;

	public DiceView(Dice subject) {
		setText("<html><div style=\"text-align: center;\">" + HELLO_MESSAGE
				+ "</html>");
		setBounds(0, 0, VIEW_WIDHT, VIEW_HEIGHT);
		_subject = subject;
		subject.attachObs(this);
	}
	
	public void update() {
		if (_subject instanceof Dice) {
			setState(_subject.getStatus());
		}
	}
	
	public void setState(int state) {
		setIcon(STATES[state - 1]);
	}
	
	private static ImageIcon[] getIcons() {
		ImageIcon[] result = new ImageIcon[Dice.DEFAULT_NUMBER_OF_STATES];
		for (int i = 0; i < result.length; i++) {
			result[i] = new ImageIcon(DiceView.class.getResource(DEFAULT_RESOURCE_FOLDER + (i + 1) + ".png"));
		}
		return result;
	}

}