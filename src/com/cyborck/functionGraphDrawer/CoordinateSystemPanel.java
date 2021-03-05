package com.cyborck.functionGraphDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class CoordinateSystemPanel extends JPanel implements MouseWheelListener, MouseMotionListener {
    private final int width;
    private final int height;

    private Function function;
    private double zoom;
    private int offsetX, offsetY;

    private int mouseX, mouseY;

    public CoordinateSystemPanel ( int width, int height, Function function ) {
        setPreferredSize( new Dimension( width, height ) );
        addMouseWheelListener( this );
        addMouseMotionListener( this );

        this.height = height;
        this.width = width;
        this.function = function;
        zoom = 50;
        offsetX = width / 2;
        offsetY = height / 2;
    }

    public void setFunction ( Function function ) {
        this.function = function;
        repaint();
    }

    private void adjustZoom ( int sign ) {
        if ( sign > 0 )
            zoom *= 1.1;
        else if ( sign < 0 )
            zoom /= 1.1;
        repaint();
    }

    @Override
    public void mouseWheelMoved ( MouseWheelEvent e ) {
        adjustZoom( -e.getWheelRotation() );
    }

    @Override
    protected void paintComponent ( Graphics g ) {
        super.paintComponent( g );

        FunctionValue[] functionValues = function.getFunctionValues( -offsetX / zoom,
                ( width - offsetX ) / zoom, 1 / zoom );

        //draw background
        g.setColor( Color.DARK_GRAY.brighter() );
        g.fillRect( 0, 0, width, height );

        //coordinate system
        g.setColor( Color.BLACK );
        g.drawLine( 0, offsetY, width, offsetY ); //x-axis
        g.drawLine( offsetX, 0, offsetX, height ); //y-axis

        double distance = 1;
        while ( distance < 50d / zoom ) distance *= 2;
        while ( distance > 100d / zoom ) distance /= 2;

        //positive x
        for ( double x = distance; x <= ( width - offsetX ) / zoom; x += distance ) {
            int realX = ( int ) ( x * zoom + offsetX );
            int y1 = offsetY - 5;
            int y2 = offsetY + 5;
            g.drawLine( realX, y1, realX, y2 );

            String str = String.valueOf( Math.round( x * 100 ) / 100d );
            int strWidth = g.getFontMetrics().stringWidth( str );
            int strX = ( int ) ( realX - strWidth / 2d - 1 );
            int strY = y1 - 2;
            g.drawString( str, strX, strY );
        }
        //negative x
        for ( double x = -distance; x >= -offsetX / zoom; x -= distance ) {
            int realX = ( int ) ( x * zoom + offsetX );
            int y1 = offsetY - 5;
            int y2 = offsetY + 5;
            g.drawLine( realX, y1, realX, y2 );

            String str = String.valueOf( Math.round( x * 100 ) / 100d );
            int strWidth = g.getFontMetrics().stringWidth( str );
            int strX = ( int ) ( realX - strWidth / 2d - 1 );
            int strY = y1 - 2;
            g.drawString( str, strX, strY );
        }
        //positive y
        for ( double y = distance; y <= offsetY / zoom; y += distance ) {
            int realY = ( int ) ( -y * zoom + offsetY );
            int x1 = offsetX - 5;
            int x2 = offsetX + 5;
            g.drawLine( x1, realY, x2, realY );

            String str = String.valueOf( Math.round( y * 100 ) / 100d );
            int strHeight = g.getFontMetrics().getHeight();
            int strX = x2 + 2;
            int strY = realY + strHeight / 4;
            g.drawString( str, strX, strY );
        }
        //negative y
        for ( double y = -distance; y >= -( height - offsetY ) / zoom; y -= distance ) {
            int realY = ( int ) ( -y * zoom + offsetY );
            int x1 = offsetX - 5;
            int x2 = offsetX + 5;
            g.drawLine( x1, realY, x2, realY );

            String str = String.valueOf( Math.round( y * 100 ) / 100d );
            int strHeight = g.getFontMetrics().getHeight();
            int strX = x2 + 2;
            int strY = realY + strHeight / 4;
            g.drawString( str, strX, strY );
        }

        //graph
        g.setColor( Color.RED );
        for ( int i = 0; i < functionValues.length - 1; i++ ) {
            if ( !Double.isNaN( functionValues[ i ].getY() ) && !Double.isNaN( functionValues[ i + 1 ].getY() ) ) {
                int x1 = ( int ) ( offsetX + functionValues[ i ].getX() * zoom );
                int y1 = ( int ) ( offsetY - functionValues[ i ].getY() * zoom );
                int x2 = ( int ) ( offsetX + functionValues[ i + 1 ].getX() * zoom );
                int y2 = ( int ) ( offsetY - functionValues[ i + 1 ].getY() * zoom );
                g.drawLine( x1, y1, x2, y2 );
            }
        }
    }

    @Override
    public void mouseDragged ( MouseEvent e ) {
        int xDiff = e.getX() - mouseX;
        int yDiff = e.getY() - mouseY;

        offsetX += xDiff;
        offsetY += yDiff;
        repaint();

        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved ( MouseEvent e ) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
