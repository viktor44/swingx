package org.jdesktop.swingx;

import java.awt.Color;

import javax.swing.JTextArea;

import org.jdesktop.beans.JavaBean;
import org.jdesktop.swingx.prompt.BuddySupport;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.jdesktop.swingx.prompt.PromptSupport.FocusBehavior;

/**
 * {@link JTextArea}, with integrated support for prompts.
 * 
 * @see PromptSupport
 * @see BuddySupport
 * @author <a href="mailto:petw@gmx.net">Peter Weishapl</a>
 * 
 */
@JavaBean
public class JXTextArea extends JTextArea {
	public JXTextArea() {
		this(null);
	}

	public JXTextArea(String promptText) {
		this(promptText, null);
	}

	public JXTextArea(String promptText, Color promptForeground) {
		this(promptText, promptForeground, null);
	}

	public JXTextArea(String promptText, Color promptForeground,
			Color promptBackground) {
		PromptSupport.init(promptText, promptForeground, promptBackground,
				this);
	}

	/**
	 * @return the focus behavior of this component.
	 * 
	 * @see PromptSupport#getFocusBehavior(javax.swing.text.JTextComponent)
	 */
	public FocusBehavior getFocusBehavior() {
		return PromptSupport.getFocusBehavior(this);
	}

	/**
	 * @return the prompt text of this component.
	 * 
	 * @see PromptSupport#getPrompt(javax.swing.text.JTextComponent)
	 */
	public String getPrompt() {
		return PromptSupport.getPrompt(this);
	}

	/**
	 * @return the foreground color of the prompt text.
	 * 
	 * @see PromptSupport#getForeground(javax.swing.text.JTextComponent)
	 */
	public Color getPromptForeground() {
		return PromptSupport.getForeground(this);
	}

	/**
	 * @return the background color of the prompt text.
	 * 
	 * @see PromptSupport#getForeground(javax.swing.text.JTextComponent)
	 */
	public Color getPromptBackground() {
		return PromptSupport.getBackground(this);
	}

	/**
	 * @return the font style of the prompt text.
	 * 
	 * @see PromptSupport#getFontStyle(javax.swing.text.JTextComponent)
	 */
	public Integer getPromptFontStyle() {
		return PromptSupport.getFontStyle(this);
	}

	/**
	 * @param focusBehavior - the focus behavior to set
	 * 
	 * @see PromptSupport#getFocusBehavior(javax.swing.text.JTextComponent)
	 */
	public void setFocusBehavior(FocusBehavior focusBehavior) {
		PromptSupport.setFocusBehavior(focusBehavior, this);
	}

	/**
	 * @param labelText - the text to set as prompt
	 * 
	 * @see PromptSupport#setPrompt(String, javax.swing.text.JTextComponent)
	 */
	public void setPrompt(String labelText) {
		PromptSupport.setPrompt(labelText, this);
	}

	/**
	 * @param promptTextColor - the color to set for the prompt text
	 * 
	 * @see PromptSupport#setForeground(Color, javax.swing.text.JTextComponent)
	 */
	public void setPromptForeground(Color promptTextColor) {
		PromptSupport.setForeground(promptTextColor, this);
	}

	/**
	 * @param promptTextColor - the color to set for the prompt background
	 * 
	 * @see PromptSupport#setBackground(Color, javax.swing.text.JTextComponent)
	 */
	public void setPromptBackround(Color promptTextColor) {
		PromptSupport.setBackground(promptTextColor, this);
	}

	/**
	 * @param fontStyle - the font style to set for the prompt text
	 * 
	 * @see PromptSupport#setFontStyle(Integer, javax.swing.text.JTextComponent)
	 */
	public void setPromptFontStyle(Integer fontStyle) {
		PromptSupport.setFontStyle(fontStyle, this);
	}
}
