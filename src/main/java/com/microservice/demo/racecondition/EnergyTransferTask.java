package com.microservice.demo.racecondition;

public class EnergyTransferTask implements Runnable {

    //共享的能量世界
    private EnergySystem energySystem;
    //能量转移的源能量盒子下标
    private int fromBox;
    //单次能量转移的最大单元
    private double maxAmount;
    //最大休眠的时间（毫秒）
    private int DELAY = 10;

    public EnergyTransferTask(EnergySystem energySystem, int from, double max) {
        this.energySystem = energySystem;
        this.fromBox = from;
        this.maxAmount = max;
    }

    @Override
    public void run() {
        //获取目标能源编号
        int toBox = (int) (energySystem.getBoxAmount() * Math.random());
        double amount = maxAmount * Math.random();
        try {
            energySystem.transfer(fromBox, toBox, amount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep((int)(DELAY * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
