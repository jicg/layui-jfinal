package com.jicg.common.vo;

import com.jicg.common.model.Mean;

import java.util.List;

/**
 * Created by jicg on 2018/4/7.
 */
public class SideMean {
    private Mean mean;

    private List<Mean> children;

    public SideMean(Mean mean) {
        this.mean = mean;
    }

    public SideMean(Mean mean, List<Mean> children) {
        this.mean = mean;
        this.children = children;
    }

    public Mean getMean() {
        return mean;
    }

    public void setMean(Mean mean) {
        this.mean = mean;
    }

    public List<Mean> getChildren() {
        return children;
    }

    public void setChildren(List<Mean> children) {
        this.children = children;
    }
}
