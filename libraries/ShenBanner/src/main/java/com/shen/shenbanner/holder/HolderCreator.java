package com.shen.shenbanner.holder;

/**
 *
 */
public interface HolderCreator<VH extends ShenViewHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}
