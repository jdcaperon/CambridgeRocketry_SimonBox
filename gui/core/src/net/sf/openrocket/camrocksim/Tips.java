/*
%## Copyright (C) 2011, 2016 S.Box
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

%## Tips.java

%## Author: S.Box
%## Created: 2011-10-27

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Tips.java
 *
 * Created on 08-Sep-2011, 09:38:20
 */

package net.sf.openrocket.camrocksim;

/**
 *
 * @author sb4p07
 */
public class Tips extends javax.swing.JDialog {
	
	/** Creates new form Tips */
	public Tips(java.awt.Frame parent, boolean modal) {
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
		
		CloseTipButton = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		TipTextArea = new javax.swing.JTextPane();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Tips");
		
		CloseTipButton.setText("OK");
		CloseTipButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				CloseTipButtonActionPerformed(evt);
			}
		});
		
		TipTextArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		TipTextArea.setEditable(false);
		jScrollPane1.setViewportView(TipTextArea);
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addGap(42, 42, 42)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
										.addComponent(CloseTipButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(56, Short.MAX_VALUE)));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addGap(30, 30, 30)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(38, 38, 38)
								.addComponent(CloseTipButton)
								.addContainerGap(38, Short.MAX_VALUE)));
		
		pack();
	}// </editor-fold>//GEN-END:initComponents
	
	private void CloseTipButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseTipButtonActionPerformed
		this.dispose();
		;
	}//GEN-LAST:event_CloseTipButtonActionPerformed
	
	public void setTipTxt(String Text) {
		TipTextArea.setText(Text);
	}
	
	/**
	* @param args the command line arguments
	*/
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				Tips dialog = new Tips(new javax.swing.JFrame(), true);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}
	
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton CloseTipButton;
	private javax.swing.JTextPane TipTextArea;
	private javax.swing.JScrollPane jScrollPane1;
	// End of variables declaration//GEN-END:variables
	
}
