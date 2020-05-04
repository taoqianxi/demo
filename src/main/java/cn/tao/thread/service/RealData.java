package cn.tao.thread.service;

public class RealData {

    private final String content;

    public RealData(int count, char c) {

        System.out.println("making RealData(" + count + ", " + c + ") BEGIN");

        char[] buffer = new char[count];

        synchronized (this){
            for (int i = 0; i < count; i++) {

                buffer[i] = c;
                try {
                    System.out.println("RealData.RealData" + Thread.currentThread().getName());
                    Thread.sleep(1000);

                } catch (InterruptedException e) {

                }
            }
        }

        System.out.println("making RealData(" + count + ", " + c + ") END");

        this.content = new String(buffer);

    }

    public String getContent() {

        return content;

    }
}
