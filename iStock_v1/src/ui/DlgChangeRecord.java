package ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.json.JSONException;
import org.json.JSONObject;

import controller.GetSingleStock;
import controller.StockMath;

public class DlgChangeRecord extends Dialog {
	private static final SimpleDateFormat NDF = 
			new SimpleDateFormat("yyyy/MM/dd");
	private static final SimpleDateFormat DF = 
			new SimpleDateFormat("yy-MM-dd");
	
	public static final String[] KEYS = new String[]{
		"name","code","date","type", "price", 
		"volumes", "taxes", "commission", 
		"state", "remark", "handle", };
	
	protected Object result;
	
	private Composite composite;
	private Label lblName;
	
	private Shell parentShell;
	protected Shell shell;
	
	private Label lblCurPrice; //当前价
	private DateTime date; //交易日期
	private Combo cbType; //交易类型，买入，卖出等
	private Text state; //说明
	private Text volumes; //交易数量
	private Text taxes; //税率
	private Text price; //交易价格
	private Text commission; //佣金
	private Text remark; //备注
	
	private Button btnOk; //确定按钮
	private Button btnCancel; //取消按钮
	
//	private String[] stockInfo;
	
	private JSONObject joStockInfo;  //修改或添加的股票
	
	private JSONObject intimeStockInfo; //修改或者添加的股票的即时信息
	
	private String stockName; //股票名字
	private String code; //股票代码
	private String curPrice; //股票当前价格
	private String operateStr;//操作类型，添加或者修改

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DlgChangeRecord(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
		this.operateStr = "";
		this.stockName = "股票名字";
		
		this.parentShell = parent;
		shell = new Shell(parentShell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	
		createContents();
	}
	
	//修改股票记录调用的构造函数
	public DlgChangeRecord(Shell parent, JSONObject jo, String code){
		super(parent, SWT.CLOSE | SWT.MIN);
		
//		this.parentShell = parent;
//		shell = new Shell(parentShell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		
		this.joStockInfo = jo;
		this.intimeStockInfo = getIntimeStockInfo(code);
		try {
			this.stockName = jo.getString("name");
			this.curPrice = intimeStockInfo.getString("currentPrice");
			this.code = code;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setText("修改股票交易记录信息");
//		this.operateStr = "修改股票交易记录信息";
		
		
	}
	
	//添加股票交易记录调用的构造函数
	public DlgChangeRecord(Shell parent,  String code){
		super(parent, SWT.CLOSE | SWT.MIN);
		
//		this.parentShell = parent;
//		shell = new Shell(parentShell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
//		System.out.println("ssssss  code   " + code);
		joStockInfo = null;
		this.intimeStockInfo = getIntimeStockInfo(code);
		System.out.println(intimeStockInfo.toString());
//		this.stockName = stockName;
		this.code = code;
		try {
			this.curPrice = intimeStockInfo.getString("currentPrice");
			System.out.println("curprice      " + curPrice);
			this.stockName = intimeStockInfo.getString("name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		setText("添加股票交易记录");
//		this.operateStr = "添加股票交易记录";
		
		
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
//		createContents();
		
//		shell.addListener(SWT.CLOSE, new Listener(){
//
//			@Override
//			public void handleEvent(Event arg0) {
//				// TODO Auto-generated method stub
//				shell.dispose();
//				return ;
//			}
//			
//		});
		
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(561, 573);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		composite = new Composite(shell, SWT.NONE);
		
		lblName = new Label(composite, SWT.NONE);
		lblName.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.BOLD));
		lblName.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lblName.setBounds(37, 10, 236, 32);
		lblName.setText(stockName);
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_1.setBounds(37, 54, 74, 24);
		label_1.setText("当 前 价：");
		
		lblCurPrice = new Label(composite, SWT.NONE);
		lblCurPrice.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblCurPrice.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		lblCurPrice.setBounds(129, 54, 61, 24);
		lblCurPrice.setText("27.89");
		
		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setText("日       期：");
		label_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_2.setBounds(37, 97, 74, 24);
		
		date = new DateTime(composite, SWT.BORDER);
		date.setBounds(129, 97, 99, 24);
		
		Label label_6 = new Label(composite, SWT.NONE);
		label_6.setText("说      明：");
		label_6.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_6.setBounds(37, 297, 74, 24);
		
		state = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		state.setBounds(128, 293, 370, 68);
		
		Label label_7 = new Label(composite, SWT.NONE);
		label_7.setText("类      型：");
		label_7.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_7.setBounds(307, 99, 74, 24);
		
		cbType = new Combo(composite, SWT.READ_ONLY);
		cbType.setVisible(true);
		cbType.setItems(new String[] {"买入", "卖出", "补仓", "卖空"});
		cbType.setBounds(398, 98, 100, 25);
		
		volumes = new Text(composite, SWT.BORDER);
		volumes.setText("0");
		volumes.setBounds(398, 160, 100, 24);
		
		Label label_8 = new Label(composite, SWT.NONE);
		label_8.setText("数      量：");
		label_8.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_8.setBounds(307, 165, 74, 24);
		
		Label label_4 = new Label(composite, SWT.NONE);
		label_4.setText("税率(‰)：");
		label_4.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_4.setBounds(307, 230, 74, 24);
		
		taxes = new Text(composite, SWT.BORDER);
		taxes.setText("0.00");
		taxes.setBounds(398, 226, 100, 24);
		
		Label label_3 = new Label(composite, SWT.NONE);
		label_3.setText("价 格(元)：");
		label_3.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_3.setBounds(37, 165, 74, 24);
		
		price = new Text(composite, SWT.BORDER);
		price.setText("0.00");
		price.setBounds(129, 160, 100, 24);
		
		commission = new Text(composite, SWT.BORDER);
		commission.setText("0.00");
		commission.setBounds(129, 226, 100, 24);
		
		Label label_5 = new Label(composite, SWT.NONE);
		label_5.setText("佣金(‰)：");
		label_5.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_5.setBounds(36, 230, 74, 24);
		
		Label label_9 = new Label(composite, SWT.NONE);
		label_9.setText("备      注：");
		label_9.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_9.setBounds(37, 385, 74, 24);
		
		remark = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		remark.setBounds(128, 381, 370, 68);
		
		btnOk = new Button(composite, SWT.NONE);
		btnOk.setBounds(161, 485, 80, 27);
		btnOk.setText("确定");
		btnOk.addSelectionListener(new OkListener());
		
		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setBounds(330, 485, 80, 27);
		btnCancel.setText("取消");
		btnCancel.addSelectionListener(new CancelListener());

	}
	
	public JSONObject getIntimeStockInfo(String code){
		GetSingleStock gifs = new GetSingleStock(code);
		Thread td = new Thread(gifs);
		td.start();
		try {
			td.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gifs.getJsonObj();
	}
	
	public void change() throws JSONException{
//		open();
		createContents();
//		btnOk.setText("确认修改");
		String dstr = joStockInfo.getString("date");
		dstr = "20" + dstr;
		Date d = new Date();
		try {
			d = DF.parse(dstr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lblCurPrice.setText(curPrice);
		String[] s = dstr.split("-");
		date.setDate(Integer.valueOf(s[0]), 
				Integer.valueOf(s[1])-1, Integer.valueOf(s[2]));
		price.setText(joStockInfo.getString("price"));
		volumes.setText(joStockInfo.getString("volumes"));
		taxes.setText(StockMath.doubleToMilli(
				Double.valueOf(joStockInfo.getString("taxes"))));
		commission.setText(StockMath.doubleToMilli(
				Double.valueOf(joStockInfo.getString("commission"))));
		state.setText(joStockInfo.getString("state"));
		remark.setText(joStockInfo.getString("remark"));
		cbType.select(3);//typeIndex(joStockInfo.getString("type")));
		
		open();
	}
	
	public void add(){
//		open();
		System.out.println("add  ");
		createContents();
		btnOk.setText("确认添加");
		lblCurPrice.setText(curPrice);
		
		open();
	}
	
	public int typeIndex(String type){
		if(type.equals("买入"))
			return 0;
		else if(type.equals("卖出"))
			return 1;
		else if(type.equals("补仓"))
			return 2;
		else if(type.equals("卖空"))
			return 3;
		else
			return -1;
	}
	
	public void messageBox(SelectionEvent e, String title, String message) {
		int style = SWT.APPLICATION_MODAL | SWT.YES;
		MessageBox messageBox = new MessageBox(shell, style);
		messageBox.setText(title);
		messageBox.setMessage(message);
		e.doit = messageBox.open() == SWT.YES;
		e.doit = messageBox.open() == SWT.NO;
	}
	
	//确定按钮监听事件
	class OkListener implements SelectionListener{
		private ArrayList<Object > list;//保存交易记录信息
		
		public OkListener(){
			list = new ArrayList<Object>();
		}
		ArrayList<Object> getStockStringArray(){
			return list;
		}
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
			String p = price.getText();
			String v = volumes.getText();
			String t = taxes.getText();
			String c = commission.getText();
			
			if(p.isEmpty()){
				messageBox(e, "提示", "价格不能为空，请重新填写！");
				return ;
			}
			else if(v.isEmpty()){
				messageBox(e, "提示", "数量不能为空，请重新填写！");
				return ;
			}
			else if(t.isEmpty()){
				messageBox(e, "提示", "税率不能为空，请重新填写！");
				return ;
			}
			else if(c.isEmpty()){
				messageBox(e, "提示", "佣金不能为空，请重新填写！");
				return ;
			}
			
			
			
			list = getData();
			
			shell.close();
			shell.dispose();
			
			str2json(list);
		}
	}
	
	//取消按钮监听
	class CancelListener implements SelectionListener{

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			shell.close();
			shell.dispose();
		}
		
	}
	
	public ArrayList<Object > getData(){
		String[] sa = new String[11];
		
		ArrayList<Object > list = new ArrayList<Object>();
		
		String s = date.getYear() + "-" + (Integer.valueOf(date.getMonth())+1) + "-" + date.getDay();
		s = s.substring(2);
		list.add(0, stockName);
		list.add(1, code);
		list.add(2, s);
		list.add(3, cbType.getText());
		list.add(4, StockMath.valueOf(price.getText()));
		list.add(5, Integer.valueOf(volumes.getText()));
		list.add(6, StockMath.milliToDouble(taxes.getText()));
		list.add(7, StockMath.milliToDouble(commission.getText()));
		list.add(8, state.getText());
		list.add(9, remark.getText());
		list.add(10, "删除 修改");

//		for(Object l : list){
//			System.out.println(l.getClass() + " ssss    " + l);
//		}
		return list;
	}
	
	public JSONObject str2json(ArrayList<Object> list){
		if(joStockInfo == null){
			joStockInfo = new JSONObject();
		}
		for(int i = 0; i < list.size(); ++i){
			try {
				joStockInfo.put(KEYS[i], list.get(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return joStockInfo;
	}
	
	public JSONObject getJoStockInfo() {
		return joStockInfo;
	}

	public static void main(String[] argv){
		DlgChangeRecord ds = new DlgChangeRecord(new Shell(Display.getDefault()), SWT.NONE);
		ds.createContents();
		ds.open();
	}
}
