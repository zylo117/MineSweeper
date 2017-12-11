package tool;  
import java.awt.BorderLayout;  
import java.awt.EventQueue;  
import javax.swing.JFrame;  
import javax.swing.JPanel;  
import javax.swing.border.EmptyBorder;  
import javax.swing.JLabel;  
import java.awt.Color;  
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;  
public class MouseTest extends JFrame {  
    private JPanel contentPane;  
    /** 
     * Launch the application. 
     */  
    public static void main(String[] args) {  
        EventQueue.invokeLater(new Runnable() {  
            public void run() {  
                try {  
                    MouseTest frame = new MouseTest();  
                    frame.setVisible(true);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        });  
    }  
    /** 
     * Create the frame. 
     */  
    public MouseTest() {  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setBounds(100, 100, 450, 300);  
        contentPane = new JPanel();  
  
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  
        setContentPane(contentPane);  
        contentPane.setLayout(null);  
        JLabel label = new JLabel("此处显示鼠标右键点击的坐标");  
        label.setBounds(5, 5, 424, 31);  
        label.setOpaque(true);//设置控件不透明  
        label.setBackground(Color.GREEN); //<span style="font-family:Verdana;">设置标间颜色</span>  
        contentPane.add(label);  
        contentPane.addMouseListener(new MouseAdapter() {  
            @Override  
            public void mouseClicked(MouseEvent e) {  
                if(e.getButton()==e.BUTTON1){  
                    int x=e.getX();  
                    int y=e.getY();  
                    String str="您点击的是左键，鼠标当前点击位置的坐标是(" + x + "," + y+")";  
                    label.setText(str);  
                }else if(e.getButton()==e.BUTTON2){  
                    int x=e.getX();  
                    int y=e.getY();  
                    String str="您点击的是滑轮，鼠标当前点击位置的坐标是(" + x + "," + y+")";  
                    label.setText(str);   
                }  
                else if(e.getButton()==e.BUTTON3){  
                    int x=e.getX();  
                    int y=e.getY();  
                    String str="您点击的是右键，鼠标当前点击位置的坐标是(" + x + "," + y+")";  
                    label.setText(str);       
                }  
            }  
        });  
    }  
}  