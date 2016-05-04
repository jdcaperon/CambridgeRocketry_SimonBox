/*
%## Copyright (C) 2010 S.Box
%## 
%## This program is free software; you can redistribute it and/or modify
%## it under the terms of the GNU General Public License as published by
%## the Free Software Foundation; either version 2 of the License, or
%## (at your option) any later version.
%## 
%## This program is distributed in the hope that it will be useful,
%## but WITHOUT ANY WARRANTY; without even the implied warranty of
%## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
%## GNU General Public License for more details.
%## 
%## You should have received a copy of the GNU General Public License
%## along with this program; if not, write to the Free Software
%## Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA

%## BlockDialog.java

%## Author: S.Box
%## Created: 2010-05-27
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BlockDialog.java
 *
 * Created on 24-Jan-2010, 13:30:42
 */

package net.sf.openrocket.camrocksim;

import java.util.Vector;

import javax.swing.JOptionPane;

/**
 *
 * @author simon
 */
public class BlockDialog extends javax.swing.JDialog {
	
	//**Class Members
	double length,
			Width,
			Height,
			Mass,
			Position,
			RadialPosition;
	boolean ReadOk;
	String Name;
	
	/** Creates new form BlockDialog */
	public BlockDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		
		BlkMassLabel = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		BlkSpecTitle = new javax.swing.JLabel();
		BlkLengthText = new javax.swing.JTextField();
		BlkMassUnit = new javax.swing.JLabel();
		jButton2 = new javax.swing.JButton();
		BlkWidthLable = new javax.swing.JLabel();
		BlkPosUnit = new javax.swing.JLabel();
		BlkWidthUnit = new javax.swing.JLabel();
		BlkNameText = new javax.swing.JTextField();
		BlkPosText = new javax.swing.JTextField();
		BlkPosLabel = new javax.swing.JLabel();
		BlkLengthUnit = new javax.swing.JLabel();
		BlkNameLabel = new javax.swing.JLabel();
		BlkLengthLabel = new javax.swing.JLabel();
		BlkMassText = new javax.swing.JTextField();
		BlkWidthText = new javax.swing.JTextField();
		BlkHeightLable1 = new javax.swing.JLabel();
		BlkHeightText = new javax.swing.JTextField();
		BlkHeightUnit = new javax.swing.JLabel();
		BlkRadPosLabel = new javax.swing.JLabel();
		BlkRadPosText = new javax.swing.JTextField();
		BlkRadPosUnit = new javax.swing.JLabel();
		jButton3 = new javax.swing.JButton();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
		BlkMassLabel.setText("Mass");
		
		jButton1.setText("Add");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		
		BlkSpecTitle.setText("Set Block specifications");
		
		BlkLengthText.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				BlkLengthTextFocusLost(evt);
			}
		});
		
		BlkMassUnit.setText("kg");
		
		jButton2.setText("Cancel");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});
		
		BlkWidthLable.setText("Width");
		
		BlkPosUnit.setText("m");
		
		BlkWidthUnit.setText("m");
		
		BlkNameText.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				BlkNameTextFocusLost(evt);
			}
		});
		
		BlkPosText.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				BlkPosTextFocusLost(evt);
			}
		});
		
		BlkPosLabel.setText("Position");
		
		BlkLengthUnit.setText("m");
		
		BlkNameLabel.setText("Name");
		
		BlkLengthLabel.setText("Length");
		
		BlkMassText.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				BlkMassTextFocusLost(evt);
			}
		});
		
		BlkWidthText.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				BlkWidthTextFocusLost(evt);
			}
		});
		
		BlkHeightLable1.setText("Height");
		
		BlkHeightText.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				BlkHeightTextFocusLost(evt);
			}
		});
		
		BlkHeightUnit.setText("m");
		
		BlkRadPosLabel.setText("Radial Position");
		
		BlkRadPosText.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				BlkRadPosTextFocusLost(evt);
			}
		});
		
		BlkRadPosUnit.setText("m");
		
		jButton3.setText("?");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(BlkNameLabel)
										.addComponent(BlkSpecTitle)
										.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(BlkPosLabel)
																		.addComponent(BlkMassLabel))
																.addGap(49, 49, 49)
																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(BlkPosText, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
																		.addComponent(BlkMassText, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
																		.addComponent(BlkRadPosText, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)))
														.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
																.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
																		.addComponent(BlkHeightLable1)
																		.addGap(55, 55, 55)
																		.addComponent(BlkHeightText))
																.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(BlkWidthLable)
																				.addComponent(BlkLengthLabel))
																		.addGap(55, 55, 55)
																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																				.addComponent(BlkLengthText)
																				.addComponent(BlkWidthText)
																				.addComponent(BlkNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(BlkRadPosUnit)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(BlkHeightUnit, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
																.addComponent(BlkWidthUnit)
																.addComponent(BlkLengthUnit, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
																.addComponent(BlkPosUnit)
																.addComponent(BlkMassUnit, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)))))
								.addGap(56, 56, 56))
						.addGroup(layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(BlkRadPosLabel)
								.addContainerGap(255, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup()
								.addGap(56, 56, 56)
								.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
								.addGap(65, 65, 65)));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addGap(31, 31, 31)
								.addComponent(BlkSpecTitle)
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(BlkNameLabel)
										.addComponent(BlkNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(BlkLengthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(BlkLengthText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(BlkLengthUnit))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(BlkWidthLable)
										.addComponent(BlkWidthText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(BlkWidthUnit))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(BlkHeightLable1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(BlkHeightText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(BlkHeightUnit))
								.addGap(9, 9, 9)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(BlkMassLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(BlkMassText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(BlkMassUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(BlkPosLabel)
										.addComponent(BlkPosText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(BlkPosUnit))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(BlkRadPosLabel)
										.addComponent(BlkRadPosText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(BlkRadPosUnit))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jButton1)
										.addComponent(jButton2)
										.addComponent(jButton3))
								.addGap(21, 21, 21)));
		
		pack();
	}// </editor-fold>//GEN-END:initComponents
	
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		// TODO add your handling code here:
		boolean Goer = CheckValidity();
		if (Goer == true) {
			ReadOk = true;
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(null, ("Something is wrong with the data that you entered"));
		}
	}//GEN-LAST:event_jButton1ActionPerformed
	
	private void BlkLengthTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BlkLengthTextFocusLost
		//Function to test the validity of the user input
		TestUserTextInput LengthTest = new TestUserTextInput(BlkLengthText);
		length = LengthTest.TestDouble();
		BlkLengthText = LengthTest.InputField;
	}//GEN-LAST:event_BlkLengthTextFocusLost
	
	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
		// TODO add your handling code here:
		this.dispose();
	}//GEN-LAST:event_jButton2ActionPerformed
	
	private void BlkNameTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BlkNameTextFocusLost
		// TODO add your handling code here:
		Name = BlkNameText.getText();
	}//GEN-LAST:event_BlkNameTextFocusLost
	
	private void BlkPosTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BlkPosTextFocusLost
		///Function to test the validity of the user input
		TestUserTextInput PositionTest = new TestUserTextInput(BlkPosText);
		Position = PositionTest.TestDouble();
		BlkPosText = PositionTest.InputField;
	}//GEN-LAST:event_BlkPosTextFocusLost
	
	private void BlkMassTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BlkMassTextFocusLost
		///Function to test the validity of the user input
		TestUserTextInput MassTest = new TestUserTextInput(BlkMassText);
		Mass = MassTest.TestDouble();
		BlkMassText = MassTest.InputField;
	}//GEN-LAST:event_BlkMassTextFocusLost
	
	private void BlkWidthTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BlkWidthTextFocusLost
		///Function to test the validity of the user input
		TestUserTextInput ODTest = new TestUserTextInput(BlkWidthText);
		Width = ODTest.TestDouble();
		BlkWidthText = ODTest.InputField;
	}//GEN-LAST:event_BlkWidthTextFocusLost
	
	private void BlkHeightTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BlkHeightTextFocusLost
		// TODO add your handling code here:
		TestUserTextInput HeightTest = new TestUserTextInput(BlkHeightText);
		Height = HeightTest.TestDouble();
		BlkHeightText = HeightTest.InputField;
	}//GEN-LAST:event_BlkHeightTextFocusLost
	
	private void BlkRadPosTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BlkRadPosTextFocusLost
		// TODO add your handling code here:
		///Function to test the validity of the user input
		TestUserTextInput RadPositionTest = new TestUserTextInput(BlkRadPosText);
		RadialPosition = RadPositionTest.TestDouble();
		BlkRadPosText = RadPositionTest.InputField;
	}//GEN-LAST:event_BlkRadPosTextFocusLost
	
	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
		Tips tDiag = new Tips(null, true);
		tDiag.setTipTxt(
				"Block Settings \n\n Use this window to specify the parameters of the block part. Position is the distance, along the rocket's axis, from the nose tip to the foremost edge of the part. Radial position is the offset between the centre of the part and the rocket's axis. ");
		tDiag.setVisible(true);
	}//GEN-LAST:event_jButton3ActionPerformed
	
	private boolean CheckValidity() {
		boolean answer;
		Vector<javax.swing.JTextField> FieldsList = new Vector<javax.swing.JTextField>();
		FieldsList.add(BlkLengthText);
		FieldsList.add(BlkWidthText);
		FieldsList.add(BlkHeightText);
		FieldsList.add(BlkMassText);
		FieldsList.add(BlkPosText);
		FieldsList.add(BlkRadPosText);
		
		TestUserTextInput TUI1 = new TestUserTextInput(FieldsList);
		answer = TUI1.TestDoubleList();
		return (answer);
	}
	
	/**
	* @param args the command line arguments
	*/
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				BlockDialog dialog = new BlockDialog(new javax.swing.JFrame(), true);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}
	
	//**Function to fill in the fields for a block being edited
	public void FillFields(double d1, double d2, double d3, double d4, double d5, double d6, String s1) {
		length = d1;
		Width = d2;
		Height = d3;
		Mass = d4;
		Position = d5;
		RadialPosition = d6;
		Name = s1;
		
		BlkLengthText.setText(Double.toString(d1));
		BlkWidthText.setText(Double.toString(d2));
		BlkHeightText.setText(Double.toString(d3));
		BlkMassText.setText(Double.toString(d4));
		BlkPosText.setText(Double.toString(d5));
		BlkNameText.setText(s1);
		BlkRadPosText.setText(Double.toString(RadialPosition));
	}
	
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel BlkHeightLable1;
	private javax.swing.JTextField BlkHeightText;
	private javax.swing.JLabel BlkHeightUnit;
	private javax.swing.JLabel BlkLengthLabel;
	private javax.swing.JTextField BlkLengthText;
	private javax.swing.JLabel BlkLengthUnit;
	private javax.swing.JLabel BlkMassLabel;
	private javax.swing.JTextField BlkMassText;
	private javax.swing.JLabel BlkMassUnit;
	private javax.swing.JLabel BlkNameLabel;
	private javax.swing.JTextField BlkNameText;
	private javax.swing.JLabel BlkPosLabel;
	private javax.swing.JTextField BlkPosText;
	private javax.swing.JLabel BlkPosUnit;
	private javax.swing.JLabel BlkRadPosLabel;
	private javax.swing.JTextField BlkRadPosText;
	private javax.swing.JLabel BlkRadPosUnit;
	private javax.swing.JLabel BlkSpecTitle;
	private javax.swing.JLabel BlkWidthLable;
	private javax.swing.JTextField BlkWidthText;
	private javax.swing.JLabel BlkWidthUnit;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	// End of variables declaration//GEN-END:variables
	
}