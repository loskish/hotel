/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUIPlayersDialog.java
 *
 * Created on 24-lug-2010, 8.32.48
 */

package it.losko.hotel.view.gui;

import it.losko.hotel.model.action.CollectPlayersAction;
import it.losko.hotel.model.exception.PlayerColorAlreadyChosenException;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.info.Info;
import it.losko.hotel.model.player.PlayersBuilder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author losko
 */
public class GUIPlayersDialog extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;

	private final GUI gui;
	private final CollectPlayersAction collectPlayersAction;
    private final SpinnerNumberModel playersSpinnerModel;
    private final SpinnerNumberModel humanplayersSpinnerModel;
    private final JTable humanPlayersTable;
    private final DefaultTableModel humanPlayersTableModel;
    private final JComboBox colorComboBox;
    private final Color[] colors;

    /** Creates new form GUIPlayersDialog */
    public GUIPlayersDialog(final GUI gui, java.awt.Frame parent, boolean modal, final CollectPlayersAction collectPlayersAction) {
        super(parent, modal);

        // Variables initialization
        this.gui = gui;
        this.collectPlayersAction = collectPlayersAction;
        
        // Players spinner model
        playersSpinnerModel = new SpinnerNumberModel(Game.getSingleton().getPlayers().getDefaultNumber(), Game.getSingleton().getPlayers().getMinimumNumber(), Game.getSingleton().getPlayers().getMaximumNumber(),1);
        // Human players spinner model
        humanplayersSpinnerModel = new SpinnerNumberModel(1, 0, Game.getSingleton().getPlayers().getDefaultNumber(), 1);
        // Human players table model
        humanPlayersTableModel = new HumanPlayersTableModel();
        // Human players table
        humanPlayersTable = new ColorTable();
        humanPlayersTable.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        humanPlayersTable.setModel(humanPlayersTableModel);
        humanPlayersTable.getTableHeader().setReorderingAllowed(false);
        humanPlayersTable.getColumnModel().getColumn(0).setMinWidth(30);
        humanPlayersTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        humanPlayersTable.getColumnModel().getColumn(0).setMaxWidth(30);
        // Sets the 3rd column to show a combobox
        colors = new Color[] {Color.yellow, Color.green, Color.blue, Color.red};
        colorComboBox = new JComboBox(colors);
        colorComboBox.setRenderer(new ColorComboRenderer());
        humanPlayersTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(colorComboBox));
        // Necessary to commit the change on cell focus lost. Otherwise, clicking on the OK button while cell is selected will lose the cell value
        humanPlayersTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        
        // NetBeans components
        initComponents();
        
        // Post NetBeans compontent initialization
        humanPlayersScrollPane.setViewportView(humanPlayersTable);
        setLocationRelativeTo(parent);
    }

    public GUI getGui() {
		return gui;
	}
    
    private String translateColor(final Color color) {
    	String res = null;
    	if(Color.yellow.equals(color)) {
    		res = "yellow";
    	} else if(Color.green.equals(color)) {
    		res = "green";
    	} else if(Color.blue.equals(color)) {
    		res = "blue";
    	} else if(Color.red.equals(color)) {
    		res = "red";
    	}
    	
    	return res;
    }

	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        humanPlayersLabel = new javax.swing.JLabel();
        humanPlayersSpinner = new javax.swing.JSpinner();
        playersLabel = new javax.swing.JLabel();
        playersSpinner = new javax.swing.JSpinner();
        humanPlayersScrollPane = new javax.swing.JScrollPane();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(Info.getSingleton().getName() + " - Players");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        humanPlayersLabel.setText("Human players:");

        humanPlayersSpinner.setModel(humanplayersSpinnerModel);
        humanPlayersSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                humanPlayersSpinnerStateChanged(evt);
            }
        });

        playersLabel.setText("Total number of players:");

        playersSpinner.setModel(playersSpinnerModel);
        playersSpinner.setValue(Game.getSingleton().getPlayers().getDefaultNumber());
        playersSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                playersSpinnerStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                    .addComponent(humanPlayersScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(playersLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE))
                            .addComponent(cancelButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(humanPlayersLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(okButton)
                            .addComponent(playersSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(humanPlayersSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playersLabel)
                    .addComponent(playersSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(humanPlayersLabel)
                    .addComponent(humanPlayersSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(humanPlayersScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void playersSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_playersSpinnerStateChanged
        humanplayersSpinnerModel.setMaximum((Integer) playersSpinnerModel.getValue());
    }//GEN-LAST:event_playersSpinnerStateChanged

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void humanPlayersSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_humanPlayersSpinnerStateChanged
        final Integer numberOfRows = (Integer) humanplayersSpinnerModel.getValue();
        humanPlayersTableModel.setRowCount(numberOfRows);
        
        final Vector dataVector = humanPlayersTableModel.getDataVector();
        for(int i = 0; i < dataVector.size(); i++) {
            final Vector row = (Vector) dataVector.get(i);
            row.set(0, Integer.valueOf(i+1));
        }
    }//GEN-LAST:event_humanPlayersSpinnerStateChanged

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
    	final PlayersBuilder pb = new PlayersBuilder();

        for(int i = 0; i < humanPlayersTable.getRowCount(); i++) {
        	final String name = (String) humanPlayersTable.getValueAt(i, 1);
        	if(name == null || name.trim().isEmpty()) {
        		return;
        	}
        	
        	final Color color = (Color) humanPlayersTable.getValueAt(i, 2);
        	if(color == null) {
        		return;
        	}
        	
            try {
				pb.add(name, translateColor(color), false);
			} catch (PlayerColorAlreadyChosenException e) {
				gui.showMessageDialog(e.getMessage());
				return;
			}
        }

        for(int i = (Integer) humanPlayersSpinner.getValue(), j = 1; i < (Integer) playersSpinnerModel.getValue(); i++, j++) {
        	for(final Color color : colors) {
        		try {
        			pb.add("Computer " + j, translateColor(color), true);
        		} catch (PlayerColorAlreadyChosenException e) {
        			continue;
        		}
        		
        		// If there's no exception, then the color has not been used, thus breaks the cycle.
        		break;
        	}
        }

        collectPlayersAction.setInputAsPlayersBuilder(pb);
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        collectPlayersAction.setInputAsPlayersBuilder(null);
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        collectPlayersAction.setInputAsPlayersBuilder(null);
        dispose();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel humanPlayersLabel;
    private javax.swing.JScrollPane humanPlayersScrollPane;
    private javax.swing.JSpinner humanPlayersSpinner;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel playersLabel;
    private javax.swing.JSpinner playersSpinner;
    // End of variables declaration//GEN-END:variables

}

class HumanPlayersTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "rawtypes" })
	private final Class[] types;
	private final boolean[] canEdit;
	
	public HumanPlayersTableModel() {
		super(new Object [][] {{1, null, null}}, new String [] {"#", "Name", "Color"});
		types = new Class [] {Integer.class, String.class, Color.class};
		canEdit = new boolean [] {false, true, true};
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int columnIndex) {
		return types [columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return canEdit [columnIndex];
	}
}

class ColorComboRenderer extends JPanel implements ListCellRenderer {
	private static final long serialVersionUID = 1L;

	protected Color m_c = Color.black;

	public ColorComboRenderer() {
		super();
		setBorder(new CompoundBorder(new MatteBorder(2, 10, 2, 10, Color.white), new LineBorder(Color.black)));
	}

	public Component getListCellRendererComponent(JList list, Object obj, int row, boolean sel, boolean hasFocus) {
		if (obj instanceof Color)
			m_c = (Color) obj;
		else if(obj != null ){
			System.out.println("test");
		}
		return this;
	}

	public void paint(Graphics g) {
		setBackground(m_c);
		super.paint(g);
	}
}

class TableColoredCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	public TableColoredCellRenderer() {
		super();
	}
	
	public void setValue(Object value) {
		if(value instanceof Color) {
			setBorder(new CompoundBorder(new MatteBorder(2, 10, 2, 10, Color.white), new LineBorder(Color.black)));
			setBackground((Color) value);
		}
    }
}

class ColorTable extends JTable {
	private static final long serialVersionUID = 1L;
	
	private final Map<Integer, TableColoredCellRenderer> coloredCellsRenderers;
	
	public ColorTable() {
		coloredCellsRenderers = new HashMap<Integer, TableColoredCellRenderer>();
	}
	
	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		if(column == 2) {
			if(coloredCellsRenderers.get(row) == null) {
				coloredCellsRenderers.put(row, new TableColoredCellRenderer());
			}
			
			return coloredCellsRenderers.get(row);
		}
		
		return super.getCellRenderer(row, column);
	}
}