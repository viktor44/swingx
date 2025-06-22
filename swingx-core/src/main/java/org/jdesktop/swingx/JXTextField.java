package org.jdesktop.swingx;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.util.List;

import javax.swing.JTextField;

import org.jdesktop.beans.JavaBean;
import org.jdesktop.swingx.prompt.BuddySupport;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.jdesktop.swingx.prompt.BuddySupport.Position;
import org.jdesktop.swingx.prompt.PromptSupport.FocusBehavior;

/**
 * {@link JTextField}, with integrated support for prompts and buddies.
 * 
 * @see PromptSupport
 * @see BuddySupport
 * @author <a href="mailto:petw@gmx.net">Peter Weishapl</a>
 * 
 */
@JavaBean
public class JXTextField extends JTextField {
	public JXTextField() {
		this(null);
	}

	public JXTextField(String promptText) {
		this(promptText, null);
	}

	public JXTextField(String promptText, Color promptForeground) {
		this(promptText, promptForeground, null);
	}

	public JXTextField(String promptText, Color promptForeground,
			Color promptBackground) {
		PromptSupport.init(promptText, promptForeground, promptBackground,
				this);
	}

	/**
	 * @return the current focus behavior of this text field.
	 * 
	 * @see PromptSupport#getFocusBehavior(javax.swing.text.JTextComponent)
	 */
	public FocusBehavior getFocusBehavior() {
		return PromptSupport.getFocusBehavior(this);
	}

	/**
	 * @return the current prompt text of this text field.
	 * 
	 * @see PromptSupport#getPrompt(javax.swing.text.JTextComponent)
	 */
	public String getPrompt() {
		return PromptSupport.getPrompt(this);
	}

	/**
	 * @return the current prompt foreground color of this text field.
	 * 
	 * @see PromptSupport#getForeground(javax.swing.text.JTextComponent)
	 */
	public Color getPromptForeground() {
		return PromptSupport.getForeground(this);
	}

	/**
	 * @return the current prompt background color of this text field.
	 * 
	 * @see PromptSupport#getForeground(javax.swing.text.JTextComponent)
	 */
	public Color getPromptBackground() {
		return PromptSupport.getBackground(this);
	}

	/**
	 * @return the current prompt font style of this text field.
	 * 
	 * @see PromptSupport#getFontStyle(javax.swing.text.JTextComponent)
	 */
	public Integer getPromptFontStyle() {
		return PromptSupport.getFontStyle(this);
	}

	/**
	 * @param focusBehavior - the new focus behavior to set for this text field.
	 * 
	 * @see PromptSupport#getFocusBehavior(javax.swing.text.JTextComponent)
	 */
	public void setFocusBehavior(FocusBehavior focusBehavior) {
		PromptSupport.setFocusBehavior(focusBehavior, this);
	}

	/**
	 * @param labelText - the new prompt text to set for this text field.
	 * 
	 * @see PromptSupport#setPrompt(String, javax.swing.text.JTextComponent)
	 */
	public void setPrompt(String labelText) {
		PromptSupport.setPrompt(labelText, this);
	}

	/**
	 * @param promptTextColor - the new prompt foreground color to set for this text field.
	 * 
	 * @see PromptSupport#setForeground(Color, javax.swing.text.JTextComponent)
	 */
	public void setPromptForeground(Color promptTextColor) {
		PromptSupport.setForeground(promptTextColor, this);
	}

	/**
	 * @param promptTextColor - the new prompt background color to set for this text field.
	 * 
	 * @see PromptSupport#setBackground(Color, javax.swing.text.JTextComponent)
	 */
	public void setPromptBackround(Color promptTextColor) {
		PromptSupport.setBackground(promptTextColor, this);
	}

	/**
	 * @param fontStyle - the new prompt font style to set for this text field.
	 * 
	 * @see PromptSupport#setFontStyle(Integer, javax.swing.text.JTextComponent)
	 */
	public void setPromptFontStyle(Integer fontStyle) {
		PromptSupport.setFontStyle(fontStyle, this);
	}

	/**
	 * @param margin - the new outer margin to set for this text field.
	 * 
	 * @see BuddySupport#setOuterMargin(JTextField, Insets)
	 */
	public void setOuterMargin(Insets margin) {
		BuddySupport.setOuterMargin(this, margin);
	}

	/**
	 * @return the current outer margin of this text field.
	 * 
	 * @see BuddySupport#getOuterMargin(JTextField)
	 */
	public Insets getOuterMargin() {
		return BuddySupport.getOuterMargin(this);
	}

	/**
	 * @param buddy - the buddy component to add to this text field.
	 * @param pos - the position where to add the buddy component.
	 * 
	 * @see BuddySupport#add(Component, Position, JTextField)
	 */
	public void addBuddy(Component buddy, Position pos) {
		BuddySupport.add(buddy, pos, this);
	}

	/**
	 * @param width - the width of the gap to add.
	 * @param pos - the position where to add the gap.
	 * 
	 * @see BuddySupport#addGap(int, Position, JTextField)
	 */
	public void addGap(int width, Position pos) {
		BuddySupport.addGap(width, pos, this);
	}

	/**
	 * @return the list of buddy components at the specified position.
	 * 
	 * @see BuddySupport#getBuddies(Position, JTextField)
	 */
	public List<Component> getBuddies(Position pos) {
		return BuddySupport.getBuddies(pos, this);
	}

	/**
	 * @see BuddySupport#removeAll(JTextField)
	 */
	public void removeAllBuddies() {
		BuddySupport.removeAll(this);
	}
}
