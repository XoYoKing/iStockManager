package ui;

import java.awt.MenuBar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class MyMenu{
	
	private Shell shell;
	private Menu menu;
	//private MenuBar menuBar;
	private MenuItem menuItem_file;
	private Menu menu_file;
	private MenuItem menuItem_file_import;
	private MenuItem menuItem_file_outport;
	private MenuItem menuItem_file_clean;
	private MenuItem menuItem_file_exit;
	private MenuItem menuItem_set;
	private Menu menu_set;
	//private Menu fileMenu;
	//private MenuItem menuItem_set;
	private MenuItem menuItem_about;
	private Menu menu_about;
	
	MyMenu(Shell parentShell){
		this.shell = parentShell;
		menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		addMenu();
	}
	
	private void addMenu(){
		menuItem_file = new MenuItem(menu, SWT.CASCADE);
		menuItem_file.setText("文件");
		
		menu_file = new Menu(menuItem_file);
		menuItem_file.setMenu(menu_file);
		
		menuItem_file_import = new MenuItem(menu_file, SWT.NONE);
		menuItem_file_import.setText("导入");
		
		menuItem_file_outport = new MenuItem(menu_file, SWT.NONE);
		menuItem_file_outport.setText("导出");
		
		menuItem_file_clean = new MenuItem(menu_file, SWT.NONE);
		menuItem_file_clean.setText("清除历史");
		
		menuItem_file_exit = new MenuItem(menu_file, SWT.NONE);
		menuItem_file_exit.setText("退出");
		
		menuItem_set = new MenuItem(menu, SWT.CASCADE);
		menuItem_set.setText("设置");
		
		menu_set = new Menu(menuItem_set);
		menuItem_set.setMenu(menu_set);
		
		menuItem_about = new MenuItem(menu, SWT.CASCADE);
		menuItem_about.setText("关于");
		
		menu_about = new Menu(menuItem_about);
		menuItem_about.setMenu(menu_about);
		
	}
	//Menu menu = new Menu(shell, SWT.BAR);
	
	
	
}
