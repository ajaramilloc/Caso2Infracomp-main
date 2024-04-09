public class BitRManagerT extends Thread{
    public void run(){
        while (true) {
            try {
                Calculator.resetBitR();
                Thread.sleep(1);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
