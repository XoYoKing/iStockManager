package ui;

//单股详情

import models.RecordsSet;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;

import ui.DlgStockSituation;
import ui.DlgStockHistory;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import controller.GetInfoFromSina;
import controller.GetSingleStock;
import controller.StockMath;

public class DlgStockDetails extends Dialog {
	private Shell parentShell;
	private final Shell shell;
	
	private Object result;
	
	private String stockName;
	
	private String code;
	private JSONObject stockInfo;
	private JSONArray recordArray;
	private String[][] recordStrArr;
	private Composite recordComp;

	public DlgStockDetails(Shell parent, String code) {
		// TODO Auto-generated constructor stub

		super(parent, SWT.NONE);
		parentShell = getParent();
		shell = new Shell(parentShell, SWT.CLOSE | SWT.MIN);
		
		this.code = code;
		
		try {
			stockInfo = getStockInfo(this.code);
			recordArray = new RecordsSet()
				.getRecordsSet().getJSONArray(code);
			
			recordStrArr = jsonArray2StringArray(recordArray);
			
			stockName = stockInfo.getString("name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public String[][] jsonArray2StringArray(JSONArray ja) 
			throws JSONException{
		int len = ja.length();
		String[][] sa = new String[len][];
		
		for(int i = 0; i < len; ++i){
			String[] stra = new String[4];
			JSONObject jo = ja.getJSONObject(i);
			stra[0] = jo.getString("date");
			stra[1] = jo.getString("type");
			stra[2] = jo.getString("price");
			stra[3] = jo.getString("volumes");
			
			sa[i] = stra;
		}
		return sa;
	}

	//从新浪上即使获取股票信息
	public JSONObject getStockInfo(String code) 
			throws JSONException{
		//获取股票信息进程
		GetSingleStock gss = new GetSingleStock(code);
		Thread tdf = new Thread(gss);
		tdf.start();
		try {
			tdf.join();// 等待子线程结束
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gss.getJsonObj();
	}


	public Object open() {
//		setStockName(stockN);
		Display display = shell.getDisplay();
		shell.setSize(610, 650);
		shell.setText(stockName);
		shell.setLayout(null);
		shell.setVisible(true);

		create();


		while (!shell.isDisposed()) {
			
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	public void create() {
		// 交易记录组合框
		Group group = new Group(shell, SWT.NONE);
		group.setText("交易记录");
		group.setBounds(206, 20, 383, 243);
		group.setLayout(null);
		
		recordComp = new Composite(group, SWT.NONE);
		recordComp.setBounds(10, 20, 363, 220);

		addRecord(recordComp); //添加交易记录按钮

		seeAll(recordComp); //查看全部按钮

		recordDetail(recordComp);//交易记录明细
		
		
		kChart();//K线图
		
		try {
			stockDetail();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //股票详情
	}


	//股票详情
	public void stockDetail() throws JSONException {
//		String name_code = stockInfo.getString("name");
		
		Label name = new Label(shell, SWT.FILL);
		name.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		name.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.NORMAL));
		name.setText(stockInfo.getString("name"));
		name.setBounds(10, 20, 198, 34);
		name.setVisible(true);

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 18, SWT.NORMAL));
		lblNewLabel.setBounds(10, 60, 24, 34);
		lblNewLabel.setText("¥");
		
		Double curPrice = stockInfo.getDouble("currentPrice");
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 18, SWT.NORMAL));
		lblNewLabel_1.setBounds(40, 60, 61, 34);
		lblNewLabel_1.setText(StockMath.valueOf(curPrice));
		
//		Label label_4 = new Label(shell, SWT.NONE);
//		label_4.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
//		label_4.setBounds(134, 56, 66, 38);
//		label_4.setText("0.00\r\n0.00%");
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		lblNewLabel_2.setBounds(10, 100, 44, 27);
		lblNewLabel_2.setText("涨跌：");
		
		
		double yesClose = stockInfo.getDouble("yesterdayClosePrice");
		double raisefall = curPrice - yesClose;
		double raisefallRatio = raisefall / yesClose;
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		lblNewLabel_3.setBounds(60, 100, 121, 27);
		lblNewLabel_3.setText(StockMath.valueOf(raisefall) + 
			"(" + StockMath.doubleToPercent(raisefallRatio) + ")");
		
		Label label_10 = new Label(shell, SWT.NONE);
		label_10.setText("今开：");
		label_10.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_10.setBounds(10, 168, 44, 27);
		
		Label label_5 = new Label(shell, SWT.NONE);
		label_5.setText("4.66");
		label_5.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_5.setBounds(60, 168, 76, 27);
		
		Label label_12 = new Label(shell, SWT.NONE);
		label_12.setText("昨收：");
		label_12.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_12.setBounds(10, 133, 44, 27);
		
		Label label_11 = new Label(shell, SWT.NONE);
		label_11.setText(StockMath.valueOf(yesClose));
		label_11.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_11.setBounds(60, 133, 76, 27);
		
		Label label_14 = new Label(shell, SWT.NONE);
		label_14.setText("最高：");
		label_14.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_14.setBounds(10, 201, 44, 27);
		
		Label label_13 = new Label(shell, SWT.NONE);
		label_13.setText(stockInfo.getString("todayHightestPrice"));
		label_13.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_13.setBounds(60, 201, 76, 27);
		
		Label label_16 = new Label(shell, SWT.NONE);
		label_16.setText("最低：");
		label_16.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_16.setBounds(10, 236, 44, 27);
		
		Label label_15 = new Label(shell, SWT.NONE);
		label_15.setText(stockInfo.getString("todayLowestPrice"));
		label_15.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_15.setBounds(60, 236, 76, 27);
	}


	//K线图
	public void kChart() {
		// 标签切换图表
		final TabFolder tab = new TabFolder(shell, SWT.NONE);
		tab.setBounds(20, 280, 569, 331);
		// 添加标签
		final TabItem TabI1 = new TabItem(tab, SWT.NONE);
		TabI1.setText("分时");

		Composite composite = new Composite(tab, SWT.NONE);
		TabI1.setControl(composite);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		// new ImageComposite(composite, SWT.NONE, "data/temp/min.gif",
		// ImageComposite.SCALED);
		Image image = new Image(Display.getDefault(), "data/temp/min.gif");
		composite.setBackgroundImage(image);

		final TabItem TabI2 = new TabItem(tab, SWT.NONE);
		TabI2.setText("日K");

		Composite composite_1 = new Composite(tab, SWT.NONE);
		TabI2.setControl(composite_1);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		new ImageComposite(composite_1, SWT.NONE, "data/temp/daily.gif",
				ImageComposite.SCALED);

		final TabItem TabI3 = new TabItem(tab, SWT.NONE);
		TabI3.setText("周K");

		Composite composite_2 = new Composite(tab, SWT.NONE);
		TabI3.setControl(composite_2);
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		new ImageComposite(composite_2, SWT.NONE, "data/temp/weekly.gif",
				ImageComposite.SCALED);

		final TabItem TabI4 = new TabItem(tab, SWT.NONE);
		TabI4.setText("月K");

		Composite composite_3 = new Composite(tab, SWT.NONE);
		TabI4.setControl(composite_3);
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));
		new ImageComposite(composite_3, SWT.NONE, "data/temp/monthly.gif",
				ImageComposite.SCALED);
	}


	//股票交易记录
	public void recordDetail(Composite composite) {
		
//		HoldStockDetails rd = new HoldStockDetails(composite, SWT.NONE);
		RecordDetails rdHead = new RecordDetails(composite, SWT.NONE);
		rdHead.setBounds(10, 0, 340, 30);

		int len = recordStrArr.length;
		System.out.println(len);
		len = len > 5 ? 5 : len;
		
		for(int i = 0; i < len; ++i){
			RecordDetails rd = new RecordDetails(composite, SWT.NONE);
			rd.setBounds(10, 30 + 30 * i, 340, 30);
			
			for(int j = 0; j < recordStrArr[i].length; ++j){
				Label lbl = rd.getLabel(j);
				lbl.setForeground(
						SWTResourceManager.getColor(SWT.COLOR_BLACK));
				lbl.setText(recordStrArr[i][j]);
			}
			Label lbl = rd.getLabel(4);
			lbl.setVisible(false);
			
			Label lblChange = rd.getChange();
			lblChange.setVisible(true);
			Image changeIcon = new Image(Display.getDefault(),
					"icon/change.png");
			lblChange.setImage(changeIcon);
			
			Label lblDelete = rd.getDelete();
			lblDelete.setVisible(true);
			Image deleteIcon = new Image(Display.getDefault(),
					"icon/delete.png");
			lblDelete.setImage(deleteIcon);
		}
		
	}


	//查看全部
	public void seeAll(Composite composite) {
//		Button allBtn = new Button(composite, SWT.PUSH);
//		allBtn.setBounds(181, 0, 182, 211);
//		allBtn.setText("查看全部");
//		allBtn.setBounds(303, 181, 60, 30);
//		allBtn.setVisible(true);
		
		Label lblAll = new Label(composite, SWT.CENTER );
		lblAll.setTouchEnabled(true);
		lblAll.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.BOLD));
		lblAll.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblAll.setText("查看全部");
		lblAll.setBounds(303, 200, 60, 23);
		lblAll.setVisible(true);
//		
//		allBtn.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				try {
//					DlgStockHistory window = new DlgStockHistory(shell);
//					window.open(stockName);
//				} catch (Exception er) {
//					er.printStackTrace();
//				}
//			}
//		});
	}


	//添加交易记录
	public void addRecord(Composite composite) {
//		recordComp.setLayout(null);
//		Button gainBtn = new Button(composite, SWT.CENTER);
//		gainBtn.setTouchEnabled(true);
//		gainBtn.setBounds(0, 0, 60, 20);
//		gainBtn.setText("添加交易");
//		gainBtn.setBounds(237, 181, 60, 30);
//		gainBtn.setVisible(true);
//
//		// “添加记录”按钮的点击事件
//		gainBtn.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				try {
//					System.out.println("sssss");
//					DlgStockSituation window = new DlgStockSituation(shell);
//					window.open("添加记录", stockName);
//				} catch (Exception er) {
//					er.printStackTrace();
//				}
//			}
//		});
		
		Label lblAdd = new Label(composite, SWT.CENTER );
		lblAdd.setTouchEnabled(true);
		lblAdd.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.BOLD));
		lblAdd.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblAdd.setText("添加记录");
		lblAdd.setBounds(227, 200, 60, 23);
		lblAdd.setVisible(true);
	}

	private void setStockName(String stockN) {
		// TODO Auto-generated method stub
		stockName = stockN;
	}
}
