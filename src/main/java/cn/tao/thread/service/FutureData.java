package cn.tao.thread.service;

public class FutureData {
    private RealData realdata = null;

    private boolean ready = false;

    public synchronized void setRealData(RealData realdata) {

        if (ready) {

            return;   // 防止setRealData被调用两次以上。

        }

        this.realdata = realdata;

        this.ready = true;

        notifyAll();

    }

    public synchronized String getContent() {

        while (!ready) {

            try {

                wait();

            } catch (InterruptedException e) {

            }
            System.out.println("FutureData.getContent" + Thread.currentThread().getName());
        }

        return realdata.getContent();

    }
}
