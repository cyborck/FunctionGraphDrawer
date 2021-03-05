package com.cyborck.functionGraphDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI extends JFrame implements KeyListener {
    private final CoordinateSystemPanel csp;
    private final JTextField functionInput;

    public GUI () {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setResizable( false );
        setVisible( true );

        Font standardFont = new Font( "Arial", Font.PLAIN, 20 );

        JPanel content = new JPanel();
        content.setLayout( new BorderLayout() );
        add( content );

        csp = new CoordinateSystemPanel( 700, 700, FunctionGenerator.generateFunction( "x" ) );
        content.add( csp, BorderLayout.CENTER );

        JPanel southPanel = new JPanel();
        southPanel.setLayout( new FlowLayout( FlowLayout.CENTER ) );
        content.add( southPanel, BorderLayout.SOUTH );

        JLabel functionLabel = new JLabel();
        functionLabel.setText( "f(x) = " );
        functionLabel.setFont( standardFont );
        southPanel.add( functionLabel );

        functionInput = new JTextField();
        functionInput.setFont( standardFont );
        functionInput.setText( "x" );
        functionInput.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
        functionInput.setPreferredSize( new Dimension( 300,
                functionInput.getFontMetrics( standardFont ).getHeight() ) );
        functionInput.addKeyListener( this );
        southPanel.add( functionInput );

        pack();
        setLocationRelativeTo( null );
    }

    @Override
    public void keyPressed ( KeyEvent e ) {
        if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
            Function function = FunctionGenerator.generateFunction( functionInput.getText() );
            csp.setFunction( function );
        }
    }

    //not used
    @Override
    public void keyTyped ( KeyEvent e ) {}

    @Override
    public void keyReleased ( KeyEvent e ) {}
}
