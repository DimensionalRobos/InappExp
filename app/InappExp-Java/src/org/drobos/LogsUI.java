package org.drobos;


import java.awt.Color;



/**
 *
 * @author Anj Lasala
 */
public class LogsUI extends javax.swing.JFrame {

    /**
     * Creates new form LogsUI
     */
    public LogsUI() {
        initComponents();
        this.getContentPane().setBackground(Color.BLACK);
    }
    
    public LogsUI(Report report){
        this();
        this.setVisible(true);
        this.txtPOSTag.setText(report.postag);
        this.txtNERTag.setText(report.nertag);
        for(Expression expression:report.expressions){
            this.txtDefinitions.setText(txtDefinitions.getText()+expression.word+"\n");
            for(String definition:expression.definitions){
                this.txtDefinitions.setText(txtDefinitions.getText()+"=>"+definition+"\n");
            }
            for(String definition:expression.urbanDefinitions){
                this.txtDefinitions.setText(txtDefinitions.getText()+"=>"+definition+"\n");
            }
            this.txtStemmer.setText(txtStemmer.getText()+expression.word+"\n");
            for(String baseForm:expression.baseForms){
                this.txtStemmer.setText(txtStemmer.getText()+"=>"+baseForm+"\n");
            }
            this.txtSentiAna.setText(txtSentiAna.getText()+"====================\n");
            this.txtSentiAna.setText(txtSentiAna.getText()+"Word:"+expression.word+"\n");
            this.txtSentiAna.setText(txtSentiAna.getText()+"POSTag:"+expression.postag+"\n");
            this.txtSentiAna.setText(txtSentiAna.getText()+"NERTag:"+expression.nertag+"\n");
            this.txtSentiAna.setText(txtSentiAna.getText()+"Word Inappropriateness level:"+expression.value+"\n");
            this.txtSentiAna.setText(txtSentiAna.getText()+"Sentiment Value:"+expression.sentimentValue+"\n");
            this.txtSentiAna.setText(txtSentiAna.getText()+"Structural Inappropriateness:"+expression.isInvoked+"\n");
            this.txtSentiAna.setText(txtSentiAna.getText()+"isInappropriate:"+expression.isInappropriate+"\n");
            this.txtSentiAna.setText(txtSentiAna.getText()+"Targetable Entity:"+InappExp.targetableUnit(expression)+"\n");
        }
        this.txtSentenceSplit.setText(report.sentences);
        this.txtTokenizer.setText(report.tokens);
        this.txtNGrams.setText(report.ngram);
        this.txtRIA.setText(report.ria);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        txtNERTag = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDefinitions = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtPOSTag = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtSentiAna = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtRIA = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtNGrams = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSentenceSplit = new javax.swing.JTextArea();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtTokenizer = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtStemmer = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Logs");

        txtNERTag.setEditable(false);
        txtNERTag.setBackground(new java.awt.Color(102, 102, 102));
        txtNERTag.setColumns(20);
        txtNERTag.setForeground(new java.awt.Color(255, 255, 255));
        txtNERTag.setRows(5);
        jScrollPane3.setViewportView(txtNERTag);
        txtNERTag.getAccessibleContext().setAccessibleName("NERTag");

        txtDefinitions.setEditable(false);
        txtDefinitions.setBackground(new java.awt.Color(102, 102, 102));
        txtDefinitions.setColumns(20);
        txtDefinitions.setForeground(new java.awt.Color(255, 255, 255));
        txtDefinitions.setRows(5);
        jScrollPane4.setViewportView(txtDefinitions);
        txtDefinitions.getAccessibleContext().setAccessibleName("DefinitionExtraction");

        txtPOSTag.setEditable(false);
        txtPOSTag.setBackground(new java.awt.Color(102, 102, 102));
        txtPOSTag.setColumns(20);
        txtPOSTag.setForeground(new java.awt.Color(255, 255, 255));
        txtPOSTag.setRows(5);
        jScrollPane5.setViewportView(txtPOSTag);
        txtPOSTag.getAccessibleContext().setAccessibleName("POSTag");

        txtSentiAna.setEditable(false);
        txtSentiAna.setBackground(new java.awt.Color(102, 102, 102));
        txtSentiAna.setColumns(20);
        txtSentiAna.setForeground(new java.awt.Color(255, 255, 255));
        txtSentiAna.setRows(5);
        jScrollPane6.setViewportView(txtSentiAna);
        txtSentiAna.getAccessibleContext().setAccessibleName("InappExpressScoring");

        txtRIA.setEditable(false);
        txtRIA.setBackground(new java.awt.Color(102, 102, 102));
        txtRIA.setColumns(20);
        txtRIA.setForeground(new java.awt.Color(255, 255, 255));
        txtRIA.setRows(5);
        jScrollPane7.setViewportView(txtRIA);
        txtRIA.getAccessibleContext().setAccessibleName("SentimentScoring");

        txtNGrams.setEditable(false);
        txtNGrams.setBackground(new java.awt.Color(102, 102, 102));
        txtNGrams.setColumns(20);
        txtNGrams.setForeground(new java.awt.Color(255, 255, 255));
        txtNGrams.setRows(5);
        jScrollPane8.setViewportView(txtNGrams);
        txtNGrams.getAccessibleContext().setAccessibleName("NGRAMAnalysis");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sentence Splitter");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("NER Tag");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("NGRAM Analysis");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Definition Extraction");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("InappExpress Scoring");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Relational Inference Analyzer");

        txtSentenceSplit.setEditable(false);
        txtSentenceSplit.setBackground(new java.awt.Color(102, 102, 102));
        txtSentenceSplit.setColumns(20);
        txtSentenceSplit.setForeground(new java.awt.Color(255, 255, 255));
        txtSentenceSplit.setRows(5);
        jScrollPane1.setViewportView(txtSentenceSplit);
        txtSentenceSplit.getAccessibleContext().setAccessibleName("SentenceSplitter");

        txtTokenizer.setEditable(false);
        txtTokenizer.setBackground(new java.awt.Color(102, 102, 102));
        txtTokenizer.setColumns(20);
        txtTokenizer.setForeground(new java.awt.Color(255, 255, 255));
        txtTokenizer.setRows(5);
        jScrollPane9.setViewportView(txtTokenizer);
        txtTokenizer.getAccessibleContext().setAccessibleName("Tokenizer");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("POS Tag");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tokenizer");

        txtStemmer.setEditable(false);
        txtStemmer.setBackground(new java.awt.Color(102, 102, 102));
        txtStemmer.setColumns(20);
        txtStemmer.setForeground(new java.awt.Color(255, 255, 255));
        txtStemmer.setRows(5);
        jScrollPane2.setViewportView(txtStemmer);
        txtStemmer.getAccessibleContext().setAccessibleName("Stemmer");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Stemmer");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(146, 146, 146)
                        .addComponent(jLabel6)
                        .addGap(64, 64, 64))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31)
                                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(82, 82, 82)
                                        .addComponent(jLabel1)
                                        .addGap(230, 230, 230)
                                        .addComponent(jLabel8))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(103, 103, 103)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2)
                                        .addGap(142, 142, 142)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(83, 83, 83)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(121, 121, 121))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(68, 68, 68))))
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, Short.MAX_VALUE)
                                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(18, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LogsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LogsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LogsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LogsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LogsUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    public javax.swing.JTextArea txtDefinitions;
    public javax.swing.JTextArea txtNERTag;
    public javax.swing.JTextArea txtNGrams;
    public javax.swing.JTextArea txtPOSTag;
    public javax.swing.JTextArea txtRIA;
    private javax.swing.JTextArea txtSentenceSplit;
    public javax.swing.JTextArea txtSentiAna;
    private javax.swing.JTextArea txtStemmer;
    private javax.swing.JTextArea txtTokenizer;
    // End of variables declaration//GEN-END:variables
}
