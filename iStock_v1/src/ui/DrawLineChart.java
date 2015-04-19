/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * -------------------
 * LineChartDemo1.java
 * -------------------
 * (C) Copyright 2002-2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: LineChartDemo1.java,v 1.27 2004/05/27 09:10:42 mungady Exp $
 *
 * Changes
 * -------
 * 08-Apr-2002 : Version 1 (DG);
 * 30-May-2002 : Modified to display values on the chart (DG);
 * 25-Jun-2002 : Removed redundant import (DG);
 * 11-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 *
 */

package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a line chart using data from a
 * {@link CategoryDataset}.
 */
public class DrawLineChart {

	private JFreeChart chart;
    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public DrawLineChart(final String title) {
       
        final CategoryDataset dataset = createDataset();
        chart = createChart(dataset);
//        final ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new Dimension(500, 270));
//        setContentPane(chartPanel);
    }

    /**
     * Creates a sample dataset.
     * 
     * @return The dataset.
     */
    private CategoryDataset createDataset() {
        
        // row keys...
        final String series1 = "First";

        // column keys...
        final String type1 = "03/06";
        final String type2 = "03/07";
        final String type3 = "03/08";
        final String type4 = "03/09";
        final String type5 = "03/10";
        final String type6 = "03/11";
        final String type7 = "03/12";
        final String type8 = "03/13";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(-0.01, series1, type1);
        dataset.addValue(-0.003, series1, type2);
        dataset.addValue(0.009, series1, type3);
        dataset.addValue(0.02, series1, type4);
        dataset.addValue(0.053, series1, type5);
        dataset.addValue(0.04, series1, type6);
        dataset.addValue(0.029, series1, type7);
        dataset.addValue(0.03, series1, type8);

        return dataset;
                
    }
    
    /**
     * Creates a sample chart.
     * 
     * @param dataset  a dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(final CategoryDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createLineChart(
            null,       // chart title
            null,                    // domain axis label
            null,                   // range axis label
            dataset,                   // data
            PlotOrientation.VERTICAL,  // orientation
            false,                      // include legend
            true,                      // tooltips
            false                      // urls
        );


        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.lightGray);
        
        plot.setDomainGridlinesVisible(true);
        // customise the range axis...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(false);
        
        //rangeAxis.setInverted(true);
        rangeAxis.setAutoRange(true);
        rangeAxis.setAutoRangeMinimumSize(0.01);
//        rangeAxis.setFixedAutoRange(0.1);
        rangeAxis.setAutoRangeIncludesZero(true);
//        rangeAxis.setLowerBound(-0.2);
//        rangeAxis.setUpperBound(0.2);
//        rangeAxis.setTickMarksVisible(true);
        rangeAxis.setTickUnit(new NumberTickUnit(0.01));//设置显示Y轴数据间隔

        // ****************************************************************************
        // * JFREECHART DEVELOPER GUIDE                                               *
        // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
        // * to purchase from Object Refinery Limited:                                *
        // *                                                                          *
        // * http://www.object-refinery.com/jfreechart/guide.html                     *
        // *                                                                          *
        // * Sales are used to provide funding for the JFreeChart project - please    * 
        // * support us so that we can continue developing free software.             *
        // ****************************************************************************
        
        // customise the renderer...
        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        renderer.setDrawShapes(true);
        renderer.setItemLabelsVisible(true);
        return chart;
    }
    
    public JFreeChart getChart(){
    	return chart;
    }
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {
        
        DrawLineChart chart = new DrawLineChart("Line Chart Demo");
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setSize(600, 300);
        shell.setLayout(new FillLayout());
        shell.setText("Test for jfreechart running with SWT");
        
        final ChartComposite frame2 = new ChartComposite(shell, SWT.NONE, chart.chart,true);

        //输出图片
        //        try {
//			ChartUtilities.saveChartAsPNG(new File("img/linechart.png"), chart.chart, 550, 250);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
        frame2.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }

    }

}
