package com.github.vivchar.rendererrecyclerviewadapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 21.10.17.
 */
public abstract class DiffCallback <BM extends ViewModel> extends DiffUtil.ItemCallback<BM> {}