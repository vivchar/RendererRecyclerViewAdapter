package com.github.vivchar.network;

import android.support.annotation.NonNull;

/**
 * Created by Vivchar Vitaly on 09.10.17.
 */

public interface Listener <T> {
	void onChange(@NonNull T data);
}
