package com.github.vivchar.rendererrecyclerviewadapter;

import androidx.recyclerview.widget.DiffUtil;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */
public abstract class DiffCallback <BM extends ViewModel> extends DiffUtil.ItemCallback<BM> {}