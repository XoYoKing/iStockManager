package applicationUI;

import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import org.jfree.experimental.chart.swt.ChartComposite;

import ui.ChangeMoney;
import ui.Set;
import ui.StockDetails;
import ui.StockSituation;

public class MainFrame {
	private Shell _shell;
	private TabFolder tFolder;
	private TabItem ownershipTabItem;
	private TabItem graphTabItem;
	private TabItem personalWealTab;
	private Menu menu;
	private MenuBar menuBar;
	private MenuItem menuItem_file;
	private Menu fileMenu;
	private MenuItem menuItem_set;
	private MenuItem menuItem_about;
	private Label historylabel;
	private GridData data;
	private Button[] goDetailsbButton;
	private Button[] rHistoryButton;
	private String curHistoryFlag;
	private Button setRecord;
	private Composite statusbar;
	private Label statusbarLabel;
	
	private Composite graphTabComposite;
	
	private int i;
	String[] stocks = new String[]{"中国银行","工商银行","复星医药"};

	// private Shell _shell;
	public MainFrame() {
		final Display display = new Display();
		// this shell is the main shell
		final Shell shell = new Shell(display, SWT.SHELL_TRIM);
		setShell(shell);
		GridLayout mainGridLayout = new GridLayout(2, false);
		_shell.setLayout(mainGridLayout);
		// the title of the shell
		_shell.setText("Storck Manager v1");

		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.horizontalSpan = 3;
		data.verticalSpan = 2;
		data.heightHint = 50;
		Label decorate = new Label(_shell, SWT.PUSH);
		decorate.setText("decorate");
		decorate.setVisible(false);
		decorate.setLayoutData(data);

//		Label decorate2 = new Label(_shell, SWT.FILL);
//		data = new GridData();
//		data.widthHint = 100;
//		decorate2.setVisible(false);
//		decorate2.setLayoutData(data);
//		decorate2.setText("decorate");

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint=850;
		tFolder = new TabFolder(_shell, SWT.None);
		tFolder.setLayoutData(data);
		tFolder.setLayout(new GridLayout());

		// 持股情况
		ownershipTabItem = new TabItem(tFolder, SWT.NONE);
		ownershipTabItem.setText("持股构成");
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 400;
		data.widthHint = 900;
		Composite ownershipTabComposite = new Composite(tFolder, SWT.NONE);
		ownershipTabComposite.setLayoutData(data);
		ownershipTabComposite.setLayout(new GridLayout(3, true));

		Composite mStockListComposite = new Composite(ownershipTabComposite,
				SWT.BORDER);
		//持股情况内容布局
		data = new GridData();
		data.heightHint = 300;
		data.widthHint = 350;
        data.horizontalIndent=40;
        data.verticalIndent=40;
		mStockListComposite.setLayoutData(data);
		mStockListComposite.setLayout(new GridLayout(2, true));

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.minimumHeight = 100;
		data.minimumWidth = 200;
		data.horizontalSpan = 5;
		Label title = new Label(mStockListComposite, SWT.NONE);
		title.setLayoutData(data);
		title.setText("持股情况");
		
		goDetailsbButton=new Button[stocks.length];
		rHistoryButton=new Button[stocks.length];
		for (i = 0; i < 3; i++) {
			goDetailsbButton[i]=new Button(mStockListComposite, SWT.NONE);
			goDetailsbButton[i].setText(stocks[i]);
		    goDetailsbButton[i].addSelectionListener(new SelectionAdapter() {
		    	private String str=stocks[i];

				public void widgetSelected(SelectionEvent e) {
					try {
						StockDetails situation=new StockDetails(shell);
						situation.open(str);
					} catch (Exception e2) {
						// TODO: handle exception
						   e2.printStackTrace();
					}
				}
			});
		    
		    rHistoryButton[i]=new Button(mStockListComposite, SWT.NONE);
		    rHistoryButton[i].setText("历史");
		    rHistoryButton[i].addSelectionListener(new SelectionAdapter() {
		    	private String str=stocks[i];
		 
				public void widgetSelected(SelectionEvent e) {
					historylabel.setText(str+"股票记录");
					curHistoryFlag=str;
					setRecord.setVisible(true);
				}
			});
		}

		Composite buyHistorycomposite = new Composite(ownershipTabComposite,
				SWT.BORDER|SWT.V_SCROLL);
		data = new GridData();
		data.heightHint = 300;
		data.widthHint = 350;
		data.horizontalSpan = 2; // Composite的占两个Cell
        data.horizontalIndent=40;
        data.verticalIndent=40;
		buyHistorycomposite.setLayoutData(data);
		GridLayout layout = new GridLayout();
		layout.numColumns = 5;
		layout.marginHeight = 15;
		layout.marginRight = 150;
		buyHistorycomposite.setLayout(layout);

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.minimumHeight = 100;
		data.minimumWidth = 200;
		data.horizontalSpan = 5;
		Label title2 = new Label(buyHistorycomposite, SWT.NONE);
		title2.setLayoutData(data);
		title2.setText("历史记录");

		
		
		data = new GridData(GridData.FILL);
		data.minimumHeight = 100;
		data.minimumWidth = 200;
		data.horizontalSpan = 2;
		historylabel = new Label(buyHistorycomposite, SWT.NONE);
		historylabel.setLayoutData(data);
		historylabel.setText("                                  ");
	
		 setRecord = new Button(buyHistorycomposite, SWT.PUSH);
		setRecord.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END|GridData.FILL_BOTH, SWT.NONE, false,
				false));
		setRecord.setText("修改");
		setRecord.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					StockSituation stockDetails=new StockSituation(shell);
					stockDetails.open("修改记录",curHistoryFlag);
				} catch (Exception e2) {
					// TODO: handle exception
					   e2.printStackTrace();
				}
			}
		});
		setRecord.setVisible(false);
		
		ownershipTabItem.setControl(ownershipTabComposite);

		// 构成图
		graphTabItem = new TabItem(tFolder, SWT.NONE);
		graphTabItem.setText("构成图");
		graphTabComposite = new Composite(tFolder, SWT.NONE);
		graphTabComposite.setLayout(new GridLayout(1, true));

		//  构成图布局
		// TODO
//        Label image = new Label(graphTabComposite, SWT.FILL);
//		image.setLayoutData(new GridData(SWT.HORIZONTAL, SWT.VERTICAL, false,
//				false));
//		image.setImage(new Image(display, "/res/image/构成图。png"));
		
		graphTabItem.setControl(graphTabComposite);

		// 个人资产
		personalWealTab = new TabItem(tFolder, SWT.NONE);
		personalWealTab.setText("个人资产");
		Composite personalcTabComposite = new Composite(tFolder, SWT.NONE);
		personalcTabComposite.setLayout(new GridLayout(2, true));
		
		// 个人资产布局
		// TODO
        Label aMarket = new Label(personalcTabComposite, SWT.NONE);
		aMarket.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		aMarket.setText("A股资产情况");
		
		Button setCapital=new Button(personalcTabComposite, SWT.NONE);
		setCapital.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				try {
					ChangeMoney changeMoneyDlg=new ChangeMoney(shell);
					changeMoneyDlg.open();
				} catch (Exception e2) {
					// TODO: handle exception
					   e2.printStackTrace();
				}
			}
		});
		setCapital.setText("修改");
		
		
		personalWealTab.setControl(personalcTabComposite);

		
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.horizontalSpan = 3;
		data.verticalSpan = 2;
		data.heightHint = 50;
		Label decorate2 = new Label(_shell, SWT.PUSH);
		decorate2.setText("decorate");
		decorate2.setVisible(false);
		decorate2.setLayoutData(data);
		
		//statuBar
        statusbar = new Composite(shell, SWT.BORDER);
        //设置工具栏在Shell中的形状为水平抢占充满，并高19像素
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.heightHint = 19;
        statusbar.setLayoutData(gridData);
        //设置为用行列式布局管理状态栏里的组件
        RowLayout layout2 = new RowLayout();
        layout2.marginLeft = layout.marginTop = 0; //无边距
        statusbar.setLayout(layout2);
        statusbarLabel = new Label(statusbar, SWT.BORDER);
        statusbarLabel.setLayoutData(new RowData(70, -1));
        statusbarLabel.setText("状态栏");
        
        
		// menu
		menu = new Menu(_shell, SWT.BAR);

		menuItem_file = new MenuItem(menu, SWT.CASCADE);
		menuItem_file.setText("&文件");
		fileMenu = new Menu(_shell, SWT.DROP_DOWN);
		MenuItem menuItem_Import = new MenuItem(fileMenu, SWT.PUSH);
		menuItem_Import.setText("&导入");
		//打开导入文件对话框
		menuItem_Import.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileSelect=new FileDialog(shell,SWT.SINGLE);
				fileSelect.setFilterNames(new String[]{"Excel Files (*.xsl)"});
				fileSelect.setFilterExtensions(new String[]{"Excel Files (*.xsl)"});
				String url=""; 
				url=fileSelect.open();
			}	
		});
		
		MenuItem menuItem_Export = new MenuItem(fileMenu, SWT.PUSH);
		menuItem_Export.setText("&导出");
		// 打开导入文件对话框
		menuItem_Export.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileSelect = new FileDialog(shell, SWT.SAVE);
				fileSelect.setFilterNames(new String[] { "Excel Files (*.xsl)" });
				fileSelect.setFilterExtensions(new String[] { "Excel Files (*.xsl)" });
				String url = "";
				url = fileSelect.open();
			}
		});

		MenuItem menuItem_Separator = new MenuItem(fileMenu, SWT.SEPARATOR);
		MenuItem menuItem_clearHistory = new MenuItem(fileMenu, SWT.PUSH);
		menuItem_clearHistory.setText("&清除历史");
		MenuItem menuItem_Separator2 = new MenuItem(fileMenu, SWT.SEPARATOR);
		MenuItem menuItem_exit = new MenuItem(fileMenu, SWT.PUSH);
		menuItem_exit.setText("&退出");
		menuItem_file.setMenu(fileMenu);
		
		//添加“退出”事件
		menuItem_exit.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});

		menuItem_set = new MenuItem(menu, SWT.PUSH);
		menuItem_set.setText("&设置");
		menuItem_set.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					Set setDlg = new Set(shell);
					setDlg.open();
				} catch (Exception e2) {
					// TODO: handle exception
					   e2.printStackTrace();
				}
			};
		});

		menuItem_about = new MenuItem(menu, SWT.PUSH);
		menuItem_about.setText("&关于");

		_shell.setMenuBar(menu);
		_shell.setBounds(80, 35, 1100, 700);
		_shell.open();

		while (!_shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	
	private void setShell(Shell shell) {
		// TODO Auto-generated method stub
		this._shell = shell;
	}

	public static void main(String[] args) {

		MainFrame frame = new MainFrame();
		frame.drawLineChart();

	}

	// private void setShell(Shell shell) {
	// // TODO Auto-generated method stub
	// this._shell=shell;
	// }
	
	public void drawLineChart(){
		LineChartDemo1 chart = new LineChartDemo1("Line Chart Demo");
		 final ChartComposite frame2 = new ChartComposite(graphTabComposite, SWT.NONE, chart.getChart(),true);
	       // frame2.pack();
//		Control controlChart = (Control)chart.getChart();
//		graphTabItem.setControl(frame2);
	}
}
