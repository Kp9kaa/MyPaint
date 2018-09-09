import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class DrawFrame extends JFrame
{
    private JList jList;
    private int currentChooseShape;
    private boolean checkChange;

    private DefaultListModel<String> dlm;

    private JLabel statusLabel;
    private DrawPanel panel;
    
    private JButton undo;
    private JButton redo;
    private JButton clear;
    private JButton save;
    private JButton download;
    private JCheckBox change;
    
    private JComboBox colors;
    

    private String colorOptions[]=
    {"Black","Blue","Cyan","Dark Gray","Gray","Green","Light Gray",
        "Magenta","Orange","Pink","Red","White","Yellow"};
    

    private Color colorArray[]=
    {Color.BLACK , Color.BLUE , Color.CYAN , Color.darkGray , Color.GRAY , 
        Color.GREEN, Color.lightGray , Color.MAGENTA , Color.ORANGE , 
    Color.PINK , Color.RED , Color.WHITE , Color.YELLOW};
    
    private JComboBox shapes;
    

    private String shapeOptions[]=
    {"Line","Rectangle","Oval","Square", "Circle", "Triangle"};
    
    private JCheckBox filled;
        
    private JPanel widgetJPanel;
    private JPanel widgetPadder;

    public DrawFrame()
    {
        super("SuperPaint Application v2.0!");
        
        JLabel statusLabel = new JLabel( "" );
        dlm = new DefaultListModel<>();
        jList = new JList(dlm);


        panel = new DrawPanel(statusLabel, dlm);


        undo = new JButton( "Undo" );
        redo = new JButton( "Redo" );
        clear = new JButton( "Clear" );

        JScrollPane myScrollPaneList = new JScrollPane(jList);
        myScrollPaneList.setPreferredSize(new Dimension(50,50));
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                currentChooseShape = jList.getSelectedIndex();

            }
        });

        colors = new JComboBox( colorOptions );
        shapes = new JComboBox( shapeOptions );
        

        filled = new JCheckBox( "Filled" );
        change = new JCheckBox("Change");

        save = new JButton("Save");
        download = new JButton("Download");

        JPanel listJPanel = new JPanel();
        listJPanel.setLayout(new GridLayout(1,2,10,10));

        widgetJPanel = new JPanel();
        widgetJPanel.setLayout( new GridLayout( 2, 5, 10, 10 ) );
        widgetPadder = new JPanel();
        widgetPadder.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 5));
        widgetJPanel.add( undo );
        widgetJPanel.add( redo );
        widgetJPanel.add( clear );
        widgetJPanel.add( colors );
        widgetJPanel.add( shapes );                 
        widgetJPanel.add( filled );
        widgetJPanel.add(change);
        widgetJPanel.add(save);
        widgetJPanel.add(download);
        listJPanel.add(widgetJPanel);
        listJPanel.add(myScrollPaneList);

        widgetPadder.add( listJPanel );

        add( widgetPadder, BorderLayout.NORTH);
        add( panel, BorderLayout.CENTER);
        

        ButtonHandler buttonHandler = new ButtonHandler();
        undo.addActionListener( buttonHandler );
        redo.addActionListener( buttonHandler );
        clear.addActionListener( buttonHandler );
        save.addActionListener(buttonHandler);
        download.addActionListener(buttonHandler);

        ItemListenerHandler handler = new ItemListenerHandler();
        colors.addItemListener( handler );
        shapes.addItemListener( handler );
        filled.addItemListener( handler );
        change.addItemListener(handler);

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 1200, 700 );
        setVisible( true );
        
    }

    private class ButtonHandler implements ActionListener
    {

        public void actionPerformed( ActionEvent event )
        {
            if (event.getActionCommand().equals("Undo")){
                panel.clearLastShape();
            }
            else if (event.getActionCommand().equals("Redo")){
                panel.redoLastShape();
            }
            else if (event.getActionCommand().equals("Clear")){
                panel.clearDrawing();
            }
            else if (event.getActionCommand().equals("Save")){
                panel.saveFile();
            }
            else if (event.getActionCommand().equals("Download")){
                panel.downloadFile();
            }

             
        }
    }

    private class ItemListenerHandler implements ItemListener
    {
        public void itemStateChanged( ItemEvent event )
        {

            if ( event.getSource() == filled )
            {
                boolean checkFill=filled.isSelected() ? true : false; //
                panel.setCurrentShapeFilled(checkFill);
            }
            if(event.getSource()==change){
                checkChange=change.isSelected() ? true : false;
                panel.setCurrentShapeChange(checkChange);
            }
            

            if ( event.getStateChange() == ItemEvent.SELECTED )
            {

                if ( event.getSource() == colors)
                {
                    panel.setCurrentShapeColor
                        (colorArray[colors.getSelectedIndex()]);
                    if(currentChooseShape >= 0 && checkChange){
                        panel.changeColor(currentChooseShape);
                    }
                }
                

                else if ( event.getSource() == shapes)
                {
                    panel.setCurrentShapeType(shapes.getSelectedIndex());
                }
            }
            
        }
    }

    
}