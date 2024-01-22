/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
public class ShuffleTimer {
//declare tk and t
Toolkit tk;
Timer t;
public int beep = 30;
//constructor of CountDownTimerExample class
public ShuffleTimer() {
tk = Toolkit.getDefaultToolkit();
t = new Timer();
//initial delay and subsequent rate
t.schedule(new rt(), 0, 1*1000);
}

class rt extends TimerTask {
//declare a variable beep

//task to be performed
public void run() {
//if BEEP VARIABLE IS GREATER THAN ZERO
if (beep > 0) {
//perform beep operation and print after each second
tk.beep();
System.out.println("One second over . . . Beep!");
//decrement the value beep
beep--;
}
//if beep variable is less than zero
else {
tk.beep();
System.out.println("The Time's over. . .!");
//AWT thread stops
System.exit(0);
}
}
}
//public static void main(String args[]) {
//System.out.println("Task is going to start. . .");
//new CountDownTimerExample();
//System.out.println("Task that is set up is scheduled. . .");
//
//}
}

