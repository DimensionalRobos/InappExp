import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Daikaiser
 */
public class Learner extends javax.swing.JFrame {
    
    /**
     * Creates new form Learner
     */
    public Learner() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnLearn = new javax.swing.JButton();
        btnViewLearning = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txaInput = new java.awt.TextArea();
        txaOutput = new java.awt.TextArea();
        lblIn = new javax.swing.JLabel();
        lblOut = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inappropriate Expressions Learning");

        btnLearn.setText("Learn");
        btnLearn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLearnActionPerformed(evt);
            }
        });

        btnViewLearning.setText("View Learning Graph");
        btnViewLearning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewLearningActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Neutra Display Titling", 0, 24)); // NOI18N
        jLabel1.setText("INAPPROPRIATE EXPRESSIONS LEARNING");
        jLabel1.setToolTipText("");

        txaInput.setEditable(false);

        txaOutput.setBackground(new java.awt.Color(255, 255, 255));
        txaOutput.setEditable(false);

        lblIn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblIn.setText("INPUT");

        lblOut.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblOut.setText("OUTPUT");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txaInput, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblIn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblOut)
                                    .addComponent(txaOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLearn)))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnViewLearning)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIn)
                    .addComponent(lblOut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txaInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txaOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLearn)
                    .addComponent(btnViewLearning))
                .addGap(54, 54, 54))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public ExpressionList learn(WordList sample,ExpressionList sentiments){
        if(sample.isEmpty())return sentiments;
        WordList resampleList=new WordList();
        for (String input : sample) {
            this.txaInput.setText(txaInput.getText() + input + "\n");
            try {
                for (String scrape : UrbanDictScraper.scrape(input)) {
                    Sentiment senti = SentiAnalyzer.analyze(POSTagger.tag(scrape));
                    if (senti != null && (!sentiments.contains(senti))) {
                        sentiments.add(senti);
                        txaOutput.setText(txaOutput.getText() + senti.word + "#" + senti.sentimentValue + "\n");
                        MLDAO.write(senti.word + "#" + senti.sentimentValue + "\n");
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Learner.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                for (String look : DefinitionExtractor.extract(input)) {
                    Sentiment senti = SentiAnalyzer.analyze(POSTagger.tag(look));
                    if(senti!=null){
                        for(String resample:DefinitionExtractor.resample(input, look)){
                            if(!(sample.contains(resample)|resampleList.contains(resample))){
                                resampleList.addLast(resample);
                            }
                        }
                    }
                    if (senti != null && (!sentiments.contains(senti))) {
                        sentiments.add(senti);
                        txaOutput.setText(txaOutput.getText() + senti.word + "#" + senti.sentimentValue + "\n");
                        MLDAO.write(senti.word + "#" + senti.sentimentValue + "\n");
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Learner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.err.println("resamples="+resampleList.size()+" samples="+sample.size()+"\n");
        learn(resampleList,sample,sentiments);
        return sentiments;
    }
    
    public ExpressionList learn(WordList sample,WordList sampledList,ExpressionList sentiments){
        WordList resampleList=new WordList();
        if(sample.isEmpty())return sentiments;
        sampledList.addAll(sample);
        for (String input : sample) {
            this.txaInput.setText(txaInput.getText() + input + "\n");
            try {
                for (String scrape : UrbanDictScraper.scrape(input)) {
                    Sentiment senti = SentiAnalyzer.analyze(POSTagger.tag(scrape));
                    if (senti != null && (!sentiments.contains(senti))) {
                        sentiments.add(senti);
                        txaOutput.setText(txaOutput.getText() + senti.word + "#" + senti.sentimentValue + "\n");
                        MLDAO.write(senti.word + "#" + senti.sentimentValue + "\n");
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Learner.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                for (String look : DefinitionExtractor.extract(input)) {
                    Sentiment senti = SentiAnalyzer.analyze(POSTagger.tag(look));
                    if(senti!=null){
                        for(String resample:DefinitionExtractor.resample(input, look)){
                            if(!(sampledList.contains(resample)|resampleList.contains(resample))){
                                resampleList.addLast(resample);
                            }
                        }
                    }
                    if (senti != null && (!sentiments.contains(senti))) {
                        sentiments.add(senti);
                        txaOutput.setText(txaOutput.getText() + senti.word + "#" + senti.sentimentValue + "\n");
                        MLDAO.write(senti.word + "#" + senti.sentimentValue + "\n");
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Learner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.err.println("resamples="+resampleList.size()+" samples="+sampledList.size()+"\n");
        learn(resampleList,sampledList,sentiments);
        return sentiments;
    }
    private void btnLearnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLearnActionPerformed
        ExpressionList sentiments=MLDAO.getSentiments();
        for(Sentiment sentiment:sentiments){
            txaOutput.setText(txaOutput.getText()+sentiment.word+"#"+sentiment.sentimentValue+"\n");
        }
        WordList sample = new WordList();
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                Scanner scanFile = new Scanner(new File(chooser.getSelectedFile().getAbsolutePath()));
                while (scanFile.hasNextLine()) {
                    String input = scanFile.nextLine();
                    sample.addLast(input);
                }
                txaInput.setText(txaInput.getText().trim());
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "You selected an unopenable file");
            }
        }
        this.learn(sample,sentiments);
        try {
            PlotTool.funcPlot(new LinkedList<>(sentiments));
        } catch (Exception ex) {
            Logger.getLogger(Learner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLearnActionPerformed

    private void btnViewLearningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewLearningActionPerformed
        LinkedList<Sentiment> sentiments = new LinkedList<Sentiment>();
            try {
                Scanner scanFile = new Scanner(new File("MLearn.txt"));
                while (scanFile.hasNextLine()) {
                    String s = scanFile.nextLine();
                    Sentiment sentiment = new Sentiment(s.split("#")[0], Double.valueOf(s.split("#")[1]));
                    sentiments.add(sentiment);
                }
                try {
                    PlotTool.funcPlot(sentiments);
                } catch (Exception ex) {
                    Logger.getLogger(Learner.class.getName()).log(Level.SEVERE, null, ex);
                }
                txaInput.setText(txaInput.getText().trim());
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "You selected an unopenable file");
            }
    }//GEN-LAST:event_btnViewLearningActionPerformed

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
            java.util.logging.Logger.getLogger(Learner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Learner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Learner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Learner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Learner().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLearn;
    private javax.swing.JButton btnViewLearning;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblIn;
    private javax.swing.JLabel lblOut;
    private java.awt.TextArea txaInput;
    private java.awt.TextArea txaOutput;
    // End of variables declaration//GEN-END:variables
}