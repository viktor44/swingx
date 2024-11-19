/*
 * $Id$
 *
 * Copyright 2008 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.jdesktop.swingx.autocomplete;

import static java.awt.event.KeyEvent.CHAR_UNDEFINED;
import static java.awt.event.KeyEvent.KEY_PRESSED;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_END;
import static java.awt.event.KeyEvent.VK_HOME;
import static java.awt.event.KeyEvent.VK_PAGE_DOWN;
import static java.awt.event.KeyEvent.VK_PAGE_UP;
import static java.awt.event.KeyEvent.VK_UP;
import static java.lang.System.currentTimeMillis;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import org.jdesktop.test.EDTRunner;
import org.jdesktop.test.categories.Visual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

/**
 *
 * @author Karl George Schaefer
 */
@RunWith(EDTRunner.class)
public class AutoCompleteDecoratorTest  {
    private JComboBox combo;
    
    @Before
    public void setUp() throws Exception {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        combo = new JComboBox(new String[]{"Alpha", "Bravo", "Charlie", "Delta"});
    }
    
    /**
     * Issue #1570-swingx: decorating Jlist without converter throws NPE
     * test listAdapter
     */
    @Test
    public void testDefaultConverterListAdapterTwo() {
        JList list = new JList(combo.getModel());
        JTextComponent text = new JTextField();
        ListAdaptor adapter = new ListAdaptor(list, text);
        assertSame("default adapter in two-param constructor", 
                ObjectToStringConverter.DEFAULT_IMPLEMENTATION, adapter.stringConverter);
    }
    
    /**
     * Issue #1570-swingx: decorating Jlist without converter throws NPE
     * test listAdapter, three param constructor
     */
    @Test
    public void testDefaultConverterListAdapterThree() {
        JList list = new JList(combo.getModel());
        JTextComponent text = new JTextField();
        ListAdaptor adapter = new ListAdaptor(list, text, null);
        assertSame("default adapter in three-param constructor", 
                ObjectToStringConverter.DEFAULT_IMPLEMENTATION, adapter.stringConverter);
    }
    
    /**
     * Issue #1570-swingx: decorating Jlist without converter throws NPE
     * 
     */
    @Test
    public void testDefaultConverterJList() {
        JList list = new JList(combo.getModel());
        JTextComponent text = new JTextField();
        AutoCompleteDecorator.decorate(list, text);
        text.setText("A");
    }
    
    /**
     * Issue #1570-swingx: decorating list without converter throws NPE
     * completeness: test default converter for textComponent with List
     * 
     */
    @Test
    public void testDefaultConverterList() {
        List<?> list = Arrays.asList(new String[]{"Alpha", "Bravo", "Charlie", "Delta"});
        JTextComponent text = new JTextField();
        AutoCompleteDecorator.decorate(text, list, true);
        text.setText("A");
    }
    
    /**
     * Issue #1570-swingx: decorating list without converter throws NPE
     * completeness: test default converter for combo
     */
    @Test
    public void testDefaultConverterCombo() {
        AutoCompleteDecorator.decorate(combo);
        JTextComponent text = (JTextComponent) combo.getEditor().getEditorComponent();
        text.setText("A");
    }
    
    /**
     * SwingX Issue #299.
     */
    @Test
    public void testUndecorateComboBox() {
        combo.setEditable(false);
        AutoCompleteDecorator.decorate(combo);
        AutoCompleteDecorator.undecorate(combo);
        
        for (PropertyChangeListener l : combo.getPropertyChangeListeners("editor")) {
            assertFalse(l instanceof AutoComplete.PropertyChangeListener);
        }
        
        assertFalse(combo.getEditor() instanceof AutoCompleteComboBoxEditor);
        
        JTextComponent editorComponent = (JTextComponent) combo.getEditor().getEditorComponent();
        
        for (KeyListener l : editorComponent.getKeyListeners()) {
            assertFalse(l instanceof AutoComplete.KeyAdapter);
        }
        
        for (InputMap map = editorComponent.getInputMap(); map != null; map = map.getParent()) {
            assertFalse(map instanceof AutoComplete.InputMap);
        }
        
        assertNull(editorComponent.getActionMap().get("nonstrict-backspace"));
        
        for (FocusListener l : editorComponent.getFocusListeners()) {
            assertFalse(l instanceof AutoComplete.FocusAdapter);
        }
        
        assertFalse(editorComponent.getDocument() instanceof AutoCompleteDocument);
        
        for (ActionListener l : combo.getActionListeners()) {
            assertFalse(l instanceof ComboBoxAdaptor);
        }
        
    }
    
    /**
     * SwingX Issue #299.
     */
    @Test
    public void testRedecorateComboBox() {
        AutoCompleteDecorator.decorate(combo);
        Component editor = combo.getEditor().getEditorComponent();
        
        int expectedFocusListenerCount = editor.getFocusListeners().length;
        int expectedKeyListenerCount = editor.getKeyListeners().length;
        int expectedPropListenerCount = combo.getPropertyChangeListeners("editor").length;
        int expectedActionListenerCount = combo.getActionListeners().length;
        
        
        AutoCompleteDecorator.decorate(combo);
        editor = combo.getEditor().getEditorComponent();
        
        assertEquals(editor.getFocusListeners().length, expectedFocusListenerCount);
        assertEquals(editor.getKeyListeners().length, expectedKeyListenerCount);
        assertEquals(combo.getPropertyChangeListeners("editor").length, expectedPropListenerCount);
        assertEquals(combo.getActionListeners().length, expectedActionListenerCount);
    }
    
    /**
     * SwingX Issue #299.
     */
    @Test
    public void testUndecorateList() {
        JList list = new JList();
        JTextField textField = new JTextField();
        AutoCompleteDecorator.decorate(list, textField);
        AutoCompleteDecorator.undecorate(list);
        
        for (ListSelectionListener l : list.getListSelectionListeners()) {
            Assert.assertFalse(l instanceof ListAdaptor);
        }
    }
    
    /**
     * SwingX Issue #299.
     */
    @Test
    public void testRedecorateList() {
        JList list = new JList();
        JTextField textField = new JTextField();
        AutoCompleteDecorator.decorate(list, textField);
        
        int expectedListSelectionListenerCount = list.getListSelectionListeners().length;
        
        AutoCompleteDecorator.decorate(list, textField);
        
        assertEquals(list.getListSelectionListeners().length, expectedListSelectionListenerCount);
    }
    
    /**
     * SwingX Issue #299.
     */
    @Test
    public void testUndecorateTextComponent() {
        JTextField textField = new JTextField();
        
        AutoCompleteDecorator.decorate(textField, Collections.emptyList(), true);
        
        AutoCompleteDecorator.undecorate(textField);
        
        assertFalse(textField.getInputMap() instanceof AutoComplete.InputMap);
        assertNull(textField.getActionMap().get("nonstrict-backspace"));
        for (FocusListener l : textField.getFocusListeners()) {
            assertFalse(l instanceof AutoComplete.FocusAdapter);
        }
        assertFalse(textField.getDocument() instanceof AutoCompleteDocument);
    }
    
    /**
     * SwingX Issue #299.
     */
    @Test
    public void testRedecorateTextComponent() {
        JTextField textField = new JTextField();
        AutoCompleteDecorator.decorate(textField, Collections.emptyList(), true);
        
        int expectedFocusListenerLength = textField.getFocusListeners().length;
        
        AutoCompleteDecorator.decorate(textField, Collections.emptyList(), true);
        
        assertEquals(textField.getFocusListeners().length, expectedFocusListenerLength);
    }
    
    @Test
    public void testDecoratingJTextPane() {
        List<String> strings = Arrays.asList("Alpha", "Bravo", "Charlie", "Delta");
        AutoCompleteDecorator.decorate(new JTextPane(), strings, true);
    }
    
    @Test
    public void testAddingItemsAfterDecorating() {
        AutoCompleteDecorator.decorate(combo);
        combo.addItem("Echo");
    }
    
    @Test
    public void testAddingItemsAfterDecoratingEmpty() {
        JComboBox box = new JComboBox();
        AutoCompleteDecorator.decorate(box);
        box.addItem("Alhpa");
    }
    
    @Test
    public void testRemovingItemsAfterDecorating() {
        AutoCompleteDecorator.decorate(combo);
        combo.removeAll();
    }
    
    /**
     * SwingX Issue #1322.
     */
    @Test
    @Category(Visual.class)
    public void testNonStrictCompletionWithKeyMovement() {
        assumeThat(GraphicsEnvironment.isHeadless(), is(false));
        
        combo.setEditable(true);
        AutoCompleteDecorator.decorate(combo);
        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("".equals(combo.getSelectedItem())) {
                    fail("received illegal value");
                }
            }
        });
        
        JFrame frame = new JFrame();
        frame.add(combo);
        frame.pack();
        frame.setVisible(true);
        
        assertEquals(combo.getSelectedItem(), "Alpha");
        assumeThat(combo.isPopupVisible(), is(false));
        
        combo.processKeyEvent(new KeyEvent(combo, KEY_PRESSED, currentTimeMillis(), 0, VK_DOWN, CHAR_UNDEFINED));
        assertTrue(combo.isPopupVisible());
        
        combo.processKeyEvent(new KeyEvent(combo, KEY_PRESSED, currentTimeMillis(), 0, VK_DOWN, CHAR_UNDEFINED));
        assertEquals(combo.getSelectedItem(), "Bravo");
        
        combo.processKeyEvent(new KeyEvent(combo, KEY_PRESSED, currentTimeMillis(), 0, VK_PAGE_DOWN, CHAR_UNDEFINED));
        assertEquals(combo.getSelectedItem(), "Delta");
        
        combo.processKeyEvent(new KeyEvent(combo, KEY_PRESSED, currentTimeMillis(), 0, VK_UP, CHAR_UNDEFINED));
        assertEquals(combo.getSelectedItem(), "Charlie");
        
        combo.processKeyEvent(new KeyEvent(combo, KEY_PRESSED, currentTimeMillis(), 0, VK_PAGE_UP, CHAR_UNDEFINED));
        assertEquals(combo.getSelectedItem(), "Alpha");
        
        combo.processKeyEvent(new KeyEvent(combo, KEY_PRESSED, currentTimeMillis(), 0, VK_END, CHAR_UNDEFINED));
        assertEquals(combo.getSelectedItem(), "Delta");
        
        combo.processKeyEvent(new KeyEvent(combo, KEY_PRESSED, currentTimeMillis(), 0, VK_HOME, CHAR_UNDEFINED));
        assertEquals(combo.getSelectedItem(), "Alpha");
    }
}
