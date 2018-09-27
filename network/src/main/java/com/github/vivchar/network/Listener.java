package com.github.vivchar.network;

import androidx.annotation.NonNull;

/**
 * Created by Vivchar Vitaly on 09.10.17.
 */

public interface Listener <T> {
	void onChange(@NonNull T data);
}
